package com.zzikza.springboot.web.domain.enums;

public enum EClientBoardCategory {

    이벤트("event"),
    공지사항("notice")
    ;
    String value;

    EClientBoardCategory(String value) {
        this.value = value;
    }
}
