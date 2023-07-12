package com.ader.consumer.consumer;

import com.ader.consumer.domain.Coupon;
import com.ader.consumer.domain.FailedEvent;
import com.ader.consumer.repository.CouponRepository;
import com.ader.consumer.repository.FailedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponCreateConsumer {

    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;

    @KafkaListener(topics = "coupon-create", groupId = "group_1")
    public void listen(String userId) {
        try{
            couponRepository.save(Coupon.of(userId));
        } catch (Exception e) {
            log.error("쿠폰 생성 중 오류가 발생했습니다.", e);
            failedEventRepository.save(FailedEvent.of(userId));
        }
    }
}
