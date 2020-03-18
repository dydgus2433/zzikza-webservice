package com.zzikza.springboot.web.studio;

import com.zzikza.springboot.web.annotaion.LoginStudio;
import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.dto.FileResponseDto;
import com.zzikza.springboot.web.dto.StudioHolidayResponseDto;
import com.zzikza.springboot.web.dto.StudioKeywordResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class StudioViewController {

    private final StudioRepository studioRepository;
    private final StudioFileRepository studioFileRepository;

    private final String STORE_VIEW = "/store/storeView";

    private final StudioKeywordMapRepository studioKeywordMapRepository;
    private final StudioKeywordRepository studioKeywordRepository;
    private final StudioHolidayRepository studioHolidayRepository;

    @GetMapping(value = "/store/view")
    @Transactional
    public String storeViewPage(@LoginStudio StudioResponseDto sessionVo, Model model) {


        if (sessionVo == null) {
            try {
                throw new IllegalAccessException("로그인 해주세요.");
//        에러시 페이지 이동 해야함
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //detail
//        assert sessionVo != null;
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("해당 스튜디오가 존재하지 않습니다."));
        model.addAttribute(new StudioResponseDto(studio));
        //files

        List<StudioFile> files = studio.getStudioFiles();


        if (!files.isEmpty()) {
            List<FileResponseDto> studioFiles = files.stream().map(FileResponseDto::new).sorted().collect(Collectors.toList());
            model.addAttribute("files", studioFiles);
            model.addAttribute("file-size", studioFiles.size());
        }
        model.addAttribute("file-empty", files.isEmpty());

        StudioDetail studioDetail = studio.getStudioDetail();
        model.addAttribute("detail", studioDetail);
        List<Map<String, Object>> times = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Map<String, Object> time = new HashMap<>();
            time.put("idx", i);
            time.put("is-open-time", studioDetail.getOpenTime() == i);
            time.put("is-end-time", studioDetail.getCloseTime() == i);
            time.put("is-weekend-start-time", studioDetail.getWeekendOpenTime() == i);
            time.put("is-weekend-end-time", studioDetail.getWeekendCloseTime() == i);
            times.add(time);
        }
        model.addAttribute("times", times);

        List<StudioHoliday> studioHolidays = studioHolidayRepository.findAllByOrderByIdAsc();

        List<StudioHolidayResponseDto> studioHolidayResponseDtos = studioHolidays.stream()
                .map(StudioHolidayResponseDto::new).sorted()
                .collect(Collectors.toList());

        model.addAttribute("holidays", studioHolidayResponseDtos);
        List<StudioKeywordMap> studioKeywordMaps = studioKeywordMapRepository.findAllByStudio(studio);// studio.getStudioKeywordMaps();

        List<StudioKeywordResponseDto> studioKeywords = studioKeywordMaps.stream()
                .filter(Objects::nonNull).map((keywordMap) -> new StudioKeywordResponseDto(keywordMap.getStudioKeyword()))
                .collect(Collectors.toList());

        List<StudioKeyword> studioNoKeywordMaps = studioKeywordRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        List<StudioKeywordResponseDto> collect = studioNoKeywordMaps.stream()
                .map(StudioKeywordResponseDto::new)
                .collect(Collectors.toList());

        List<StudioKeywordResponseDto> noChoiceKeywords = collect.stream().filter((a) -> !studioKeywords.contains(a))
                .map((a) -> new StudioKeywordResponseDto(a.getId(), a.getKeywordName()))
                .collect(Collectors.toList());
        model.addAttribute("keywordMaps", studioKeywords);
        model.addAttribute("NoKeywordMaps", noChoiceKeywords);

        model.addAttribute("title", "찍자사장님 사이트 - 매장정보");
        return STORE_VIEW;
    }
}
