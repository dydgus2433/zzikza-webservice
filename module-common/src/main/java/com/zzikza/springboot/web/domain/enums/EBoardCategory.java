package com.zzikza.springboot.web.domain.enums;

public enum EBoardCategory implements EnumModel{
    notice("공지사항"),
    qna("Q&A"),
    faq("FAQ");
    String value;

    EBoardCategory(String value) {
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
