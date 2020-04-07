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
@Entity(name = "tb_user_req_prd_fl")
public class UserRequestProductFile extends BaseTimeEntity {


    @Id
    @Column(name = "REQ_PRD_FL_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_user_req_prd_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "RPF"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "REQ_PRD_ID")
    UserRequestProduct userRequestProduct;

    @Embedded
    private FileAttribute file;


    @Builder
    public UserRequestProductFile(FileAttribute fileAttribute) {
        file = fileAttribute;
    }

    public UserRequestProductFile(UserRequestProductFileTemp productFileTemp) {
        this.file = productFileTemp.getFile();
    }

    public void setUserRequestProduct(UserRequestProduct userRequestProduct) {
        this.userRequestProduct = userRequestProduct;
    }

    public void setFileOrder(int i) {
        this.getFile().setFileOrder(i);
    }

    public String getFileName() {
        return this.file.getFileName();
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
}
