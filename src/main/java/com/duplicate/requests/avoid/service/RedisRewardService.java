package com.duplicate.requests.avoid.service;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.common.code.RewardCode;
import com.duplicate.requests.avoid.model.reward.Reward;
import com.duplicate.requests.avoid.model.reward.RewardCount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisRewardService {

    private final RedisTemplate<String, Object> redisTemplate;
    private RewardCount rewardCount;
    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;
    private static final long LAST_INDEX = 1;

    // public RedisRewardService(RedisTemplate<String, Object> redisTemplate) {
    // this.redisTemplate = redisTemplate;
    // }

    public void setEventCount(RewardCode rewardCode, int queue) {
        this.rewardCount = new RewardCount(rewardCode, queue);
    }

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
            final Reward redisRewardDto = new Reward(rewardCode);
            log.info("'{}'님의 {} 기프티콘이 발급되었습니다 ({})", people, redisRewardDto.getRewardCode().getName(),
                    redisRewardDto.getCode());
            redisTemplate.opsForZSet().remove(rewardCode.toString(), people);
            this.rewardCount.decrease();
        }
    }

    public boolean validEnd() {
        return this.rewardCount != null ? this.rewardCount.end() : false;
    }

    public long getSize(RewardCode rewardCode) {
        return this.redisTemplate.opsForZSet().size(rewardCode.toString());
    }

}
