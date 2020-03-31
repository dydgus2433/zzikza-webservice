package com.zzikza.springboot.web.domain.enums;

public enum EReservationStatus implements EnumModel {

    C("예약취소"),
    F("예약실패"),
    N("예약반려"),
    W("예약대기"),
    Y("예약완료");
    String value;
    EReservationStatus(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
