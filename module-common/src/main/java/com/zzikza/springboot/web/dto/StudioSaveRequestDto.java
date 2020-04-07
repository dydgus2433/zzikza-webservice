package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EStudioStatus;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioDetail;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudioSaveRequestDto {
    //{studioId=tester2, password=dkssud1, passwordChk=dkssud1, studioName=찍자컴퍼티, tel=01063626542, postCode=06302,
    // addr=서울 강남구 양재천로 167, addrDtl=ddd, managerName=리롱센, managerTel=01022772433, managerEmail=tester1@d.cd,
    // businessNumber=8935300335, requiredTermStatus=Y, sido=서울, gugun=강남구, dong=도곡동, lttd=37.4816284, lgtd=127.0447061}
    String studioId;
    String password;
    String passwordChk;
    String studioName;
    EStudioStatus studioStatus;
    String tel;
    String postCode;
    String addr;
    String addrDtl;
    String managerName;
    String managerTel;
    String managerEmail;
    String businessNumber;
    ETermStatus requiredTermStatus;
    String sido;
    String gugun;
    String dong;
    String lttd;
    String lgtd;

    public Studio toEntity() {
        String encPassword = PasswordUtil.getEncriptPassword(password, studioId);


        return Studio.builder()
                .password(encPassword)
                .studioId(studioId)
                .studioName(studioName)
                .accountStatus(studioStatus)
                //스튜디오상태
                .accountStatus(EStudioStatus.Y)
                .tel(tel)
                .postCode(postCode)
                .address(addr)
                .addressDetail(addrDtl)
                .managerName(managerName)
                .managerTel(managerTel)
                .managerEmail(managerEmail)
                .businessNumber(businessNumber)
                .sido(sido)
                .gugun(gugun)
                .dong(dong)
                .requiredTermStatus(requiredTermStatus)

                .lttd(Double.parseDouble(lttd))
                .lgtd(Double.parseDouble(lgtd))
                .build();
    }
}
