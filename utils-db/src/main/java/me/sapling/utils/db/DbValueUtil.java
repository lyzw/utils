package me.sapling.utils.db;

import me.sapling.utils.common.base.bean.tuple.Tuple;
import me.sapling.utils.db.bean.ColumnSchema;
import me.sapling.utils.db.bean.TableSchema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/21
 * @since 1.0
 */
public class DbValueUtil {

    private static final String SDF_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String SDF_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static Map<String, String> covertToSqlValues(Map<String, Object> values) {
        return values.entrySet().stream()
                .map(entry -> new Tuple<>(entry.getKey(), covertToSqlValue(entry.getValue())))
                .collect(Collectors.toMap(Tuple::getParam1, Tuple::getParam2, (older, newer) -> older));
    }

    public static Map<String, String> covertToSqlValues(Map<String, Object> values, TableSchema tableSchema) {
        Map<String, ColumnSchema> columnSchemaMap = tableSchema.getColumns().stream().collect(Collectors.toMap(ColumnSchema::getColumnName, f-> f));
        return values.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey().toLowerCase();
                    return  new Tuple<>(entry.getKey(), covertToSqlValue(entry.getValue(), columnSchemaMap.get(key)));
                })
                .collect(Collectors.toMap(Tuple::getParam1, Tuple::getParam2, (older, newer) -> older));
    }

    public static String covertToSqlValue(Object value) {
        if (value == null) {
            return "null";
        }
        // 数字直接转换为字符串
        if (value instanceof Number) {
            return value.toString();
        }
        // 布尔型
        if (value instanceof Boolean) {
            return value.toString();
        }
        // 日期类型
        if (value instanceof Date) {
            return "'" + new SimpleDateFormat(SDF_PATTERN_YYYY_MM_DD_HH_MM_SS).format(value) + "'" ;
        }
        return "'" + value.toString().replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"") + "'";

    }
    public static String covertToSqlValue(Object value, ColumnSchema columnSchema) {
        if (columnSchema == null || value == null){
            return covertToSqlValue(value);
        }
        // 数字直接转换为字符串
        if (value instanceof Number) {
            return value.toString();
        }
        // 布尔型
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (columnSchema.getDataType().equalsIgnoreCase("date") && value instanceof Date){
            return "'" + new SimpleDateFormat(SDF_PATTERN_YYYY_MM_DD).format(value) + "'" ;
        }
        // 日期类型
        if (value instanceof Date) {
            return "'" + new SimpleDateFormat(SDF_PATTERN_YYYY_MM_DD_HH_MM_SS).format(value) + "'" ;
        }
        return "'" + value.toString().replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"") + "'";
    }
}
