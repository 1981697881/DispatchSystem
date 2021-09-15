package com.dispatch.system.entity;

import android.text.TextUtils;

/**
 * 配送件详情
 * <p>
 * 
 * @author chenjunxu
 * @date 2020/7/21
 */
public class DeliveryDetailBean extends BaseBean {


    /**
     * data : {"deliveryRecord":{"code":"e770641c2cd34f40b2d0fd3b3f6057e5","deliveryMethod":"DELIVERY","deliverymanMobileNumber":"13058141139","exceptionCount":0,"signedFileCode":"e770641c2cd34f40b2d0fd3b3f6057e5","houseNumber":"b202","state":"WAIT","trackingNumber":"SF000000000001","deliveryHistory":"[{\"desc\":\"快递已到站！\",\"time\":\"2020-07-19 15:21:28\"}]","memberCode":"e770641c2cd34f40b2d0fd3b3f6057e5","business":"SF","deliveryEndTime":37800000,"addresseeMobileNumber":"18816785821","deliveryman":"郑振威","buildingCode":"bd3560e095894f32be30e70176a53efc","deliveryTimeCode":"bd3560e095894f32be30e70176a53efc","updateTime":1595143288981,"verifyState":"NOT_SIGNED","buildingName":"广地花园一期D区D1栋","addressee":"张园园","createTime":1595143288981,"epsEmployeeCode":"bd3560e095894f32be30e70176a53efc","deliveryStartTime":34200000,"noticeState":"NOT_SENT","remarks":""}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * deliveryRecord : {"code":"e770641c2cd34f40b2d0fd3b3f6057e5","deliveryMethod":"DELIVERY","deliverymanMobileNumber":"13058141139","exceptionCount":0,"signedFileCode":"e770641c2cd34f40b2d0fd3b3f6057e5","houseNumber":"b202","state":"WAIT","trackingNumber":"SF000000000001","deliveryHistory":"[{\"desc\":\"快递已到站！\",\"time\":\"2020-07-19 15:21:28\"}]","memberCode":"e770641c2cd34f40b2d0fd3b3f6057e5","business":"SF","deliveryEndTime":37800000,"addresseeMobileNumber":"18816785821","deliveryman":"郑振威","buildingCode":"bd3560e095894f32be30e70176a53efc","deliveryTimeCode":"bd3560e095894f32be30e70176a53efc","updateTime":1595143288981,"verifyState":"NOT_SIGNED","buildingName":"广地花园一期D区D1栋","addressee":"张园园","createTime":1595143288981,"epsEmployeeCode":"bd3560e095894f32be30e70176a53efc","deliveryStartTime":34200000,"noticeState":"NOT_SENT","remarks":""}
         */

        private DeliveryRecordBean deliveryRecord;

        public DeliveryRecordBean getDeliveryRecord() {
            return deliveryRecord;
        }

        public void setDeliveryRecord(DeliveryRecordBean deliveryRecord) {
            this.deliveryRecord = deliveryRecord;
        }

        public static class DeliveryRecordBean {
            /**
             * code : e770641c2cd34f40b2d0fd3b3f6057e5
             * deliveryMethod : DELIVERY
             * deliverymanMobileNumber : 13058141139
             * exceptionCount : 0
             * signedFileCode : e770641c2cd34f40b2d0fd3b3f6057e5
             * houseNumber : b202
             * state : WAIT
             * trackingNumber : SF000000000001
             * deliveryHistory : [{"desc":"快递已到站！","time":"2020-07-19 15:21:28"}]
             * memberCode : e770641c2cd34f40b2d0fd3b3f6057e5
             * business : SF
             * deliveryEndTime : 37800000
             * addresseeMobileNumber : 18816785821
             * deliveryman : 郑振威
             * buildingCode : bd3560e095894f32be30e70176a53efc
             * deliveryTimeCode : bd3560e095894f32be30e70176a53efc
             * updateTime : 1595143288981
             * verifyState : NOT_SIGNED
             * buildingName : 广地花园一期D区D1栋
             * addressee : 张园园
             * createTime : 1595143288981
             * epsEmployeeCode : bd3560e095894f32be30e70176a53efc
             * deliveryStartTime : 34200000
             * noticeState : NOT_SENT
             * remarks : 
             */

