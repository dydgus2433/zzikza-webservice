package com.zzikza.springboot.web.domain.studio;

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
@Entity(name = "tb_stdo_keyword_map")
public class StudioKeywordMap  extends BaseTimeEntity {
    @Id
    @Column(name = "STDO_KEYWORD_MAP_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_studio_keyword_map"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SKM"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @ManyToOne
    @JoinColumn(name = "STDO_KEYWORD_ID")
    StudioKeyword studioKeyword;

    @Builder
    public StudioKeywordMap(Studio studio, StudioKeyword studioKeyword) {
        this.studio = studio;
        this.studioKeyword = studioKeyword;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public void setStudioKeyword(StudioKeyword studioKeyword) {
        this.studioKeyword = studioKeyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudioKeywordMap that = (StudioKeywordMap) o;
        return
                Objects.equals(studio, that.studio) &&
                Objects.equals(studioKeyword, that.studioKeyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studio, studioKeyword);
    }
}
