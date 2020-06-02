package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_stdo_fl")
public class StudioFile extends BaseTimeEntity {
    @Id
    @Column(name = "STDO_FL_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_stdo_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "STF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;
    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;
    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "TITLE", column = @Column(name = "")),
//            @AttributeOverride(name = "CONTENT", column = @Column(name = ""))
//    })
    private FileAttribute file;

    @Builder
    public StudioFile(Studio studio, FileAttribute fileAttribute) {
        this.studio = studio;
        this.file = fileAttribute;
    }

    public StudioFile(FileAttribute fileAttribute) {
        this.file = fileAttribute;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
        //기존 스튜디오와 관계 제거
        if (this.studio != null) {
            this.studio.getStudioFiles().remove(this);
        }

        studio.getStudioFiles().add(this);
        this.studio = studio;
    }

    public String getFileName() {
        return this.file.getFileName();
    }
    public String getFilePath() {
        return this.file.getFilePath();
    }

    public String getFileLargePath() {
        return this.file.getFileLargePath();
    }

    public String getFileMidsizePath() {
        return this.file.getFileMidsizePath();
    }

    public String getFileThumbPath() {
        return this.file.getFileThumbPath();
    }

    public Integer getFileOrder() {
        return this.file.getFileOrder();
    }

    public void setFileOrder(int fileOrder) {
        this.file.setFileOrder(fileOrder);
    }

    public boolean isFirst(){
        return 0 == this.file.getFileOrder();
    }
}
