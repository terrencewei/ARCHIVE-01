package com.terrencewei.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by terrence on 2018/05/23.
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (StringUtils.isNotBlank(rawPassword)) {
            return rawPassword.toString();
        }
        return null;
    }



    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(rawPassword) || StringUtils.isBlank(encodedPassword)) {
            return false;
        }
        return encodedPassword.equals(rawPassword.toString());
    }
}