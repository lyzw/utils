package me.sapling.utils.common.base.api.response;

import lombok.Data;
import me.sapling.utils.common.base.BaseEntity;

/**
 * @author zhouwei
 * @version v1.0
 * @date 2018/6/7
 * @since v1.0
 */
public class BaseResponse<T> extends BaseEntity {

    private String retCode;

    private String retMsg;

    private T data;

    private Long responseTime = System.currentTimeMillis();

    private String requestId;

    private String responseId;

    public static <T> BaseResponse<T> success(T data) {
        return builder(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.message, null, null, data);
    }

    public static <T> BaseResponse<T> success(String requestId, T data) {
        return builder(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.message, requestId, null, data);
    }

    public static <T> BaseResponse<T> success(String requestId, String responseId, T data) {
        return builder(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.message, requestId, responseId, data);
    }

    public static <T> BaseResponse<T> failure(ResponseCode responseCode) {
        return builder(responseCode.getCode(), responseCode.getMessage(), null, null, null);
    }

    public static <T> BaseResponse<T> failure(Exception e) {
        return builder(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage(), null, null, null);
    }


    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    private static <T> BaseResponse<T> builder(String retCode, String retMsg, String requestId, String responseId, T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setRetCode(retCode);
        baseResponse.setRetMsg(retMsg);
        baseResponse.setRequestId(requestId);
        baseResponse.setResponseId(responseId);
        baseResponse.setResponseTime(System.currentTimeMillis());
        baseResponse.setData(data);
        return baseResponse;
    }


}
