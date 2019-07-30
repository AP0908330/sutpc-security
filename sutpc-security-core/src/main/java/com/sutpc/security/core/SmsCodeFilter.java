package com.sutpc.security.core;

import com.sutpc.security.core.authentication.exception.ValidateException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 14:54 2019/7/29 0029.
 * @Modified By:
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

  private AuthenticationFailureHandler sysAuthenticationFailureHandler;

  private Set<String> urls = new HashSet<>();

  private AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    urls.add("/authentication/mobile");
  }

  public AuthenticationFailureHandler getSysAuthenticationFailureHandler() {
    return sysAuthenticationFailureHandler;
  }

  public void setSysAuthenticationFailureHandler(
      AuthenticationFailureHandler sysAuthenticationFailureHandler) {
    this.sysAuthenticationFailureHandler = sysAuthenticationFailureHandler;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    boolean action = false;
    //匹配是否需要手机验证码校验
    for (String url : urls) {
      if (pathMatcher.match(url, request.getRequestURI())) {
        action = true;
      }
    }
    if (action) {
      try {
        validateCode(request);
      } catch (ValidateException e) {
        sysAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * 校验手机验证码
   * @param request
   */
  private void validateCode(HttpServletRequest request) {
    //校验手机验证码
    //TODO
  }
}
