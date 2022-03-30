package me.sapling.utils.common.web.base;

import io.swagger.annotations.ApiModelProperty;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2022/3/9
 */
public class BaseRequest<T> {

    @ApiModelProperty("时间戳")
    Long timestamp;

    @ApiModelProperty("请求数据")
    T data;

    @ApiModelProperty("应用id")
    String appid;

    @ApiModelProperty("签名")
    String sign;
    
    @ApiModelProperty("版本号")
    String version;

}
