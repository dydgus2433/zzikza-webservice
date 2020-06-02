package com.zzikza.springboot.web.domain.enums;

public enum ESnsType implements EnumModel {
    FACEBOOK("페이스북"),
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글"),
    NORMAL("일반");
    String value;

    ESnsType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
