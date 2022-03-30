package me.sapling.utils.db;

import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.db.helper.ResultSetConverter;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
@SuppressWarnings("unused")
@Slf4j
public class DbQueryUtil {


    /**
     * @param connection 数据库链接
     * @param sql        sql语句
     * @param clazz      转换的类
     * @param <T>        类型
     * @return 查询的数据
     * @throws SQLException           sql异常
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 异常访问异常
     */
    public static <T> List<T> queryForObject(Connection connection, String sql, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            return null;
        }
        List<T> values;
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            values = ResultSetConverterUtil.convertToObject(resultSet, clazz);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            log.error("there is an error happen when query database! {}", e.getMessage());
            throw e;
        }
        return values;
    }


    /**
     * 流式查询
     *
     * @param connection 数据库链接
     * @param sql        sql语句
     * @param converter  转换器
     * @param <T>        转换类型
     */
    public static <T> void streamReadNext(Connection connection, String sql, ResultSetConverter<T> converter) {
        try (Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setFetchSize(Integer.MIN_VALUE);
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                converter.convert(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式数据查询
     *
     * @param connection 数据库链接
     * @param sql        SQL语句
     * @param converter  转换器
     * @param <T>        指定的类型
     */
    public static <T> void streamReadAll(Connection connection, String sql, ResultSetConverter<T> converter) {
        try (Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setFetchSize(Integer.MIN_VALUE);
            ResultSet resultSet = stmt.executeQuery(sql);
            converter.convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式获取所有的数据，指定获取数据量
     *
     * @param connection 数据库链接
     * @param sql        查询语句
     * @param fetchSize  获取的数量
     * @param converter  转换器
     * @param <T>        指定的目标类型
     */
    public static <T> void streamReadAll(Connection connection, String sql, Integer fetchSize, ResultSetConverter<T> converter) {
        try (Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setFetchSize(fetchSize == null ? Integer.MIN_VALUE : fetchSize);
            ResultSet resultSet = stmt.executeQuery(sql);
            converter.convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用命名参数PreparedStatement
     * sql语句必须类似于 select * from tbl where table.name = ?
     *
     * @param connection 数据库连接
     * @param sql        预编译语句
     * @param clazz      类型
     * @param <T>        返回类型
     * @return 返回指定类型的数据列表
     */
    public static <T> List<T> preparedQuery(Connection connection, String sql, Class<T> clazz, PreparedStatementValueSetter valueSetter) throws SQLException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            return null;
        }
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            valueSetter.setValues(pstm);
            try (ResultSet resultSet = pstm.executeQuery(sql)) {
                return ResultSetConverterUtil.convertToObject(resultSet, clazz);
            }
        }
    }


    /**
     * 获取数据转化为Map
     *
     * @param connection 数据库链接
     * @param sql        查询语句
     * @return 数据库查询结果
     * @throws SQLException           数据库异常
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 错误访问异常
     */
    public static List<Map<String, Object>> queryForMap(Connection connection, String sql) throws SQLException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            return null;
        }
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            return ResultSetConverterUtil.convertToMap(resultSet);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            log.error("there is an error happen when query database or covert result set to target entity! {}", e.getMessage());
            throw e;
        }
    }


}
