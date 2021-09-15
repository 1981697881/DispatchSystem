package com.dispatch.system.entity;

import java.util.List;

/**
 * 异常原因模板
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/21
 */
public class ExceptionTemplateBean extends BaseBean {

    /**
     * data : {"exceptionTemplates":[{"number":"10001","code":"5d7074d633f1468d985bf1b4fffd1971","description":"电话暂无人接听"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ExceptionTemplatesBean> exceptionTemplates;

        public List<ExceptionTemplatesBean> getExceptionTemplates() {
            return exceptionTemplates;
        }

        public void setExceptionTemplates(List<ExceptionTemplatesBean> exceptionTemplates) {
            this.exceptionTemplates = exceptionTemplates;
        }

        public static class ExceptionTemplatesBean {
            /**
             * number : 10001
             * code : 5d7074d633f1468d985bf1b4fffd1971
             * description : 电话暂无人接听
             */

            private String number;
            private String code;
            private String description;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
