package com.zzikza.springboot.web.domain.enums;

public enum EQuestionCategory  implements EnumModel {
    PR("상품문의"),
    SC("일정문의");
    String value;
    EQuestionCategory(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
