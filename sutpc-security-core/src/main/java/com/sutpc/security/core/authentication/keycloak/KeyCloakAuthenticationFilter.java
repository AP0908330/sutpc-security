package com.sutpc.security.core.authentication.keycloak;

import com.sutpc.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 14:36 2019/7/29 0029.
 * @Modified By:
 */
public class KeyCloakAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public static final String SUTPC_SECURITY_FORM_MOBILE_KEY = "username";
  private String usernameParameter = "username";
  private boolean postOnly = true;

  public KeyCloakAuthenticationFilter() {
    super(new AntPathRequestMatcher("/authentication/keycloak", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    if (this.postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    } else {
      String username = this.obtainUsername(request);
      if (username == null) {
        username = "";
      }
      username = username.trim();
      KeyCloakAuthenticationToken authRequest = new KeyCloakAuthenticationToken(username);
      this.setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
  }

  /**
   * 获取用户名
   * @param request
   * @return
   */
  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter(this.usernameParameter);
  }

  protected void setDetails(HttpServletRequest request, KeyCloakAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }

  public void setMobileParameter(String usernameParameter) {
    Assert.hasText(usernameParameter, "username parameter must not be empty or null");
    this.usernameParameter = usernameParameter;
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public final String getUsernameParameter() {
    return this.usernameParameter;
  }


}
