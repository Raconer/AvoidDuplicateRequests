package com.duplicate.requests.avoid.api.reward.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.duplicate.requests.avoid.api.reward.dto.RewardDto;
import com.duplicate.requests.avoid.api.reward.dto.RewardInfoDto;
import com.duplicate.requests.avoid.api.reward.mapper.RewardMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RewardService {

    RewardMapper rewardMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int request(int userIdx) {
        RewardInfoDto rewardInfoDto = new RewardInfoDto(userIdx);
        this.rewardMapper.insert(rewardInfoDto);
        return 0;
    }
}