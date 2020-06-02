package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.ESnsConnectStatus;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnsUserRequestDto {
    public ETermStatus useTermYn;
    public ETermStatus cancelTermYn;
    public ETermStatus privateTermYn;
    public ETermStatus otherTermYn;
    String userId;
    String userName;
    String tel;
    EUserStatus userStatus;
    ESnsConnectStatus snsConnectStatus;
    ESnsType snsType;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .name(userName)
                .userStatus(userStatus)
                .snsConnectStatus(snsConnectStatus)
                .snsType(snsType)
                .tel(tel)
                .useTermYn(useTermYn)
                .cancelTermYn(cancelTermYn)
                .privateTermYn(privateTermYn)
                .otherTermYn(otherTermYn)
                .build();
    }

}
