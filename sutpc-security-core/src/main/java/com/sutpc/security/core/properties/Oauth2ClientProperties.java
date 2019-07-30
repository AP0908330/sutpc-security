package com.sutpc.security.core.properties;

import lombok.Data;

/** .
 * OAuth2 client属性配置
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 10:02 2019/7/30 0030.
 * @Modified By:
 */
@Data
public class Oauth2ClientProperties {
  private String clientId;
  private String clientSecret;
  private int accessTokenValiditySeconds;
}
