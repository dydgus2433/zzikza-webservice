package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.StudioKeywordMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "tb_prd_keyword")
public class ProductKeyword extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_KEYWORD_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd_keyword"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PSK"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "PRD_KEYWORD_NM")
    String keywordName;

    @OneToMany(mappedBy = "productKeyword", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ProductKeywordMap> productKeywordMaps;
    @Builder
    public ProductKeyword(String keywordName) {
        this.keywordName = keywordName;
    }



}
