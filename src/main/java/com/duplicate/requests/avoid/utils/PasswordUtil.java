package com.duplicate.requests.avoid.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    @Resource
    PasswordEncoder encoder;

    static PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initialize() {
        PasswordUtil.passwordEncoder = encoder;
    }

    public static String encoding(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean equals(String inputPw, String pw) {
        return passwordEncoder.matches(inputPw, pw);
    }

}
