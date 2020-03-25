package com.zzikza.springboot.web.domain.product;

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
@Entity(name = "tb_prd_fl")
public class ProductFile extends BaseTimeEntity {
    @Id
    @Column(name = "PRD_FL_ID", length = 15)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd_fl"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PFI"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")
    Product product;

    @Embedded
    private FileAttribute file;

    @Builder
    public ProductFile(Product product, FileAttribute fileAttribute) {
        this.product = product;
        this.file = fileAttribute;
    }

    public ProductFile(FileAttribute fileAttribute) {
        this.file = fileAttribute;
    }

    public ProductFile(ProductFileTemp productFileTemp) {
        this.file = productFileTemp.getFile();
    }

    public void setProduct(Product product) {
        this.product = product;
        if (this.product != null) {
            this.product.getProductFiles().remove(this);
        }

        product.getProductFiles().add(this);
        this.product = product;
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
