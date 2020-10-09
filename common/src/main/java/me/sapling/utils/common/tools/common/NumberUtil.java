package me.sapling.utils.common.tools.common;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/8/21
 * @since 1.0
 */
public class NumberUtil {

    private NumberUtil(){}

    public static boolean isPositiveNumber(Number number){
        if (number == null){
            return false;
        }
        return number.doubleValue() > 0;
    }
}
