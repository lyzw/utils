package me.sapling.utils.db;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import me.sapling.utils.db.helper.ResultSetConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
public class DbQueryUtil {


    /**
     *
     * @param connection
     * @param sql
     * @param clazz
     * @param <T>
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> queryForObject(Connection connection, String sql, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            return null;
        }
        List<T> values = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql);) {
            values = ResultSetConverterUtil.convertToObject(resultSet, clazz);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            throw e;
        }
        return values;
    }


    public static void streamReadNext(Connection connection, String sql, ResultSetConverter converter) {
        try(Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            stmt.setFetchSize(Integer.MIN_VALUE);
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                converter.convert(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void streamReadAll(Connection connection, String sql, ResultSetConverter converter) {
        try(Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            stmt.setFetchSize(Integer.MIN_VALUE);
            ResultSet resultSet = stmt.executeQuery(sql);
            converter.convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void streamReadAll(Connection connection, String sql, Integer fetchSize, ResultSetConverter converter) {
        try(Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){
            stmt.setFetchSize(fetchSize == null? Integer.MIN_VALUE:fetchSize);
            ResultSet resultSet = stmt.executeQuery(sql);
            converter.convert(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 使用命名参数PreparedStatement
     * sql语句必须类似于 select * from tbl where tabl.name = ?
     *
     * @param connection  数据库连接
     * @param sql   预编译语句
     * @param clazz 类型
     * @param <T>   返回类型
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


    public static List<Map<String, Object>> queryForMap(Connection connection, String sql) throws SQLException, InstantiationException, IllegalAccessException {
        if (connection == null) {
            return null;
        }
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql);) {
            return ResultSetConverterUtil.convertToMap(resultSet);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            throw e;
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Connection connection = ConnectionUtil.connect("jdbc:mysql://10.30.0.93:3306/qf_dataproduct_ods",
//                "user_mysql_adb",
//                "w!jckelNW&4NsQ!^",
//                "com.mysql.cj.jdbc.Driver", null);
//        System.out.println(queryForMap(connection, "select * from at_agent limit 10"));
//        ConnectionUtil.release(connection);
//
//        if (connection.isClosed()) {
//            Properties properties = connection.getClientInfo();
//
//            System.out.println(connection.getMetaData());
//        }
                Connection connection = ConnectionUtil.connect("jdbc:sqlserver://192.168.1.156:1433;DatabaseName=saas2_company_ceshituanduizhuanyon",
                "saas2_devcompany_dbuser",
                "saas2_p&ssword09iojk",
                "com.mysql.cj.jdbc.Driver", null);
                List<Map<String,Object>> data =  queryForMap(connection, "select top 100 * from activity_customer");
        System.out.println(data);
        System.out.println(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat));
//        streamReadAll(connection, "select * from activity_customer", (resultSet) -> {
//            try {
//                List<String> names = ResultSetUtil.getColumnNames(resultSet);
//                List<Map<String,Object>> values = new ArrayList<>();
//                int index = 0;
//                while(resultSet.next()){
//                    Map<String,Object > value = new HashMap<>();
//                    names.forEach(f-> {
//                        try {
//                            value.put(f,resultSet.getObject(f));
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                    values.add(value);
//                    index ++;
//                    if (index % 50 == 0){
//                        System.out.println(" index --> " + index + ", value -> " + values);
//                        values = new ArrayList<>();
//                    }
//                }
//                System.out.println(" index --> " + index + ", value -> " + values);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });

    }
}
