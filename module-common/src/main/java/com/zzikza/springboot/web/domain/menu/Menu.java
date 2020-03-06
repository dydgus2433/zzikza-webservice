package com.zzikza.springboot.web.domain.menu;

import com.zzikza.springboot.web.domain.BaseTimeEntity;
import com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "tb_menu")
public class Menu  extends BaseTimeEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.zzikza.springboot.web.domain.sequence.CustomPrefixTableSequnceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "sequences"),
            @org.hibernate.annotations.Parameter(name = "value_column_name", value = "currval"),
            @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "name"),
            @org.hibernate.annotations.Parameter(name = "segment_value", value = "tb_menu"),
            @org.hibernate.annotations.Parameter(name = "prefix_key", value = "MN"),
            @Parameter(name = CustomPrefixTableSequnceGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")})
    String id;

    String menuName;


    @ManyToOne
    @JoinColumn(name = "PARENT_MENU_ID")
    Menu parentMenu;

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
    List<Menu> menus = new ArrayList<>();

    @Builder
    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
    }

    public void addMenu(Menu menu){
        this.menus.add(menu);
        menu.setParentMenu(this);
    }

    public List<Menu> getMenus() {
        if(menus.isEmpty()){
            throw new IllegalArgumentException("서브메뉴가 없습니다.");
        }
        return menus;
    }
}
