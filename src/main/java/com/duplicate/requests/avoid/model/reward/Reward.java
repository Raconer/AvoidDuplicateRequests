package com.duplicate.requests.avoid.model.reward;

import java.util.UUID;

import com.duplicate.requests.avoid.common.code.RewardCode;

import lombok.Data;

@Data
public class Reward {
    private RewardCode rewardCode;
    private String code;

    public Reward(RewardCode rewardCode) {
        this.rewardCode = rewardCode;
        this.code = UUID.randomUUID().toString();
    }
}
