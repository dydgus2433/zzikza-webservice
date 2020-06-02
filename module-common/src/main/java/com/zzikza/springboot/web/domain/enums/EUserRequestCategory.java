package com.zzikza.springboot.web.domain.enums;

public enum EUserRequestCategory implements EnumModel {
    MD("상품"),
    PR("전문작가"),
    FD("음식");

    String value;

    EUserRequestCategory(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
