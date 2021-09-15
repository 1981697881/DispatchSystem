package com.dispatch.system.entity;

/**
 * 入库
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class AddStorageBean extends BaseBean {

    /**
     * trackingNumber : SF123456789123
     * memberCode : 292f902fbfed4fd2852fb28132ad4f3b
     * business : SF
     */

    private String trackingNumber;
    private String memberCode;
    private String business;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
