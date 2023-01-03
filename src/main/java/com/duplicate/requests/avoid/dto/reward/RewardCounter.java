package com.duplicate.requests.avoid.dto.reward;

import com.duplicate.requests.avoid.common.code.RewardCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardCounter {
    private RewardCode rewardCode;
    private int limit;

    private static final int END = 0;

    public RewardCounter(RewardCode rewardCode, int limit) {
        this.rewardCode = rewardCode;
        this.limit = limit;
    }

    public synchronized void decrease() {
        this.limit--;
    }

    public boolean end() {
        return this.limit == END;
    }
}