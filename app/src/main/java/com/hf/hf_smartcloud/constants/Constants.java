package com.hf.hf_smartcloud.constants;

/**
 * @类名称: Constants
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @作者
 * @日期
 * 
 */
public class Constants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxd374bbe7c38365e8";
    public static final String APP_SECRECT = "c710fe39b264afacc97efd3809610b24";
//    public static final String PARTNER_ID = "1519511821";
//    public static final String PACKAGE_VALUE = "Sign=WXPay";
//    public static final String KEY = "changliandaxinshenghuo1234567899";
    /** 服务器地址 */
//    public static String SERVER_BASE_URL ="http://192.168.1.254/www/openly/api/?";//党鹏松本地器测试
    public static String SERVER_BASE_URL ="http://124.114.179.82/www/openly/api/?";//服务器域名
//    public static String SERVER_BASE_URL ="https://www.huafanyun.com/api/?";//公网正式器测试
//    public static String SERVER_BASE_URL ="http://open.huafanyun.com/api/?";//测试


    /**六合一 07寄存器  零点校正   W/R*/
    //读
    public static final String TEMPORARYSTORAGE0701R = ":010307F5";
    public static final String TEMPORARYSTORAGE0702R = ":020307F4";
    public static final String TEMPORARYSTORAGE0703R = ":030307F3";
    public static final String TEMPORARYSTORAGE0704R = ":040307F2";
    public static final String TEMPORARYSTORAGE0705R = ":050307F1";
    public static final String TEMPORARYSTORAGE0706R = ":060307F0";
    //写 eg：01060707D01B(W)   07D0的值为2000    2000为用户设置的值        #01870078    判断87的最高位是否为1，1：成功   0：失败
    public static final String TEMPORARYSTORAGE0701W = "010607";
    public static final String TEMPORARYSTORAGE0702W = "020607";
    public static final String TEMPORARYSTORAGE0703W = "030607";
    public static final String TEMPORARYSTORAGE0704W = "040607";
    public static final String TEMPORARYSTORAGE0705W = "050607";
    public static final String TEMPORARYSTORAGE0706W = "060607";
    /**六合一 08寄存器  满量程校正  W/R*/
    //读   同07寄存器
    public static final String TEMPORARYSTORAGE0801R = ":010308F4";
    public static final String TEMPORARYSTORAGE0802R = ":020308F3";
    public static final String TEMPORARYSTORAGE0803R = ":030308F2";
    public static final String TEMPORARYSTORAGE0804R = ":040308F1";
    public static final String TEMPORARYSTORAGE0805R = ":050308F0";
    public static final String TEMPORARYSTORAGE0806R = ":060308EF";
    //写
    public static final String TEMPORARYSTORAGE0801W = "010608";
    public static final String TEMPORARYSTORAGE0802W = "020608";
    public static final String TEMPORARYSTORAGE0803W = "030608";
    public static final String TEMPORARYSTORAGE0804W = "040608";
    public static final String TEMPORARYSTORAGE0805W = "050608";
    public static final String TEMPORARYSTORAGE0806W = "060608";

    /**六合一 09寄存器  所有设置参数*/
    public static final String TEMPORARYSTORAGE0901 = ":010309F3";
    public static final String TEMPORARYSTORAGE0902 = ":020309F2";
    public static final String TEMPORARYSTORAGE0903 = ":030309F1";
    public static final String TEMPORARYSTORAGE0904 = ":040309F0";
    public static final String TEMPORARYSTORAGE0905 = ":050309EF";
    public static final String TEMPORARYSTORAGE0906 = ":060309EE";
    /**六合一 0F寄存器  当前气体浓度值*/
    //浓度，温湿度，时间值不用转   量程，AD值需要转10进制
    public static final String TEMPORARYSTORAGE0F01R = ":01030FED";
    public static final String TEMPORARYSTORAGE0F02R = ":02030FEC";
    public static final String TEMPORARYSTORAGE0F03R = ":03030FEB";
    public static final String TEMPORARYSTORAGE0F04R = ":04030FEA";
    public static final String TEMPORARYSTORAGE0F05R = ":05030FE9";
    public static final String TEMPORARYSTORAGE0F06R = ":06030FE8";

    /**六合一 14寄存器  开启显示   W/R*/
    //读
    public static final String TEMPORARYSTORAGE1401R = ":010314E8";
    public static final String TEMPORARYSTORAGE1402R = ":020314E7";
    public static final String TEMPORARYSTORAGE1403R = ":030314E6";
    public static final String TEMPORARYSTORAGE1404R = ":040314E5";
    public static final String TEMPORARYSTORAGE1405R = ":050314E4";
    public static final String TEMPORARYSTORAGE1406R = ":060314E3";
    //写  开启(显示)（1：显示  0：不显示）
    public static final String TEMPORARYSTORAGE1401WOPEN = "01061401E4";
    public static final String TEMPORARYSTORAGE1402WOPEN = "02061401E3";
    public static final String TEMPORARYSTORAGE1403WOPEN = "03061401E2";
    public static final String TEMPORARYSTORAGE1404WOPEN = "04061401E1";
    public static final String TEMPORARYSTORAGE1405WOPEN = "05061401E0";
    public static final String TEMPORARYSTORAGE1406WOPEN = "06061401DF";
    //写  关闭(不显示)（1：显示  0：不显示）
    public static final String TEMPORARYSTORAGE1401WCLOSE = "01061400E5";
    public static final String TEMPORARYSTORAGE1402WCLOSE = "02061400E4";
    public static final String TEMPORARYSTORAGE1403WCLOSE = "03061400E3";
    public static final String TEMPORARYSTORAGE1404WCLOSE = "04061400E2";
    public static final String TEMPORARYSTORAGE1405WCLOSE = "05061400E1";
    public static final String TEMPORARYSTORAGE1406WCLOSE = "06061400E0";

    /**六合一 15寄存器  程序版本*/
    public static final String TEMPORARYSTORAGE1501R = ":010315E7";
    public static final String TEMPORARYSTORAGE1502R = ":020315E6";
    public static final String TEMPORARYSTORAGE1503R = ":030315E5";
    public static final String TEMPORARYSTORAGE1504R = ":040315E4";
    public static final String TEMPORARYSTORAGE1505R = ":050315E3";
    public static final String TEMPORARYSTORAGE1506R = ":060315E2";
    /**六合一 16寄存器  版本*/
    public static final String TEMPORARYSTORAGE1601R = ":010316E6";
    public static final String TEMPORARYSTORAGE1602R = ":020316E5";
    public static final String TEMPORARYSTORAGE1603R = ":030316E4";
    public static final String TEMPORARYSTORAGE1604R = ":040316E3";
    public static final String TEMPORARYSTORAGE1605R = ":050316E2";
    public static final String TEMPORARYSTORAGE1606R = ":060316E1";
    /**六合一 17寄存器  温湿度*/
    public static final String TEMPORARYSTORAGE1701R = ":010317E5";
