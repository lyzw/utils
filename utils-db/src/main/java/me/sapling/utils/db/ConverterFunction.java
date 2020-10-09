package me.sapling.utils.db;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
public interface ConverterFunction<T> {

    /**
     * 数据转换，将ResultSet中的数据转换为对应的实体的数据
     *
     * @param name   字段名称
     * @param object 数据实体
     * @param value  字段的值
     * @throws NoSuchFieldException   在对实体进行转换的时候，如果没有此字段时候报错
     * @throws IllegalAccessException 此字段在实体中访问权限不足时候报错
     */
    void convert(String name, T object, Object value) throws NoSuchFieldException, IllegalAccessException;
}
