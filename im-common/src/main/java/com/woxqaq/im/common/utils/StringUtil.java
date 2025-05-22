package com.woxqaq.im.common.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringUtil {

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str.trim());
    }
}
