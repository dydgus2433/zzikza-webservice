package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo_brd_fl")
public class StudioBoardFile {
    @Id
    @Column(name = "STDO_BRD_FL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_brd_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SBF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @Column
    String fileName;

    @ManyToOne
    @JoinColumn(name = "STDO_BRD_ID")
    StudioBoard studioBoard;

    @Builder
    public StudioBoardFile(String fileName, StudioBoard studioBoard) {
        this.fileName = fileName;
        this.studioBoard = studioBoard;
    }

    public void setStudioBoard(StudioBoard studioBoard) {
        this.studioBoard = studioBoard;
        //기존 스튜디오와 관계 제거
        if(this.studioBoard != null){
            this.studioBoard.getStudioBoardFiles().remove(this);
        }

        this.studioBoard = studioBoard;
        studioBoard.getStudioBoardFiles().add(this);
    }
}
