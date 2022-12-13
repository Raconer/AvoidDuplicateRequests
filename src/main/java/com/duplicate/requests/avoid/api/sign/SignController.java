package com.duplicate.requests.avoid.api.sign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.api.service.SignService;
import com.duplicate.requests.avoid.common.model.DefResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/sign")
public class SignController {

    SignService signService;

    /* 회원 가입 */
    @PostMapping("/up")
    public ResponseEntity<?> signUp() {
        int count = signService.getCount();

        return ResponseEntity.ok(new DefResponse(HttpStatus.OK));
    }

    /* 로그인 */
    @PostMapping
    public void signIn() {

    }

}
