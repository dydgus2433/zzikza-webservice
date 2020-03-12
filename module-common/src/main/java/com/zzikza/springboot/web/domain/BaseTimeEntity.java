package com.zzikza.springboot.web.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(name = "REG_ID")
    protected  String registedId;
    @Column(name = "MOD_ID")
    protected String modifiedId;
    @Column(name = "DEL_ID")
    protected String deletedId;

    @CreatedDate
    @Column(name = "REG_DT", updatable = false)
    private LocalDateTime registedDate;


    @LastModifiedDate
    @Column(name = "MOD_DT")
    private LocalDateTime modifiedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "DEL_DT")
    protected LocalDateTime deleteDate;

    public String getCreatedDate(){
        if(registedDate == null){
            return "";
        }
        return registedDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"));
    }
}
