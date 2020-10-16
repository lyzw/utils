package me.sapling.utils.common.base.api.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhouwei
 * @version v1.0
 * @date 2018/6/7
 * @since v1.0
 */
public class BasePageRequest<T> extends BaseRequest<T> {

    /**
     * page info see {@link PageRequest}
     */
    PageRequest page;


    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }
}
