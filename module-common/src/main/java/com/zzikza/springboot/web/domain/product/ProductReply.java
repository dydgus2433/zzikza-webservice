package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EBoardStatus;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "tb_prd_repl")
public class ProductReply extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_REPL_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd_repl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PRE"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @OneToOne
    @JoinColumn(name = "USER_SEQ", nullable = true)
    User user;

    @Column(name = "GRADE")
    Double grade;

    @OneToOne
    @JoinColumn(name = "STDO_SEQ", nullable = true)
    Studio studio;


    @Column(name = "CONTENT")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN")
    EBoardStatus status;

    @ManyToOne
    @JoinColumn(name = "PARENT_PRD_REPL_ID")
    ProductReply parentReply;

    @OneToMany(mappedBy = "parentReply", fetch = FetchType.EAGER)
    List<ProductReply> ProductReplies = new ArrayList<>();

    @Builder
    ProductReply(Product product, User user, Studio studio, String content, Double grade, ProductReply parentReply) {
        this.product = product;
        this.user = user;
        this.studio = studio;
        this.content = content;
        this.grade = grade;
        this.parentReply = parentReply;
    }

    public void setParentReply(ProductReply parentReply) {
        this.parentReply = parentReply;
    }
}
