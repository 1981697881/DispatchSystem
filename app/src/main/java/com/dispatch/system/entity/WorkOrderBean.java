package com.dispatch.system.entity;

import java.util.List;

/**
 * 工单
 */
public class WorkOrderBean extends BaseBean{
    private DataBean data;

    public DataBean getData() {
        return data;
    }
    public void setData(DataBean data) {
        this.data = data;
    }
    public static class DataBean {

        private List<workOrderBean>  workOrder;

        public List<workOrderBean> getWorkOrder() {
            return workOrder;
        }
        public void setWorkOrder(List<workOrderBean> workOrder) {
            this.workOrder = workOrder;
        }
        public static class workOrderBean {
            private int id;
            private String waybillNo;
            private String remark;
            private String senderName;
            private String senderWorkNo;
            private String isUrgent;
            private String customerContact;
            private String customerName;
            private String customerAddress;
            private String status;
            private String feedback;

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setWaybillno(String waybillNo) {
                this.waybillNo = waybillNo;
            }
            public String getWaybillno() {
                return waybillNo;
            }



            public void setRemark(String remark) {
                this.remark = remark;
            }
            public String getRemark() {
                return remark;
            }

            public void setSendername(String sendername) {
                this.senderName = senderName;
            }
            public String getSendername() {
                return senderName;
            }

            public void setSenderworkno(String senderWorkNo) {
                this.senderWorkNo = senderWorkNo;
            }
            public String getSenderworkno() {
                return senderWorkNo;
            }

            public void setIsurgent(String isUrgent) {
                this.isUrgent = isUrgent;
            }
            public String getIsurgent() {
                return isUrgent;
            }

            public void setCustomercontact(String customerContact) {
                this.customerContact = customerContact;
            }
            public String getCustomercontact() {
                return customerContact;
            }

            public void setCustomername(String customerName) {
                this.customerName = customerName;
            }
            public String getCustomername() {
                return customerName;
            }

            public void setCustomeraddress(String customerAddress) {
                this.customerAddress = customerAddress;
            }
            public String getCustomeraddress() {
                return customerAddress;
            }

            public void setStatus(String status) {
                this.status = status;
            }
            public String getStatus() {
                return status;
            }

            public void setFeedback(String feedback) {
                this.feedback = feedback;
            }
            public String getFeedback() {
                return feedback;
            }
        }
    }
}
