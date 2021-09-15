package com.dispatch.system.entity;

public class UploadSignBean extends BaseBean {

    private static final long serialVersionUID = -3967653753856708048L;


    /**
     * data : {"code":"a53be68798aa422bb400acbc977e1883"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean {

        private static final long serialVersionUID = -814998368019097720L;

        /**
         * code : a53be68798aa422bb400acbc977e1883
         */

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
