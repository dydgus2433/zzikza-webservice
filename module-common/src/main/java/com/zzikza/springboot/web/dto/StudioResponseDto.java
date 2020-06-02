package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class StudioResponseDto implements Serializable {
    private String id;
    private String studioId;
    public String studioName;
    public String tel;
    private EStudioStatus accountStatus;

    public StudioResponseDto(Studio entity){
        this.id = entity.getId();
        this.studioId = entity.getStudioId();
        this.accountStatus = entity.getAccountStatus();
        this.studioName = entity.getStudioName();
        this.tel = entity.getTel();
    }

    public Studio toEntity(){
        return Studio.builder()
                .studioId(studioId)
                .studioName(studioName)
                .accountStatus(accountStatus)
                .tel(tel)
                .build();
    }

    public String getTel(){
        return StringUtil.formatPhone(this.tel);
    }
}
