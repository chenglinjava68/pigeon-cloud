package cn.yiidii.pigeon.auth.endpoint;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xnio.Result;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-13 14:48
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {


    @Autowired
    @Lazy
    private TokenStore tokenStore;

    @Autowired
    private TokenEndpoint tokenEndpoint;


    /**
     * 重写login接口
     *
     * @param principal
     * @param parameters
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @PostMapping("/token")
    public R<Map<String, Object>> postAccessToken(Principal principal,
                                                  @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken;
        accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Map<String, Object> resultMap = Maps.newLinkedHashMap();
        // token信息
        resultMap.put("access_token", accessToken.getValue());
        resultMap.put("token_type", accessToken.getTokenType());
        resultMap.put("expires_in", accessToken.getExpiresIn());
        resultMap.put("scope", StringUtils.join(accessToken.getScope(), ","));
        resultMap.putAll(accessToken.getAdditionalInformation());
        return R.ok(resultMap);
    }


}
