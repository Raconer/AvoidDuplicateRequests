package com.duplicate.requests.avoid.api.user.model;

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
public class User extends Account {

    private int idx;

    @NotBlank
    private String name;

    private Date regDate;
    private String refreshToken;

    public User() {
    }

    public User(String email) {
        super.setEmail(email);
    }

}
