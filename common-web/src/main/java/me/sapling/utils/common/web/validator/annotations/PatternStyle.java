package me.sapling.utils.common.web.validator.annotations;

import me.sapling.utils.common.web.validator.PatternStyleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2022/3/7
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {PatternStyleValidator.class}
)
public @interface PatternStyle {

    String message() default "{custom.value.invalid}";

    String value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
