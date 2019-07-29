package com.sutpc.security.app;

import com.sutpc.security.app.authentication.SysAuthenticationFailureHandler;
import com.sutpc.security.app.authentication.SysAuthenticationSuccessHandler;
import com.sutpc.security.core.KeyCloakFilter;
import com.sutpc.security.core.SmsCodeFilter;
import com.sutpc.security.core.authentication.keycloak.KeyCloakAuthenticationSecurityConfig;
import com.sutpc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.sutpc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * .
 *  配置资源服务器
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 16:27 2019/7/29 0029.
 * @Modified By:
 */
@Configuration
@EnableResourceServer
public class SysResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;
  @Autowired
  private SysAuthenticationSuccessHandler sysAuthenticationSuccessHandler;
  @Autowired
  private SysAuthenticationFailureHandler sysAuthenticationFailureHandler;
  @Autowired
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
  @Autowired
  private KeyCloakAuthenticationSecurityConfig keyCloakAuthenticationSecurityConfig;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    //短信验证码登陆配置
    SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
    smsCodeFilter.setSysAuthenticationFailureHandler(sysAuthenticationFailureHandler);
    smsCodeFilter.afterPropertiesSet();
    //keycloak登陆
    KeyCloakFilter keyCloakFilter=new KeyCloakFilter();
    keyCloakFilter.setSysAuthenticationFailureHandler(sysAuthenticationFailureHandler);
    keyCloakFilter.afterPropertiesSet();
    http
        //短信验证码拦截器
        .addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
        //keycloak拦截器
        .addFilterBefore(keyCloakFilter,UsernamePasswordAuthenticationFilter.class)
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
        .apply(smsCodeAuthenticationSecurityConfig)
        .and().apply(keyCloakAuthenticationSecurityConfig);
  }
}
