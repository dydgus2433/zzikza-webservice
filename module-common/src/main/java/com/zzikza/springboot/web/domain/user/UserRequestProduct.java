package com.zzikza.springboot.web.domain.user;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_user_req_prd")
public class UserRequestProduct  extends BaseTimeEntity {

    @Id
    @Column(name = "REQ_PRD_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_user_req_prd"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "URP"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @ManyToOne
    @JoinColumn(name = "REQ_ID")
    UserRequest userRequest;

    @Builder
    public UserRequestProduct(Product product) {
        this.product = product;
    }


    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}
