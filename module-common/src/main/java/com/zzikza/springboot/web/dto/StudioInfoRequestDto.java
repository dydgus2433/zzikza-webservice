package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudioInfoRequestDto {
    public String password;
    public String changePassword;
    public String businessNumber;
    public EStudioStatus accountStatus;
    public String tel;
    public String managerName;
    public String managerTel;
    public String sido;
    public String gugun;
    public String dong;
    public Double lttd;
    public Double lgtd;
}
