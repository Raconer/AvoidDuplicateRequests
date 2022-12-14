package com.duplicate.requests.avoid.api.sign;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.api.sign.model.Sign;
import com.duplicate.requests.avoid.common.model.DefResponse;
import com.duplicate.requests.avoid.utils.ValidErrUtil;

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
    public ResponseEntity<?> signUp(@RequestBody @Valid Sign signInfo, BindingResult result) {
        if (result.hasErrors()) {
            return ValidErrUtil.getValidateError(result.getFieldErrors());
        }
        if (signService.insert(signInfo) == 1) {
            return ResponseEntity.ok(new DefResponse(HttpStatus.OK));
        }

        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }

    /* 로그인 */
    @PostMapping
    public void signIn() {

    }

}
