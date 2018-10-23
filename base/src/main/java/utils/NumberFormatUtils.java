package utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字各种转换操作
 * <p>
 * 距离米转换为公里
 */
public class NumberFormatUtils {
    private static final int ERROR_TO_INT = -100;
    private static final long ERROR_TO_LONG = -100L;
    private static final float ERROR_TO_FLOAT = -100F;
    private static final double ERROR_TO_DOUBLE = -100D;

    public static int strToInt(String data, int errorValue) {
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            return errorValue;
        }
    }

    public static int strToInt(String data) {
        return strToInt(data, ERROR_TO_INT);
    }

    public static long strToLong(String data, long errorValue) {
        try {
            return Long.parseLong(data);
        } catch (Exception e) {
            return errorValue;
        }
    }

    public static long strToLong(String data) {
        return strToLong(data, ERROR_TO_LONG);
    }

    public static float strToFloat(String data, float errorValue) {
        try {
            return Float.parseFloat(data);
        } catch (Exception e) {
            return errorValue;
        }
    }

    public static float strToFloat(String data) {
        return strToFloat(data, ERROR_TO_FLOAT);
    }

    public static double strToDouble(String data, double errorValue) {
        try {
            return Double.parseDouble(data);
        } catch (Exception e) {
            return errorValue;
        }
    }

    public static double strToDouble(String data) {
        return strToDouble(data, ERROR_TO_DOUBLE);
    }

    public static String strToDoubleStr(String data) {
        try {
            Double doubleData = Double.parseDouble(data);
            return removeDecimalZero(doubleData + "");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将Double至保留一位小数
     */
    public static String formatDoubleToStrWithSingleDecimal(String data) {
        try {
            Double doubleData = Double.parseDouble(data);
            DecimalFormat df = new DecimalFormat("0.0");
            return df.format(doubleData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将Double至保留二位小数
     */
    public static String formatDoubleToStrWithTwoDecimal(String data) {
        try {
            Double doubleData = Double.parseDouble(data);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(doubleData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 去掉小数点后面的0
     */
    public static String removeDecimalZero(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    /**
     * 向上取整并去除0
     */
    public static String removeDecimal(String s) {
        return removeDecimalZero(Math.ceil(Double.parseDouble(s)) + "");
    }

    /**
     * BigDecimal 转成 String(四舍五入)
     */
    public static String convertBigDecimalToString(String doString, int Scale) {

        if (TextUtils.isEmpty(doString)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(doString);
        BigDecimal b = bigDecimal.setScale(Scale, BigDecimal.ROUND_HALF_UP);

        return b.toString();
    }

    /**
     * String(BigDecimal) 转成 String(向上取整)
     */
    public static String convertBigDecimalToStringRoundup(String doString, int Scale) {
        if (TextUtils.isEmpty(doString)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(doString);

        BigDecimal b = bigDecimal.setScale(Scale, BigDecimal.ROUND_UP);

        return b.toString();
    }

    /**
     * 距离米转换为公里
     */
    public static String changeToStr(String distance) {
        String str = "0";
        if (!TextUtils.isEmpty(distance)) {
            float d = strToFloat(distance);
            float ret = 0.1f;
            if (d > 100) {
                ret = (d / 1000);
            }

            str = formatDoubleToStrWithSingleDecimal("" + ret);
        }
        return str;
    }

    public static String getStrWithComma(String... strArray) {
        String result = "";
        String[] str;
        str = strArray;
        for (String aStr : str) {
            result = aStr + ",";
        }
        return result.substring(0, result.length() - 1);
    }

    // 判断一个字符串是否含有数字

    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }

    /**
     * 小数限定前后位数
     *
     * @param src                   源字符串
     * @param maxDecimalPointBefore 小数点前面的位数，比如限定小数点前面只能是3位，那么自动去除第四位
     * @param maxDecimalPointAfter  小数点后面的位数，同上
     */
    public static String decimalRestrict(String src, int maxDecimalPointBefore, int maxDecimalPointAfter) {
        int posDot = src.indexOf(".");
        if (!src.contains(".")) {
            // 没有小数点
            if (src.length() < maxDecimalPointBefore) {
                return src;
            } else {
                src = src.substring(0, maxDecimalPointBefore);
            }
        } else {
            // 存在小数点
            String sbefore = src.substring(0, posDot);
            String safter = src.substring(posDot + 1);
            // 小数点前判断
            if (sbefore.length() < maxDecimalPointBefore) {
                // 如果小数点前面没有数字，则自动生成“0”
                if (TextUtils.isEmpty(sbefore)) {
                    sbefore = "0";
                }
            } else {
                //小数点前超过位数
                sbefore = sbefore.substring(0, maxDecimalPointBefore);
            }
            //小数点后面判断
            if (safter.length() >= maxDecimalPointAfter) {
                //小数点前超过位数
                safter = safter.substring(0, maxDecimalPointAfter);
            }
            src = sbefore + "." + safter;
        }
        return src;
    }
}
