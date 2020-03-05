package com.zzikza.springboot.web.domain.enums;

public enum EBannerCategory {
    메인하단("BOT"),
    메인중단("MIT"),
    메인상단("TOP");

    String value;

    EBannerCategory(String value) {
        this.value = value;
    }
}
