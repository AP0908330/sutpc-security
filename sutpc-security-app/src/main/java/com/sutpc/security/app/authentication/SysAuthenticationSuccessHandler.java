package com.sutpc.security.app.authentication;

import com.alibaba.fastjson.JSON;
import com.sutpc.security.core.properties.LoginType;
import com.sutpc.security.core.properties.SecurityProperties;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by sx on 2019/7/28.
 */
@Component
public class SysAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private SecurityProperties securityProperties;
  @Autowired
  private ClientDetailsService clientDetailsService;
  @Autowired
  private AuthorizationServerTokenServices authorizationServerTokenServices;

  private ObjectMapper objectMapper=new ObjectMapper();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    logger.info("登录成功");
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Basic ")) {
      //解析clientId和clientSecret
      String[] tokens = this.extractAndDecodeHeader(header, request);
      assert tokens.length == 2;
      String clientId = tokens[0];
      String clientSecret = tokens[1];
      //获取clientDetails
      ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
      if (null == clientDetails) {
        throw new UnapprovedClientAuthenticationException("clientId :" + clientId + ",配置信息不存在！");
      } else if (!StringUtils.pathEquals(clientDetails.getClientSecret(), clientSecret)) {
        throw new UnapprovedClientAuthenticationException(
            "clientId :" + clientId + ",clientSecret不匹配！");
      }
      //构建tokenRequest
      TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId,
          clientDetails.getScope(), "sutpc_type");
      //构建oAuth2Request
      OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
      //构建oAuth2Authentication
      OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,
          authentication);
      OAuth2AccessToken accessToken = authorizationServerTokenServices
          .createAccessToken(oAuth2Authentication);
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

  }


  private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
      throws IOException {
    byte[] base64Token = header.substring(6).getBytes("UTF-8");

    byte[] decoded;
    try {
      decoded = Base64.decode(base64Token);
    } catch (IllegalArgumentException var7) {
      throw new BadCredentialsException("Failed to decode basic authentication token");
    }

    String token = new String(decoded, "UTF-8");
    int delim = token.indexOf(":");
    if (delim == -1) {
      throw new BadCredentialsException("Invalid basic authentication token");
    } else {
      return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
  }
}
