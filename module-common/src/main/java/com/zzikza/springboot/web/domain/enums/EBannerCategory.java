package com.zzikza.springboot.web.domain.enums;

public enum EBannerCategory {
    BOT("메인하단"),
    MID("메인중단"),
    TOP("메인상단");

    String value;

    EBannerCategory(String value) {
        this.value = value;
    }
}
