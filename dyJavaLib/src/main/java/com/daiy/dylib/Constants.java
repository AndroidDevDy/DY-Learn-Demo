package com.daiy.dylib;


public class Constants {

    public static final String TAG = "DYLog";

    public static final String service_url = "";

    /**
     * 指向App的启动Activity
     */
    public static String firstActivity = "LoginActivity";
    /**
     * 15位或18位身份证号正则表达式
     */
    public static final String REGEX_ID_CARD = "^(\\d{15})|(\\d{18})|(\\d{17}[0-9Xx])$";
    /**
     * 15位身份证号正则表达式
     */
    public static final String REGEX_ID_CARD_15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";
    /**
     * 18位身份证号正则表达式
     */
    public static final String REGEX_ID_CARD_18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    /**
     * 手机号正则表达式
     */
    public static final String REGEX_PHONE = "^((\\d{11})|(\\d{10}))$";
    /**
     * 固话正则表达式
     */
    public static final String REGEX_TEL = "^(\\d{3}\\-\\d{8})$";
    /**
     * Email正则表达式
     */
    public static final String REGEX_EMAIL = "^((([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)))$";
    /**
     * 得到SD卡路径
     */
    public final static String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/dyCache/";
}
