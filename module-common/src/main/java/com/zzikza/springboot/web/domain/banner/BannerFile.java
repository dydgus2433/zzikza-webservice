package com.zzikza.springboot.web.domain.banner;

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
@Entity(name = "tb_adv_fl")
public class BannerFile extends FileAttribute {
    @Id
    @Column(name = "ADV_FL_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_adv_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "TAF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "ADV_ID")
    Banner banner;

    @Builder
    public BannerFile(String fileName, String fileSourceName, int fileSize, String fileExt, String filePath, String fileThumbPath, int fileOrder, EFileStatus fileStatus) {
        this.fileName = fileName;
        this.fileSourceName = fileSourceName;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.filePath = filePath;
        this.fileThumbPath = fileThumbPath;
        this.fileOrder = fileOrder;
        this.fileStatus = fileStatus;
    }


    public void setBanner(Banner banner) {
        this.banner = banner;
    }
}
