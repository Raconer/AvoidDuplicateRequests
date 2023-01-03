package com.duplicate.requests.avoid.service;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedisRewardService {

    RedisTemplate<String, Object> redisTemplate;

    public void set(String id) {
        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(id, UUID.randomUUID().toString(), (int) now);
    }

}
