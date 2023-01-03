package com.duplicate.requests.avoid.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.duplicate.requests.avoid.dto.RewardInfoDto;
import com.duplicate.requests.avoid.dto.RewardUserDto;
import com.duplicate.requests.avoid.mapper.RewardMapper;

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
            if (this.rewardMapper.insert(rewardInfoDto) == 1) {
                // 선착순 등록 했을 때 포인트 지금후 return
                return this.rewardMapper.rewardPoint(rewardInfoDto);
            }
            return 2; // 이미 선착순 등록이 되었을 때
        }
        return 0; // 선착순에 등록이 되지 않았을 때
    }

    // READ
    public List<RewardUserDto> list(Date date) {

        List<RewardUserDto> userDtos = this.rewardMapper.list(date);
        if (userDtos == null) {
            return new ArrayList<>();
        }

        return userDtos;
    }
}