package com.zzikza.springboot.web.domain.enums;

public enum ECertificationStatus {
    C0("SNS가입"),
    C1("일반가입"),
    S0("아이디찾기"),
    S1("비밀번호찾기"),
    S2("회원정보변경");

    String value;

    ECertificationStatus(String value) {
        this.value = value;
    }
}
