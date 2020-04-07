package com.zzikza.springboot.web.domain.enums;

public enum EStudioStatus implements EnumModel{
    N("탈퇴"),
    Y("정상");
    String value;
    EStudioStatus(String value) {
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
