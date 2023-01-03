package com.duplicate.requests.avoid.api.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.duplicate.requests.avoid.service.SignService;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location = classpath:application-default.yml" })
public class SignServiceTest {

    @Autowired
    SignService signService;

}
