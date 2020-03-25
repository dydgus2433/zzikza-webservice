package com.zzikza.springboot.web.domain.enums;

public enum EShowStatus implements EnumModel{

    N("비공개"),
    Y("공개");
    String value;
    EShowStatus(String value) {
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
