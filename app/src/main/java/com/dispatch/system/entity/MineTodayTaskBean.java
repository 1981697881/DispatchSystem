package com.dispatch.system.entity;

import java.util.List;

/**
 * 我今天的任务
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/18
 */
public class MineTodayTaskBean extends BaseBean {

    private static final long serialVersionUID = 12158935578781062L;


    /**
     * data : {"deliveryTimeTasks":[{"code":"9c7cd917c67847edb1d7f8434b3ce31f","timeInterval":"08:03~09:03","buildingTaskCountList":[{"code":"6cd4e787977a4819a094c118f5497d67","name":"测试花园 商业区 A栋","notDeliveryCount":0,"deliveryCount":0,"receivingCount":0,"notReceivingCount":0}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean {

        private static final long serialVersionUID = -84878963958474918L;

        private List<DeliveryTimeTasksBean> deliveryTimeTasks;

        public List<DeliveryTimeTasksBean> getDeliveryTimeTasks() {
            return deliveryTimeTasks;
        }

        public void setDeliveryTimeTasks(List<DeliveryTimeTasksBean> deliveryTimeTasks) {
            this.deliveryTimeTasks = deliveryTimeTasks;
        }

        public static class DeliveryTimeTasksBean extends BaseBean {

            private static final long serialVersionUID = -3375837341878468927L;

            /**
             * code : 9c7cd917c67847edb1d7f8434b3ce31f
             * timeInterval : 08:03~09:03
             * buildingTaskCountList : [{"code":"6cd4e787977a4819a094c118f5497d67","name":"测试花园 商业区 A栋","notDeliveryCount":0,"deliveryCount":0,"receivingCount":0,"notReceivingCount":0}]
             */

            private String code;
            private String timeInterval;
            private List<BuildingTaskCountListBean> buildingTaskCountList;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTimeInterval() {
                return timeInterval;
            }

            public void setTimeInterval(String timeInterval) {
                this.timeInterval = timeInterval;
            }

            public List<BuildingTaskCountListBean> getBuildingTaskCountList() {
                return buildingTaskCountList;
            }

            public void setBuildingTaskCountList(List<BuildingTaskCountListBean> buildingTaskCountList) {
                this.buildingTaskCountList = buildingTaskCountList;
            }

            public static class BuildingTaskCountListBean extends BaseBean {

                private static final long serialVersionUID = 8916250303203817411L;
                /**
                 * code : 6cd4e787977a4819a094c118f5497d67
                 * name : 测试花园 商业区 A栋
                 * notDeliveryCount : 0
                 * deliveryCount : 0
                 * receivingCount : 0
                 * notReceivingCount : 0
                 */

                private String code;
                private String name;
                private int notDeliveryCount;
                private int deliveryCount;
                private int receivingCount;
                private int notReceivingCount;
                private int timeoutCount;
                // 包含加急件（只有一个派送时间）
                private boolean containUrgentItem;

                public boolean isContainUrgentItem() {
                    return containUrgentItem;
                }

                public void setContainUrgentItem(boolean containUrgentItem) {
                    this.containUrgentItem = containUrgentItem;
                }

                public String getDeliveryTimeCode() {
                    return deliveryTimeCode;
                }

                public void setDeliveryTimeCode(String deliveryTimeCode) {
                    this.deliveryTimeCode = deliveryTimeCode;
                }

                public String deliveryTimeCode;

                public int getTimeoutCount() {
                    return timeoutCount;
                }

                public void setTimeoutCount(int timeoutCount) {
                    this.timeoutCount = timeoutCount;
                }

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
}
