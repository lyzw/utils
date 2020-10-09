package me.sapling.utils.common.tools.common;

/**
 * 中文字符处理工具
 *
 * @author weizhou
 * @version v1.0
 * @date 2018/7/2
 * @since v1.0
 */
public class ChineseCharUtil {

    /**
     * 判断字符是否是中文字符
     * 使用CJK字符集来判断，只要字符的编码处于相关的字符集区域，即判断他属于中文字符
     * CJK--Chinese/Japanese/Korea
     *
     * @param c 待判断的字符串
     * @return 是否是中文字符：true - 是，false - 不是
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 判断字符串是否包含中文字符
     *
     * @param value 待检测字符串
     * @return true - 包含中文字符， false - 不包含
     */
    public static boolean includeChinese(String value) {
        if (StringUtil.isEmpty(value)) {
            return false;
        }
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if (isChineseChar(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 按字节数截取字符串，需要关注相关的字符集，不能截取字符的一半而导致乱码
     *
     * @param value  待截取字符串
     * @param length 字节长度
     * @return 截取后的字符串
     */
    public static String subStringByByteLength(String value, int length) {
        if (StringUtil.isEmpty(value, false)) {
            return value;
        }
        if (length <= 0) {
            return "";
        }
        if (length >= value.getBytes().length) {
            return value;
        }
        StringBuilder sb = new StringBuilder();
        int size = 0;
        // 字节长度累加，如果超过，则直接获取
        for (int i = 0; i < value.length(); i++) {
            String c = String.valueOf(value.charAt(i));
            size += c.getBytes().length;
            if (size > length) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
