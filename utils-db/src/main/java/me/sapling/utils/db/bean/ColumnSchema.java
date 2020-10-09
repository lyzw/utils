package me.sapling.utils.db.bean;

import lombok.Data;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/20
 * @since 1.0
 */
@Data
public class ColumnSchema {

    String tableName;

    String  columnName;

    String key;

    String dataType;

    int length;

    int scale;

    boolean identity;

    boolean pk;

    int position;

    String nullable;

    String description;

    String defaultValue;
}
