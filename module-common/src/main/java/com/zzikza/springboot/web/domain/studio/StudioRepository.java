package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, String> {
    Optional<Studio> findByStudioIdAndPassword(String stdoId, String encPw);

    Optional<Studio> findByStudioId(String studioId);
}
