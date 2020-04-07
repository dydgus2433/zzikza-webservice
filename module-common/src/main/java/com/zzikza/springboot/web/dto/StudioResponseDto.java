package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.studio.Studio;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class StudioResponseDto implements Serializable {
    private String id;
    private String studioId;
    public String studioName;
    private EStudioStatus accountStatus;

    public StudioResponseDto(Studio entity){
        this.id = entity.getId();
        this.studioId = entity.getStudioId();
        this.accountStatus = entity.getAccountStatus();
        this.studioName = entity.getStudioName();
    }

    public Studio toEntity(){
        return Studio.builder()
                .studioId(studioId)
                .studioName(studioName)
                .accountStatus(accountStatus)
                .build();
    }
}
