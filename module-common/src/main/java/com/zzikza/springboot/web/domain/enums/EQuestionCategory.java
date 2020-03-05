package com.zzikza.springboot.web.domain.enums;

public enum EQuestionCategory {
    상품문의("PR"),
    일정문의("SC");
    String value;
    EQuestionCategory(String value) {
        this.value = value;
    }
}
