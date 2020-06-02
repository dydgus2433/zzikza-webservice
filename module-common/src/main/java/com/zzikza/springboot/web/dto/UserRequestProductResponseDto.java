package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.request.UserRequestProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestProductResponseDto {
    String id;
    EProductCategory productCategory;
    String title;
    Integer price;
    Integer productSalePrice;
    Integer hour;
    Integer minute;
    String productBriefDesc;
    EShowStatus showStatus;
    String exhId;
    String keyword;
    String index;
    String productDescription;
    String createDate;

    public UserRequestProductResponseDto(UserRequestProduct entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.productCategory = entity.getProductCategory();
        this.showStatus = entity.getShowStatus();
        this.hour = entity.getHour();
        this.minute = entity.getMinute();
        this.productDescription = entity.getProductDescription();
        this.productBriefDesc = entity.getProductBriefDescription();
        this.createDate = entity.getCreatedDate();
    }

}
