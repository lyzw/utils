package me.sapling.utils.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * basic database table entity
 *
 * @author wei.zhou
 * @date 2019/11/12
 * @since 1.0
 */
public class BaseDbEntity extends BaseEntity{

    /**
     * the time (Greenwich Mean Time) of record create
     */
    private Date createdTime;

    /**
     * the time (Greenwich Mean Time) of record modified
     */
    private Date modifiedTime;

    /**
     * the identification of user who created the record
     */
    private String createUser;

    /**
     * the identification of user who modified the record
     */
    private String modifiedUser;


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
}
