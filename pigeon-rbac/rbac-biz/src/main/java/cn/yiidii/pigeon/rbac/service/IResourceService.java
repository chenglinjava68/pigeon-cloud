package cn.yiidii.pigeon.rbac.service;

import cn.yiidii.pigeon.rbac.api.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.Set;

/**
 * 资源服务接口
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:00
 */
public interface IResourceService extends IService<Menu> {

    /**
     * 通过角色id集合获取资源
     * @param roleIdCollection
     * @return
     */
    Set<Menu> getResourceByRids(Collection<Long> roleIdCollection);

}
