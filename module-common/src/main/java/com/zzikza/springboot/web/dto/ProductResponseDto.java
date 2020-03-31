package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    String id;
    EProductCategory productCategory;
    String title;
    Integer price;
    Integer productSalePrice;
    Integer hour;
    Integer minute;
    String briefDescription;
    EShowStatus showStatus;
    String exhId;
    String keyword;
    String index;
    String productDescription;

    public ProductResponseDto(Product dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.productCategory = dto.getProductCategory();
        this.showStatus = dto.getShowStatus();
        this.hour = dto.getHour();
        this.minute =dto.getMinute();
        this.productDescription = dto.getProductDescription();
        this.briefDescription = dto.getBriefDescription();
    }
}
