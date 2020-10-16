package me.sapling.utils.common.base.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/10/10
 * @since 1.0
 */
public interface GenericBaseInterface<T> {

    default Class<T> getEntityClass() {
        Class<T> entitiClass = null;
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<T>) actualTypeArguments[0];
            }
        }
        return entitiClass;
    }

}
