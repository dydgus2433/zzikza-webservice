package com.zzikza.springboot.web.domain.enums;

public enum EAuthority {

    관리자("ADMIN"),
    사용자("USER"),
    사장님("STUDIO"),
    ;
    String value;

    EAuthority(String value) {
        this.value = value;
    }
}
