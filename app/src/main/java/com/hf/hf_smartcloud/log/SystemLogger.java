package com.hf.hf_smartcloud.log;

import android.util.Log;

/**
 *
 */
final class SystemLogger implements Logger {
    private LogLevel mMinLevel = LogLevel.VERBOSE;

    @Override
    public void writeLog(String logName, String message, LogLevel level, Throwable e) {
        if (level.ordinal() < mMinLevel.ordinal()) {
            return;
        }
        switch (level) {
        case DEBUG:
            Log.d(logName, message, e);
            break;
        case INFO:
            Log.i(logName, message, e);
            break;
        case WARN:
            Log.w(logName, message, e);
            break;
        case ERROR:
            Log.e(logName, message, e);
            break;
        case VERBOSE:
        default:
            Log.v(logName, message, e);
            break;
        }
    }

    @Override
    public void writeLog(String logName, String message, LogLevel level) {
        writeLog(logName, message, level, null);
    }

    @Override
    public void setMinLogLevel(LogLevel level) {
        mMinLevel = level;
    }
}
