package com.zzikza.springboot.web.domain.enums;

public enum EProductKeyword {

    증명사진("3"),
    결혼사진("4"),
    아기사진("5"),
    가족사진("6");

    String value;

    EProductKeyword(String value) {
        this.value = value;
    }
}
