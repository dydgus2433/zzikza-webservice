package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.request.UserRequestProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRequestProductRequestDto {
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
    String reqId;

    @Builder
    public UserRequestProductRequestDto(EProductCategory productCategory, String title, Integer price, Integer productSalePrice, Integer productHour, Integer productMinute, String productBriefDesc, EShowStatus showStatusCode, String exhId, String keyword, String index, String productDescription, String tempKey, String id, String reqId) {
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
        this.reqId = reqId;
    }

    public UserRequestProduct toEntity() {

        return UserRequestProduct.builder()
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
