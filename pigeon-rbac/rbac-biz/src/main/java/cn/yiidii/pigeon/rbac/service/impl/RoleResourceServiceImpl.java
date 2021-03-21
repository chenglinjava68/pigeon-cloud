package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.RoleResource;
import cn.yiidii.pigeon.rbac.api.enumeration.ResourceType;
import cn.yiidii.pigeon.rbac.mapper.RoleResourceMapper;
import cn.yiidii.pigeon.rbac.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色资源业务实现
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:03
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Override
    public List<Long> getResourceByRids(Collection<Long> roleIdCollection, ResourceType type) {
        if (CollectionUtils.isEmpty(roleIdCollection)) {
            return new ArrayList<>();
        }
        List<Long> resourceIdList = this.lambdaQuery().eq(RoleResource::getType, type).in(RoleResource::getRoleId, roleIdCollection).list().stream().map(RoleResource::getResourceId).collect(Collectors.toList());
        return resourceIdList;
    }
}
