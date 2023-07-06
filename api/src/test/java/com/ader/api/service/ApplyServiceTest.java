package com.ader.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ader.api.repository.CouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰이 한 번만 응모되어야 한다")
    void applyCoupon() {
        applyService.applyCoupon("ader");

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("여러명이 응모하는 경우 동시성 문제")
    void concurrencyProblem() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 모든 요청이 끝날때까지 대기하기 위해 선언
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
             executorService.submit(() -> {
                 try {
                     applyService.applyCoupon("ader");
                 } finally {
                     latch.countDown();
                 }
             });
        }

        latch.await();

        long count = couponRepository.count();

        assertThat(count).isEqualTo(100);
    }
}
