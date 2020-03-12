package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioBoardRepository extends JpaRepository<StudioBoard, String> {
    Page<StudioBoard> findAllByBoardCategoryCodeEquals(EBoardCategory boardCategory , Pageable pageable);
    List<StudioBoard> findAllByStudio(Studio studio);
}
