package com.zzikza.springboot.web.domain.menu;

import com.zzikza.springboot.web.domain.enums.ETableStatus;
import com.zzikza.springboot.web.domain.enums.EUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
//    @Query("select DISTINCT a from Menu a join fetch a.menus s join fetch s.parents_menu")
    List<Menu> findAllByParentMenuIsNullAndUseStatusEquals(ETableStatus useStatus);

    List<Menu> findAllByParentMenuIsNull();
}
