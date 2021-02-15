package cn.yiidii.pigeon.auth.endpoint;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.*;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-13 14:48
 */
@RestController
@RequestMapping("/oauth")
@Api(tags = {"token接口"})
public class OauthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;


    /**
     * 重写token接口
     *
     * @param principal
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @PostMapping("/token")
    @ApiOperation("获取token接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "客户端ID", required = true, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "client_secret", value = "客户端密钥", required = true, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "grant_type", value = "授权类型", required = true, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "code", value = "授权码", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "redirect_uri", value = "回调地址", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "password", value = "密码", required = false, paramType = "query", dataType = "String", defaultValue = "")
    })
    public R<OAuth2AccessToken> postAccessToken(Principal principal,
                                                  @RequestParam String client_id,
                                                  @RequestParam String client_secret,
                                                  @RequestParam String grant_type,
                                                  @RequestParam(required = false) String code,
                                                  @RequestParam(required = false) String redirect_uri,
                                                  @RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String password) throws HttpRequestMethodNotSupportedException {
        Map<String, String> params = new HashMap<>(16);
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", grant_type);
        params.put("code", code);
        params.put("redirect_uri", redirect_uri);
        params.put("username", username);
        params.put("password", password);
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
        return R.ok(accessToken);
    }


}
