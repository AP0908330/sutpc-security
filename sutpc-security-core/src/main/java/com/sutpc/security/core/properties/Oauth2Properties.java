package com.sutpc.security.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**.
 * OAuth2配置
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 10:05 2019/7/30 0030.
 * @Modified By:
 */
@Data
public class Oauth2Properties {

  /**
   * OAuth2 client配置
   */
  private Oauth2ClientProperties[]  clients;
  /**
   * jwt签名秘钥
   */
  private String jwtSigningKey = "sutpc-123456";
}
