package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.editor.EditorFile;
import com.zzikza.springboot.web.domain.enums.EFileStatus;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EditorFileDto {

    public String fileName;
    public String fileSourceName;
    public Long fileSize;
    public String fileExt;
    public String filePath;
    public Integer fileOrder;
    public EFileStatus fileStatus;

    @Builder
    public EditorFileDto(String fileName, String fileSourceName, Long fileSize, String fileExt, String filePath, Integer fileOrder, EFileStatus fileStatus) {
        this.fileName = fileName;
        this.fileSourceName = fileSourceName;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.filePath = filePath;
        this.fileOrder = fileOrder;
        this.fileStatus = fileStatus;
    }

    public EditorFileDto(FileAttribute fileAttribute) {
        this.fileName = fileAttribute.getFileName();
        this.fileSourceName = fileAttribute.fileSourceName;
        this.fileSize = fileAttribute.fileSize;
        this.fileExt = fileAttribute.fileExt;
        this.filePath = fileAttribute.filePath;
        this.fileOrder = fileAttribute.fileOrder;
        this.fileStatus = fileAttribute.fileStatus;
    }

    public EditorFile toEntity() {
        return EditorFile.builder()
                .fileName(getFileName())
                .fileSourceName(getFileSourceName())
                .fileSize(getFileSize())
                .fileExt(getFileExt())
                .filePath(getFilePath())
                .fileOrder(getFileOrder())
                .fileStatus(getFileStatus())
                .build();
    }
}
