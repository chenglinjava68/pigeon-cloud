package cn.yiidii.pigeon.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.dto.RoleDTO;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.entity.RoleResource;
import cn.yiidii.pigeon.rbac.api.entity.UserRole;
import cn.yiidii.pigeon.rbac.api.form.RoleForm;
import cn.yiidii.pigeon.rbac.api.form.RoleMenuForm;
import cn.yiidii.pigeon.rbac.api.form.RoleUserForm;
import cn.yiidii.pigeon.rbac.mapper.RoleMapper;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import cn.yiidii.pigeon.rbac.service.IUserRoleService;
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
    private final IResourceService resourceService;
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
        return this.lambdaQuery().eq(Role::getCode, code).one();
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
        return this.baseMapper.insert(role);
    }

    @Override
    public IPage<Role> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), Role::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, Role::getName, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, Role::getId, searchParam.getKeyword());

        // 根据排序字段进行排序
        if (StringUtils.isNotBlank(searchParam.getOrderBy())) {
        }

        // 分页查询
        Page<Role> page = new Page<>(searchParam.getCurrent(), searchParam.getSize());
        Page<Role> rolePage = this.baseMapper.selectPage(page, queryWrapper);
        return rolePage;
    }

    @Override
    public List<Resource> getRoleMenu(Long roleId) {
        // roleId有效性
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        Set<Resource> roleResourceSet = resourceService.getResourceByRids(Lists.newArrayList(roleId));
        List<Resource> roleResourceList = new ArrayList<>(roleResourceSet);
        roleResourceList.sort(Comparator.comparing(TreeEntity::getSort));
        return TreeUtil.buildTree(roleResourceList);
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
    public void bindMenu(RoleMenuForm roleMenuForm) {
        Long roleId = roleMenuForm.getRoleId();
        Role roleExist = this.getById(roleId);
        if (Objects.isNull(roleExist)) {
            throw new BizException(StrUtil.format("角色ID[{}]不存在", roleId));
        }
        // 先删除角色下的资源
        roleResourceService.remove(Wrappers.<RoleResource>lambdaQuery().eq(RoleResource::getRoleId, roleId));
        List<Long> resourceIdList = roleMenuForm.getMenuIdList();
        List<RoleResource> roleResourceList = resourceIdList.stream().map(resId -> RoleResource.builder()
                .roleId(roleId)
                .resourceId(resId)
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
                .updateTime(LocalDateTime.now())
                .updatedBy(SecurityUtils.getUser().getId())
                .status(30)
                .build())
                .collect(Collectors.toList());
        boolean isDelRoleSuccess = this.updateBatchById(delRoleList);
        boolean isDelRoleResSuccess = roleResourceService.remove(Wrappers.<RoleResource>lambdaQuery().in(RoleResource::getRoleId, roleIdList));
        return isDelRoleSuccess && isDelRoleResSuccess;
    }

}
