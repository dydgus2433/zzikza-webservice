package com.zzikza.springboot.web.domain.enums;

public enum EProductAcceptStatus {


    대기("WAIT"),
    반려("REJECT"),
    수락("ACCEPT");
    String value;

    EProductAcceptStatus(String value) {
        this.value = value;
    }
}
