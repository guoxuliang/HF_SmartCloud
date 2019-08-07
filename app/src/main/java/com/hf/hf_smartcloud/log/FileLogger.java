package com.hf.hf_smartcloud.log;

import android.os.Environment;
import android.util.Log;


import com.hf.hf_smartcloud.utils.DateTimeHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 *
 */
class FileLogger implements Logger {
    private LogLevel mMinLevel = LogLevel.VERBOSE;
    private String mLogFileBaseDir;

    public FileLogger(String aLogFileBaseDir) {
        setLogFileBaseDir(aLogFileBaseDir);
    }

    /**
     * @return the mLogFileBaseDir
     */
    public String getLogFileBaseDir() {
        return mLogFileBaseDir;
    }

    private FileWriter createLogWriter() throws IOException {
        File theLogFile = new File(this.mLogFileBaseDir + File.separator
                + DateTimeHelper.formatAsUtc(new Date(), "yyyy-MM-dd") + ".log");
        if (!theLogFile.exists()) {
            synchronized (this) {
                if (!theLogFile.exists()) {
                    theLogFile.createNewFile();
                }
            }
        }
        FileWriter writer = new FileWriter(theLogFile);

        return writer;
    }

    /**
     * @param mLogFileBaseDir
     *            the mLogFileBaseDir to set
     */
    public void setLogFileBaseDir(String mLogFileBaseDir) {
        this.mLogFileBaseDir = mLogFileBaseDir;
    }

    @Override
    public void writeLog(String logName, String message, LogLevel level) {
        writeLog(logName, message, level, null);
    }

    @Override
    public void writeLog(String logName, String message, LogLevel level,
                         Throwable e) {
        if (level.ordinal() < mMinLevel.ordinal()) {
            return;
        }
        if (!checkExternalStorageWriteable()) {
            Log.i("FileLogger", "External storage is not writeable.");
            return;
        }
        StringBuilder logContent = new StringBuilder();

        logContent.append(DateTimeHelper.formatAsUtc(new Date(),
                "yyyy-MM-dd HH:mm:ss z"));

        logContent.append(" ");
        logContent.append(level);
        logContent.append(" [");
        logContent.append(logName);
        logContent.append("] ");
        logContent.append(message);

        synchronized (this) {
            FileWriter theFileWriter = null;
            try {
                theFileWriter = createLogWriter();
                PrintWriter thePrintWriter = new PrintWriter(theFileWriter);
                thePrintWriter.println(logContent.toString());
                if (null != e) {
                    thePrintWriter.print(" Exception: ");
                    e.printStackTrace(thePrintWriter);
                }
            } catch (IOException ioe) {
                Log.w(logName, logContent.toString(), ioe);
            } finally {
                if (null != theFileWriter) {
                    try {
                        theFileWriter.close();
                    } catch (IOException ioe) {
                        Log.w(logName, "IOException when close log file.", ioe);
                    }
                }
            }
        }
    }

    private boolean checkExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void setMinLogLevel(LogLevel level) {
        mMinLevel = level;
    }

}
