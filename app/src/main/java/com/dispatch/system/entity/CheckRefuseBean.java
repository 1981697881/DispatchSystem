package com.dispatch.system.entity;

/**
 * 检测是否拒收件
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/20
 */
public class CheckRefuseBean extends BaseBean {

    /**
     * data : {"isMemberRefuse":true,"refuseDesc":"不需要了"}
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
         * isMemberRefuse : true
         * refuseDesc : 不需要了
         */

        private boolean isMemberRefuse;
        private String refuseDesc;
        // DELIVERY（派送件）、SELF（自提件）
        private String deliveryMethod;

        public String getDeliveryMethod() {
            return deliveryMethod;
        }

        public void setDeliveryMethod(String deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
        }

        public boolean isIsMemberRefuse() {
            return isMemberRefuse;
        }

        public void setIsMemberRefuse(boolean isMemberRefuse) {
            this.isMemberRefuse = isMemberRefuse;
        }

        public String getRefuseDesc() {
            return refuseDesc;
        }

        public void setRefuseDesc(String refuseDesc) {
            this.refuseDesc = refuseDesc;
        }
    }
}
