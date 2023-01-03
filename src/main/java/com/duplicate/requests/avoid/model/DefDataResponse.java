package com.duplicate.requests.avoid.model;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DefDataResponse extends DefResponse {
    private Object data;

    public DefDataResponse(HttpStatus status, Object data) {
        super(status);
        this.data = data;
    }
}
