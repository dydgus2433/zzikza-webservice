package com.zzikza.springboot.web.domain.enums;

public enum ECertificationStatus {
    CO("인증번호0"),
    C1("인증번호"),
    S0("비밀번호0"),
    S1("비밀번호1");

    String value;

    ECertificationStatus(String value) {
        this.value = value;
    }
}
