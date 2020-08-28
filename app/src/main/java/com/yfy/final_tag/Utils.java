package com.yfy.final_tag;

import android.text.TextUtils;

import java.util.Collection;

/**
 * 封装判断是否为 空
 */
public class Utils {

    /**
     * 判断一个对象是否为空
     */
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个对象是否不为空
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }


    /**
     * 判断是否为空字符串
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否不为空字符串
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断是否为空集合
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否不为空集合
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 比较
     */
    public static boolean equals(Object text1, Object text2) {
        if (text1 == null || text2 == null) {
            return false;
        }
        return TextUtils.equals(text1.toString(), text2.toString());
    }
}
