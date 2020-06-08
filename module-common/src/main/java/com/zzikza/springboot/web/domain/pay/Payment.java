package com.zzikza.springboot.web.domain.pay;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_pay")
public class Payment {
    @Id
    @Column(name = "PAY_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_pay"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PAY"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "NAME")
    String name;

    @Column(name = "merchant_uid")
    String merchantUid;

    @OneToOne
    @JoinColumn(name = "FNL_PAY_ID", nullable = false)
    FinalPaymentPrice finalPaymentPrice;

    @Builder
    public Payment(String name, FinalPaymentPrice finalPaymentPrice) {
        this.name = name;
        this.finalPaymentPrice = finalPaymentPrice;
    }

    public int getRealPrice() {
        return finalPaymentPrice.getFinalPrice();
    }
}
