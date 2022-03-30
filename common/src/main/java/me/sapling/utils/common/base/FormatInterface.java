package me.sapling.utils.common.base;

import me.sapling.utils.common.tools.reflect.FieldReflectUtil;
import me.sapling.utils.common.tools.reflect.MethodReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2019/11/12
 * @since 1.0
 */
public interface FormatInterface {


    /**
     * @return
     */
    default String toJsonString() {
        StringBuilder sb = new StringBuilder();
        Set<Field> fields = FieldReflectUtil.getDeclaredFieldsRecursion(this);
        sb.append("{");
        fields.forEach(field -> {
            try {
                if (Number.class.isAssignableFrom(field.getType())) {
                    String value = FieldReflectUtil.readField(field, this).toString();
                    sb.append(FieldFormatHelper.appendFieldName(field))
                            .append(value);
                } else if (FormatInterface.class.isAssignableFrom(field.getType())) {
                    Object fieldValue = FieldReflectUtil.readField(field, this);
                    String value = (String) MethodReflectUtil.invokeMethod("toJsonString", fieldValue);
                    sb.append(FieldFormatHelper.appendFieldName(field))
                            .append(value);
                } else if (field.getType().equals(String.class)) {
                    String value = FieldReflectUtil.readField(field, this).toString();
                    sb.append(FieldFormatHelper.appendFieldName(field))
                            .append(FieldFormatHelper.DOUBLE_QUOTATION_MARK)
                            .append(value)
                            .append(FieldFormatHelper.DOUBLE_QUOTATION_MARK);
                } else if (Map.class.isAssignableFrom(field.getType())){

                }
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        sb.append("}");
        return sb.toString();
    }


}
