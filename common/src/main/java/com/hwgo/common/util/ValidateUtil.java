package com.hwgo.common.util;

import android.net.ParseException;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Filename: ValidateUtil
 * @Description:
 */
public class ValidateUtil {

    /**
     * 验证电话
     *
     * 新增判断10-19开头全为手机号码
     * 保留长段的正则预防后面改回来
     */
    public static final String VLIDATE_PHONE_PATTERN = "^((10[0-9])|(11[0-9])|(12[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    /**
     * 获取电话
     */
    public static final String GET_PHONE_NUM = "((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}";
    /**
     * 验证密码，允许特殊字符。不允许^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$
     */
    public static final String VLIDATE_PASSWORD_PATTERN = "^(?![^a-zA-Z]+$)(?![^0-9]+$).{6,16}$";
    /**
     * 验证学信网密码，允许特殊字符。
     */
    public static final String VLIDATE_LEARN_LETTER_PASSWORD_PATTERN = "^.{6,30}";
    /**
     * 验证邮箱
     */
    public static final String VLIDATE_EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    /**
     * 验证用户昵称特殊字符
     */
    public static final String VLIDATE_NICKNAME_PATTERN = "^[\u4E00-\u9FA5A-Za-z0-9_]{2,12}$";
    /**
     * 验证用户昵称特殊字符
     */
    public static final String VLIDATE_BORROW_TITLE_PATTERN = "^[\u4E00-\u9FA5A-Za-z0-9]+$";
    /**
     * 验证是否有汉字外的其它字符
     */
    public static final String VLIDATE_BORROW_TITLE_CN_PATTERN = "^[\u4E00-\u9FA5]+$";

    /**
     * 验证邮箱
     *
     * @param @param  email
     * @param @return
     * @return boolean
     * @throws
     * @Title: isValidateEmial
     */
    public static boolean isValidateEmial(String email) {
        return isValidate(email, VLIDATE_EMAIL_PATTERN);
    }

    /**
     * 获取手机号
     *
     * @param phone
     * @return String
     */
    public static String GetPhoneNum(String phone) {
        Pattern pattern = Pattern.compile(GET_PHONE_NUM);
        Matcher matcher = pattern.matcher(phone.replaceAll(" ", "").replaceAll("-", ""));
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }
    }

    /**
     * 获取数字字符串集合
     *
     * @param string
     * @param initial 最少多少个连续数字才收集
     * @return String
     */
    public static List<String> GetNums(String string, int initial) {
        Pattern pattern = Pattern.compile("\\d{" + initial + ",}");
        Matcher matcher = pattern.matcher(string);
        List<String> Strings = new ArrayList<String>();
        while (matcher.find()) {
            Strings.add(matcher.group());
        }
        return Strings;
    }

    /**
     * 从字符串中获取手机号码
     *
     * @param @param  phone
     * @param @return
     * @return boolean
     * @throws
     * @Title: isValidatePhone
     */
    public static boolean isValidatePhone(String phone) {
        return isValidate(phone, VLIDATE_PHONE_PATTERN);
    }

    /**
     * 验证密码
     *
     * @param @param  pwd
     * @param @return
     * @return boolean
     * @throws
     * @Title: isValidatePassword
     */
    public static boolean isValidatePassword(String pwd) {
        return isValidate(pwd, VLIDATE_PASSWORD_PATTERN);
    }

    /**
     * 验证学信网密码
     *
     * @param pwd
     * @return
     */
    public static boolean isValidateLearnLetterPassword(String pwd) {
        return isValidate(pwd, VLIDATE_LEARN_LETTER_PASSWORD_PATTERN);
    }

    /**
     * 验证用户昵称特殊字符
     *
     * @param @param  pwd
     * @param @return
     * @return boolean
     * @throws
     * @Title: isValidatePassword
     */
    public static boolean isValidateNickname(String nickname) {
        return isValidate(nickname, VLIDATE_NICKNAME_PATTERN);
    }

    /**
     * 验证发资产标标题
     *
     * @param title
     * @return
     */
    public static boolean isValidateBorrowTitle(String title) {
        return isValidate(title, VLIDATE_BORROW_TITLE_PATTERN);
    }

    /**
     * 验证除汉字外是否还有其它字符
     *
     * @param title
     * @return
     */
    public static boolean isValidateBorrowNOCNTitle(String title) {
        return isValidate(title, VLIDATE_BORROW_TITLE_CN_PATTERN);
    }

    private static boolean isValidate(String value, String patternStr) {
        if (!TextUtils.isEmpty(value)) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public static double getJsonToDouble(JSONObject json, String key) throws JSONException {
        if (json.has(key)) {
            Object obj = json.get(key);
            if (obj != null && !obj.toString().equals("null")) {
                return json.getDouble(key);
            }
        }
        return 0;
    }

    public static int getJsonToInt(JSONObject json, String key) throws JSONException {
        if (json.has(key)) {
            Object obj = json.get(key);
            if (obj != null && !obj.toString().equals("null")) {
                return json.getInt(key);
            }
        }
        return 0;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 是否为正确的浮点数
     *
     * @param str
     * @return boolean
     */
    public static boolean isFloatNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.substring(0, 1).equals(".")) {
            return false;
        }
        return true;
    }

    /**
     * 是否为中文字符
     *
     * @param str
     * @return
     * @throws
     */
    public static boolean isChinese(String str) {
//        boolean temp = false;
//        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.find()) {
//            temp = true;
//        }
//        return temp;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String reg = "[\\u4e00-\\u9fa5]+";//表示+表示一个或多个中文，
        return str.matches(reg);
    }

    /*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
     * X 9 8 7 6 5 4 3 2
     */

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static String IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 18) {
            errorInfo = "目前只支持18位身份证号码";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        // LogTool.print("modValue", modValue);
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }


    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param args
     * @throws ParseException
     */
    // public static void main(String[] args) throws ParseException {
    // // String IDCardNum="210102820826411";
    // // String IDCardNum="210102198208264114";
    // String IDCardNum = "500113198606245216";
    // CommonUtil cc = new CommonUtil();
    // System.out.println(cc.IDCardValidate(IDCardNum));
    // // System.out.println(cc.isDate("1996-02-29"));
    // }
    /*********************************** 身份证验证结束 ****************************************/

}
