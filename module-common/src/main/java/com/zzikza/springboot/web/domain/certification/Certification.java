package com.zzikza.springboot.web.domain.certification;

import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_crtf")
public class Certification {
    @Id
    @Column(name = "CRTF_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_crtf"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "CRT"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "TEL")
    String tel;
    @Enumerated(EnumType.STRING)
    @Column(name = "CRTF_STAT")
    ECertificationStatus certificationStatus;
    @Column(name = "CRTF_VAL")
    String certificationValue;

}
