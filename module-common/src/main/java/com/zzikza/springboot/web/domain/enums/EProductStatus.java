package com.zzikza.springboot.web.domain.enums;

public enum EProductStatus {


    NORMAL("NORMAL"),
    DELETE("DELETE");

    String value;

    EProductStatus(String value) {
        this.value = value;
    }
}
