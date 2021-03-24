package cn.yiidii.pigeon.auth.service;

import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
public interface IThirdPartyService {

    /**
     * 处理登录逻辑<br />
     * 用户不存在，则新增用户，再登陆；用户存在，直接登录，返回token
     * @param authUser
     * @return
     */
    OAuth2AccessToken handle(AuthUser authUser);

    /**
     * 保存用户
     * @param authUser
     * @return
     */
    UserDTO save(AuthUser authUser);

}
