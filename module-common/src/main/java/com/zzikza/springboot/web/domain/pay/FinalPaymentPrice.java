package com.zzikza.springboot.web.domain.pay;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity(name = "tb_final_pay")
public class FinalPaymentPrice  extends BaseTimeEntity {
    @Id
    @Column(name = "FNL_PAY_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_final_pay"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "FPI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;


    @OneToOne
    @JoinColumn(name = "PRD_ID", nullable = false)
    Product product;

    @OneToOne
    @JoinColumn(name = "SALE_ID", nullable = true)
    Sale sale;

    @Builder
    public FinalPaymentPrice(Product product, Sale sale) {
        this.product = product;
        this.sale = sale;
    }

    public int getFinalPrice() {
        int price = product.getPrice();
        if(this.sale != null){
            if(price > this.sale.getSalePrice()){
                price = this.sale.getSalePrice();
            }
        }
        return price;
    }
}
