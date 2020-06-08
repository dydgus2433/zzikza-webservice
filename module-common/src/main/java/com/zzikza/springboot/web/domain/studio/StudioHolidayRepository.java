package com.zzikza.springboot.web.domain.studio;

import com.zzikza.springboot.web.domain.enums.EDateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudioHolidayRepository extends JpaRepository<StudioHoliday, String> {
    List<StudioHoliday> findAllByOrderByIdAsc();
    List<StudioHoliday> findAllByStudio(Studio studio);
    List<StudioHoliday> findAllByStudioAndDateCode(Studio studio, EDateStatus dateStatus);

    Optional<StudioHoliday> findByStudioAndDateCodeAndDateValue(Studio studio, EDateStatus d, int dayNum);
}
