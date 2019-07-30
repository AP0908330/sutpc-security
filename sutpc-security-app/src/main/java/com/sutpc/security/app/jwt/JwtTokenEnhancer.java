package com.sutpc.security.app.jwt;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 11:06 2019/7/30 0030.
 * @Modified By:
 */
public class JwtTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
      OAuth2Authentication oAuth2Authentication) {
    Map<String,Object> map=new HashMap<>();
    map.put("company","sutpc");
    ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(map);
    return oAuth2AccessToken;
  }
}
