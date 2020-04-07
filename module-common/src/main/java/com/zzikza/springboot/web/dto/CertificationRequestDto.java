package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.certification.Certification;
import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CertificationRequestDto {
    String managerName;
    String managerTel;
    String studioId;
    String certificationValue;

    ECertificationStatus certificationStatus;

    public Certification toEntity() {
        return Certification.builder()
                .certificationStatus(certificationStatus)
                .certificationValue(certificationValue)
                .studioId(studioId)
                .managerTel(managerTel)
                .managerName(managerName)
                .build();
    }

}
