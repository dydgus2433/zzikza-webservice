package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.studio.Studio;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudioResponseDto {
    private String id;
    private String studioId;
    private String accountStatus;

    public StudioResponseDto(Studio entity){
        this.id = entity.getId();
        this.studioId = entity.getStudioId();
        this.accountStatus = entity.getAccountStatus();
    }
}
