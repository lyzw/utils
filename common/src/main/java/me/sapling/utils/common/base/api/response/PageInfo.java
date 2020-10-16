package me.sapling.utils.common.base.api.response;

import me.sapling.utils.common.base.BaseEntity;

/**
 * @author zhouwei
 * @version v1.0
 * @date 2018/6/7
 * @since v1.0
 */
public class PageInfo extends BaseEntity {

    private Integer currentPage =1 ;

    private Integer total = 0;

    private Integer pageSize = 20;

    private Integer totalPage = 0;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getOffset(){
        if (this.getCurrentPage() == null || this.getCurrentPage() < 1){
            this.setCurrentPage(1);
        }
        if (this.getPageSize() == null || this.getPageSize() < 1){
            this.setPageSize(20);
        }
        if (this.getPageSize() > 5000){
            this.setPageSize(5000);
        }
        return (this.getCurrentPage()-1) * this.getPageSize();
    }
}
