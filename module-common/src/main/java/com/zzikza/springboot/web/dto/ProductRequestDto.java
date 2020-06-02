package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductRequestDto {
    String id;
    EProductCategory productCategory;
    String title;
    Integer price;
    Integer productSalePrice;
    Integer productHour;
    Integer productMinute;
    String productBriefDesc;
    EShowStatus showStatusCode;
    String exhId;
    String keyword;
    String index;
    String productDescription;
    String tempKey;
    String searchText;
    Double latitude;
    Double longitude;
    String column;
    String order;



    @Builder
    public ProductRequestDto(EProductCategory productCategory, String title, Integer price, Integer productSalePrice,
                             Integer productHour, Integer productMinute, String productBriefDesc, EShowStatus showStatusCode, String exhId,
                             String keyword, String index, String productDescription, String tempKey, String id, Double latitude, Double longitude
            ,                             String column, String order
    ) {
        this.id = id;
        this.productCategory = productCategory;
        this.title = title;
        this.price = price;
        this.productSalePrice = productSalePrice;
        this.productHour = productHour;
        this.productMinute = productMinute;
        this.productBriefDesc = productBriefDesc;
        this.showStatusCode = showStatusCode;
        this.exhId = exhId;
        this.keyword = keyword;
        this.index = index;
        this.productDescription = productDescription;
        this.tempKey = tempKey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.column = column;
        this.order = order;
    }

    public Product toEntity() {

        return Product.builder()
                .id(id)
                .productCategory(productCategory)
                .title(title)
                .price(price)
                //세일은 없앨지 고민중
//                .productSalePrice(productSalePrice)
                .productHour(productHour)
                .productMinuteute(productMinute)
                .productDescription(productDescription)
                .productBriefDescription(productBriefDesc)
                .showStatus(showStatusCode)
                .build();
    }

    public String[] getSplitKeywords(){
        return keyword.split(",");
    }

}
