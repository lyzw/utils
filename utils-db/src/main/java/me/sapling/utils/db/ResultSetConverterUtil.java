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
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/9
 * @since 1.0
 */
@Slf4j
public class ResultSetConverterUtil {

    public static <T> List<T> convertToObject(ResultSet resultSet, Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException {
        return convert(resultSet, clazz::newInstance, FieldReflectUtil::setRecursionDeclaredFieldValue);
    }


    public static List<Map<String, Object>> convertToMap(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        return convert(resultSet, HashMap::new, (name, obj, value) -> obj.put(name, value));
    }


    public static <T> List<T> convert(ResultSet resultSet, InstanceCreator<T> supplier, ConverterFunction<T> coverter) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> values = new ArrayList<>();
        List<String> names = ResultSetUtil.getColumnNames(resultSet);
        while (resultSet.next()) {
            try {
                T obj = supplier.create();
                names.forEach(name -> {
                    try {
                        coverter.convert(name, obj, resultSet.getObject(name));
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
                throw e;
            }
        }
        return values;
    }

}
