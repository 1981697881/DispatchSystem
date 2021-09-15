package com.dispatch.system.entity;

/**
 * 楼栋树子集
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/22
 */
public class BuildingTreeChildBean extends BaseBean {
    /**
     * code : ec7cd917c67adb1d7fs843d4b3ae31cf
     * name : C区
     * parentCode : 9c7cd917c67847edb1d7f8434b3ce31f
     * sort : 1
     * createTime : 1590395664853
     * updateTime : 1590395664853
     */

    private String code;
    private String name;
    private String parentCode;
    private String sort;
    private long createTime;
    private long updateTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
