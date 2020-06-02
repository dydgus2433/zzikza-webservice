package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.user.User;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    public String changePassword;
    public ETermStatus useTermYn;
    public ETermStatus cancelTermYn;
    public ETermStatus privateTermYn;
    public ETermStatus otherTermYn;
    String userId;
    String tel;
    String password;
    EUserStatus userStatus;
    ESnsType snsType;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(getEncodingPassword())
                .tel(tel)
                .useTermYn(useTermYn)
                .cancelTermYn(cancelTermYn)
                .privateTermYn(privateTermYn)
                .otherTermYn(otherTermYn)
                .snsType(snsType)
                .build();
    }

    public String getEncodingPassword() {
        return PasswordUtil.getEncriptPassword(password, userId);
    }

}
