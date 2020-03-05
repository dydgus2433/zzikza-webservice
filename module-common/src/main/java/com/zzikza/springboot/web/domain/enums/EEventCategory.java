package com.zzikza.springboot.web.domain.enums;

public enum EEventCategory {


    추첨("ENTRY"),
    일반("NORMAL");
    String value;

    EEventCategory(String value) {
        this.value = value;
    }
}
