package com.sutpc.security.web.rest;

import com.sutpc.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**.
 * @Author:wusx
 * @Date: Created in 10:39 2020/4/21 0021.
 * @Description .
 * @Modified By:
 */
@RestController
@Slf4j
public class IndexController {

  @Autowired
  private SecurityProperties securityProperties;

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
      log.info(claims.get("company", String.class));
    }

    return authentication;
  }
}
