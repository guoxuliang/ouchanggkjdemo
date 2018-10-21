package com.example.ouc.demo.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PhoneFormatCheckUtils {

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
//        String regExp = "^((13[0-9])|(15[0-3, 5-9])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        String regExp = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }



    /**
     * 15位到18位身份证验证正则
     */
    public static boolean isSFid(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    /**
     * 正则表达式：验证用户名
     */ public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$"; /**
     * 正则表达式：验证密码
     */ public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$"; /**
     * 移动手机号码的正则表达式。
     */ private static final String REGEX_CHINA_MOBILE ="1(3[4-9]|4[7]|5[012789]|8[278])\\d{8}"; /**
     * 联通手机号码的正则表达式。
     */ private static final String REGEX_CHINA_UNICOM = "1(3[0-2]|5[56]|8[56])\\d{8}"; /**
     * 电信手机号码的正则表达式。
     */ private static final String REGEX_CHINA_TELECOM = "(?!00|015|013)(0\\d{9,11})|(1(33|53|80|89)\\d{8})"; /**
     * 正则表达式：验证手机号
     */ private static final String REGEX_PHONE_NUMBER = "^(0(10|2\\d|[3-9]\\d\\d)[- ]{0,3}\\d{7,8}|0?1[3584]\\d{9})$"; /**
     * 正则表达式：验证邮箱
     */ public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"; /**
     * 正则表达式：验证汉字
     */ public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$"; /**
     * 正则表达式：验证身份证
     */ public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)"; /**
     * 正则表达式：验证URL
     */ public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"; /**
     * 正则表达式：验证IP地址
     */ public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"; /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */ public static boolean isUsername(String username) { return Pattern.matches(REGEX_USERNAME, username); } /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */ public static boolean isPassword(String password) { return Pattern.matches(REGEX_PASSWORD, password); } /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */ public static boolean isMobile(String mobile) { return Pattern.matches(REGEX_PHONE_NUMBER, mobile); } /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */ public static boolean isEmail(String email) { return Pattern.matches(REGEX_EMAIL, email); } /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */ public static boolean isChinese(String chinese) { return Pattern.matches(REGEX_CHINESE, chinese); } /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */ public static boolean isIDCard(String idCard) { return Pattern.matches(REGEX_ID_CARD, idCard); } /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */ public static boolean isUrl(String url) { return Pattern.matches(REGEX_URL, url); } /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */ public static boolean isIPAddr(String ipAddr) { return Pattern.matches(REGEX_IP_ADDR, ipAddr); }
}
