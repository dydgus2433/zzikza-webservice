package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.enums.ETableStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import com.zzikza.springboot.web.domain.menu.MenuRepository;
import com.zzikza.springboot.web.dto.MenusListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    @Transactional(readOnly = true)
    public List<MenusListResponseDto> findAllByParentMenuIsNull() {
        return menuRepository.findAllByParentMenuIsNull().stream()
//                .filter(menu -> menu.getParentMenu() == null)
                .map(MenusListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MenusListResponseDto> findAllByParentMenuIsNullAndUseStatusEquals(ETableStatus useStatus) {
        return menuRepository.findAllByParentMenuIsNullAndUseStatusEquals(useStatus).stream()
                .map(MenusListResponseDto::new)
                .collect(Collectors.toList());
    }
}
