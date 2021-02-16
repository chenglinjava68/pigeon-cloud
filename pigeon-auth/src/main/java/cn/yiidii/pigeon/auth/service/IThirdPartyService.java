package cn.yiidii.pigeon.auth.service;

import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import me.zhyd.oauth.model.AuthUser;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
public interface IThirdPartyService {

    /**
     * 保存用户
     * @param authUser
     * @return
     */
    UserDTO save(AuthUser authUser);

}
