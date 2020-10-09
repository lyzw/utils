package me.sapling.utils.common.base.api.request;

import lombok.Data;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2019/11/12
 * @since 1.0
 */
@Data
public class PageRequest {

    private Integer page = 0;

    private Integer pageSize = 20;
}
