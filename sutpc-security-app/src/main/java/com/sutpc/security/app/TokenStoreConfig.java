package com.sutpc.security.app;

import com.sutpc.security.app.jwt.JwtTokenEnhancer;
import com.sutpc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 10:32 2019/7/30 0030.
 * @Modified By:
 */
@Configuration
public class TokenStoreConfig {
  //redis token store

  @Configuration
  @ConditionalOnProperty(prefix = "sutpc.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
  public static class JwtTokenConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public TokenStore jwtTokenStore() {
      return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
      accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
      return accessTokenConverter;
    }

    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer(){
      return new JwtTokenEnhancer();
    }

  }

}
