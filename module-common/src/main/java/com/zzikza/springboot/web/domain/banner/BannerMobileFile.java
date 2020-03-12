package com.zzikza.springboot.web.domain.banner;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.enums.EFileStatus;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tb_adv_m_fl")
public class BannerMobileFile extends BaseTimeEntity {
    @Id
    @Column(name = "ADV_M_FL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_adv_m_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "AMF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "TITLE", column = @Column(name = "")),
//            @AttributeOverride(name = "CONTENT", column = @Column(name = ""))
//    })
    private FileAttribute file;

    @ManyToOne
    @JoinColumn(name = "ADV_ID")
    Banner banner;

    @Builder
    public BannerMobileFile(String fileName, String fileSourceName, Long fileSize, String fileExt, String filePath, int fileOrder, EFileStatus fileStatus) {
        FileAttribute fileAttribute = new FileAttribute();
        fileAttribute.fileName = fileName;
        fileAttribute.fileSourceName = fileSourceName;
        fileAttribute.fileSize = fileSize;
        fileAttribute.fileExt = fileExt;
        fileAttribute.filePath = filePath;
        fileAttribute.fileOrder = fileOrder;
        fileAttribute.fileStatus = fileStatus;
        this.file = fileAttribute;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getFileName() {
        return this.file.getFileName();
    }
}
