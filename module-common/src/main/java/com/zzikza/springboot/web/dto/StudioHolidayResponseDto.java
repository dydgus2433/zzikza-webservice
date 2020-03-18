package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EDateStatus;
import com.zzikza.springboot.web.domain.studio.StudioHoliday;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudioHolidayResponseDto implements Comparable<StudioHolidayResponseDto> {
    EDateStatus dateCode;

    String dateValue;

    public StudioHolidayResponseDto(StudioHoliday entity) {
        dateCode = entity.getDateCode();
        dateValue = entity.getDateValue();
    }

    public StudioHolidayResponseDto(StudioHolidayRequestDto holidayDto) {
        dateCode = holidayDto.getDateCode();
        dateValue = holidayDto.getDateValue();
    }

    public String getDateName() {
        if (dateCode.equals(EDateStatus.W)) {
            String dateName = "";
            if ("0".equals(dateValue)) {
                dateName = "일요일";
            } else if ("1".equals(dateValue)) {
                dateName = "월요일";
            } else if ("2".equals(dateValue)) {
                dateName = "화요일";
            } else if ("3".equals(dateValue)) {
                dateName = "수요일";
            } else if ("4".equals(dateValue)) {
                dateName = "목요일";
            } else if ("5".equals(dateValue)) {
                dateName = "금요일";
            } else if ("6".equals(dateValue)) {
                dateName = "토요일";
            }
            return dateName;
        }

        return dateValue;

    }


    @Override
    public int compareTo(StudioHolidayResponseDto o) {
        if(this.getDateCode().equals(o.dateCode)){
            if(this.getDateCode().equals(EDateStatus.D)){
                return this.getDateValue().compareTo(o.getDateValue());
            }
            return this.getDateValue().compareTo(o.getDateValue());
        }else{
            return this.getDateCode().compareTo(o.getDateCode());
        }


//        return this.getDateValue().compareTo(o.getDateValue());
    }
}
