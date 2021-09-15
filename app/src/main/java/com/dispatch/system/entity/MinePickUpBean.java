package com.dispatch.system.entity;

import java.util.List;

/**
 * 我的揽件实体
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class MinePickUpBean extends BaseBean {


    /**
     * data : {"receivingRecords":[{"code":"29ce8d13faf24f9fa73ab482e9f78889","receivingMethod":"RECEIVING","trackingNumber":"YD568487779990044","addressee":"王小二","addresseeMobileNumber":"13454554567","addresseeAreaName":"北京市市辖区东城区","addresseeAddress":"东城花园C区12栋502","sender":"王二狗","senderMobileNumber":"13454554588","senderAddress":"广东省广州市天河区","senderAreaName":"白沙水路26号","state":"SIGNED","business":"YD","payState":"PAID","amount":12}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ReceivingRecordsBean> receivingRecords;

        public List<ReceivingRecordsBean> getReceivingRecords() {
            return receivingRecords;
        }

        public void setReceivingRecords(List<ReceivingRecordsBean> receivingRecords) {
            this.receivingRecords = receivingRecords;
        }
    }
}
