package com.zzikza.springboot.web.domain.enums;

public enum EQuestionCategory {
    PR("상품문의"),
    SC("일정문의");
    String value;
    EQuestionCategory(String value) {
        this.value = value;
    }
}
