package com.goj.restservice.worker;

import com.goj.restservice.entity.ResultRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FetchResult {

    @Autowired
    private RedisTemplate<Integer, ResultRedis> redisTemplate;

    @Autowired
    private UpdateResult updateResult;

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void fetchResult() {
        ResultRedis resultRedis = redisTemplate.opsForList().rightPop(2);
        if (resultRedis != null) {
            {
                updateResult.update(resultRedis);
            }
        }

    }
}