package com.sutpc.security.app;

import com.sutpc.security.app.jwt.JwtTokenEnhancer;
import com.sutpc.security.core.properties.Oauth2ClientProperties;
import com.sutpc.security.core.properties.SecurityProperties;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * . 配置认证服务器
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 15:44 2019/7/29 0029.
 * @Modified By:
 */
@Configuration
@EnableAuthorizationServer
public class SysAuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserDetailsService sysUserDetailsService;
  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private TokenStore tokenStore;

  @Autowired(required = false)
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired(required = false)
  private TokenEnhancer jwtTokenEnhancer;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * 配置 authenticationManager和sysUserDetailsService
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(sysUserDetailsService);
    if (null != tokenStore) {
      endpoints.tokenStore(tokenStore);
    }
    if (null != jwtAccessTokenConverter && null != jwtTokenEnhancer) {

      TokenEnhancerChain chain = new TokenEnhancerChain();

      List<TokenEnhancer> enhancers = new ArrayList<>();
      //jwt令牌增强器
      enhancers.add(jwtTokenEnhancer);
      enhancers.add(jwtAccessTokenConverter);
      chain.setTokenEnhancers(enhancers);
      endpoints.tokenEnhancer(chain)
          .accessTokenConverter(jwtAccessTokenConverter);
    }
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    InMemoryClientDetailsServiceBuilder builder = clients
        .inMemory();
    Oauth2ClientProperties[] clientProperties = securityProperties.getOauth2().getClients();
    if (ArrayUtils.isNotEmpty(clientProperties)) {
      for (Oauth2ClientProperties client : clientProperties) {
        builder
            .withClient(client.getClientId())
            .secret(passwordEncoder.encode(client.getClientSecret()))
            .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
            .authorizedGrantTypes("refresh_token", "password")
            .scopes("all", "read", "write");
      }
    }
  }
}
