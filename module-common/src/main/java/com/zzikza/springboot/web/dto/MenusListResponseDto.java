package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.menu.Menu;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenusListResponseDto {
    private String id;
    private String menuName;
    private List<MenusListResponseDto> childrenMenus;

    public MenusListResponseDto(Menu entity){
        this.id = entity.getId();
        this.menuName = entity.getMenuName();
        this.childrenMenus = entity.getMenus().stream().map(MenusListResponseDto::new)
                .collect(Collectors.toList());;
    }
}
