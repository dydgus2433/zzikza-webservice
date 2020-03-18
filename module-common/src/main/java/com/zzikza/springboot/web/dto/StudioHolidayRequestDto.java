package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EDateStatus;
import com.zzikza.springboot.web.domain.studio.StudioHoliday;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioHolidayRequestDto {
    EDateStatus dateCode;

    String dateValue;

    public StudioHolidayRequestDto(String dateCode, String dateValue) {
        this.dateCode = EDateStatus.valueOf(dateCode);
        this.dateValue = dateValue;
    }

    public StudioHoliday getEntity() {
        return StudioHoliday.builder()
                .dateCode(dateCode)
                .dateValue(dateValue)
                .build();
    }

    @Builder
    public StudioHolidayRequestDto(EDateStatus dateCode, String dateValue) {
        this.dateCode = dateCode;
        this.dateValue = dateValue;
    }
}
