package me.sapling.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 此接口用于{@link DbQueryUtil#preparedQuery(Connection, String, Class, PreparedStatementValueSetter)},
 * 在使用的时候，提供了对PreparedStatement参数进行设置的能力
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
public interface PreparedStatementValueSetter {

    /**
     * 设置PreparedStatement中的参数的值
     *
     * @param pstm PreparedStatement
     * @throws SQLException 参数设置有问题时候报错
     */
    void setValues(PreparedStatement pstm) throws SQLException;
}
