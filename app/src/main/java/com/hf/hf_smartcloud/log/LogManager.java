package com.hf.hf_smartcloud.log;

import java.util.ArrayList;

/**
 *操作日志的入口类
 */
public final class LogManager {
    public final static LogManager instance = new LogManager();

    private ArrayList<Logger> mLoggerList = new ArrayList<Logger>();
//    private LogLevel mMinLevel = LogLevel.WARN;
    private LogLevel mMinLevel = LogLevel.DEBUG;
//    private LogLevel mMinLevel = LogLevel.ERROR;
//    private LogLevel mMinLevel = LogLevel.WARN;
    
    private LogManager() {
        mLoggerList.add(new SystemLogger());
    }

    public void setMinLogLevel(LogLevel level) {
        mMinLevel = level;
    }

    public LogLevel getMinLogLevel() {
        return mMinLevel;
    }

    private void addLoggerInternal(Logger aLogger) {
        if (null == aLogger) {
            throw new IllegalArgumentException("aLogger");
        }
        mLoggerList.add(aLogger);
    }

    /* package */void writeLog(String logName, String message, LogLevel level,
                               Throwable tr) {
        if (level.ordinal() < mMinLevel.ordinal()) {
            return;
        }
        for (Logger aLogger : mLoggerList) {
            aLogger.writeLog(logName, message, level, tr);
        }
    }

    public static void addLogger(Logger aLogger) {
        instance.addLoggerInternal(aLogger);
    }

    public static void addFileLogger(String theLoggerName,
                                     String theLogFileBaseDir) {
        FileLogger theLogger = new FileLogger(theLogFileBaseDir);
        addLogger(theLogger);
    }

    public static void addSystemLogger(String theLoggerName) {
        SystemLogger theLogger = new SystemLogger();
        addLogger(theLogger);
    }

    public static LogProxy getLog(String logName) {
        return new DefaultLogProxy(logName);
    }
}
