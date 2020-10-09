package me.sapling.utils.common.base.api.response;

import lombok.Data;
import me.sapling.utils.common.base.BaseEntity;

/**
 * @author zhouwei
 * @version v1.0
 * @date 2018/6/7
 * @since v1.0
 */
@Data
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
