package com.zzikza.springboot.web.domain.certification;

import com.zzikza.springboot.web.domain.enums.ECertificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, String> {
    Optional<Certification> findByCertificationValueAndManagerNameAndManagerTelAndCertificationStatus(String certificationValue,  String managerName, String managerTel, ECertificationStatus certificationStatus);
    Optional<Certification> findByCertificationValueAndStudioIdAndManagerTelAndCertificationStatus(String certificationValue,  String studioId, String managerTel, ECertificationStatus certificationStatus);
}
