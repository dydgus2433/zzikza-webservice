package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioFileRepository extends JpaRepository<StudioFile, String> {
    List<StudioFile> findAllByStudio(Studio studio);
}

