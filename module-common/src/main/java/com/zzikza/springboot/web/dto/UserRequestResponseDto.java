package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EUserRequestStatus;
import com.zzikza.springboot.web.domain.request.UserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserRequestResponseDto {

    String id;
    String userName;
    String title;
    String content;
    String createDate;
    EUserRequestStatus requestStatus;

    boolean getIsWait(){
        return requestStatus.equals(EUserRequestStatus.W);
    }

    public UserRequestResponseDto(UserRequest entity){
        id = entity.getId();
        userName = entity.getUser().getName();
        title = entity.getTitle();
        content = entity.getContent();
        requestStatus = entity.getRequestStatus();
        createDate = entity.getCreatedDate();
    }

}
