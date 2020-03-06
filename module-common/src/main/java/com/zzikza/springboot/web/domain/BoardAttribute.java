package com.zzikza.springboot.web.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BoardAttribute extends BaseTimeEntity{

    @Column
    protected String title;
    @Column
    protected String content;
}
