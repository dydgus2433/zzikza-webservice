package com.zzikza.springboot.web.domain.enums;

public enum EKeywordCode {

    상품("tb_prd"),
    스튜디오("tb_stdo");
    String value;

    EKeywordCode(String value) {
        this.value = value;
    }
}
