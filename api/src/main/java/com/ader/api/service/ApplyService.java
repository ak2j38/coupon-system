package com.ader.api.service;

import com.ader.api.domain.Coupon;
import com.ader.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {

    private final CouponRepository couponRepository;

    public void applyCoupon(String userId) {
        try {
            long count = couponRepository.count();

            if (count > 100) {
                return;
            }

            couponRepository.save(Coupon.of(userId));
        }
        catch(Exception e) {
            log.error("쿠폰 응모 중 오류가 발생했습니다.", e);
        }
    }
}