//    public static final String TEMPORARYSTORAGE1702R = ":020317E4";
//    public static final String TEMPORARYSTORAGE1703R = ":030317E3";
//    public static final String TEMPORARYSTORAGE1704R = ":040317E2";
//    public static final String TEMPORARYSTORAGE1705R = ":050317E1";
//    public static final String TEMPORARYSTORAGE1706R = ":060317E0";

    /**六合一 20寄存器  报警记录条数*/
    public static final String TEMPORARYSTORAGE2001R = ":010320DC";
    public static final String TEMPORARYSTORAGE2002R = ":020320DB";
    public static final String TEMPORARYSTORAGE2003R = ":030320DA";
    public static final String TEMPORARYSTORAGE2004R = ":040320D9";
    public static final String TEMPORARYSTORAGE2005R = ":050320D8";
    public static final String TEMPORARYSTORAGE2006R = ":060320D7";

    /**六合一 21寄存器  读取报警记录   W/R*/
    //读
    public static final String TEMPORARYSTORAGE2101R = ":010321DB";
    public static final String TEMPORARYSTORAGE2102R = ":020321DA";
    public static final String TEMPORARYSTORAGE2103R = ":030321D9";
    public static final String TEMPORARYSTORAGE2104R = ":040321D8";
    public static final String TEMPORARYSTORAGE2105R = ":050321D7";
    public static final String TEMPORARYSTORAGE2106R = ":060321D6";
    //写
    public static final String TEMPORARYSTORAGE2101W = "";
    public static final String TEMPORARYSTORAGE2102W = "";
    public static final String TEMPORARYSTORAGE2103W = "";
    public static final String TEMPORARYSTORAGE2104W = "";
    public static final String TEMPORARYSTORAGE2105W = "";
    public static final String TEMPORARYSTORAGE2106W = "";

    /**六合一 22寄存器  清除报警记录*/
    public static final String TEMPORARYSTORAGE2201W = ":01062200D7";
    public static final String TEMPORARYSTORAGE2202W = ":02062200D6";
    public static final String TEMPORARYSTORAGE2203W = ":03062200D5";
    public static final String TEMPORARYSTORAGE2204W = ":04062200D4";
    public static final String TEMPORARYSTORAGE2205W = ":05062200D3";
    public static final String TEMPORARYSTORAGE2206W = ":06062200D2";

    /**六合一 30寄存器  设置时间*/
    public static final String TEMPORARYSTORAGE30 = "";

    /**六合一 01寄存器  一级报警*/
    public static final String TEMPORARYSTORAGE01 = "010601";

    /**六合一 02寄存器  二级报警*/
    public static final String TEMPORARYSTORAGE02 = "010602";

}


