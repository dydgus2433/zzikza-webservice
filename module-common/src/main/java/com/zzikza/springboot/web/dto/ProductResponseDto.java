package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EProductCategory;
import com.zzikza.springboot.web.domain.enums.EShowStatus;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.product.Product;
import com.zzikza.springboot.web.domain.product.ProductFile;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.studio.Studio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
    String id;
    EProductCategory productCategory;
    String title;
    private Integer price;
    Integer productSalePrice;
    Integer hour;
    Integer minute;
    String productBriefDescription;
    EShowStatus showStatus;
    String exhId;
    String keyword;
    String index;
    String productDescription;
    ProductFile productFile;
    StudioResponseDto studio;
    Double distance;
    Double lttd;
    Double lgtd;
    Exhibition exhibition;
    boolean isWish = false;
    private DecimalFormat format;
    private double grade;

    public ProductResponseDto(Product dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.productCategory = dto.getProductCategory();
        this.showStatus = dto.getShowStatus();
        this.hour = dto.getHour();
        this.minute =dto.getMinute();
        this.productDescription = dto.getProductDescription();
        this.productBriefDescription = dto.getProductBriefDescription();
        if(!Objects.isNull(dto.getStudio())){
            this.studio = new StudioResponseDto(dto.getStudio());
        }
        if(!Objects.isNull(dto.getExhibition())){
            this.exhibition = dto.getExhibition();
        }
    }

    public void setProductFile(ProductFile productFile) {
        this.productFile = productFile;
    }
    public boolean isZero(){
        return Objects.isNull(price) || price == 0;
    }
    public boolean isExhibition(){
        return exhId !=null && !"".equals(exhId);
    }
    public String getPrice(){
        if(format == null){
            format = new DecimalFormat("###,###");
            return format.format(price);
        }
        return price.toString();
    }

    public void isContains() {
        this.isWish = true;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }
}
