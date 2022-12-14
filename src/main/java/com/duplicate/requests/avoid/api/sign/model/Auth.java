package com.duplicate.requests.avoid.api.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Auth {
    private String email;
    private String name;
    private String token;
    private String refreshToken;

}
