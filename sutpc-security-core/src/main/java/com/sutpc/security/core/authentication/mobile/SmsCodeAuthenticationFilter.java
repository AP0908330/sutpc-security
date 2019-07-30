package com.sutpc.security.core.authentication.mobile;

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
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public static final String SUTPC_SECURITY_FORM_MOBILE_KEY = "mobile";
  private String mobileParameter = "mobile";
  private boolean postOnly = true;

  public SmsCodeAuthenticationFilter() {
    super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    if (this.postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    } else {
      String mobile = this.obtainMobile(request);
      if (mobile == null) {
        mobile = "";
      }
      mobile = mobile.trim();
      SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
      this.setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
  }

  /**
   * 获取手机号码
   * @param request
   * @return
   */
  protected String obtainMobile(HttpServletRequest request) {
    return request.getParameter(this.mobileParameter);
  }

  protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }

  public void setMobileParameter(String mobileParameter) {
    Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
    this.mobileParameter = mobileParameter;
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public final String getMobileParameter() {
    return this.mobileParameter;
  }


}
