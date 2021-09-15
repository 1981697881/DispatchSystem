package com.dispatch.system.entity;

import java.util.List;

public class SearchMemberBean extends BaseBean {

    /**
     * data : {"receivingRecord":{"addresseeAreaNumber":"130102","senderMobileNumber":"13600000123","senderAddress":"东城花园7栋501","code":"60c849af9e5d401b8915e9c5a5b45e72","articleInfo":"笔记本电脑","receivingEndTime":7200000,"receivingStartTime":3660000,"receivingHistory":"[{time:2020-7-1 10:10:00,desc:\u2018上门揽件\u2019}]","payState":"UNPAID","waiterCode":"3969184945fb4ec1bc0060007214048c","waiterMobileNumber":"18800000000","payMethod":"WX","orderRecordCode":"332384935fb4ec1bc006000721saaa","state":"CANCELLED","trackingNumber":"SF45678584671","receivingMethod":"RECEIVING","budget":25,"memberCode":"262184945fb4ec1bc006000721saaa","addresseeAreaName":"河北省石家庄市长安区","amount":10,"business":"SF","orderCreateTime":1590917962818,"addresseeMobileNumber":"13711111110","buildingCode":"aa2184945fb4ec1bc006000721sbba","weight":1,"deliveryTimeCode":"c2bea1de272241ca8a3523c8b6a90ccc","updateTime":1591081153288,"addresseeAddress":"黎明路26利丰大酒店","senderAreaName":"北京市市辖区朝阳区","addressee":"刘醒","sender":"梁非凡","createTime":1590917962902,"epsEmployeeCode":"cabea1de1234541ca8a3526a91acc11","senderAreaNumber":"110105","waiter":"李狗蛋","remarks":"到付"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<MemberBean> members;

        public List<MemberBean> getMembers() {
            return members;
        }

        public void setMembers(List<MemberBean> members) {
            this.members = members;
        }
    }

}
