package com.zzikza.springboot.web.domain.enums;

public enum EShowStatus {

    N("비공개"),
    Y("공개");
    String value;
    EShowStatus(String value) {
        this.value = value;
    }
}
