package com.zzikza.springboot.web.domain.enums;

//실제론 미사용
public enum EExhibitionCategory {

    IDPHOTO("증명사진"),
    FAMILY("가족사진");
    String value;

    EExhibitionCategory(String value) {
        this.value = value;
    }
}
