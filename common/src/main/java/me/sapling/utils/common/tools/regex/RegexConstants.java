package me.sapling.utils.common.tools.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weizhou
 * @version v1.0
 * @date 2018/9/5
 * @since v1.0
 */
public class RegexConstants {


    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static final String REGEX_ID_CARD_18 = "^(\\d{6})(((18|19|20)\\d{2})(10|11|12|0[0-9])(0[1-9]|[12][0-9]|3[01]))\\d{3}[0-9xX].*$";

    public static final String REGEX_MOBILE = "1\\d{10}";




}
