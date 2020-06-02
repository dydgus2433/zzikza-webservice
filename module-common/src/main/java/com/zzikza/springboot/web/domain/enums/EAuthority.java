package com.zzikza.springboot.web.domain.enums;

public enum EAuthority {

    ADMIN("관리자"),
    USER("사용자"),
    STUDIO("사장님"),
    ;
    String value;

    EAuthority(String value) {
        this.value = value;
    }
}
