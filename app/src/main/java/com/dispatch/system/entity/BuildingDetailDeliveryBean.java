package com.dispatch.system.entity;

import com.dispatch.system.utils.BusinessHelper;

import java.util.List;

/**
 * 
 * <p>
 * 
 * @author chenjunxu
 * @date 2020/7/23
 */
public class BuildingDetailDeliveryBean extends BaseBean {

    /**
     * data : {"houseDeliveryRecords":[{"code":"29ce8d13faf24f9fa73ab482e9f78889","addressee":"王小二","addresseeMobileNumber":"13454554567","state":"SIGNED","signedTime":1593391856738,"business":"YD","houseNumber":"A001","buildingName":"测试花园 C区 A栋","trackingNumber":"YD568487779990044"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HouseDeliveryRecordsBean> houseDeliveryRecords;

        public List<HouseDeliveryRecordsBean> getHouseDeliveryRecords() {
            return houseDeliveryRecords;
        }

        public void setHouseDeliveryRecords(List<HouseDeliveryRecordsBean> houseDeliveryRecords) {
            this.houseDeliveryRecords = houseDeliveryRecords;
        }

        public static class HouseDeliveryRecordsBean {
            /**
             * code : 29ce8d13faf24f9fa73ab482e9f78889
             * addressee : 王小二
             * addresseeMobileNumber : 13454554567
             * state : SIGNED
             * signedTime : 1593391856738
             * business : YD
             * houseNumber : A001
             * buildingName : 测试花园 C区 A栋
             * trackingNumber : YD568487779990044
             */

            private String code;
            private String addressee;
            private String addresseeMobileNumber;
            private String state;
            private long signedTime;
            private long createTime;
            private String business;
            private String houseNumber;
            private String buildingName;
            private String trackingNumber;
            private String exceptionDesc;

            private boolean isCheck;
            private boolean isOpenCheckMode;

            public String getExceptionDesc() {
                return exceptionDesc;
            }

            public void setExceptionDesc(String exceptionDesc) {
                this.exceptionDesc = exceptionDesc;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public boolean isOpenCheckMode() {
                return isOpenCheckMode;
            }

            public void setOpenCheckMode(boolean openCheckMode) {
                isOpenCheckMode = openCheckMode;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public long getSignedTime() {
                return signedTime;
            }

            public void setSignedTime(long signedTime) {
                this.signedTime = signedTime;
            }

            public String getBusiness() {
                return BusinessHelper.get(business);
            }

            public void setBusiness(String business) {
                this.business = business;
            }

            public String getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(String houseNumber) {
                this.houseNumber = houseNumber;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public String getTrackingNumber() {
                return trackingNumber;
            }

            public void setTrackingNumber(String trackingNumber) {
                this.trackingNumber = trackingNumber;
            }
        }
    }
}
