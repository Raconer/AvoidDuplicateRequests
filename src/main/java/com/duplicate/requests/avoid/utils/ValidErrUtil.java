package com.duplicate.requests.avoid.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import com.duplicate.requests.avoid.common.code.ValidCode;
import com.duplicate.requests.avoid.common.model.DefDataResponse;
import com.duplicate.requests.avoid.common.model.valid.Validate;

public class ValidErrUtil {
    public static ResponseEntity<?> getValidateError(List<FieldError> errors) {
        List<Validate> validates = new ArrayList<>();
        for (FieldError error : errors) {
            Validate validate = new Validate(error.getField(), error.getCode());
            validates.add(validate);
        }
        return ResponseEntity.ok(new DefDataResponse(HttpStatus.BAD_REQUEST, validates));
    }

    public static ResponseEntity<?> getValidateError(String field, ValidCode validCode) {
        List<Validate> validates = new ArrayList<>();
        Validate validate = new Validate(field, validCode.name());
        validates.add(validate);
        return ResponseEntity.ok(new DefDataResponse(HttpStatus.BAD_REQUEST, validates));
    }
}
