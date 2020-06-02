package com.zzikza.springboot.web.domain.enums;

public enum EEventCategory {


    ENTRY("추첨"),
    NORMAL("일반");
    String value;

    EEventCategory(String value) {
        this.value = value;
    }
}
