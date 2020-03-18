package com.zzikza.springboot.web.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioHolidayRepository extends JpaRepository<StudioHoliday, String> {
    List<StudioHoliday> findAllByOrderByIdAsc();
    List<StudioHoliday> findAllByStudio(Studio studio);
}
