package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CertificationClientRequestDto {
    String userName;
    String tel;
    String userId;
    String certificationValue;

    ECertificationStatus certificationStatus;

    public Certification toEntity() {
        return Certification.builder()
                .certificationStatus(certificationStatus)
                .certificationValue(certificationValue)
                .userId(userId)
                .tel(tel)
                .userName(userName)
                .build();
    }

}