            private String code;
            private String deliveryMethod;
            private String deliverymanMobileNumber;
            private int exceptionCount;
            private String signedFileCode;
            private String houseNumber;
            private String state;
            private String trackingNumber;
            private String deliveryHistory;
            private String memberCode;
            private String business;
            private int deliveryEndTime;
            private String addresseeMobileNumber;
            private String deliveryman;
            private String buildingCode;
            private String deliveryTimeCode;
            private long updateTime;
            private String verifyState;
            private String buildingName;
            private String addressee;
            private long createTime;
            private String epsEmployeeCode;
            private int deliveryStartTime;
            private String noticeState;
            private String remarks;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDeliveryMethod() {
                return deliveryMethod;
            }

            public void setDeliveryMethod(String deliveryMethod) {
                this.deliveryMethod = deliveryMethod;
            }

            public String getDeliverymanMobileNumber() {
                return deliverymanMobileNumber;
            }

            public void setDeliverymanMobileNumber(String deliverymanMobileNumber) {
                this.deliverymanMobileNumber = deliverymanMobileNumber;
            }

            public int getExceptionCount() {
                return exceptionCount;
            }

            public void setExceptionCount(int exceptionCount) {
                this.exceptionCount = exceptionCount;
            }

            public String getSignedFileCode() {
                return signedFileCode;
            }

            public void setSignedFileCode(String signedFileCode) {
                this.signedFileCode = signedFileCode;
            }

            public String getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(String houseNumber) {
                this.houseNumber = houseNumber;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTrackingNumber() {
                return trackingNumber;
            }

            public void setTrackingNumber(String trackingNumber) {
                this.trackingNumber = trackingNumber;
            }

            public String getDeliveryHistory() {
                return deliveryHistory;
            }

            public void setDeliveryHistory(String deliveryHistory) {
                this.deliveryHistory = deliveryHistory;
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

            public int getDeliveryEndTime() {
                return deliveryEndTime;
            }

            public void setDeliveryEndTime(int deliveryEndTime) {
                this.deliveryEndTime = deliveryEndTime;
            }

            public String getAddresseeMobileNumber() {
                return addresseeMobileNumber;
            }

            public void setAddresseeMobileNumber(String addresseeMobileNumber) {
                this.addresseeMobileNumber = addresseeMobileNumber;
            }

            public String getDeliveryman() {
                return deliveryman;
            }

            public void setDeliveryman(String deliveryman) {
                this.deliveryman = deliveryman;
            }

            public String getBuildingCode() {
                if (TextUtils.isEmpty(buildingCode)) {
                    return "";
                }
                return buildingCode;
            }

            public void setBuildingCode(String buildingCode) {
                this.buildingCode = buildingCode;
            }

            public String getDeliveryTimeCode() {
                return deliveryTimeCode;
            }

            public void setDeliveryTimeCode(String deliveryTimeCode) {
                this.deliveryTimeCode = deliveryTimeCode;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getVerifyState() {
                return verifyState;
            }

            public void setVerifyState(String verifyState) {
                this.verifyState = verifyState;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public String getAddressee() {
                return addressee;
            }

            public void setAddressee(String addressee) {
                this.addressee = addressee;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getEpsEmployeeCode() {
                return epsEmployeeCode;
            }

            public void setEpsEmployeeCode(String epsEmployeeCode) {
                this.epsEmployeeCode = epsEmployeeCode;
            }

            public int getDeliveryStartTime() {
                return deliveryStartTime;
            }

            public void setDeliveryStartTime(int deliveryStartTime) {
                this.deliveryStartTime = deliveryStartTime;
            }

            public String getNoticeState() {
                return noticeState;
            }

            public void setNoticeState(String noticeState) {
                this.noticeState = noticeState;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }
        }
    }
}
