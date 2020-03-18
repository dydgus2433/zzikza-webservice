package com.zzikza.springboot.web.domain.enums;

public enum EDateStatus implements EnumModel{

    W("WEEK"),
    D("DAY");

    String value;
    EDateStatus(String value) {
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
