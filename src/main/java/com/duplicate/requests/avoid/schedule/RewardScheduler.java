package com.duplicate.requests.avoid.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duplicate.requests.avoid.service.RewardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardScheduler {

    RewardService rewardService;

    @Scheduled(fixedDelay = 1000)
    private void chickenEventScheduler() {
        // if (rewardService.validEnd()) {
        // log.info("===== 선착순 이벤트가 종료되었습니다. =====");
        // return;
        // }
        // gifticonService.publish(Event.CHICKEN);
        // gifticonService.getOrder(Event.CHICKEN);
    }
}
