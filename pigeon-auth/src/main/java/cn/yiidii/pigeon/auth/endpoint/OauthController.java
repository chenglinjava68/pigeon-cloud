package cn.yiidii.pigeon.auth.endpoint;

import cn.yiidii.pigeon.auth.service.IThirdPartyService;
import cn.yiidii.pigeon.auth.util.AuthRequestHelper;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.code.ExceptionCode;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-13 14:48
 */
@RestController
@RequestMapping("/oauth")
@Api(tags = {"oauth接口"})
@Slf4j
@RequiredArgsConstructor
public class OauthController {

    private final TokenEndpoint tokenEndpoint;
    private final AuthRequestHelper authRequestHelper;
    private final IThirdPartyService thirdPartyService;

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

    @GetMapping("/render/{source}")
    @ApiOperation("第三方登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "source", value = "授权来源", required = true, paramType = "path", dataType = "String", defaultValue = "")
    })
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = authRequestHelper.getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }


    /**
     * oauth平台中配置的授权回调地址
     */
    @RequestMapping("/callback/{source}")
    @ApiIgnore
    public R login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request) {
        AuthRequest authRequest = authRequestHelper.getAuthRequest(source);
        AuthResponse<AuthUser> response = authRequest.login(callback);

        if (response.ok()) {
            // TODO 不存在用户则新增并登录；存在用户直接登录返回token
            UserDTO save = thirdPartyService.save(response.getData());
            return R.ok(save);
        }
        return R.failed(ExceptionCode.THIRD_PARTY_LOGIN.getCode(), ExceptionCode.THIRD_PARTY_LOGIN.getMsg(), response.getMsg());
    }

}
