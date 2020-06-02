package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.pay.FinalPaymentPrice;
import com.zzikza.springboot.web.domain.pay.Payment;

public class PaymentResponseDto {
    String id;
    String name;
    FinalPaymentPrice finalPaymentPrice;

    PaymentResponseDto(Payment payment){
        this.id = payment.getId();
        this.name = payment.getName();
        this.finalPaymentPrice = payment.getFinalPaymentPrice();
    }
}
