package com.zzikza.springboot.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "REG_ID")
    protected  String registedId;
    @Column(name = "MOD_ID")
    protected String modifiedId;
    @Column(name = "DEL_ID")
    protected String deletedId;

    @CreatedDate
    @Column(name = "REG_DT")
    private LocalDateTime registedDate;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    private LocalDateTime modifiedDate;

    @Column(name = "DEL_DT")
    protected LocalDateTime deleteDate;

    public void setRegistedId(String registedId) {
        this.registedId = registedId;
    }
}
