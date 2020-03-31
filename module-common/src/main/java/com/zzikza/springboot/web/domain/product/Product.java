package com.zzikza.springboot.web.domain.product;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EProductStatus;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.dto.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "tb_prd")
public class Product extends BaseTimeEntity {

    @Id
    @Column(name = "PRD_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_prd"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "PRD"),
            @org.hibernate.annotations.Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    @Column
    String title;
    @Column(name = "PRD_DSC")
    String productDescription;
    @Column(name = "PRD_BRF_DSC")
    String briefDescription;

    @ManyToOne
    @JoinColumn(name = "STDO_SEQ")
    Studio studio;

    @Column(name = "PRD_PRC")
    Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRD_STAT_CD")
    EProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRD_CATE_CD")
    EProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_STAT_CD")
    EShowStatus showStatus;

    @Column(name = "PRD_HOUR")
    Integer hour;

    @Column(name = "PRD_MIN")
    Integer minute;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ProductKeywordMap> productKeywordMaps = new HashSet<>();


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductExhibition> productExhbitions = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductFile> productFiles = new ArrayList<>();

    @Builder
    public Product(String id, String title, Studio studio, Integer price, EProductStatus productStatus,
                   EProductCategory productCategory, EShowStatus showStatus, Integer productHour, Integer productMinuteute,
                   String productDescription, String productBriefDescription) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.price = price;
        this.productStatus = productStatus;
        this.productCategory = productCategory;
        this.showStatus = showStatus;
        this.hour = productHour;
        this.minute = productMinuteute;
        this.productDescription = productDescription;
        this.briefDescription = productBriefDescription;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public void addProductFile(ProductFile productFile) {
        this.productFiles.add(productFile);
        if (productFile.getProduct() != this) {
            productFile.setProduct(this);
        }
    }

    public void addProductKeyword(ProductKeyword productKeyword) {
        ProductKeywordMap productKeywordMap = ProductKeywordMap.builder()
                .productKeyword(productKeyword)
                .product(this)
                .build();
        this.productKeywordMaps.add(productKeywordMap);
    }

    public void addProductExhibition(Exhibition exhibition) {
        ProductExhibition productExhibition = ProductExhibition.builder()
                .exhibition(exhibition)
                .product(this)
                .build();
        this.productExhbitions.add(productExhibition);
    }

    public void update(ProductRequestDto dto) {
        this.title = dto.getTitle();
        this.price = dto.getPrice();
//        this.productStatus = dto.getShowStatusCode();
        this.productCategory = dto.getProductCategory();
        this.showStatus = dto.getShowStatusCode();
        this.hour = dto.getProductHour();
        this.minute =dto.getProductMinute();
        this.productDescription = dto.getProductDescription();
        this.briefDescription = dto.getProductBriefDesc();
    }
}
