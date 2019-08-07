package com.hf.hf_smartcloud.entity;

import java.util.List;

public class QueryAddressEntity {


    /**
     * ret : 200
     * data : {"status":0,"msg":"success","error":{},"lists":[{"customer_address_id":"29","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦dfgsdgdfsgds","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"30","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦bxf","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"31","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"32","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦vvvvvvvvv","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"33","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦vvvvvvvvv","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"34","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦qqqqqq","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"35","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦wwwwwwwwww","postcode":"710000","remark":"","is_default":"1"}]}
     * msg :
     * debug : {"stack":["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 1ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 43ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"],"sqls":[],"version":"2.6.1"}
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
         * lists : [{"customer_address_id":"29","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦dfgsdgdfsgds","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"30","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦bxf","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"31","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"32","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦vvvvvvvvv","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"33","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦vvvvvvvvv","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"34","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦qqqqqq","postcode":"710000","remark":"","is_default":"0"},{"customer_address_id":"35","customer_id":"102","recipients":"忍者","email":"632977592@qq.com","telephone":"+86-18191757870","address":"陕西省西安市新城区金花北路西铁工程大厦wwwwwwwwww","postcode":"710000","remark":"","is_default":"1"}]
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
             * customer_address_id : 29
             * customer_id : 102
             * recipients : 忍者
             * email : 632977592@qq.com
             * telephone : +86-18191757870
             * address : 陕西省西安市新城区金花北路西铁工程大厦dfgsdgdfsgds
             * postcode : 710000
             * remark :
             * is_default : 0
             */

            private String customer_address_id;
            private String customer_id;
            private String recipients;
            private String email;
            private String telephone;
            private String address;
            private String postcode;
            private String remark;
            private String is_default;

            public String getCustomer_address_id() {
                return customer_address_id;
            }

            public void setCustomer_address_id(String customer_address_id) {
                this.customer_address_id = customer_address_id;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getRecipients() {
                return recipients;
            }

            public void setRecipients(String recipients) {
                this.recipients = recipients;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }
        }
    }

    public static class DebugBean {
        /**
         * stack : ["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 1ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 43ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"]
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
