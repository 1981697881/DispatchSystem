package com.dispatch.system.entity;

import java.util.List;

public class BusinessBean extends BaseBean {

    /**
     * data : {"businesses":[{"name":"申通快递","value":"STO"},{"name":"中通快递","value":"ZTO"},{"name":"韵达快递","value":"YD"},{"name":"圆通快递","value":"YT"},{"name":"顺丰快递","value":"SF"},{"name":"德邦快递","value":"DB"},{"name":"百世快递","value":"BS"},{"name":"天天快递","value":"TT"},{"name":"中国邮政","value":"YZ"},{"name":"EMS快递","value":"EMS"},{"name":"天猫超市","value":"TM"},{"name":"京东物流","value":"JD"},{"name":"苏宁物流","value":"SN"},{"name":"极兔快递","value":"JT"},{"name":"快捷快递","value":"KJ"},{"name":"安能快递","value":"AN"},{"name":"优速快递","value":"YS"},{"name":"宅急送","value":"ZJS"},{"name":"其他快递","value":"QT"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BusinessesBean> businesses;

        public List<BusinessesBean> getBusinesses() {
            return businesses;
        }

        public void setBusinesses(List<BusinessesBean> businesses) {
            this.businesses = businesses;
        }

        public static class BusinessesBean {
            /**
             * name : 申通快递
             * value : STO
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
