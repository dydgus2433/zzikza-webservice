package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.posts.Posts;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudioRequestDto {
    private String studioId;
    private String password;
    private String accountStatus;

    @Builder
    public StudioRequestDto(String studioId, String password, String accountStatus){
        this.studioId = studioId;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    public Studio toEntity() {
        return Studio.builder()
                .studioId(studioId)
                .password(getEncodingPassword())
                .accountStatus(accountStatus)
                .build();
    }

    public String getEncodingPassword() {
        return PasswordUtil.getEncriptPassword(password, studioId);
    }
}
