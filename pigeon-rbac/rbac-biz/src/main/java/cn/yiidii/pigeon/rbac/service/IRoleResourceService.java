package cn.yiidii.pigeon.rbac.service;


import cn.yiidii.pigeon.rbac.api.entity.RoleResource;
import cn.yiidii.pigeon.rbac.api.enumeration.ResourceType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 角色资源服务接口
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:02
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /**
     * 通过角色ID集合获取资源ID
     * @param roleIdCollection  角色ID集合
     * @param type              资源类型
     * @return
     */
    List<Long> getResourceByRids(Collection<Long> roleIdCollection, ResourceType type);

}
