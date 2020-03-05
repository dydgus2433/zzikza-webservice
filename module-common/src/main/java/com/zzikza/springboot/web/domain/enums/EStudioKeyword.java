package com.zzikza.springboot.web.domain.enums;

public enum EStudioKeyword {

    기업사진("101"),
    기타촬영("102"),
    제품사진("103"),
    전문작가("104");
    String value;

    EStudioKeyword(String value) {
        this.value = value;
    }
}
