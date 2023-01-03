package com.duplicate.requests.avoid.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.common.code.RewardCode;
import com.duplicate.requests.avoid.dto.reward.RedisRewardDto;
import com.duplicate.requests.avoid.dto.reward.RewardCounter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RedisRewardService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;
    private static final long LAST_INDEX = 1;
    private RewardCounter rewardCounter;

    public void addQueue(RewardCode rewardCode) {
        final String people = Thread.currentThread().getName();
        final long now = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(rewardCode.toString(), people, (int) now);
        log.info("대기열에 추가 - {} ({}초)", people, now);
    }

    public void getOrder(RewardCode rewardCode) {
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        Set<Object> queue = redisTemplate.opsForZSet().range(rewardCode.toString(), start, end);

        for (Object people : queue) {
            Long rank = redisTemplate.opsForZSet().rank(rewardCode.toString(), people);
            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", people, rank);
        }
    }

    public void publish(RewardCode rewardCode) {
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - LAST_INDEX;

        Set<Object> queue = redisTemplate.opsForZSet().range(rewardCode.toString(), start, end);
        for (Object people : queue) {
            final RedisRewardDto redisRewardDto = new RedisRewardDto(rewardCode);
            log.info("'{}'님의 {} 기프티콘이 발급되었습니다 ({})", people, redisRewardDto.getRewardCode().getName(),
                    redisRewardDto.getCode());
            redisTemplate.opsForZSet().remove(rewardCode.toString(), people);
            this.rewardCounter.decrease();
        }
    }

}
