package com.zzikza.springboot.web.domain.enums;

public enum EProductAcceptStatus {


    WAIT("대기"),
    REJECT("반려"),
    ACCEPT("수락");
    String value;

    EProductAcceptStatus(String value) {
        this.value = value;
    }
}
