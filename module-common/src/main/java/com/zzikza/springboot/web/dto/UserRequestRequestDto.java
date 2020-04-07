package com.zzikza.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestRequestDto {
    String id;

    public UserRequestRequestDto(String id) {
        this.id = id;
    }
}
