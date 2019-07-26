package com.sutpc.security.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
@SpringBootApplication
@RestController
public class SysSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SysSecurityApplication.class,args);
  }

  @GetMapping("/hello")
  public String hello(){
    return "hello spring security.";
  }

}
