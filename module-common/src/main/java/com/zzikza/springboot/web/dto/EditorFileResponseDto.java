package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.editor.EditorFile;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import lombok.Getter;

@Getter
public class EditorFileResponseDto {

    String fileName;
    String filePath;

    private String id;

    public EditorFileResponseDto(EditorFile entity) {
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
        this.id = entity.getId();
    }
}
