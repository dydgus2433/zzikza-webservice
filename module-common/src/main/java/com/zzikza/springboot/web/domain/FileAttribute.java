package com.zzikza.springboot.web.domain;

import com.zzikza.springboot.web.domain.enums.EFileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class FileAttribute {

    @Column(name = "FL_NM")
    public String fileName;

    @Column(name = "FL_SRC_NM")
    public String fileSourceName;

    @Column(name = "FL_SZ")
    public Long fileSize;

    @Column(name = "FL_EXT")
    public String fileExt;

    @Column(name = "FL_PTH")
    public String filePath;


    @Column(name = "FL_THUMB_PTH")
    public String fileThumbPath;

    @Column(name = "FL_MIDSIZE_PTH")
    public String fileMidsizePath;

    @Column(name = "FL_LARGE_PTH")
    public String fileLargePath;


    @Column(name = "FL_ORD")
    public Integer fileOrder;

    @Column(name = "FL_STAT_CD")
    public EFileStatus fileStatus;

}
