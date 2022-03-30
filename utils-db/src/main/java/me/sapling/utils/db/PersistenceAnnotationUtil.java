package me.sapling.utils.db;

import me.sapling.utils.common.base.bean.tuple.Tuple;
import me.sapling.utils.common.tools.common.StringUtil;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/10/10
 * @since 1.0
 */
public class PersistenceAnnotationUtil {

    public static boolean isTableAnnotationPresent(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class);
    }

    public static String getColumnName(Class<?> clazz, String propertyName) throws NoSuchFieldException {
        Field field = clazz.getField(propertyName);
        String name = "";
        if (field.isAnnotationPresent(Column.class)) {
            name = field.getAnnotation(Column.class).name();
        }
        if (StringUtil.isEmpty(name)) {
            name = field.getName();
        }
        return name;
    }

    public static List<String> getColumnNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).map(item -> {
            if (item.isAnnotationPresent(Column.class)) {
                return item.getAnnotation(Column.class).name();
            }
            return item.getName();
        }).collect(Collectors.toList());
    }

    public static List<Tuple<String, String>> getColumnTuples(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).map(item -> {
            if (item.isAnnotationPresent(Column.class)) {
                return new Tuple<String, String>(item.getName(), item.getAnnotation(Column.class).name());
            }
            return new Tuple<String, String>(item.getName(), item.getName());
        }).collect(Collectors.toList());
    }

    public static Map<String, String> getColumnMap(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> data = new HashMap<>();
        Arrays.stream(fields).forEach(item -> {
            if (item.isAnnotationPresent(Column.class)) {
                data.put(item.getName(), item.getAnnotation(Column.class).name());
            } else {
                data.put(item.getName(), item.getName());
            }
        });
        return data;
    }
}
