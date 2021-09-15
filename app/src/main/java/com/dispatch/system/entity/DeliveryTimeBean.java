package com.dispatch.system.entity;

import androidx.annotation.NonNull;

import java.util.List;

public class DeliveryTimeBean extends BaseBean{

    /**
     * data : {"deliveryTimes":[{"code":"9c7cd917c67847edb1d7f8434b3ce31f","startTime":217000,"endTime":3817000,"type":"MORNING"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DeliveryTimesBean> deliveryTimes;

        public List<DeliveryTimesBean> getDeliveryTimes() {
            return deliveryTimes;
        }

        public void setDeliveryTimes(List<DeliveryTimesBean> deliveryTimes) {
            this.deliveryTimes = deliveryTimes;
        }

        public static class DeliveryTimesBean implements Cloneable {
            /**
             * code : 9c7cd917c67847edb1d7f8434b3ce31f
             * startTime : 217000
             * endTime : 3817000
             * type : MORNING
             */

            private String code;
            private int startTime;
            private int endTime;
            private String type; // 派送频次 MORNING（上午）、AFTERNOON（下午）

            /**
             * 显示的文案，客户端使用
             */
            private String showText;
            public boolean isCheck;

            public String getShowText() {
                return showText;
            }

            public void setShowText(String showText) {
                this.showText = showText;
            }

            public boolean isMorning() {
                return "MORNING".equals(type);
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @NonNull
            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
    }
}
