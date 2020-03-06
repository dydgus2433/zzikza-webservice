package com.zzikza.springboot.web.domain.studio;


import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tb_stdo_keyword")
public class StudioKeyword  extends BaseTimeEntity {
    @Id
    @Column(name = "STDO_KEYWORD_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_keyword"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "TSK"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column(name = "STDO_KEYWORD_NM")
    String keywordName;

    @OneToMany(mappedBy = "studioKeyword", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<StudioKeywordMap> studioKeywordMaps;

    @Builder
    public StudioKeyword(String keywordName) {
        this.keywordName = keywordName;
    }
}
