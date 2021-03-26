package cn.yiidii.pigeon.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.dto.RoleDTO;
import cn.yiidii.pigeon.rbac.api.entity.*;
import cn.yiidii.pigeon.rbac.api.enumeration.ResourceType;
import cn.yiidii.pigeon.rbac.api.form.RoleForm;
import cn.yiidii.pigeon.rbac.api.form.RoleResourceForm;
import cn.yiidii.pigeon.rbac.api.form.RoleUserForm;
import cn.yiidii.pigeon.rbac.mapper.RoleMapper;
import cn.yiidii.pigeon.rbac.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色业务实现
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:48
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IUserRoleService userRoleService;
    private final IMenuService menuService;
    private final IPermissionService permissionService;
    private final IRoleResourceService roleResourceService;
    private final DozerUtils dozerUtils;

    @Override
    public List<Role> getRoleListByUid(Long uid) {
        Set<Long> roleIdList = userRoleService.lambdaQuery().eq(UserRole::getUserId, uid).list().stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }
        return this.lambdaQuery().in(Role::getId, roleIdList).list();
    }

    @Override
    public Role getRoleByCode(String code) {
        return this.lambdaQuery().eq(Role::getCode, code).ne(Role::getStatus, Status.DELETED).one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(RoleDTO roleDTO) {
        Role roleExist = this.lambdaQuery().eq(Role::getName, roleDTO.getName()).one();
        if (Objects.nonNull(roleExist)) {
            throw new BizException(StrUtil.format("角色已[{}]存在", roleDTO.getName()));
        }
        Role role = new Role();
        dozerUtils.map(roleDTO, role);
        Long currUid = SecurityUtils.getUser().getId();
        role.setCreateTime(LocalDateTime.now());
        role.setCreatedBy(currUid);
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdatedBy(currUid);
        return this.getBaseMapper().insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(RoleForm roleForm) {
        String code = roleForm.getCode();
        Role roleByCode = getRoleByCode(code);
        if (Objects.nonNull(roleByCode)) {
            throw new BizException(StrUtil.format("角色[{}]已存在", code));
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        Long uid = SecurityUtils.getUser().getId();
        role.setCreateTime(LocalDateTime.now());
        role.setCreatedBy(uid);
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdatedBy(uid);
        return this.baseMapper.insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(RoleForm roleForm) {
        Long id = roleForm.getId();
        // roleId有效性
        Role roleExist = this.getById(id);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", id));
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        Long uid = SecurityUtils.getUser().getId();
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdatedBy(uid);
        return this.updateById(role);
    }

    @Override
    public IPage<Role> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), Role::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, Role::getName, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, Role::getId, searchParam.getKeyword())
                .in(Role::getStatus, Status.ENABLED, Status.DISABLED);

        // 根据排序字段进行排序
        if (StringUtils.isNotBlank(searchParam.getOrderBy())) {
        }

        // 分页查询
        Page<Role> page = new Page<>(searchParam.getCurrent(), searchParam.getSize());
        Page<Role> rolePage = this.baseMapper.selectPage(page, queryWrapper);
        return rolePage;
    }

    @Override
    public List<Long> getRoleUserIdList(Long roleId) {
        // roleId有效性
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        List<Long> userIdList = userRoleService.lambdaQuery().eq(UserRole::getRoleId, roleId).list().stream().map(UserRole::getUserId).collect(Collectors.toList());
        return userIdList;
    }

    @Override
    public List<Menu> getRoleMenu(Long roleId) {
        // roleId有效性
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        List<Long> menuIdList = roleResourceService.getResourceByRids(Lists.newArrayList(roleId), ResourceType.MENU);
        if (CollectionUtils.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }
        List<Menu> menuList = menuService.lambdaQuery().in(Menu::getId, menuIdList).list();
        menuList.sort(Comparator.comparing(TreeEntity::getSort));
        return menuList;
    }

    @Override
    public List<Permission> getRolePermission(Long roleId){
        // roleId有效性
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        List<Long> permissionIdList = roleResourceService.getResourceByRids(Lists.newArrayList(roleId), ResourceType.PERM);
        if (CollectionUtils.isEmpty(permissionIdList)) {
            return new ArrayList<>();
        }
        List<Permission> permissionList = permissionService.lambdaQuery().in(Permission::getId, permissionIdList).list();
        return permissionList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUser(RoleUserForm roleUserForm) {
        // roleId有效性
        Long roleId = roleUserForm.getRoleId();
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        // 先删除角色下的用户
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId, roleId));
        // 绑定
        List<Long> userIdList = roleUserForm.getUserIdList();
        List<UserRole> userRoleList = userIdList.stream().map(uid -> UserRole.builder()
                .roleId(roleId)
                .userId(uid)
                .createTime(LocalDateTime.now())
                .createdBy(SecurityUtils.getUser().getId())
                .build()).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindResource(RoleResourceForm roleResourceForm) {
        Long roleId = roleResourceForm.getRoleId();
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        // 先删除角色下的菜单和权限
        roleResourceService.remove(Wrappers.<RoleResource>lambdaQuery().eq(RoleResource::getRoleId, roleId));
        // 绑定菜单
        List<Long> resourceIdList = roleResourceForm.getMenuIdList();
        List<RoleResource> roleResourceList = resourceIdList.stream().map(resId -> RoleResource.builder()
                .roleId(roleId)
                .resourceId(resId)
                .type(ResourceType.MENU)
                .createTime(LocalDateTime.now())
                .createdBy(SecurityUtils.getUser().getId())
                .build()).collect(Collectors.toList());
        roleResourceService.saveBatch(roleResourceList);

        // 绑定权限
        List<Long> permissionIdList = roleResourceForm.getPermissionIdList();
        roleResourceList = permissionIdList.stream().map(resId -> RoleResource.builder()
                .roleId(roleId)
                .resourceId(resId)
                .type(ResourceType.PERM)
                .createTime(LocalDateTime.now())
                .createdBy(SecurityUtils.getUser().getId())
                .build()).collect(Collectors.toList());
        roleResourceService.saveBatch(roleResourceList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delRole(List<Long> roleIdList) {
        List<Role> delRoleList = roleIdList.stream().map(rid -> Role.builder()
                .id(rid)
                .status(Status.DELETED)
                .updateTime(LocalDateTime.now())
                .updatedBy(SecurityUtils.getUser().getId())
                .build())
                .collect(Collectors.toList());
        boolean isDelRoleSuccess = this.updateBatchById(delRoleList);
        roleResourceService.remove(Wrappers.<RoleResource>lambdaQuery().in(RoleResource::getRoleId, roleIdList));
        return isDelRoleSuccess;
    }

}
