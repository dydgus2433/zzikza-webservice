package com.zzikza.springboot.web.domain.enums;

public enum EEmail {


    구글("google.com"),
    네이버("naver.com"),
    네이트("nate.com")
    ;
    String value;

    EEmail(String value) {
        this.value = value;
    }
}
