package com.duplicate.requests.avoid.model.valid;

import lombok.Data;

@Data
public class Validate {
    private String field;
    private String code;

    public Validate(String field, String code) {
        this.field = field;
        this.code = code;
    }
}
