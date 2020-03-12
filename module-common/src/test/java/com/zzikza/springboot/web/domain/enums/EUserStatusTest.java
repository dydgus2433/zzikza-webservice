package com.zzikza.springboot.web.domain.enums;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class EUserStatusTest {

    @Test
    public void EnumModel타입확인() {

        //given
        List<EnumModel> enumModels = new ArrayList<>();
        enumModels.add(EUserStatus.N);
        enumModels.add(EUserStatus.Y);
        //when

        //then
        assertThat(enumModels.get(0).getValue()).isEqualTo("N");
        assertThat(enumModels.get(1).getValue()).isEqualTo("Y");
    }
}