package com.zzikza.springboot.web.domain.enums;

public enum EKeywordCode {

    tb_prd("상품"),
    tb_stdo("스튜디오");
    String value;

    EKeywordCode(String value) {
        this.value = value;
    }
}
