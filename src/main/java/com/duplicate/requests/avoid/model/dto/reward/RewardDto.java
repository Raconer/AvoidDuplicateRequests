package com.duplicate.requests.avoid.model.dto.reward;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RewardDto {
    private int idx;
    private int userIdx;
    private Date regDate;

    public RewardDto(int userIdx) {
        this.userIdx = userIdx;
    }

    public RewardDto(int userIdx, Date regDate) {
        this.userIdx = userIdx;
        this.regDate = regDate;
    }
}
