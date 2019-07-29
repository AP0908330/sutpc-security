package com.sutpc.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 14:45 2019/7/29 0029.
 * @Modified By:
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
    UserDetails userDetails = userDetailsService
        .loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());

    if (null == userDetails) {
      throw new InternalAuthenticationServiceException("无法获取用户信息！");
    }

    SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(
        userDetails.getUsername(), userDetails.getAuthorities());
    authenticationTokenResult.setDetails(smsCodeAuthenticationToken.getDetails());
    return authenticationTokenResult;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return SmsCodeAuthenticationToken.class.isAssignableFrom(clazz);
  }

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(
      UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}
