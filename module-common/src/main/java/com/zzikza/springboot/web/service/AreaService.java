package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.area.Area;
import com.zzikza.springboot.web.domain.area.AreaRepository;
import com.zzikza.springboot.web.dto.AreaRequestDto;
import com.zzikza.springboot.web.dto.AreaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AreaService {
    private final AreaRepository areaRepository;

    public List<AreaResponseDto> findBySido(String sido){
        return areaRepository.findBySido(sido)
                .stream()
                .map(AreaResponseDto::new)
                .collect(Collectors.toList());
    }
}
