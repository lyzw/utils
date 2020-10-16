package me.sapling.utils.common.tools.annotation;

import me.sapling.utils.common.tools.reflect.FieldReflectUtil;
import me.sapling.utils.common.tools.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/10/10
 * @since 1.0
 */
public class AnnotationUtil {

    public static boolean isAnnotationPresent(Class<?> sourceClazz, Class<? extends Annotation> annotationClazz){
        return sourceClazz.isAnnotationPresent(annotationClazz);
    }

    public static List<Field> getAnnotationPresentFields(Class<?> sourceClazz, Class<? extends Annotation> annotationClazz){
        Set<Field> fields = FieldReflectUtil.getSubClassDeclaredFieldsRecursion(sourceClazz);
        if (fields == null || fields.isEmpty()){
            return null;
        }
        return fields.stream().filter(field -> field.isAnnotationPresent(annotationClazz)).collect(Collectors.toList());

    }
}
