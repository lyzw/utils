package me.sapling.utils.db.bean;

import lombok.Data;

import java.util.List;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/20
 * @since 1.0
 */
@Data
public class TableSchema {

    String tableName;

    String describe;

    List<ColumnSchema> columns;


}
