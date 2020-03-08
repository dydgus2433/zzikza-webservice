package com.zzikza.springboot.web.service;


import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.StudioRequestDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioResponseDto findByStudioIdAndPassword(StudioRequestDto params) {
        String stdoId = (String) params.getStudioId();
        String encPw = params.getEncodingPassword();
        return new StudioResponseDto(studioRepository.findByStudioIdAndPassword(stdoId, encPw).orElseThrow(()-> new IllegalArgumentException("회원정보가 맞지 않습니다.")));
    }

    public StudioResponseDto findById(StudioRequestDto params) {
        return new StudioResponseDto(studioRepository.findByStudioId(params.getStudioId()).orElseThrow(()-> new IllegalArgumentException("회원정보가 맞지 않습니다.")));

    }
}
