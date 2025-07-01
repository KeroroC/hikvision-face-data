package com.huntall.common.constant;

public enum Nation {
    HAN((byte) 1, "汉"),
    MONGOLIAN((byte) 2, "蒙古"),
    HUI((byte) 3, "回"),
    TIBETAN((byte) 4, "藏"),
    UYGHUR((byte) 5, "维吾尔"),
    HMONG((byte) 6, "苗"),
    YI((byte) 7, "彝"),
    ZHUANG((byte) 8, "壮"),
    BUYEI((byte) 9, "布依"),
    KOREAN((byte) 10, "朝鲜"),
    MANCHU((byte) 11, "满"),
    DONG((byte) 12, "侗"),
    YAO((byte) 13, "瑶"),
    BAI((byte) 14, "白"),
    TUJIA((byte) 15, "土家"),
    HANI((byte) 16, "哈尼"),
    KAZAKH((byte) 17, "哈萨克"),
    DAI((byte) 18, "傣"),
    LI((byte) 19, "黎"),
    LISU((byte) 20, "傈僳"),
    VA((byte) 21, "佤"),
    SHE((byte) 22, "畲"),
    GAOSHAN((byte) 23, "高山"),
    LAHU((byte) 24, "拉祜"),
    SHUI((byte) 25, "水"),
    DONGXIANG((byte) 26, "东乡"),
    NAKHI((byte) 27, "纳西"),
    JINGPO((byte) 28, "景颇"),
    KYRGYZ((byte) 29, "柯尔克孜"),
    MONGUOR((byte) 30, "土"),
    DAUR((byte) 31, "达斡尔"),
    MULAO((byte) 32, "仫佬"),
    QIANG((byte) 33, "羌"),
    BLANG((byte) 34, "布朗"),
    SALAR((byte) 35, "撒拉"),
    MAONAN((byte) 36, "毛南"),
    GELAO((byte) 37, "仡佬"),
    XIBE((byte) 38, "锡伯"),
    ACHANG((byte) 39, "阿昌"),
    PUMI((byte) 40, "普米"),
    TAJIK((byte) 41, "塔吉克"),
    NU((byte) 42, "怒"),
    UZBEK((byte) 43, "乌兹别克"),
    RUSSIAN((byte) 44, "俄罗斯"),
    EVENK((byte) 45, "鄂温克"),
    DEANG((byte) 46, "德昂"),
    BONAN((byte) 47, "保安"),
    YUGHUR((byte) 48, "裕固"),
    KINH((byte) 49, "京"),
    TATAR((byte) 50, "塔塔尔"),
    DERUNG((byte) 51, "独龙"),
    OROQEN((byte) 52, "鄂伦春"),
    NANAI((byte) 53, "赫哲"),
    MONPA((byte) 54, "门巴"),
    LHOBA((byte) 55, "珞巴"),
    JINO((byte) 56, "基诺");

    private final byte code;

    private final String name;

    Nation(byte code, String name) {
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
     * @param code 民族代码值
     * @return 民族中文名称/null
     */
    public static String getNationName(byte code) {
        for (Nation nation : Nation.values()) {
            if (code == nation.code) {
                return nation.name;
            }
        }

        return null;
    }
}
