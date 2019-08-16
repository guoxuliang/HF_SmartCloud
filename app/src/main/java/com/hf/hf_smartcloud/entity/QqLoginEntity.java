package com.hf.hf_smartcloud.entity;

import java.util.List;

public class QqLoginEntity {

    /**
     * ret : 200
     * data : {"status":0,"msg":"success","error":{},"lists":{"customer_id":"86","customer_group_id":"1","customer_pay_level_id":"3","customer_vitality_id":"2","customer_permission_ids":"3","customer_address_id":"0","account":"QQ::UID_4D75F46C490FBFC494FB36FB5CEADC1F","nickname":"","sex":"1","birthday":"1900-01-01","pic":"","industry":"","company":"","qq":"UID_4D75F46C490FBFC494FB36FB5CEADC1F","wechat":"","trust":"60","register_time":"2019-07-31 09:11:30","real_identity":"0","status":"1","remark":"","pay_password":0,"id_card":0,"token":"6yCuJpwq0gbrw5MogNXdIuPsJIuA1roUKp6Nmh2g6AeSW6hhauDoawyRsBtUjCvC","expires_time":"2019-08-30 09:11:30","type":"qq"}}
     * msg :
     * debug : {"stack":["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 1ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 32ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"],"sqls":[],"version":"2.6.1"}
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
         * lists : {"customer_id":"86","customer_group_id":"1","customer_pay_level_id":"3","customer_vitality_id":"2","customer_permission_ids":"3","customer_address_id":"0","account":"QQ::UID_4D75F46C490FBFC494FB36FB5CEADC1F","nickname":"","sex":"1","birthday":"1900-01-01","pic":"","industry":"","company":"","qq":"UID_4D75F46C490FBFC494FB36FB5CEADC1F","wechat":"","trust":"60","register_time":"2019-07-31 09:11:30","real_identity":"0","status":"1","remark":"","pay_password":0,"id_card":0,"token":"6yCuJpwq0gbrw5MogNXdIuPsJIuA1roUKp6Nmh2g6AeSW6hhauDoawyRsBtUjCvC","expires_time":"2019-08-30 09:11:30","type":"qq"}
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
            /**
             * fail : fail
             */

            private String fail;

            public String getFail() {
                return fail;
            }

            public void setFail(String fail) {
                this.fail = fail;
            }
        }

        public static class ListsBean {
            /**
             * customer_id : 86
             * customer_group_id : 1
             * customer_pay_level_id : 3
             * customer_vitality_id : 2
             * customer_permission_ids : 3
             * customer_address_id : 0
             * account : QQ::UID_4D75F46C490FBFC494FB36FB5CEADC1F
             * nickname :
             * sex : 1
             * birthday : 1900-01-01
             * pic :
             * industry :
             * company :
             * qq : UID_4D75F46C490FBFC494FB36FB5CEADC1F
             * wechat :
             * trust : 60
             * register_time : 2019-07-31 09:11:30
             * real_identity : 0
             * status : 1
             * remark :
             * pay_password : 0
             * id_card : 0
             * token : 6yCuJpwq0gbrw5MogNXdIuPsJIuA1roUKp6Nmh2g6AeSW6hhauDoawyRsBtUjCvC
             * expires_time : 2019-08-30 09:11:30
             * type : qq
             */

            private String customer_id;
            private String customer_group_id;
            private String customer_pay_level_id;
            private String customer_vitality_id;
            private String customer_permission_ids;
            private String customer_address_id;
            private String account;
            private String nickname;
            private String sex;
            private String birthday;
            private String pic;
            private String industry;
            private String company;
            private String qq;
            private String wechat;
            private String trust;
            private String register_time;
            private String real_identity;
            private String status;
            private String remark;
            private int pay_password;
            private int id_card;
            private String token;
            private String expires_time;
            private String type;

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCustomer_group_id() {
                return customer_group_id;
            }

            public void setCustomer_group_id(String customer_group_id) {
                this.customer_group_id = customer_group_id;
            }

            public String getCustomer_pay_level_id() {
                return customer_pay_level_id;
            }

            public void setCustomer_pay_level_id(String customer_pay_level_id) {
                this.customer_pay_level_id = customer_pay_level_id;
            }

            public String getCustomer_vitality_id() {
                return customer_vitality_id;
            }

            public void setCustomer_vitality_id(String customer_vitality_id) {
                this.customer_vitality_id = customer_vitality_id;
            }

            public String getCustomer_permission_ids() {
                return customer_permission_ids;
            }

            public void setCustomer_permission_ids(String customer_permission_ids) {
                this.customer_permission_ids = customer_permission_ids;
            }

            public String getCustomer_address_id() {
                return customer_address_id;
            }

            public void setCustomer_address_id(String customer_address_id) {
                this.customer_address_id = customer_address_id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWechat() {
                return wechat;
            }

            public void setWechat(String wechat) {
                this.wechat = wechat;
            }

            public String getTrust() {
                return trust;
            }

            public void setTrust(String trust) {
                this.trust = trust;
            }

            public String getRegister_time() {
                return register_time;
            }

            public void setRegister_time(String register_time) {
                this.register_time = register_time;
            }

            public String getReal_identity() {
                return real_identity;
            }

            public void setReal_identity(String real_identity) {
                this.real_identity = real_identity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getPay_password() {
                return pay_password;
            }

            public void setPay_password(int pay_password) {
                this.pay_password = pay_password;
            }

            public int getId_card() {
                return id_card;
            }

            public void setId_card(int id_card) {
                this.id_card = id_card;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getExpires_time() {
                return expires_time;
            }

            public void setExpires_time(String expires_time) {
                this.expires_time = expires_time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class DebugBean {
        /**
         * stack : ["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 1ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 32ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"]
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
