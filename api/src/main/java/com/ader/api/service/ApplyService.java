package com.ader.api.service;

import com.ader.api.domain.Coupon;
import com.ader.api.producer.CouponCreateProducer;
import com.ader.api.repository.AppliedUserRepository;
import com.ader.api.repository.CouponCountRepository;
import com.ader.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public void applyCoupon(String userId) {
        try {
            Long applyCount = appliedUserRepository.add(userId);

            if (applyCount != 1) {
                return;
            }

            long count = couponCountRepository.increase();

            if (count > 100) {
                return;
            }

            couponCreateProducer.create(userId);
        }
        catch(Exception e) {
            log.error("쿠폰 응모 중 오류가 발생했습니다.", e);
        }
    }
}
