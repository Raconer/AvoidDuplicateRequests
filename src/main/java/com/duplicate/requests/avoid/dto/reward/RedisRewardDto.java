package com.duplicate.requests.avoid.dto.reward;

import java.util.UUID;

import com.duplicate.requests.avoid.common.code.RewardCode;

import lombok.Data;

@Data
public class RedisRewardDto {
    private RewardCode rewardCode;
    private String code;

    public RedisRewardDto(RewardCode rewardCode) {
        this.rewardCode = rewardCode;
        this.code = UUID.randomUUID().toString();
    }
}
