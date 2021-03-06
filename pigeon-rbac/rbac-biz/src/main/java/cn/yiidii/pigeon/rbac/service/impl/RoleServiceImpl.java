package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.entity.UserRole;
import cn.yiidii.pigeon.rbac.mapper.RoleResourceMapper;
import cn.yiidii.pigeon.rbac.service.IRoleResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import cn.yiidii.pigeon.rbac.mapper.RoleMapper;
import cn.yiidii.pigeon.rbac.service.IUserRoleService;
import cn.yiidii.pigeon.rbac.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    private final IRoleResourceService roleResourceService;

    @Override
    public List<Role> getRoleListByUid(Long uid) {
        Set<Integer> roleIdList = userRoleService.lambdaQuery().eq(UserRole::getUserId, uid).list().stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        return this.lambdaQuery().in(Role::getId, roleIdList).list();
    }
}
