package me.sapling.utils.db;

import me.sapling.utils.db.bean.ColumnSchema;
import me.sapling.utils.db.bean.TableSchema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/20
 * @since 1.0
 */
public class MySqlSchemaHelper {

    private static final String FILED_FIELD = "Field";
    private static final String FILED_TYPE = "Type";
    private static final String FILED_NULL = "Null";
    private static final String FILED_KEY = "Key";
    private static final String FILED_DEFAULT = "Default";
    private static final String FILED_EXTRA = "Extra";

    public static TableSchema getTableSchema(Connection connection, String tableName) throws IllegalAccessException, SQLException, InstantiationException {
        TableSchema tableSchema = new TableSchema();
//        List<Map<String, Object>> columns = DbQueryUtil.queryForMap(connection, "show COLUMNS from " + tableName);
        tableSchema.setTableName(tableName.toLowerCase());
        List<ColumnSchema> columnSchemas = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(columns)) {
            try(Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("show COLUMNS from " + tableName)){
                while (rs.next()){
                    ColumnSchema columnSchema = new ColumnSchema();
                    columnSchema.setColumnName(Objects.isNull(rs.getString(FILED_FIELD))  ? null : String.valueOf(rs.getString(FILED_FIELD)).toLowerCase());
                    columnSchema.setDefaultValue(Objects.isNull(rs.getString(FILED_DEFAULT)) ? null : String.valueOf(rs.getString(FILED_DEFAULT)));
                    columnSchema.setNullable("YES".equals(rs.getString(FILED_NULL)));
                    columnSchema.setKey(Objects.isNull(rs.getString(FILED_KEY)) ? null : String.valueOf(rs.getString(FILED_KEY)));
                    columnSchema.setDataType(Objects.isNull(rs.getString(FILED_TYPE)) ? null : String.valueOf(rs.getString(FILED_TYPE)));
                    columnSchema.setComment(Objects.isNull(rs.getString(FILED_EXTRA)) ? null : String.valueOf(rs.getString(FILED_EXTRA)));
                    columnSchemas.add(columnSchema);
                }
            }
            tableSchema.setColumns(columnSchemas);
//        }
        return tableSchema;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Connection source = ConnectionUtil.connect("jdbc:mysql://rm-uf6466t95230jn7i47o.mysql.rds.aliyuncs.com/tms_uat",
                "ty_test",
                "ty_test_1231",
                "com.mysql.cj.jdbc.Driver", null);
        String sourceTableName = "tms_report_store_day_knot_type";

        System.out.println(getTableSchema(source, sourceTableName));

    }
}
