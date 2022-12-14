package com.duplicate.requests.avoid.api.userData.model;

import lombok.Data;

@Data
public class UserData {
    private int idx;
    private int userIdx;
    private int point;

    public UserData() {
    }

    public UserData(int userIdx) {
        this.userIdx = userIdx;
        this.point = 0;
    }
}
