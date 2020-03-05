package com.zzikza.springboot.web.domain.enums;

public enum EUserStatus {
    삭제("N"),
    정상("Y");
    String value;
    EUserStatus(String value) {
        this.value = value;
    }
}
