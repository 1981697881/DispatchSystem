package com.dispatch.system.entity;

import com.dispatch.system.utils.BusinessHelper;

public class ReceivingRecordsBean extends BaseBean {

    private static final long serialVersionUID = -3455522955380781486L;

    /**
     * code : 29ce8d13faf24f9fa73ab482e9f78889
     * receivingMethod : RECEIVING
     * trackingNumber : YD568487779990044
     * addressee : 王小二
     * addresseeMobileNumber : 13454554567
     * addresseeAreaName : 北京市市辖区东城区
     * addresseeAddress : 东城花园C区12栋502
     * sender : 王二狗
     * senderMobileNumber : 13454554588
     * senderAddress : 广东省广州市天河区
     * senderAreaName : 白沙水路26号
     * state : SIGNED
     * business : YD
     * payState : PAID
     * amount : 12
     */

    private String code;
    // 揽件方式，RECEIVING（上门揽件）、SELF（门店自寄）
    private String receivingMethod;
    // "type": "SYSTEM", // 揽件类型，SYSTEM（系统揽件）、EMPLOYEE_SELF（员工自揽件）
    private String type;
    // 快递编号
    private String trackingNumber;
    // 收件人
    private String addressee;
    private String addresseeMobileNumber;
    private String addresseeAreaName;
    private String addresseeAddress;
    // 寄件人
    private String sender;
    private String senderMobileNumber;
    private String senderAddress;
    private String senderAreaName;
    // RECEIVED（已揽件）、NOT_RECEIVED（未揽件）、CANCELLED（已取消）EXCEPTION（揽件异常）
    private String state;
    // 快递厂商
    private String business;
    // 支付状态，PAID（已支付）、UNPAID（未支付）、PAID_FAIL（支付失败）
    private String payState;
    // 实际支付金额
    private int amount;
    // 核销状态 NOT_VERIFIED（未核销）、VERIFIED（已核销）
    private String verifyState;
    // 快递费用
    private String budget;
    // 揽收开始时间
    private long receivingStartTime;
    // 揽收结束时间
    private long receivingEndTime;
    private long createTime;
    private long receivedDate;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(long receivedDate) {
        this.receivedDate = receivedDate;
    }

    public long getReceivingStartTime() {
        return receivingStartTime;
    }

    public void setReceivingStartTime(long receivingStartTime) {
        this.receivingStartTime = receivingStartTime;
    }

    public long getReceivingEndTime() {
        return receivingEndTime;
    }

    public void setReceivingEndTime(long receivingEndTime) {
        this.receivingEndTime = receivingEndTime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(String verifyState) {
        this.verifyState = verifyState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReceivingMethod() {
        return receivingMethod;
    }

    public void setReceivingMethod(String receivingMethod) {
        this.receivingMethod = receivingMethod;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddresseeMobileNumber() {
        return addresseeMobileNumber;
    }

    public void setAddresseeMobileNumber(String addresseeMobileNumber) {
        this.addresseeMobileNumber = addresseeMobileNumber;
    }

    public String getAddresseeAreaName() {
        return addresseeAreaName;
    }

    public void setAddresseeAreaName(String addresseeAreaName) {
        this.addresseeAreaName = addresseeAreaName;
    }

    public String getAddresseeAddress() {
        return addresseeAddress;
    }

    public void setAddresseeAddress(String addresseeAddress) {
        this.addresseeAddress = addresseeAddress;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderMobileNumber() {
        return senderMobileNumber;
    }

    public void setSenderMobileNumber(String senderMobileNumber) {
        this.senderMobileNumber = senderMobileNumber;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderAreaName() {
        return senderAreaName;
    }

    public void setSenderAreaName(String senderAreaName) {
        this.senderAreaName = senderAreaName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBusiness() {
        return BusinessHelper.get(business);
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
