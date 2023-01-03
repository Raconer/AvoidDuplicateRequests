package com.duplicate.requests.avoid.common.code;

import lombok.Getter;

@Getter
public enum RewardCode {
    POINT("Point");

    String name;

    RewardCode(String name) {
        this.name = name;
    }
}
