package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.entity.RoleResource;
import cn.yiidii.pigeon.rbac.mapper.ResourceMapper;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源服务实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:01
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    private final IRoleService roleService;
    private final IRoleResourceService roleResourceService;


    @Override
    public Set<Resource> getResourceByUid(Long uid) {
        Set<Long> roleIdSet = roleService.getRoleListByUid(uid).stream().map(Role::getId).collect(Collectors.toSet());
        return this.getResourceByRids(roleIdSet);
    }

    @Override
    public Set<Resource> getResourceByRids(Collection<Long> roleIdCollection) {
        Set<Long> resourceIdList = roleResourceService.lambdaQuery().in(RoleResource::getRoleId, roleIdCollection).list().stream().map(RoleResource::getResourceId).collect(Collectors.toSet());
        List<Resource> resourceList = this.lambdaQuery().in(Resource::getId, resourceIdList).list();
        Set<Resource> resourceSet = resourceList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(r -> r.getId()))), HashSet::new));
        return resourceSet;
    }

}
