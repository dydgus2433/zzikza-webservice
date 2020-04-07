package com.zzikza.springboot.web.domain.request;

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
@Entity(name = "tb_req_prd_tmp_fl")
public class UserRequestProductFileTemp extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_FL_TMP_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_req_prd_tmp_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "RTF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column(name = "TMP_KEY")
    String tempKey;

    @Embedded
    private FileAttribute file;

    @Builder
    public UserRequestProductFileTemp(FileAttribute fileAttribute, String tempKey) {
        this.file = fileAttribute;
        this.tempKey = tempKey;
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
}
