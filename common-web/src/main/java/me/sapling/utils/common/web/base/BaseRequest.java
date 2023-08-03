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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
