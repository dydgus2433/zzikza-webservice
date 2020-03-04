package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "tb_stdo_brd")
public class StudioBoard {
    @Id
    @Column(name = "STDO_BRD_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_brd"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "SBI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    String title;
    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @OneToMany(mappedBy = "studioBoard", fetch = FetchType.LAZY)
    List<StudioBoardFile> studioBoardFiles = new ArrayList<>();


    @Builder
    public StudioBoard(String title, Studio studio) {
        this.title = title;
        this.studio = studio;
    }

    public void setStudio(Studio studio) {

        //기존 스튜디오와 관계 제거
        if(this.studio != null){
            this.studio.getStudioBoards().remove(this);
        }

        this.studio = studio;
        studio.getStudioBoards().add(this);
    }


    public void addStudioBoardFile(StudioBoardFile studioBoardFile){
        this.studioBoardFiles.add(studioBoardFile);
        if(studioBoardFile.getStudioBoard() != this){
            studioBoardFile.setStudioBoard(this);
        }
    }
}
