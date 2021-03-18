package cn.yiidii.pigeon.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import cn.yiidii.pigeon.rbac.mapper.UserMapper;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import cn.yiidii.pigeon.rbac.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final IResourceService resourceService;
    private final DozerUtils dozerUtils;

    @Override
    public User getUserByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public User getUserByMobile(String mobile) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        return userMapper.getUserDTOByUsername(username);
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
        user.setStatus(120);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }

    @Override
    public IPage<UserVO> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), User::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, User::getName, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, User::getId, searchParam.getKeyword());

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
        Set<Resource> resourceSet = resourceService.getResourceByRids(roleIdSet);
        List<VueRouter> routerList = dozerUtils.mapList(resourceSet, VueRouter.class);
        routerList.sort(Comparator.comparing(TreeEntity::getSort));
        return TreeUtil.buildTree(routerList);
    }
}
