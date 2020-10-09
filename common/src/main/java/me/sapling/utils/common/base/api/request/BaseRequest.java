package me.sapling.utils.common.base.api.request;

import me.sapling.utils.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhouwei
 * @version v1.0
 * @date 2018/6/7
 * @since v1.0
 */
@Data
public class BaseRequest<T> extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    private Long requestTime;

    private String requestId;

    private T data;

}
