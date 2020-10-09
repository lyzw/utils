package me.sapling.utils.db.helper;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
public interface InstanceCreator<T> {

    T create() throws InstantiationException, IllegalAccessException;
}
