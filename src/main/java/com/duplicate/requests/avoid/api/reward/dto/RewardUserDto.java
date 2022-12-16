package com.duplicate.requests.avoid.api.reward.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RewardUserDto extends RewardDto {
    private String name;
}
