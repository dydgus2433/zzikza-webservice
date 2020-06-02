package com.zzikza.springboot.web.domain.sns;

import com.zzikza.springboot.web.domain.enums.ESnsConnectStatus;
import com.zzikza.springboot.web.domain.enums.ESnsType;
import com.zzikza.springboot.web.domain.user.User;
import lombok.Builder;

public class SnsConnector {

    String snsId;
    User user;
    ESnsConnectStatus snsConnectStatus;
    ESnsType snsType;

    @Builder
    SnsConnector(User user, String id,  ESnsConnectStatus snsConnectStatus, ESnsType snsType){
        this.user = user;
        this.snsConnectStatus = snsConnectStatus;
        this.snsType = snsType;
    }
}
