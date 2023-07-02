package com.ader.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ader.api.repository.CouponRepository;
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
}
