package cn.yiidii.pigeon.auth.config;

import cn.yiidii.pigeon.auth.granter.social.SocialAuthenticationProvider;
import cn.yiidii.pigeon.common.security.config.IgnoreUrlProperties;
import cn.yiidii.pigeon.common.security.service.PigeonUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-12 09:58
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;
    @Autowired
    private PigeonUserDetailsService pigeonUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> ignoreUrls = ignoreUrlProperties.getIgnoreUrls();
        // 登录失败处理handler，返回一段json
        http
                .authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()])).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pigeonUserDetailsService);
        // 配置Provider
        auth.authenticationProvider(socialAuthenticationProvider());
    }

    /**
     * 认证管理器
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private SocialAuthenticationProvider socialAuthenticationProvider(){
        SocialAuthenticationProvider provider = new SocialAuthenticationProvider();
        provider.setUserDetailsService(pigeonUserDetailsService);
        return provider;
    }
}
