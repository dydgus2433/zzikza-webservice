package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    String id;
    String userName;
    String userId;
    EUserStatus userStatus;
    ESnsType snsType;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getName();
        this.userStatus = user.getUserStatus();
        this.snsType = user.getSnsType();

    }


    public UserResponseDto(SnsUserRequestDto user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userStatus = user.getUserStatus();
        this.snsType = user.getSnsType();

    }

    public boolean isNormal(){
        return snsType.equals(ESnsType.NORMAL);
    }
}
