package com.dispatch.system.entity;

/**
 * 揽件详情
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class PickUpDetailBean extends BaseBean {

    /**
     * data : {"receivingRecord":{"addresseeAreaNumber":"130102","senderMobileNumber":"13600000123","senderAddress":"东城花园7栋501","code":"60c849af9e5d401b8915e9c5a5b45e72","articleInfo":"笔记本电脑","receivingEndTime":7200000,"receivingStartTime":3660000,"receivingHistory":"[{time:2020-7-1 10:10:00,desc:\u2018上门揽件\u2019}]","payState":"UNPAID","waiterCode":"3969184945fb4ec1bc0060007214048c","waiterMobileNumber":"18800000000","payMethod":"WX","orderRecordCode":"332384935fb4ec1bc006000721saaa","state":"CANCELLED","trackingNumber":"SF45678584671","receivingMethod":"RECEIVING","budget":25,"memberCode":"262184945fb4ec1bc006000721saaa","addresseeAreaName":"河北省石家庄市长安区","amount":10,"business":"SF","orderCreateTime":1590917962818,"addresseeMobileNumber":"13711111110","buildingCode":"aa2184945fb4ec1bc006000721sbba","weight":1,"deliveryTimeCode":"c2bea1de272241ca8a3523c8b6a90ccc","updateTime":1591081153288,"addresseeAddress":"黎明路26利丰大酒店","senderAreaName":"北京市市辖区朝阳区","addressee":"刘醒","sender":"梁非凡","createTime":1590917962902,"epsEmployeeCode":"cabea1de1234541ca8a3526a91acc11","senderAreaNumber":"110105","waiter":"李狗蛋","remarks":"到付"}}
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
         * receivingRecord : {"addresseeAreaNumber":"130102","senderMobileNumber":"13600000123","senderAddress":"东城花园7栋501","code":"60c849af9e5d401b8915e9c5a5b45e72","articleInfo":"笔记本电脑","receivingEndTime":7200000,"receivingStartTime":3660000,"receivingHistory":"[{time:2020-7-1 10:10:00,desc:\u2018上门揽件\u2019}]","payState":"UNPAID","waiterCode":"3969184945fb4ec1bc0060007214048c","waiterMobileNumber":"18800000000","payMethod":"WX","orderRecordCode":"332384935fb4ec1bc006000721saaa","state":"CANCELLED","trackingNumber":"SF45678584671","receivingMethod":"RECEIVING","budget":25,"memberCode":"262184945fb4ec1bc006000721saaa","addresseeAreaName":"河北省石家庄市长安区","amount":10,"business":"SF","orderCreateTime":1590917962818,"addresseeMobileNumber":"13711111110","buildingCode":"aa2184945fb4ec1bc006000721sbba","weight":1,"deliveryTimeCode":"c2bea1de272241ca8a3523c8b6a90ccc","updateTime":1591081153288,"addresseeAddress":"黎明路26利丰大酒店","senderAreaName":"北京市市辖区朝阳区","addressee":"刘醒","sender":"梁非凡","createTime":1590917962902,"epsEmployeeCode":"cabea1de1234541ca8a3526a91acc11","senderAreaNumber":"110105","waiter":"李狗蛋","remarks":"到付"}
         */

        private ReceivingRecordBean receivingRecord;

        public ReceivingRecordBean getReceivingRecord() {
            return receivingRecord;
        }

        public void setReceivingRecord(ReceivingRecordBean receivingRecord) {
            this.receivingRecord = receivingRecord;
        }

        public static class ReceivingRecordBean {
            /**
             * addresseeAreaNumber : 130102
             * senderMobileNumber : 13600000123
             * senderAddress : 东城花园7栋501
             * code : 60c849af9e5d401b8915e9c5a5b45e72
             * articleInfo : 笔记本电脑
             * receivingEndTime : 7200000
             * receivingStartTime : 3660000
             * receivingHistory : [{time:2020-7-1 10:10:00,desc:‘上门揽件’}]
             * payState : UNPAID
             * waiterCode : 3969184945fb4ec1bc0060007214048c
             * waiterMobileNumber : 18800000000
             * payMethod : WX
             * orderRecordCode : 332384935fb4ec1bc006000721saaa
             * state : CANCELLED
             * trackingNumber : SF45678584671
             * receivingMethod : RECEIVING
             * budget : 25.0
             * memberCode : 262184945fb4ec1bc006000721saaa
             * addresseeAreaName : 河北省石家庄市长安区
             * amount : 10
             * business : SF
             * orderCreateTime : 1590917962818
             * addresseeMobileNumber : 13711111110
             * buildingCode : aa2184945fb4ec1bc006000721sbba
             * weight : 1.0
             * deliveryTimeCode : c2bea1de272241ca8a3523c8b6a90ccc
             * updateTime : 1591081153288
             * addresseeAddress : 黎明路26利丰大酒店
             * senderAreaName : 北京市市辖区朝阳区
             * addressee : 刘醒
             * sender : 梁非凡
             * createTime : 1590917962902
             * epsEmployeeCode : cabea1de1234541ca8a3526a91acc11
             * senderAreaNumber : 110105
             * waiter : 李狗蛋
             * remarks : 到付
             */

            private String addresseeAreaNumber;
            private String type;
            private String senderMobileNumber;
            private String senderAddress;
            private String code;
            private String articleInfo;
            private int receivingEndTime;
            private int receivingStartTime;
            private String receivingHistory;
            private String payState;
            private String waiterCode;
            private String waiterMobileNumber;
            private String payMethod;
            private String orderRecordCode;
            private String state;
            private String trackingNumber;
            private String receivingMethod;
            private double budget;
            private String memberCode;
            private String addresseeAreaName;
            private int amount;
            private String business;
            private long orderCreateTime;
            private String addresseeMobileNumber;
            private String buildingCode;
            private double weight;
            private String deliveryTimeCode;
            private long updateTime;
            private String addresseeAddress;
            private String senderAreaName;
            private String addressee;
            private String sender;
            private long createTime;
            private String epsEmployeeCode;
            private String senderAreaNumber;
            private String waiter;
            private String remarks;
            private String verifyState;
            // 优惠金额
            private String discountAmount;
            // 优惠券编号
            private String couponNumber;

            public String getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(String discountAmount) {
                this.discountAmount = discountAmount;
            }

            public String getCouponNumber() {
                return couponNumber;
            }

            public void setCouponNumber(String couponNumber) {
                this.couponNumber = couponNumber;
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

            public String getAddresseeAreaNumber() {
                return addresseeAreaNumber;
            }

            public void setAddresseeAreaNumber(String addresseeAreaNumber) {
                this.addresseeAreaNumber = addresseeAreaNumber;
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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getArticleInfo() {
                return articleInfo;
            }

            public void setArticleInfo(String articleInfo) {
                this.articleInfo = articleInfo;
            }

            public int getReceivingEndTime() {
                return receivingEndTime;
            }

            public void setReceivingEndTime(int receivingEndTime) {
                this.receivingEndTime = receivingEndTime;
            }

            public int getReceivingStartTime() {
                return receivingStartTime;
            }

            public void setReceivingStartTime(int receivingStartTime) {
                this.receivingStartTime = receivingStartTime;
            }

            public String getReceivingHistory() {
                return receivingHistory;
            }

            public void setReceivingHistory(String receivingHistory) {
                this.receivingHistory = receivingHistory;
            }

            public String getPayState() {
                return payState;
            }

            public void setPayState(String payState) {
                this.payState = payState;
            }

            public String getWaiterCode() {
                return waiterCode;
            }

            public void setWaiterCode(String waiterCode) {
                this.waiterCode = waiterCode;
            }

            public String getWaiterMobileNumber() {
                return waiterMobileNumber;
            }

            public void setWaiterMobileNumber(String waiterMobileNumber) {
                this.waiterMobileNumber = waiterMobileNumber;
            }

            public String getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(String payMethod) {
                this.payMethod = payMethod;
            }

            public String getOrderRecordCode() {
                return orderRecordCode;
            }

            public void setOrderRecordCode(String orderRecordCode) {
                this.orderRecordCode = orderRecordCode;
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

            public String getReceivingMethod() {
                return receivingMethod;
            }

            public void setReceivingMethod(String receivingMethod) {
                this.receivingMethod = receivingMethod;
            }

            public double getBudget() {
                return budget;
            }

            public void setBudget(double budget) {
                this.budget = budget;
            }

            public String getMemberCode() {
                return memberCode;
            }

            public void setMemberCode(String memberCode) {
                this.memberCode = memberCode;
            }

            public String getAddresseeAreaName() {
                return addresseeAreaName;
            }

            public void setAddresseeAreaName(String addresseeAreaName) {
                this.addresseeAreaName = addresseeAreaName;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getBusiness() {
                return business;
            }

            public void setBusiness(String business) {
                this.business = business;
            }

            public long getOrderCreateTime() {
                return orderCreateTime;
            }

            public void setOrderCreateTime(long orderCreateTime) {
                this.orderCreateTime = orderCreateTime;
            }

            public String getAddresseeMobileNumber() {
                return addresseeMobileNumber;
            }

            public void setAddresseeMobileNumber(String addresseeMobileNumber) {
                this.addresseeMobileNumber = addresseeMobileNumber;
            }

            public String getBuildingCode() {
                return buildingCode;
            }

            public void setBuildingCode(String buildingCode) {
                this.buildingCode = buildingCode;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
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

            public String getAddresseeAddress() {
                return addresseeAddress;
            }

            public void setAddresseeAddress(String addresseeAddress) {
                this.addresseeAddress = addresseeAddress;
            }

            public String getSenderAreaName() {
                return senderAreaName;
            }

            public void setSenderAreaName(String senderAreaName) {
                this.senderAreaName = senderAreaName;
            }

            public String getAddressee() {
                return addressee;
            }

            public void setAddressee(String addressee) {
                this.addressee = addressee;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
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

            public String getSenderAreaNumber() {
                return senderAreaNumber;
            }

            public void setSenderAreaNumber(String senderAreaNumber) {
                this.senderAreaNumber = senderAreaNumber;
            }

            public String getWaiter() {
                return waiter;
            }

            public void setWaiter(String waiter) {
                this.waiter = waiter;
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
