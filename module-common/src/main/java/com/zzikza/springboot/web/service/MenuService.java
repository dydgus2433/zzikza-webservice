package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.menu.MenuRepository;
import com.zzikza.springboot.web.domain.posts.Posts;
import com.zzikza.springboot.web.dto.MenusListResponseDto;
import com.zzikza.springboot.web.dto.PostsListResponseDto;
import com.zzikza.springboot.web.dto.PostsResponseDto;
import com.zzikza.springboot.web.dto.StudioRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenusListResponseDto> findAllDepth1() {
        return menuRepository.findAll().stream()
                .filter(menu -> menu.getParentMenu() == null)
                .map(MenusListResponseDto::new)
                .collect(Collectors.toList());
    }

}
