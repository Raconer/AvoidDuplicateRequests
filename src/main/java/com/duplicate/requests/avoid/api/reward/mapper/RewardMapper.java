package com.duplicate.requests.avoid.api.reward.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.api.reward.dto.RewardInfoDto;

@Mapper
@Repository
public interface RewardMapper {

    // Create
    int insert(RewardInfoDto rewardInfoDto);

}
