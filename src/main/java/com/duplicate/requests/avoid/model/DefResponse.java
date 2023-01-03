package com.duplicate.requests.avoid.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DefResponse implements Serializable {

    private int code;
    private String message;

    public DefResponse(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }
}
