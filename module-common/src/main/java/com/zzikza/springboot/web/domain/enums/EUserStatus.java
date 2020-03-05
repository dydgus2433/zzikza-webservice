package com.zzikza.springboot.web.domain.enums;

public enum EUserStatus implements EnumModel{
    삭제("N"),
    정상("Y");
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
