package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "tb_wish")
public class UserWishProduct extends BaseTimeEntity {

    @Id
    @Column(name = "WSH_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_wish"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "WSH"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    User user;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @Builder
    public UserWishProduct(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


