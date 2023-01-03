package com.duplicate.requests.avoid.dto.auth;

import org.springframework.security.core.userdetails.User;

import com.duplicate.requests.avoid.dto.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignDto extends User {
    private int idx;
    private String email;

    public SignDto(UserDto userDto, User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.idx = userDto.getIdx();
        this.email = userDto.getEmail();
    }

}
