package com.zzikza.springboot.web.domain.enums;

public enum EUserRequestStatus implements EnumModel {
    W("대기"),
    A("답변");
    String value;

    EUserRequestStatus(String value) {
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
