package com.dispatch.system.entity;

/**
 * 登录
 * <p>
 * @author chenjunxu
 * @date 2020/7/18
 */
public class LoginBean extends BaseBean {
    

    /**
     * data : {"passport":{"code":"367fbac0d8b5499d8afa356dbf0d8e82","realName":"李狗蛋","nickname":"李狗蛋","account":"18800000000","verifyCode":null,"email":null,"mobile":"18800000000","lastUpdatePwdTime":1590395664853,"birthday":null,"appCode":null,"loginType":"1"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean{
        /**
         * passport : {"code":"367fbac0d8b5499d8afa356dbf0d8e82","realName":"李狗蛋","nickname":"李狗蛋","account":"18800000000","verifyCode":null,"email":null,"mobile":"18800000000","lastUpdatePwdTime":1590395664853,"birthday":null,"appCode":null,"loginType":"1"}
         */

        private PassportBean passport;

        public PassportBean getPassport() {
            return passport;
        }

        public void setPassport(PassportBean passport) {
            this.passport = passport;
        }

        public static class PassportBean extends BaseBean {
            /**
             * code : 367fbac0d8b5499d8afa356dbf0d8e82
             * realName : 李狗蛋
             * nickname : 李狗蛋
             * account : 18800000000
             * verifyCode : null
             * email : null
             * mobile : 18800000000
             * lastUpdatePwdTime : 1590395664853
             * birthday : null
             * appCode : null
             * loginType : 1
             */

            private String code;
            private String realName;
            private String nickname;
            private String account;
            private String verifyCode;
            private String email;
            private String mobile;
            private long lastUpdatePwdTime;
            private String birthday;
            private String appCode;
            private String loginType;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getVerifyCode() {
                return verifyCode;
            }

            public void setVerifyCode(String verifyCode) {
                this.verifyCode = verifyCode;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public long getLastUpdatePwdTime() {
                return lastUpdatePwdTime;
            }

            public void setLastUpdatePwdTime(long lastUpdatePwdTime) {
                this.lastUpdatePwdTime = lastUpdatePwdTime;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAppCode() {
                return appCode;
            }

            public void setAppCode(String appCode) {
                this.appCode = appCode;
            }

            public String getLoginType() {
                return loginType;
            }

            public void setLoginType(String loginType) {
                this.loginType = loginType;
            }
        }
    }
}
