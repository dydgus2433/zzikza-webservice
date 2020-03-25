package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    EProductCategory productCategory;
    String title;
    Integer productPrice;
    Integer productSalePrice;
    Integer productHour;
    Integer prdMinute;
    String productBriefDesc;
    EShowStatus showStatusCode;
    String exhId;
    String keyword;
    String index;
    String productDesc;
    String tempKey;
    String prdId;

    @Builder
    public ProductRequestDto(EProductCategory prdCateCd, String title, Integer productPrice, Integer prdSalePrc, Integer prdHour, Integer prdMin, String prdBrfDsc, EShowStatus showStatCd, String exhId, String keyword, String index, String prdDsc, String tempKey, String prdId) {
        this.productCategory = prdCateCd;
        this.title = title;
        this.prdId = prdId;
        this.productPrice = productPrice;
        this.productSalePrice = prdSalePrc;
        this.productHour = prdHour;
        this.prdMinute = prdMin;
        this.productBriefDesc = prdBrfDsc;
        this.showStatusCode = showStatCd;
        this.exhId = exhId;
        this.keyword = keyword;
        this.index = index;
        this.productDesc = prdDsc;
        this.tempKey = tempKey;
    }

    public Product toEntity() {

        return Product.builder()
                .id(prdId)
                .productCategory(productCategory)
                .title(title)
                .price(productPrice)
                //세일은 없앨지 고민중
//                .productSalePrice(prdSalePrc)
                .prdHour(productHour)
                .prdMinute(prdMinute)
                .productDescription(productDesc)
                .productBriefDescription(productBriefDesc)
                .showStatus(showStatusCode)
                .build();
    }

    public String[] getSplitKeywords(){
        return keyword.split(",");
    }

}
