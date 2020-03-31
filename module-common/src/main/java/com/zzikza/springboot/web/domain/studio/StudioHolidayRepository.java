package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.enums.EDateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioHolidayRepository extends JpaRepository<StudioHoliday, String> {
    List<StudioHoliday> findAllByOrderByIdAsc();
    List<StudioHoliday> findAllByStudio(Studio studio);
    List<StudioHoliday> findAllByStudioAndDateCode(Studio studio, EDateStatus dateStatus);
}
