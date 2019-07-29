package com.sutpc.security.core.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by sx on 2019/7/27.
 */
@Component
public class SysUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("login username : {}",username);
        String encode = passwordEncoder.encode("123456");
        logger.info("login password : {}", encode);
        return new User(username,encode, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
