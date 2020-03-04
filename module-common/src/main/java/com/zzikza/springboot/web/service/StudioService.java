package com.zzikza.springboot.web.service;


import com.zzikza.springboot.web.domain.studio.StudioBoardFileRepository;
import com.zzikza.springboot.web.domain.studio.StudioBoardRepository;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudioService {

    private StudioRepository studioRepository;

    private StudioBoardRepository studioBoardRepository;

    private StudioBoardFileRepository studioBoardFileRepository;


}
