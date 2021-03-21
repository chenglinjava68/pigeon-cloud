package cn.yiidii.pigeon.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.rbac.api.bo.ResourceBO;
import cn.yiidii.pigeon.rbac.api.bo.UserBO;
import cn.yiidii.pigeon.rbac.api.dto.MenuDTO;
import cn.yiidii.pigeon.rbac.api.dto.PermissionDTO;
import cn.yiidii.pigeon.rbac.api.dto.RoleDTO;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.*;
import cn.yiidii.pigeon.rbac.api.enumeration.ResourceType;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import cn.yiidii.pigeon.rbac.mapper.UserMapper;
import cn.yiidii.pigeon.rbac.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户接口实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:58
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;
    private final IRoleResourceService roleResourceService;
    private final IMenuService menuService;
    private final IPermissionService permissionService;
    private final DozerUtils dozerUtils;

    @Override
    public User getUserByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).ne(User::getStatus, Status.DELETED));
    }

    @Override
    public User getUserByMobile(String mobile) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        UserBO userBO = userMapper.getUserBOByUsername(username);
        if (Objects.isNull(userBO)) {
            throw new BizException("用户不存在");
        }
        List<RoleDTO> roleDTOList = dozerUtils.mapList(userBO.getRoles(), RoleDTO.class);
        List<ResourceBO> resources = userBO.getResources();

        List<Long> menuIdList = resources.stream().filter(resourceBO -> resourceBO.getType().equals(ResourceType.MENU)).map(ResourceBO::getResourceId).collect(Collectors.toList());
        List<Long> permissionIdList = resources.stream().filter(resourceBO -> resourceBO.getType().equals(ResourceType.PERM)).map(ResourceBO::getResourceId).collect(Collectors.toList());

        UserDTO userDTO = dozerUtils.map(userBO, UserDTO.class);
        userDTO.setRoles(roleDTOList);
        if (!CollectionUtils.isEmpty(menuIdList)) {
            userDTO.setMenus(dozerUtils.mapList(menuService.lambdaQuery().in(Menu::getId, menuIdList).list(), MenuDTO.class));
        } else {
            userDTO.setMenus(new ArrayList<>());
        }

        if (!CollectionUtils.isEmpty(permissionIdList)) {
            userDTO.setPermissions(dozerUtils.mapList(permissionService.lambdaQuery().in(Permission::getId, permissionIdList).list(), PermissionDTO.class));
        }else{
            userDTO.setPermissions(new ArrayList<>());
        }
        return userDTO;
    }

    @Override
    public User create(UserDTO userDTO) {
        String username = userDTO.getUsername();
        User userExist = this.getUserByUsername(username);
        if (Objects.nonNull(userExist)) {
            throw new BizException("用户已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setSalt("");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return this.getUserByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User create(UserForm userForm) {
        String username = userForm.getUsername();
        User userByUsername = this.getUserByUsername(username);
        if (Objects.nonNull(userByUsername)) {
            throw new BizException(StrUtil.format("用户[{}]已存在", username));
        }
        String mobile = userForm.getMobile();
        User userByMobile = this.getUserByMobile(mobile);
        if (Objects.nonNull(userByMobile)) {
            throw new BizException(StrUtil.format("手机号[{}]已存在", mobile));
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setSalt("");
        user.setStatus(Status.ENABLED);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(UserForm userForm) {
        Long uid = userForm.getId();
        // 用户校验
        User userExist = this.getById(uid);
        if (Objects.isNull(userExist)) {
            throw new BizException(StrUtil.format("用户(id={})不存在", uid));
        }
        // 手机号校验
        String mobile = userForm.getMobile();
        if (StringUtils.isNotBlank(mobile) && !StringUtils.equals(mobile, userExist.getMobile())) {
            boolean mobileExist = this.lambdaQuery().eq(User::getMobile, mobile).ne(User::getId, uid).count() > 0;
            if (mobileExist) {
                throw new BizException(StrUtil.format("手机号[{}]已存在", mobile));
            }
        }
        // 复制属性，更新
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.updateById(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        User userExist = this.getById(id);
        if (Objects.isNull(userExist)) {
            throw new BizException(StrUtil.format("用户(id={})不存在", id));
        }
        // 删除用户
        userExist.setStatus(Status.DELETED);
        boolean success = this.updateById(userExist);
        if (!success) {
            throw new BizException(StrUtil.format("刪除用户(id={})失败", id));
        }
        // 删除关联的角色
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
    }

    @Override
    public IPage<UserVO> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), User::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, User::getName, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, User::getId, searchParam.getKeyword())
                .in(User::getStatus, Status.ENABLED, Status.DISABLED);

        // 根据排序字段进行排序
        if (StringUtils.isNotBlank(searchParam.getOrderBy())) {
        }

        // 分页查询
        Page<User> page = new Page<>(searchParam.getCurrent(), searchParam.getSize());
        IPage<User> userPage = this.baseMapper.selectPage(page, queryWrapper);
        return userPage.convert(user -> BeanUtil.copyProperties(user, UserVO.class));
    }

    @Override
    public List<VueRouter> getRouter(Long uid) {
        Set<Long> roleIdSet = roleService.getRoleListByUid(uid).stream().map(Role::getId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIdSet)) {
            return new ArrayList<>();
        }
        List<Long> menuIdList = roleResourceService.getResourceByRids(roleIdSet, ResourceType.MENU);
        if (CollectionUtils.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }
        List<Menu> menuSet = menuService.lambdaQuery().in(Menu::getId, menuIdList).list();
        if (CollectionUtils.isEmpty(menuSet)) {
            return new ArrayList<>();
        }
        List<VueRouter> routerList = dozerUtils.mapList(menuSet, VueRouter.class);
        routerList.sort(Comparator.comparing(TreeEntity::getSort));
        return TreeUtil.buildTree(routerList);
    }
}
