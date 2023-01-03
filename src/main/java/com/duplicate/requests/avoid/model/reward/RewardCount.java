package com.duplicate.requests.avoid.model.reward;

import com.duplicate.requests.avoid.common.code.RewardCode;

import lombok.Data;

@Data
public class RewardCount {
    private RewardCode rewardCode;
    private int limit;

    private static final int END = 0;

    public RewardCount(RewardCode rewardCode, int limit) {
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
