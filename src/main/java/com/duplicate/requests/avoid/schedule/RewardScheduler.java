package com.duplicate.requests.avoid.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duplicate.requests.avoid.common.code.RewardCode;
import com.duplicate.requests.avoid.service.RedisRewardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardScheduler {

    private final RedisRewardService redisRewardService;

    @Scheduled(fixedDelay = 1000)
    private void chickenEventScheduler() {
        if (redisRewardService.validEnd()) {
            log.info("===== 선착순 이벤트가 종료되었습니다. =====");
            return;
        }
        redisRewardService.publish(RewardCode.POINT);
        redisRewardService.getOrder(RewardCode.POINT);
    }
}
