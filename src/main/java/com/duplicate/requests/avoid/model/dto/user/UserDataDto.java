package com.duplicate.requests.avoid.model.dto.user;

import lombok.Data;

@Data
public class UserDataDto {
    private int idx;
    private int userIdx;
    private int point;

    public UserDataDto() {
    }

    public UserDataDto(int userIdx) {
        this.userIdx = userIdx;
        this.point = 0;
    }
}
