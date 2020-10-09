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
public class PageInfo extends BaseEntity {

    private Integer currentPage;

    private Integer total = 0;

    private Integer pageSize = 20;

    private Integer totalPage = 0;

}
