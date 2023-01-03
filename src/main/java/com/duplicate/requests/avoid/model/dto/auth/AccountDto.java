package com.duplicate.requests.avoid.model.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AccountDto {
    @NotBlank
    private String email;

    @NotBlank
    // @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
    // message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
}
