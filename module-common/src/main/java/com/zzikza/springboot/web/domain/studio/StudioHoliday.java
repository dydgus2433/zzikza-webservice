package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EDateStatus;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo_holi")
public class StudioHoliday  extends BaseTimeEntity {
    @Id
    @Column(name = "STDO_HOLI_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_holi"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SHI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;
    //TODO : DT_CD / DT_VAL 두개 묶어서 uniqueKey 만들어야함
    @Enumerated(EnumType.STRING)
    @Column(name = "DT_CD")
    EDateStatus dateCode;

    @Column(name = "DT_VAL")
    String dateValue;

    @Builder
    public StudioHoliday(EDateStatus dateCode, String dateValue) {
        this.dateCode = dateCode;
        this.dateValue = dateValue;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
