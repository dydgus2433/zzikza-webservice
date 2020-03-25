package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    String title;

    public ProductResponseDto(Product entity) {
        this.title = entity.getTitle();
    }
}
