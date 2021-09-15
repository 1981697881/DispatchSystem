package com.dispatch.system.entity;

import java.util.List;

public class HouseReceivingListBean extends BaseBean {

    private static final long serialVersionUID = 2165244966635884681L;

    /**
     * data : {"houseReceivingRecords":[{"code":"29ce8d13faf24f9fa73ab482e9f78889"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ReceivingRecordsBean> houseReceivingRecords;

        public List<ReceivingRecordsBean> getHouseReceivingRecords() {
            return houseReceivingRecords;
        }

        public void setHouseReceivingRecords(List<ReceivingRecordsBean> houseReceivingRecords) {
            this.houseReceivingRecords = houseReceivingRecords;
        }
    }
}
