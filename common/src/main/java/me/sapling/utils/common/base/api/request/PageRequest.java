package me.sapling.utils.common.base.api.request;

import lombok.Data;
import me.sapling.utils.common.base.api.response.PageInfo;

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


    public PageInfo convertToPageInfo(){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(this.getPageSize());
        pageInfo.setCurrentPage(this.getPage());
        return pageInfo;
    }

}
