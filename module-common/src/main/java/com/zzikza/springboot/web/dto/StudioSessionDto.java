package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.studio.Studio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class StudioSessionDto implements Serializable {
    private String id;
    private String studioId;
    public String studioName;
    private EStudioStatus accountStatus;


    public StudioSessionDto(Studio entity){
        this.id = entity.getId();
        this.studioId = entity.getStudioId();
        this.accountStatus = entity.getAccountStatus();
        this.studioName = entity.getStudioName();
    }
}
