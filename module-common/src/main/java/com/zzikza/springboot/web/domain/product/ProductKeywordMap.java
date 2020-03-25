package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "tb_prd_keyword_map")
public class ProductKeywordMap extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_KEYWORD_MAP_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd_keyword_map"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PKM"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRD_ID")
    Product product;

    @ManyToOne
    @JoinColumn(name = "PRD_KEYWORD_ID")
    ProductKeyword productKeyword;

    @Builder
    public ProductKeywordMap(Product product, ProductKeyword productKeyword) {
        this.product = product;
        this.productKeyword = productKeyword;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKeywordMap that = (ProductKeywordMap) o;
        return
                Objects.equals(product, that.product) &&
                        Objects.equals(productKeyword, that.productKeyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, productKeyword);
    }
}
