package com.zzikza.springboot.web.domain;

import com.zzikza.springboot.web.domain.enums.EFileStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@Embeddable
public class FileAttribute{

    @Column(name = "FL_NM")
    public String fileName;

    @Column(name = "FL_SRC_NM")
    public String fileSourceName;

    @Column(name = "FL_SZ")
    public int fileSize;
    @Column(name = "FL_EXT")
    public String fileExt;
    @Column(name = "FL_PTH")
    public String filePath;
//    @Column(name = "FL_THB_")
//    protected String fileThumbPath;
    @Column(name = "FL_ORD")
    public Integer fileOrder;
    @Column(name = "FL_STAT_CD")
    public EFileStatus fileStatus;

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
