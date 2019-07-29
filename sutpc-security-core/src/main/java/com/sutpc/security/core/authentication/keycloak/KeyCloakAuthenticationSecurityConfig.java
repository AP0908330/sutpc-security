package com.sutpc.security.core.authentication.keycloak;

import com.sutpc.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.sutpc.security.core.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 15:13 2019/7/29 0029.
 * @Modified By:
 */
@Component
public class KeyCloakAuthenticationSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  private AuthenticationSuccessHandler sysAuthenticationSuccessHandler;
  @Autowired
  private AuthenticationFailureHandler sysAuthenticationFailureHandler;

  @Autowired
  private UserDetailsService sysUserDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    //自定义filter
    KeyCloakAuthenticationFilter keyCloakAuthenticationFilter = new KeyCloakAuthenticationFilter();
    keyCloakAuthenticationFilter
        .setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
    keyCloakAuthenticationFilter.setAuthenticationSuccessHandler(sysAuthenticationSuccessHandler);
    keyCloakAuthenticationFilter.setAuthenticationFailureHandler(sysAuthenticationFailureHandler);
    //自定义provider
    KeyCloakAuthenticationProvider keyCloakAuthenticationProvider = new KeyCloakAuthenticationProvider();
    keyCloakAuthenticationProvider.setUserDetailsService(sysUserDetailsService);

    http.authenticationProvider(keyCloakAuthenticationProvider)
        .addFilterAfter(keyCloakAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

  }
}
