package com.zzikza.springboot.web.domain.exhibition;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity(name = "tb_exh_fl")
public class ExhibitionFile {
    @Id
    @Column(name = "EXH_FL_ID", length = 15)
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_exh_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "EXF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @OneToOne
    @JoinColumn(name = "EXH_ID")
    Exhibition exhibition;

    FileAttribute file;
}
