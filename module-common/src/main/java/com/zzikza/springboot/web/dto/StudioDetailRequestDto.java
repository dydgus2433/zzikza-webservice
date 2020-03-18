package com.zzikza.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudioDetailRequestDto {
    String studioDescription;
    int openTime;
    int closeTime;
    int weekendOpenTime;
    int weekendCloseTime;

    @Builder
    public StudioDetailRequestDto(String studioDescription, int openTime, int closeTime, int weekendOpenTime, int weekendCloseTime) {
        this.studioDescription = studioDescription;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
    }
}
