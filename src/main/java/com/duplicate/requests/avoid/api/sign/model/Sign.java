package com.duplicate.requests.avoid.api.sign.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Sign extends Account {

    private int idx;

    @NotBlank
    private String name;

    private Date regDate;
}
