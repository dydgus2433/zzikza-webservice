package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "tb_stdo_fl")
public class StudioFile {
    @Id
    @Column(name = "STDO_FL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "STF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column
    String fileName;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @Builder
    public StudioFile(String fileName, Studio studio) {
        this.fileName = fileName;
        this.studio = studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
        //기존 스튜디오와 관계 제거
        if(this.studio != null){
            this.studio.getStudioFiles().remove(this);
        }

        studio.getStudioFiles().add(this);
        this.studio = studio;
    }
}
