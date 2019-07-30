package com.sutpc.security.app.authentication;

import com.alibaba.fastjson.JSON;
import com.sutpc.security.core.properties.LoginType;
import com.sutpc.security.core.properties.SecurityProperties;
import com.sutpc.security.core.support.SimpleResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Created by sx on 2019/7/28.
 */
@Component
public class SysAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(JSON.toJSONString(new SimpleResponse(exception.getMessage())));

  }
}
