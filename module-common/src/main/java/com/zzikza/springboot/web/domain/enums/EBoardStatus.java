package com.zzikza.springboot.web.domain.enums;

public enum EBoardStatus {

    Y("정상"),
    N("삭제");
    String value;

    EBoardStatus(String value) {
        this.value = value;
    }
}
