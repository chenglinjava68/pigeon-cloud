package cn.yiidii.pigeon.auth.util;

import lombok.Data;

/**
 *
 * @author: YiiDii Wang
 * @create: 2021-02-16 12:48
 */
@Data
public abstract class SuperAuthType {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 回调地址
     */
    private String redirectUri;
}
