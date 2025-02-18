package com.hf.hf_smartcloud.entity;

import java.util.List;

public class SelectTradeEntity {


    /**
     * ret : 200
     * data : {"status":0,"msg":"success","error":{},"lists":["餐饮业","广告","文字媒体/出版","机械/设备/重工","印刷/包装/造纸","采掘业/冶炼","娱乐/休闲/体育","法律","石油/化工/矿产/地质","环保","交通/运输/物流","批发/零售","教育/培训","学术/科研","房地产开发","生活服务","政府","农业/渔业/林业","其他行业","通信/电信/网络设备","互联网/电子商务","汽车及零配件","中介服务","仪器仪表/工业自动化","电力/水利","计算机硬件","计算机服务(系统/数据服务/维修)","通信/电信运营/增值服务","网络游戏","会计/审计","银行","保险","家具/家电/工艺品/玩具","办公用品及设备","医疗/护理/保健/卫生","医疗设备/器械","公关/市场推广/会展","影视/媒体/艺术","家居/室内设计/装潢","物业管理/商业中心","检测/认证","酒店/旅游","美容/保健","航天/航空","原材料和加工","非盈利机构","多元化业务集团公司","计算机软件","电子技术/半导体/集成电路","金融/投资/证券","贸易/进出口","快速消费品(食品/饮料/化妆品)","服装/纺织/皮革","制药/生物工程","建筑与工程","专业服务(咨询/人力资源)","其他行业"]}
     * msg :
     * debug : {"stack":["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 2ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 13ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"],"sqls":[],"version":"2.6.1"}
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
         * lists : ["餐饮业","广告","文字媒体/出版","机械/设备/重工","印刷/包装/造纸","采掘业/冶炼","娱乐/休闲/体育","法律","石油/化工/矿产/地质","环保","交通/运输/物流","批发/零售","教育/培训","学术/科研","房地产开发","生活服务","政府","农业/渔业/林业","其他行业","通信/电信/网络设备","互联网/电子商务","汽车及零配件","中介服务","仪器仪表/工业自动化","电力/水利","计算机硬件","计算机服务(系统/数据服务/维修)","通信/电信运营/增值服务","网络游戏","会计/审计","银行","保险","家具/家电/工艺品/玩具","办公用品及设备","医疗/护理/保健/卫生","医疗设备/器械","公关/市场推广/会展","影视/媒体/艺术","家居/室内设计/装潢","物业管理/商业中心","检测/认证","酒店/旅游","美容/保健","航天/航空","原材料和加工","非盈利机构","多元化业务集团公司","计算机软件","电子技术/半导体/集成电路","金融/投资/证券","贸易/进出口","快速消费品(食品/饮料/化妆品)","服装/纺织/皮革","制药/生物工程","建筑与工程","专业服务(咨询/人力资源)","其他行业"]
         */

        private int status;
        private String msg;
        private ErrorBean error;
        private List<String> lists;

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

        public List<String> getLists() {
            return lists;
        }

        public void setLists(List<String> lists) {
            this.lists = lists;
        }

        public static class ErrorBean {
        }
    }

    public static class DebugBean {
        /**
         * stack : ["[#0 - 0ms - PHALAPI_INIT]D:\\program\\wamp64\\www\\www\\openly\\api\\index.php(6)","[#1 - 2ms - PHALAPI_RESPONSE]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(46)","[#2 - 13ms - PHALAPI_FINISH]D:\\program\\wamp64\\www\\www\\openly\\api\\phalapi\\vendor\\phalapi\\kernal\\src\\PhalApi.php(74)"]
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
