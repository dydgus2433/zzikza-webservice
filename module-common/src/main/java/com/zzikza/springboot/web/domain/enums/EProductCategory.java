package com.zzikza.springboot.web.domain.enums;

public enum EProductCategory {

    베이비("BP"),
    우정커플("CP"),
    가족사진("FP"),
    졸업사진("GP"),
    제품건축("MP"),
    증명여권("PP"),
    프로필("PR"),
    스냅사진("SP"),
    스튜디오렌탈("ST"),
    결혼사진("WP");
    String value;

    EProductCategory(String value) {
        this.value = value;
    }
}
