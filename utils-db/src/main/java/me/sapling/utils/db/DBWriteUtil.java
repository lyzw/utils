package me.sapling.utils.db;

import me.sapling.utils.db.bean.ColumnSchema;
import me.sapling.utils.db.bean.TableSchema;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/20
 * @since 1.0
 */
public class DBWriteUtil {


    public static List<Map<String,Object>> mergerValues(TableSchema tableSchema, List<Map<String,Object>> values){
        List<String> columns = tableSchema.getColumns()
                .stream()
                .map(ColumnSchema::getColumnName)
                .collect(Collectors.toList());
        values.forEach(f->{
            List<String> removeKeys = f.keySet().stream()
                    .filter(key-> !columns.contains(key.toLowerCase()))
                    .collect(Collectors.toList());
            removeKeys.forEach(f::remove);
        });
        return values;
    }

}
