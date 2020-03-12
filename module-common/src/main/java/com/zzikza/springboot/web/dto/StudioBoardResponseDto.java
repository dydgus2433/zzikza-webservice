package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.StudioBoard;
import lombok.Getter;

@Getter
public class StudioBoardResponseDto {

    private String id;

    public StudioBoardResponseDto(StudioBoard entity) {
        this.id = entity.getId();
    }
}
