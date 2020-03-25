package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.dto.StudioDetailRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_stdo_dtl")
public class StudioDetail  extends BaseTimeEntity {

    @Id
    @Column(name = "STDO_DTL_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_dtl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SDI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "STDO_DSC")
    String studioDescription;

    @Column(name = "OPEN_DAY_STRT_TM", columnDefinition = "int(2) default 10")
    int openTime;
    @Column(name = "OPEN_DAY_END_TM", columnDefinition = "int(2) default 18")
    int closeTime;
    @Column(name = "WKND_STRT_TM", columnDefinition = "int(2) default 10")
    int weekendOpenTime;
    @Column(name = "WKND_END_TM", columnDefinition = "int(2) default 18" )
    int weekendCloseTime;

    @Builder
    public StudioDetail(String studioDescription, int openTime, int closeTime, int weekendOpenTime, int weekendCloseTime) {
        this.studioDescription = studioDescription;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
    }

    public void update(StudioDetailRequestDto requestDto) {
        this.studioDescription = requestDto.getStudioDescription();
        this.openTime = requestDto.getOpenTime();
        this.closeTime = requestDto.getCloseTime();
        this.weekendOpenTime = requestDto.getWeekendOpenTime();
        this.weekendCloseTime = requestDto.getWeekendCloseTime();
    }
}
