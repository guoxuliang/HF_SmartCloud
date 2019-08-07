package com.hf.hf_smartcloud.log;


/**
 * 日志接口，定义了记录日志的方法，和记录日志所需的一些公共信息。
 */
public interface Logger {
    void setMinLogLevel(LogLevel level);

    /**
     * 写入日志信息，并附加Logger名称。
     * 
     * @param logName
     * @param message
     *            日志消息
     * @param level
     *            日志级别
     */
    void writeLog(String logName, String message, LogLevel level);

    /**
     * 写入日志信息，并附加Logger名称，同时将异常信息写入到日志中。
     * 
     * @param logName
     * @param message
     *            日志消息
     * @param level
     *            日志级别
     * @param e
     *            日志附加的异常
     */
    void writeLog(String logName, String message, LogLevel level, Throwable e);
}
