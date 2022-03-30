package me.sapling.utils.common.tools.text;

import org.apache.commons.lang3.RandomUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author zhouwei
 * @since 2021/10/14
 */
public class ChineseCharUtil {

    public static String generateOneChineseChar() {
        String str = null;
        int highPos = (176 + Math.abs(RandomUtils.nextInt(0, 39)));
        int lowPos = 161 + Math.abs(RandomUtils.nextInt(0, 93));
        byte[] b = new byte[]{(new Integer(highPos)).byteValue(),
                (new Integer(lowPos)).byteValue()};

        try {
            str = new String(b, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.print(generateOneChineseChar());

        }
    }
}
