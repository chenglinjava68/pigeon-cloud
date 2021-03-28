package cn.yiidii.pigeon.auth.granter.social;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.auth.service.IThirdPartyService;
import cn.yiidii.pigeon.auth.util.AuthRequestHelper;
import cn.yiidii.pigeon.common.core.util.SpringContextHolder;
import cn.yiidii.pigeon.common.security.service.PigeonUserDetailsService;
import lombok.Setter;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 社交登录Provider，执行具体的认证逻辑AuthorizationServerConfigurerAdapter的granter责任链上
 *
 * @author: YiiDii Wang
 * @create: 2021-03-27 16:00
 */
@Setter
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private PigeonUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String source = authenticationToken.getSource();
        String code = authenticationToken.getCode();
        String state = authenticationToken.getState();

        AuthRequestHelper authRequestHelper = SpringContextHolder.getBean(AuthRequestHelper.class);
        IThirdPartyService thirdPartyService = SpringContextHolder.getBean(IThirdPartyService.class);

        // 构建请求，不支持则抛异常
        AuthRequest authRequest = authRequestHelper.getAuthRequest(source);
        // 登陆
        AuthCallback callback = AuthCallback.builder().code(code).state(state).build();
        AuthResponse<AuthUser> response = authRequest.login(callback);
        AuthUser authUser = response.getData();
        // 处理用户
        if (response.ok()) {
            thirdPartyService.handle(response.getData());
        } else {
            throw new InternalAuthenticationServiceException(StrUtil.format("社交[{}]登录失败", source));
        }

        // 暂时用loadUserByUsername，过后改成source + uuid的形式
        UserDetails user = userDetailsService.loadUserByUsername(authUser.getUsername());
        if (user == null) {
            throw new InternalAuthenticationServiceException(StrUtil.format("社交[{}]登录失败: {}", source, "添加用户失败"));
        }
        SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}