package com.zzikza.springboot.web.domain.enums;

public enum EBoardCategory {


    FAQ("faq"),
    공지사항("notice"),
    QNA("qna")
    ;
    String value;

    EBoardCategory(String value) {
        this.value = value;
    }
}
