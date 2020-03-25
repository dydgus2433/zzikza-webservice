package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.product.ProductKeyword;
import com.zzikza.springboot.web.domain.studio.StudioKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ProductKeywordResponseDto {
    String id;
    String keywordName;

    @Builder
    public ProductKeywordResponseDto(String id, String keywordName) {
        this.id = id;
        this.keywordName = keywordName;
    }

    public ProductKeywordResponseDto(ProductKeyword entity) {
        this.id = entity.getId();
        this.keywordName = entity.getKeywordName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKeywordResponseDto that = (ProductKeywordResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(keywordName, that.keywordName);
    }

}
