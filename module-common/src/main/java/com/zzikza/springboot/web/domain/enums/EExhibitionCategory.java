package com.zzikza.springboot.web.domain.enums;

//실제론 미사용
public enum EExhibitionCategory {

    증명사진("IDPHOTO"),
    가족사진("FAMILY");
    String value;

    EExhibitionCategory(String value) {
        this.value = value;
    }
}
