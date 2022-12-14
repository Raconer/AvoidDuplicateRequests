package com.duplicate.requests.avoid.api.sign.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private String email;
    private String name;
    private String token;
    private String refreshToken;

}
