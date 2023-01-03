package com.duplicate.requests.avoid.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.duplicate.requests.avoid.common.code.RewardCode;

@SpringBootTest
public class RedisRewardServiceTest {

    @Autowired
    RedisRewardService redisRewardService;

    @Test
    void firstServedTest() throws InterruptedException {

        final RewardCode chickenEvent = RewardCode.POINT;
        final int people = 100;
        final int limitCount = 30;
        final CountDownLatch countDownLatch = new CountDownLatch(people);
        redisRewardService.setEventCount(chickenEvent, limitCount);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new AddQueueWorker(countDownLatch, chickenEvent)))
                .limit(people)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();
        Thread.sleep(5000); // 기프티콘 발급 스케줄러 작업 시간

        final long failEventPeople = redisRewardService.getSize(chickenEvent);
        assertEquals(people - limitCount, failEventPeople); // output : 70 = 100 - 30
    }

    private class AddQueueWorker implements Runnable {
        private CountDownLatch countDownLatch;
        private RewardCode rewardCode;

        public AddQueueWorker(CountDownLatch countDownLatch, RewardCode rewardCode) {
            this.countDownLatch = countDownLatch;
            this.rewardCode = rewardCode;
        }

        @Override
        public void run() {
            redisRewardService.addQueue(rewardCode);
            countDownLatch.countDown();
        }
    }
}
