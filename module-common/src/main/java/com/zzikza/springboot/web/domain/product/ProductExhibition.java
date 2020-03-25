package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "tb_prd_exh_map")
public class ProductExhibition extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_EXH_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd_exh_map"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PEI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @ManyToOne
    @JoinColumn(name = "EXH_ID")
    Exhibition exhibition;

    @Builder
    public ProductExhibition(Product product, Exhibition exhibition) {
        this.product = product;
        this.exhibition = exhibition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductExhibition that = (ProductExhibition) o;
        return
                Objects.equals(product, that.product) &&
                        Objects.equals(exhibition, that.exhibition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, exhibition);
    }
}