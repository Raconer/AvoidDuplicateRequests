package com.duplicate.requests.avoid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDto {
    private String email;
    private String name;
    private String token;
    private String refreshToken;

}
