package com.duplicate.requests.avoid.controller.api;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.common.code.ValidCode;
import com.duplicate.requests.avoid.model.DefDataResponse;
import com.duplicate.requests.avoid.model.DefResponse;
import com.duplicate.requests.avoid.model.dto.auth.AccountDto;
import com.duplicate.requests.avoid.model.dto.auth.AuthDto;
import com.duplicate.requests.avoid.model.dto.user.UserDto;
import com.duplicate.requests.avoid.service.SignService;
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
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return ValidErrUtil.getValidateError(result.getFieldErrors());
        }
        if (signService.signUp(user) == 1) {
            log.info("SignUp Success");
            return ResponseEntity.ok(new DefResponse(HttpStatus.OK));
        }

        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }

    /* 로그인 */
    @PostMapping
    public ResponseEntity<?> signIn(@RequestBody @Valid AccountDto accountDto, BindingResult result) {
        if (result.hasErrors()) {
            return ValidErrUtil.getValidateError(result.getFieldErrors());
        }

        log.info("User Data : {}", accountDto.toString());

        AuthDto authDto = signService.auth(accountDto);
        if (authDto != null) {
            return ResponseEntity.ok(new DefDataResponse(HttpStatus.OK, authDto));
        }

        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }

    /* 리프레쉬 토큰 */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthDto authDto) {
        String refreshToken = authDto.getRefreshToken();
        if (StringUtils.isEmpty(refreshToken)) {
            return ValidErrUtil.getValidateError("refreshToken", ValidCode.EMPTY);
        }

        authDto = signService.refresh(refreshToken);
        if (authDto != null) {
            return ResponseEntity.ok(new DefDataResponse(HttpStatus.OK, authDto));
        }

        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }
}