package com.zzikza.springboot.web.domain.enums;

public enum EUserStatus implements EnumModel{
    N("삭제"),
    Y("정상");
    String value;
    EUserStatus(String value) {
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
