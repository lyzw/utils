package me.sapling.utils.common.tools.collection;

import java.util.List;

/**
 * list util
 *
 * @author weizhou
 * @version v1.0
 * @date 2018/7/2
 * @since v1.0
 */
public class ArrayUtil {

    public static Object[] listToArray(List<?> list) {
        Object[] ret = new Object[list.size()];
        ret = list.toArray(ret);
        return ret;
    }

}
