package com.zzikza.springboot.web.domain.menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    MenuRepository menuRepository;

    @Test
    @Transactional
    public void 메뉴2DEPTH() {

        //given
        Menu menuDepth1 = Menu.builder().menuName("1뎁스").build();
        em.persist(menuDepth1);
        Menu menuDepth2 = Menu.builder().menuName("2뎁스").build();

        em.persist(menuDepth2);
        menuDepth1.addMenu(menuDepth2);
        em.flush();
        em.clear();

        //when
        //then
        Menu expectedMenu = menuRepository.findById(menuDepth1.getId()).orElseThrow(()-> new IllegalArgumentException("메뉴X"));
        assertThat(expectedMenu.getMenus().get(0).getMenuName()).isEqualTo(menuDepth2.getMenuName());
    }

    @Test
    @Transactional
    public void 메뉴3DEPTH() {

        //given
        Menu menuDepth1 = Menu.builder().menuName("1뎁스").build();
        em.persist(menuDepth1);
        Menu menuDepth2 = Menu.builder().menuName("2뎁스").build();
        em.persist(menuDepth2);
        Menu menuDepth3 = Menu.builder().menuName("3뎁스").build();
        em.persist(menuDepth3);
        menuDepth1.addMenu(menuDepth2);
        menuDepth2.addMenu(menuDepth3);
        em.flush();
        em.clear();

        //when
        //then
        Menu expectedMenu = menuRepository.findById(menuDepth1.getId()).orElseThrow(()-> new IllegalArgumentException("메뉴X"));
        assertThat(expectedMenu.getMenus().get(0).getMenuName()).isEqualTo(menuDepth2.getMenuName());
        assertThat(expectedMenu.getMenus().get(0).getMenus().get(0).getMenuName()).isEqualTo(menuDepth3.getMenuName());
    }


    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void 없는메뉴() {

        //given
        Menu menuDepth1 = Menu.builder().menuName("1뎁스").build();
        em.persist(menuDepth1);
        Menu menuDepth2 = Menu.builder().menuName("2뎁스").build();
        em.persist(menuDepth2);
        Menu menuDepth3 = Menu.builder().menuName("3뎁스").build();
        em.persist(menuDepth3);
        menuDepth1.addMenu(menuDepth2);
        menuDepth2.addMenu(menuDepth3);
        em.flush();
        em.clear();

        //when
        //then
        Menu expectedMenu = menuRepository.findById(menuDepth1.getId()).orElseThrow(()-> new IllegalArgumentException("메뉴X"));
        assertThat(expectedMenu.getMenus().get(0).getMenuName()).isEqualTo(menuDepth2.getMenuName());
        assertThat(expectedMenu.getMenus().get(0).getMenus().get(0).getMenus().get(0)).is(null);
    }
}