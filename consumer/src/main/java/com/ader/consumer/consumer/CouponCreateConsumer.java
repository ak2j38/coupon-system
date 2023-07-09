package com.ader.consumer.consumer;

import com.ader.consumer.domain.Coupon;
import com.ader.consumer.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateConsumer {

    private final CouponRepository couponRepository;

    @KafkaListener(topics = "coupon-create", groupId = "group_1")
    public void listen(String message) {
        couponRepository.save(Coupon.of(message));
    }

}
