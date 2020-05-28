package com.hanhan.store.model.po;

import com.hanhan.store.model.IsEnum;

public enum AppSettingClassLoadType implements IsEnum {

    LOCAL(0), REMOTE(1), JSON_SCHEMA(2);

    private int code;

    AppSettingClassLoadType(int v) {
        this.code = v;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.name();
    }
}
