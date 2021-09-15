package com.dispatch.system.entity;

import java.util.List;

public class NewListBean extends BaseBean {


    /**
     * data : {"newestList":[{"title":"您有一个派件被拒收！请打开app查看！","text":"快递编号：YT12345678910001","trackingNumber":"YT12345678910001","type":"APP_REFUSE_NOTICE"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<NewestListBean> newestList;

        public List<NewestListBean> getNewestList() {
            return newestList;
        }

        public void setNewestList(List<NewestListBean> newestList) {
            this.newestList = newestList;
        }

        public static class NewestListBean {
            /**
             * title : 您有一个派件被拒收！请打开app查看！
             * text : 快递编号：YT12345678910001
             * trackingNumber : YT12345678910001
             * type : APP_REFUSE_NOTICE
             */

            private String title;
            private String text;
            private String trackingNumber;
            private String type;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getTrackingNumber() {
                return trackingNumber;
            }

            public void setTrackingNumber(String trackingNumber) {
                this.trackingNumber = trackingNumber;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
