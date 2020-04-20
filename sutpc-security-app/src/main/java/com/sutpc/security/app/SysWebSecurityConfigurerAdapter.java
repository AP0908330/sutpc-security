package com.sutpc.security.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**.
 * @Author:wusx
 * @Date: Created in 14:55 2020/4/20 0020.
 * @Description .
 * @Modified By:
 */
@Configuration
public class SysWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  @Override
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
