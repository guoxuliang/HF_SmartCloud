package com.hf.hf_smartcloud.entity;

import java.util.List;

public class LoginDevEntity {


    /**
     * ret : 200
     * data : {"status":0,"msg":"success","error":{},"lists":[{"uuid":"86b25bf7-ddc0-40a1-9f70-8a68f0bfecda","name":"HUAWEI G750-T01","add_time":"2019-08-02 17:42:16"}]}
     * msg :
     * debug : {"stack":["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 0ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 49ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"],"sqls":[],"version":"2.6.1"}
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
         * lists : [{"uuid":"86b25bf7-ddc0-40a1-9f70-8a68f0bfecda","name":"HUAWEI G750-T01","add_time":"2019-08-02 17:42:16"}]
         */

        private int status;
        private String msg;
        private ErrorBean error;
        private List<ListsBean> lists;

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

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ErrorBean {
        }

        public static class ListsBean {
            /**
             * uuid : 86b25bf7-ddc0-40a1-9f70-8a68f0bfecda
             * name : HUAWEI G750-T01
             * add_time : 2019-08-02 17:42:16
             */

            private String uuid;
            private String name;
            private String add_time;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }

    public static class DebugBean {
        /**
         * stack : ["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 0ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 49ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"]
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
