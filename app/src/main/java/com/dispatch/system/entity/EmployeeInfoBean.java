package com.dispatch.system.entity;

/**
 * 员工信息
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/24
 */
public class EmployeeInfoBean extends BaseBean {

    /**
     * data : {"epsEmployee":{"realName":"李狗蛋","number":"0001","roleFlags":"SORTER,COURIER,COLLECTOR","code":"3969184945fb4ec1bc0060007214048c","gender":"MAN","createTime":1590393855401,"mobileNumber":"18800000000","updateTime":1590393855401,"userCode":"367fbac0d8b5499d8afa356dbf0d8e82"}}
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
         * epsEmployee : {"realName":"李狗蛋","number":"0001","roleFlags":"SORTER,COURIER,COLLECTOR","code":"3969184945fb4ec1bc0060007214048c","gender":"MAN","createTime":1590393855401,"mobileNumber":"18800000000","updateTime":1590393855401,"userCode":"367fbac0d8b5499d8afa356dbf0d8e82"}
         */

        private EpsEmployeeBean epsEmployee;

        public EpsEmployeeBean getEpsEmployee() {
            return epsEmployee;
        }

        public void setEpsEmployee(EpsEmployeeBean epsEmployee) {
            this.epsEmployee = epsEmployee;
        }

        public static class EpsEmployeeBean {
            /**
             * realName : 李狗蛋
             * number : 0001
             * roleFlags : SORTER,COURIER,COLLECTOR
             * code : 3969184945fb4ec1bc0060007214048c
             * gender : MAN
             * createTime : 1590393855401
             * mobileNumber : 18800000000
             * updateTime : 1590393855401
             * userCode : 367fbac0d8b5499d8afa356dbf0d8e82
             */

            private String realName;
            private String number;
            private String roleFlags;
            private String code;
            private String gender;
            private long createTime;
            private String mobileNumber;
            private long updateTime;
            private String userCode;

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getRoleFlags() {
                return roleFlags;
            }

            public void setRoleFlags(String roleFlags) {
                this.roleFlags = roleFlags;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }
        }
    }
}
