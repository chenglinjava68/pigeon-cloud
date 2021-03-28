package cn.yiidii.pigeon.auth.service;

import me.zhyd.oauth.model.AuthUser;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
public interface IThirdPartyService {

    /**
     * 处理用户，根据第三方的数据生成平台用户
     * @param authUser
     * @return
     */
    void handle(AuthUser authUser);

}
