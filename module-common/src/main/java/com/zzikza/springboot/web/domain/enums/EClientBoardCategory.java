package com.zzikza.springboot.web.domain.enums;

public enum EClientBoardCategory {

    event("이벤트"),
    notice("공지사항")
    ;
    String value;

    EClientBoardCategory(String value) {
        this.value = value;
    }
}
