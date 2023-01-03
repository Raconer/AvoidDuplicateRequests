package com.duplicate.requests.avoid.dto.reward;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RewardUserDto extends RewardDto {
    private String name;
}
