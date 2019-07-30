package com.sutpc.security.core.authentication.keycloak;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 14:32 2019/7/29 0029.
 * @Modified By:
 */
public class KeyCloakAuthenticationToken extends AbstractAuthenticationToken {
  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
  private final Object username;

  public KeyCloakAuthenticationToken(Object username) {
    super((Collection)null);
    this.username = username;
    this.setAuthenticated(false);
  }

  public KeyCloakAuthenticationToken(Object username, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.username = username;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.username;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    } else {
      super.setAuthenticated(false);
    }
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
