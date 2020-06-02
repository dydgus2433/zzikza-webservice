package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.StudioFile;
import lombok.Getter;

@Getter
public class StudioFileResponseDto {


    String fileName;
    String filePath;
    private String id;


    public StudioFileResponseDto(StudioFile entity) {
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
        this.id = entity.getId();
    }
}
