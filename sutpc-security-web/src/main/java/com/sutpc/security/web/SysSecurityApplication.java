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
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
@SpringBootApplication(scanBasePackages = {"com.sutpc.security"},
    exclude = {DataSourceAutoConfiguration.class})
public class SysSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SysSecurityApplication.class, args);
  }

}
