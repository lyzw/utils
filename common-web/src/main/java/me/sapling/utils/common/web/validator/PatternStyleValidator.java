package me.sapling.utils.common.web.validator;

import me.sapling.utils.common.web.validator.annotations.PatternStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2022/3/7
 */
public class PatternStyleValidator implements ConstraintValidator<PatternStyle, String> {

    private static final Logger logger = LoggerFactory.getLogger(PatternStyleValidator.class);

    private PatternStyle style;

    private String regex;

    @Override
    public void initialize(PatternStyle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        style = constraintAnnotation;
        regex = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(regex)) {
            return true;
        }
        try {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(value).matches();
        } catch (Exception e) {
            logger.error("pattern[{}] is not illegal!", regex);
            throw new IllegalArgumentException("pattern[" + regex + "] is not illegal!");
        }
    }


}
