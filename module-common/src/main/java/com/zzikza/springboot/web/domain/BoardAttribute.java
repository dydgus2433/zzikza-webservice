package com.zzikza.springboot.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class BoardAttribute {

    @Column(name = "TITLE")
    protected String title;
    @Column(name = "CONTENT" ,columnDefinition="TEXT" )
    protected String content;
}
