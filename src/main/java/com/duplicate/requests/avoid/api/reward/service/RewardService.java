package com.duplicate.requests.avoid.api.reward.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.duplicate.requests.avoid.api.reward.dto.RewardInfoDto;
import com.duplicate.requests.avoid.api.reward.mapper.RewardMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RewardService {

    RewardMapper rewardMapper;

    final int REWARD_CNT = 10;

    // Create
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int request(int userIdx) {
        RewardInfoDto rewardInfoDto = new RewardInfoDto(userIdx, REWARD_CNT);

        if (REWARD_CNT > this.rewardMapper.count(rewardInfoDto)) {
            return this.rewardMapper.insert(rewardInfoDto);
        }
        return 0;
    }

}