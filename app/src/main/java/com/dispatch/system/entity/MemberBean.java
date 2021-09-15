package com.dispatch.system.entity;

import java.util.Objects;

/**
 * 会员实体
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class MemberBean extends BaseBean {

    /**
     * realName : 李富贵
     * buildingName : 测试花园商业区C栋
     * code : 292f902fbfed4fd2852fb28132ad4f3b
     * mobileNumber : 18811111111
     * houseNumber : 5001
     * type : MEMBER
     */

    private String realName;
    private String buildingName;
    private String code;
    private String mobileNumber;
    private String houseNumber;
    // 会员类型[c(会员)、NONMEMBER(非会员)]
    private String type;
    // 快递单号
    private String expressNum;

    private String business;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public boolean isMember() {
        return Objects.equals(type, "MEMBER");
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
