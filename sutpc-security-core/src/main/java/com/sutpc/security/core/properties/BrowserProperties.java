package com.sutpc.security.core.properties;

import lombok.Data;

/**
 * Created by sx on 2019/7/27.
 */
@Data
public class BrowserProperties {
    /**
     * 自定义登录页面配置，默认为/sutpc-login.html
     */
    private String loginPage = "/sutpc-login.html";
    /**
     * 登录类型配置
     */
    private LoginType loginType = LoginType.JSON;
}
