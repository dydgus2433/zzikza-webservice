package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.editor.EditorFile;
import com.zzikza.springboot.web.domain.product.ProductFile;
import com.zzikza.springboot.web.domain.product.ProductFileTemp;
import com.zzikza.springboot.web.domain.request.UserRequestProductFile;
import com.zzikza.springboot.web.domain.request.UserRequestProductFileTemp;
import com.zzikza.springboot.web.domain.studio.StudioFile;
import com.zzikza.springboot.web.domain.request.UserRequestFile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponseDto implements Comparable<FileResponseDto>{

    String id;
    String fileName;
    String filePath;
    Integer fileOrder;


    public FileResponseDto(EditorFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
        this.fileOrder = 0;
    }

    public FileResponseDto(StudioFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public FileResponseDto(ProductFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public FileResponseDto(ProductFileTemp entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public FileResponseDto(UserRequestFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public FileResponseDto(UserRequestProductFileTemp entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public FileResponseDto(UserRequestProductFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFile().getFileName();
        this.filePath = entity.getFile().getFileThumbPath();
        this.fileOrder = entity.getFile().getFileOrder();
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public int compareTo(FileResponseDto o) {
        if(fileOrder != null){
            return this.fileOrder - o.getFileOrder();
        }

        return 0;
    }
}
