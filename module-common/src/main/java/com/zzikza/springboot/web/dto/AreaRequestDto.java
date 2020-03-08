package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.area.Area;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AreaRequestDto {
    private String sido;
    private String gugun;

    @Builder
    public AreaRequestDto(String sido, String gugun) {
        this.sido = sido;
        this.gugun = gugun;
    }


    public Area toEntity() {
        return Area.builder()
                .sido(sido)
                .gugun(gugun)
                .build();
    }
}
