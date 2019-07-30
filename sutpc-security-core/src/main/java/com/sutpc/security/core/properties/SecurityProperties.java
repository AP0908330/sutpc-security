package com.sutpc.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by sx on 2019/7/27.
 */
@Data
@ConfigurationProperties(prefix = "sutpc.security")
public class SecurityProperties {

  private BrowserProperties browser = new BrowserProperties();
  /**
   * OAuth2配置
   */
  private Oauth2Properties oauth2 = new Oauth2Properties();
}
