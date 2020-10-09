package me.sapling.utils.db.helper;

import java.sql.ResultSet;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/17
 * @since 1.0
 */
public interface ResultSetConverter<T> {

    /**
     * convert result set to target
     *
     * @param resultSet result of the sql query, it will closed after this function finished.
     */
    void convert(ResultSet resultSet);
}
