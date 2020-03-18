package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioKeywordRepository extends JpaRepository<StudioKeyword, String> {
//    List<StudioKeyword> findAllOrderByKeywordNameDesc();
//    List<StudioKeyword> findAllByStudio(Studio studio);
}
