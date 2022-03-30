package me.sapling.utils.db;


import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.common.tools.reflect.FieldReflectUtil;
import me.sapling.utils.db.helper.InstanceCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库结果集转换工具类
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
@Slf4j
public class ResultSetConverterUtil {

    /**
     * 将数据库结果转换为对应的类型
     *
     * @param resultSet 数据库结果集
     * @param clazz     指定的类型
     * @param <T>       目标类型
     * @return 结果列表
     * @throws SQLException           数据库执行异常
     * @throws IllegalAccessException 无效的访问异常
     * @throws InstantiationException 无效的实例化异常
     */
    public static <T> List<T> convertToObject(ResultSet resultSet, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        return convert(resultSet, clazz::newInstance, FieldReflectUtil::setRecursionDeclaredField);
    }


    /**
     * 将数据转换为Map类型的list
     *
     * @param resultSet 数据库结果集
     * @return Map类型的list结果集
     * @throws SQLException           数据库执行异常
     * @throws IllegalAccessException 无效的访问异常
     * @throws InstantiationException 无效的实例化异常
     */
    public static List<Map<String, Object>> convertToMap(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        return convert(resultSet, HashMap::new, (name, obj, value) -> obj.put(name, value));
    }


    /**
     * 根据指定的转化器，将数据集转换为指定的类型
     *
     * @param resultSet 数据库数据集
     * @param supplier  实体的构造方法，用于生成指定类型的
     * @param converter 转换器
     * @param <T>       转换的目标类型
     * @return 转换后的结果列表
     * @throws SQLException           数据库执行异常
     * @throws IllegalAccessException 无效的访问异常
     * @throws InstantiationException 无效的实例化异常
     */
    public static <T> List<T> convert(ResultSet resultSet, InstanceCreator<T> supplier, ConverterFunction<T> converter) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> values = new ArrayList<>();
        List<String> names = ResultSetUtil.getColumnNames(resultSet);
        while (resultSet.next()) {
            try {
                T obj = supplier.create();
                names.forEach(name -> {
                    try {
                        converter.convert(name, obj, resultSet.getObject(name));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        try {
                            log.warn(" there is no field named [{}] in class [{}] ,value [{}] dropped!", name, obj.getClass(), resultSet.getObject(name));
                        } catch (SQLException ex) {
                            log.warn("there is exception occur on getting value from result set");
                        }
                    }
                });
                values.add(obj);
            } catch (Exception e) {
                log.error("covert database result set to entity error :{}", e.getMessage());
                throw e;
            }
        }
        return values;
    }

}
