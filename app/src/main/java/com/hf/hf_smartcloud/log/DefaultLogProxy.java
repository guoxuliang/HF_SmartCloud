package com.hf.hf_smartcloud.log;


import com.hf.hf_smartcloud.utils.StringHelper;

/**
* @类名称: DefaultLogProxy 
* @类描述: TODO(日志类) 
* @作者 fengxian 
* @日期 2013-9-6 下午1:24:52 
*  
*/
public class DefaultLogProxy implements LogProxy {

    private String mLogName = LogProxy.DEFAULT_LOG_NAME;

    public DefaultLogProxy(String theLogName) {
        if (!StringHelper.isNullOrEmpty(theLogName)) {
            mLogName = theLogName;
        }
    }

    @Override
    public void verbose(String message, Throwable tr) {
        LogManager.instance.writeLog(mLogName, message, LogLevel.VERBOSE, tr);

    }

    @Override
    public void verbose(String message) {
        verbose(message, null);
    }

    @Override
    public void debug(String message, Throwable tr) {
        LogManager.instance.writeLog(mLogName, message, LogLevel.DEBUG, tr);

    }

    @Override
    public void debug(String message) {
        debug(message, null);

    }

    @Override
    public void info(String message, Throwable tr) {
        LogManager.instance.writeLog(mLogName, message, LogLevel.INFO, tr);

    }

    @Override
    public void info(String message) {
        info(message, null);

    }

    @Override
    public void warn(String message, Throwable tr) {
        LogManager.instance.writeLog(mLogName, message, LogLevel.WARN, tr);

    }

    @Override
    public void warn(String message) {
        warn(message, null);

    }

    @Override
    public void error(String message, Throwable tr) {
        LogManager.instance.writeLog(mLogName, message, LogLevel.ERROR, tr);

    }

    @Override
    public void error(String message) {
        error(message, null);

    }

    @Override
    public String getLogName() {
        return mLogName;
    }

    @Override
    public void setLogName(String newLogName) {
        if (StringHelper.isNullOrEmpty(newLogName)
                || newLogName.equals(mLogName)) {
            return;
        }
        mLogName = newLogName;
    }

}
