package com.zzikza.springboot.web.domain.enums;

public enum EReservationStatus {

    예약취소("C"),
    예약실패("F"),
    예약반려("N"),
    예약대기("W"),
    예약완료("Y");
    String value;
    EReservationStatus(String value) {
        this.value = value;
    }
}
