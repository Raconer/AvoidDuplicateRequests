package com.duplicate.requests.avoid.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AccountDto {

    private int idx;

    @NotBlank
    private String name;

    private Date regDate;
    private String refreshToken;

    public UserDto() {
    }

    public UserDto(String email) {
        super.setEmail(email);
    }

}
