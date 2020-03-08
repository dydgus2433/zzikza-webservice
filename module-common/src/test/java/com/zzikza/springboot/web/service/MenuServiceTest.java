package com.zzikza.springboot.web.service;


import com.zzikza.springboot.web.dto.MenusListResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTest {

    @Autowired
    MenuService menuService;

    @Test
    public void 메뉴리스트_테스트() {

        //given
        //when
        List<MenusListResponseDto> menuList = menuService.findAllDepth1();
        int count = 0;
		for (MenusListResponseDto menu : menuList) {

            System.out.println(menu.getMenuName());
            List<MenusListResponseDto> menuList2 = menu.getChildrenMenus();
            for (MenusListResponseDto menu2 : menuList2) {
                System.out.println("    "+menu2.getMenuName());
                count++;
            }
            count++;
        }
        System.out.println(count);

        //then
    }
}