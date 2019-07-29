package com.sutpc.security.browser;

import com.sutpc.security.browser.authentication.SysAuthenticationFailureHandler;
import com.sutpc.security.browser.authentication.SysAuthenticationSuccessHandler;
import com.sutpc.security.core.SmsCodeFilter;
import com.sutpc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.sutpc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by sx on 2019/7/27.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SysAuthenticationSuccessHandler sysAuthenticationSuccessHandler;
    @Autowired
    private SysAuthenticationFailureHandler sysAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
        smsCodeFilter.setSysAuthenticationFailureHandler(sysAuthenticationFailureHandler);
        smsCodeFilter.afterPropertiesSet();
        http
                //短信验证码拦截器
                .addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
                //配置表单登录
                .formLogin()
                //配置http basic登录,为默认配置
                //.httpBasic()
                //自定义登录页面
                .loginPage("/authentication/require")
                //自定义登录处理请求，使用UsernamePasswordAuthenticationFilter处理
                .loginProcessingUrl("/authentication/form")
                //自定义成功处理器
                .successHandler(sysAuthenticationSuccessHandler)
                //自定义失败处理器
                .failureHandler(sysAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                //放行部分页面
                .antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //禁止csrf跨站攻击防护
                .csrf().disable()
                //自定义短信验证码校验逻辑配置
                .apply(smsCodeAuthenticationSecurityConfig);
    }


}
