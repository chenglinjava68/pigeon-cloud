package cn.yiidii.pigeon.rbac.service;


import cn.yiidii.pigeon.rbac.api.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色业务接口
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:46
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据uid获取角色集合
     * @param uid
     * @return
     */
    List<Role> getRoleListByUid(Long uid);

}
