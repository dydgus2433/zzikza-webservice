package com.zzikza.springboot.web.domain.enums;

public enum EShowStatus {

    비공개("N"),
    공개("Y");
    String value;
    EShowStatus(String value) {
        this.value = value;
    }
}
