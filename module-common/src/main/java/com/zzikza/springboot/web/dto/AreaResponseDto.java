package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.area.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AreaResponseDto {
    private String sido;
    private String gugun;

    public AreaResponseDto(Area entity) {
        this.sido = entity.getSido();
        this.gugun = entity.getGugun();
    }
}
