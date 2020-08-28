package com.yfy.final_tag;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aj Liao on 2016/2/15.
 */
public class RegexUtils {
    /**
     * 匹配手机号码的正则表达式
     * <br>支持130——139、150——153、155——159、180、183、185、186、188、189号段
     */


    public static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|4{1}\\d{1}|5{1}\\d{1}|7{1}\\d{1}|8{1}\\d{1}|9{1}\\d{1})\\d{8}$";//
    /**
     *
     */
    /**
     * 匹配邮箱的正则表达式
     * <br>"www."可省略不写
     */
    public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";

    /**
     * 匹配身份证号的正则表达式
     */
    public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    /**
     * 匹配给定的字符串是否是一个手机号码，支持130——139、150——153、155——159、180、183、185、186、188、189号段
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isMobilePhoneNumber(String string) {
        return string.matches(PHONE_NUMBER_REGEX);
    }

    /**
     * 检查电话号码
     */
    public static String findVerfiyCode(String msgBody) {
        if (TextUtils.isEmpty(msgBody)) return "";

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(msgBody);
        String veryCode = m.replaceAll("").trim();

        if (TextUtils.isEmpty(veryCode) || veryCode.length() < 6) return "";

        return veryCode;
    }

    /**
     * 匹配给定的字符串是否是一个邮箱账号，"www."可省略不写
     *
     * @param string 给定的字符串
     * @return true：是
     */
    public static boolean isEmail(String string) {
        return string.matches(EMAIL_REGEX);
    }

    /**
     * 匹配身份证号码
     *
     * @param string 给定的字符串
     */
    public static boolean isIdcard(String string) {
        return string.matches(ID_CARD);
    }



    /**
     * 六位以上且包含字母、数字
     */
    public static final String CODE_PASSWORD = "^(?=.*[a-zA-Z])(.{6,})$";
    public static boolean isCharAndNumbar(String string) {
        return string.matches(CODE_PASSWORD);
    }


}
