package com.zzikza.springboot.web.domain.pay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
   Optional<Payment> findByMerchantUid(String merchant_uid);
}
