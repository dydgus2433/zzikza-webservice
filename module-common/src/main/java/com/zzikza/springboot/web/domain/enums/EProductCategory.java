package com.zzikza.springboot.web.domain.enums;

public enum EProductCategory implements EnumModel {

    BP("베이비"),
    CP("우정커플"),
    FP("가족사진"),
    GP("졸업사진"),
    MP("제품건축"),
    PP("증명여권"),
    PR("프로필"),
    SP("스냅사진"),
    ST("스튜디오렌탈"),
    WP("결혼사진");
    String value;

    EProductCategory(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
