package com.sb.config.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * author: dyq
 * Time: 2023/3/19
 * description: 描述
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
