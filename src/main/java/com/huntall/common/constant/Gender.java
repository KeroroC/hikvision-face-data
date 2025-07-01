package com.huntall.common.constant;

public enum Gender {
    MALE((byte) 1, "男"),
    FEMALE((byte) 2, "女");

    private final byte code;

    private final String name;

    Gender(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据代码值获取中文名
     * @param code 性别代码值
     * @return 性别中文名称/null
     */
    public static String getGenderName(byte code) {
        for (Gender gender : Gender.values()) {
            if (code == gender.code) {
                return gender.name;
            }
        }

        return null;
    }
}
