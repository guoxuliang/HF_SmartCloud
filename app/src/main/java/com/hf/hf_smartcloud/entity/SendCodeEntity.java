package com.hf.hf_smartcloud.entity;

import java.util.List;

public class SendCodeEntity {

    /**
     * ret : 200
     * data : {"status":0,"msg":"success","error":{},"lists":{"captcha_type_id":1,"account":"+86-13326917705"}}
     * msg :
     * debug : {"stack":["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 2ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 282ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"],"sqls":[],"version":"2.6.1"}
     */

    private int ret;
    private DataBean data;
    private String msg;
    private DebugBean debug;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DebugBean getDebug() {
        return debug;
    }

    public void setDebug(DebugBean debug) {
        this.debug = debug;
    }

    public static class DataBean {
        /**
         * status : 0
         * msg : success
         * error : {}
         * lists : {"captcha_type_id":1,"account":"+86-13326917705"}
         */

        private int status;
        private String msg;
        private ErrorBean error;
        private ListsBean lists;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ErrorBean getError() {
            return error;
        }

        public void setError(ErrorBean error) {
            this.error = error;
        }

        public ListsBean getLists() {
            return lists;
        }

        public void setLists(ListsBean lists) {
            this.lists = lists;
        }

        public static class ErrorBean {
        }

        public static class ListsBean {
            /**
             * captcha_type_id : 1
             * account : +86-13326917705
             */

            private int captcha_type_id;
            private String account;

            public int getCaptcha_type_id() {
                return captcha_type_id;
            }

            public void setCaptcha_type_id(int captcha_type_id) {
                this.captcha_type_id = captcha_type_id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }
        }
    }

    public static class DebugBean {
        /**
         * stack : ["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 2ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 282ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"]
         * sqls : []
         * version : 2.6.1
         */

        private String version;
        private List<String> stack;
        private List<?> sqls;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<String> getStack() {
            return stack;
        }

        public void setStack(List<String> stack) {
            this.stack = stack;
        }

        public List<?> getSqls() {
            return sqls;
        }

        public void setSqls(List<?> sqls) {
            this.sqls = sqls;
        }
    }
}
