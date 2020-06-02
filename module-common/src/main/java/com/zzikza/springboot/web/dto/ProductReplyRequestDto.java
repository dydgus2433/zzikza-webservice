package com.zzikza.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductReplyRequestDto {
    Double grade;
    String id;
    String content;

    ProductReplyRequestDto( Double grade, String id, String content){
        this.grade = grade;
        this.id = id;
        this.content = content;
    }
}
