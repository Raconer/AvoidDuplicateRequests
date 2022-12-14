package com.duplicate.requests.avoid.api.sign;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.api.sign.model.Auth;
import com.duplicate.requests.avoid.api.sign.service.SignService;
import com.duplicate.requests.avoid.api.user.model.Account;
import com.duplicate.requests.avoid.api.user.model.User;
import com.duplicate.requests.avoid.api.user.service.UserService;
import com.duplicate.requests.avoid.common.model.DefDataResponse;
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
    UserService userService;

    /* 회원 가입 */
    @PostMapping("/up")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user, BindingResult result) {
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
    public ResponseEntity<?> signIn(@RequestBody @Valid Account account, BindingResult result) {
        if (result.hasErrors()) {
            return ValidErrUtil.getValidateError(result.getFieldErrors());
        }

        log.info("User Data : {}", account.toString());

        Auth auth = signService.auth(account);
        if (auth != null) {
            return ResponseEntity.ok(new DefDataResponse(HttpStatus.OK, auth));
        }

        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }

}
