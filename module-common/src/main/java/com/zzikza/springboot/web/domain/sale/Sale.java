package com.zzikza.springboot.web.domain.sale;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_sale")
public class Sale extends BaseTimeEntity {
    @Id
    @Column(name = "SALE_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_sale"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SLI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "SALE_NM")
    String saleName;
    @Column(name = "SALE_CD")
    String saleCode;

    @Column(name = "SALE_VALUE")
    Integer salePrice;

    @ManyToOne
    @JoinColumn(name = "EXH_ID")
    Exhibition exhibition;

    @Builder
    public Sale(String saleName, Integer salePrice, Exhibition exhibition) {
        this.saleName = saleName;
        this.salePrice = salePrice;
        this.exhibition = exhibition;
    }
}
