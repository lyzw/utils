package me.sapling.utils.common.tools.collection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author weizhou
 * @version v1.0
 * @date 2018/6/25
 * @since v1.0
 */
public class ListUtil {

    public static boolean compare(List<Object> one, List<Object> other) {
        if (one == null && other == null) {
            return true;
        } else if (one == null || other == null) {
            return false;
        }
        if (one.size() != other.size()) {
            return false;
        }
        boolean flag = true;
        for (Object o : one) {
            if (other.contains(o)) {
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static <T> T[] covertToArray(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
    }
}
