package com.zzikza.springboot.web.domain;

import com.zzikza.springboot.web.domain.enums.EFileStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class FileAttribute extends BaseTimeEntity{

    @Column(name = "FL_NM")
    protected String fileName;

    protected String fileSourceName;
    protected int fileSize;
    protected String fileExt;
    protected String filePath;
    protected String fileThumbPath;
    protected int fileOrder;
    protected EFileStatus fileStatus;

//    @Builder
//    public FileAttribute(String fileName, String fileSourceName, int fileSize, String fileExt, String filePath, String fileThumbPath, int fileOrder, EFileStatus fileStatus) {
//        this.fileName = fileName;
//        this.fileSourceName = fileSourceName;
//        this.fileSize = fileSize;
//        this.fileExt = fileExt;
//        this.filePath = filePath;
//        this.fileThumbPath = fileThumbPath;
//        this.fileOrder = fileOrder;
//        this.fileStatus = fileStatus;
//    }
}
