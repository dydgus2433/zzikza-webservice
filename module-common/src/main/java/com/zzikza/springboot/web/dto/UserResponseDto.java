package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.user.User;

public class UserResponseDto {

    String userName;

    public UserResponseDto(User user) {
        this.userName = user.getName();
    }
}
