package com.dispatch.system.entity;

import java.util.List;

/**
 * 楼栋任务列表
 * <p>
 * @author chenjunxu
 * @date 2020/7/18
 */
public class BuildingTaskListBean extends BaseBean {

    /**
     * data : {"houseTasks":[{"buildingCode":"29ce8d13faf24f9fa73ab482e9f78889","houseNumber":"1101","notDeliveryCount":0,"deliveryCount":0,"receivingCount":0,"notReceivingCount":0}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HouseTasksBean> houseTasks;

        public List<HouseTasksBean> getHouseTasks() {
            return houseTasks;
        }

        public void setHouseTasks(List<HouseTasksBean> houseTasks) {
            this.houseTasks = houseTasks;
        }

        public static class HouseTasksBean {
            /**
             * buildingCode : 29ce8d13faf24f9fa73ab482e9f78889
             * houseNumber : 1101
             * notDeliveryCount : 0
             * deliveryCount : 0
             * receivingCount : 0
             * notReceivingCount : 0
             */

            private String buildingCode;
            private String houseNumber;
            private int notDeliveryCount;
            private int deliveryCount;
            private int receivingCount;
            private int notReceivingCount;
            // 包含加急件（只有一个派送时间）
            private boolean containUrgentItem;
            private int timeoutCount;

            public boolean isContainUrgentItem() {
                return containUrgentItem;
            }

            public void setContainUrgentItem(boolean containUrgentItem) {
                this.containUrgentItem = containUrgentItem;
            }

            public int getTimeoutCount() {
                return timeoutCount;
            }

            public void setTimeoutCount(int timeoutCount) {
                this.timeoutCount = timeoutCount;
            }

            public String getBuildingCode() {
                return buildingCode;
            }

            public void setBuildingCode(String buildingCode) {
                this.buildingCode = buildingCode;
            }

            public String getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(String houseNumber) {
                this.houseNumber = houseNumber;
            }

            public int getNotDeliveryCount() {
                return notDeliveryCount;
            }

            public void setNotDeliveryCount(int notDeliveryCount) {
                this.notDeliveryCount = notDeliveryCount;
            }

            public int getDeliveryCount() {
                return deliveryCount;
            }

            public void setDeliveryCount(int deliveryCount) {
                this.deliveryCount = deliveryCount;
            }

            public int getReceivingCount() {
                return receivingCount;
            }

            public void setReceivingCount(int receivingCount) {
                this.receivingCount = receivingCount;
            }

            public int getNotReceivingCount() {
                return notReceivingCount;
            }

            public void setNotReceivingCount(int notReceivingCount) {
                this.notReceivingCount = notReceivingCount;
            }
        }
    }
}
