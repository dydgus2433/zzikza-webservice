package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioQuestionRepository extends JpaRepository<StudioQuestion, String> {
    Page<StudioQuestion> findAllByStudio(Studio studio, Pageable pageable);
}

