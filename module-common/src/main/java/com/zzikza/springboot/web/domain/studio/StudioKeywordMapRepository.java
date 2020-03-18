package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioKeywordMapRepository extends JpaRepository<StudioKeywordMap, String> {
    List<StudioKeywordMap> findAllByStudio(Studio studio);
//    List<StudioKeywordMap> findAllByNotStudio(Studio studio);
}
