package com.zzikza.springboot.web.domain.enums;

public enum ETermStatus implements EnumModel{
    N("거부"),
    Y("동의");
    String value;
    ETermStatus(String value) {
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
