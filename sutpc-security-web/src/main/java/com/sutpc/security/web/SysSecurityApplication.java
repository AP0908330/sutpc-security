package com.sutpc.security.web;

import com.sutpc.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 20:07 2019/7/26 0026.
 * @Modified By:
 */
@SpringBootApplication(scanBasePackages = {"com.sutpc.security"})
@RestController
public class SysSecurityApplication {

  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private SecurityProperties securityProperties;

  public static void main(String[] args) {
    SpringApplication.run(SysSecurityApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello spring security.";
  }

  @GetMapping("/me")
  public Authentication me(Authentication authentication, HttpServletRequest request)
      throws UnsupportedEncodingException {

    String authorization = request.getHeader("Authorization");

    if (!StringUtils.isEmpty(authorization)) {
      authorization = authorization.substring(7);
      Claims claims = Jwts.parser()
          .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
          .parseClaimsJws(authorization).getBody();
      logger.info(claims.get("company", String.class));
    }

    return authentication;
  }

}
