package me.sapling.utils.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
public class ResultSetUtil {

    /**
     * get columnNames from result set
     *
     * @param resultSet the set of query
     * @return List of column names
     * @throws SQLException if a database access error occurs
     */
    public static List<String> getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> names = new ArrayList<>();
        for (int index = 1; index <= metaData.getColumnCount(); index++) {
            // use column label for column name or alias
            names.add(metaData.getColumnLabel(index));
        }
        return names;
    }

}
