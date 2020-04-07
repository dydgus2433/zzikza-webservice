package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, String> {
    Optional<Studio> findByStudioIdAndPassword(String studioId, String encPassword);

    Optional<Studio> findByStudioId(String studioId);

    Optional<Studio> findByManagerTel(String managerTel);
    Optional<Studio> findByManagerTelAndRegistedId(String managerTel, String registedId);
}
