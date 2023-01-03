package com.duplicate.requests.avoid.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.dto.reward.RewardInfoDto;
import com.duplicate.requests.avoid.dto.reward.RewardUserDto;

@Mapper
@Repository
public interface RewardMapper {

    // Create
    int insert(RewardInfoDto rewardInfoDto);

    // Read
    int count(RewardInfoDto rewardInfoDto);

    List<RewardUserDto> list(Date regDate);

    // UPDATE
    int rewardPoint(RewardInfoDto rewardInfoDto);
}
