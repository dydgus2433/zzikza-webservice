package com.zzikza.springboot.web.domain.enums;

public enum EProductStatus {


    정상("NORMAL"),
    삭제("DELETE");

    String value;

    EProductStatus(String value) {
        this.value = value;
    }
}
