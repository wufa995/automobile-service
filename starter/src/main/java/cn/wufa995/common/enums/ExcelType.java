package cn.wufa995.common.enums;

import cn.wufa995.common.enums.AbstractEnum;

public enum ExcelType implements AbstractEnum {
    NORMAL(0, "普通"),
    ROW_GROUP(2, "行分组带样式"),
    ROW_ODD(2, "隔行带样式");

    private int code;
    private String name;

    private ExcelType(int code, String name) {
        this.setCode(code);
        this.setName(name);
    }

    @Override
    public int getCode() {
        return this.code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
