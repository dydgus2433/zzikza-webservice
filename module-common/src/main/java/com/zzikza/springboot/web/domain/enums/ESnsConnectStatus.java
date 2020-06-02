package com.zzikza.springboot.web.domain.enums;

public enum ESnsConnectStatus implements EnumModel {
    N("해제"),
    Y("연결");
    String value;

    ESnsConnectStatus(String value) {
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
