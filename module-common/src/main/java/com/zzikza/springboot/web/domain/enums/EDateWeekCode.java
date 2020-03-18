package com.zzikza.springboot.web.domain.enums;

public enum EDateWeekCode implements EnumModel{


    월("0"),
    화("1"),
    수("2"),
    목("3"),
    금("4"),
    토("5"),
    일("6");
    String value;

    EDateWeekCode(String value) {
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
