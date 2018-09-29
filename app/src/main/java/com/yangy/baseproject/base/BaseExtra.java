package com.yangy.baseproject.base;

import java.io.Serializable;

public abstract class BaseExtra implements Serializable {
    private static final long serialVersionUID = 1L;

    private static String sExtraName;

    public static String getExtraName() {
        return sExtraName;
    }

    public static void setExtraName(String extraName) {
        sExtraName = extraName;
    }

}
