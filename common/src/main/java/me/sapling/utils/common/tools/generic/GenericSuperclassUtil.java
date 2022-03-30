package me.sapling.utils.common.tools.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/10/10
 * @since 1.0
 */
public class GenericSuperclassUtil {


    /**
     * 获取泛型类Class对象，不是泛型类则返回null
     */
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        if (clazz.equals(List.class)) {
            Type genericSuperclass = clazz.getGenericSuperclass();
        }
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }
        return entitiClass;
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("sss");
        System.out.println(getActualTypeArgument(a.getClass()));
    }
}
