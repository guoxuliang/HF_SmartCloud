package com.hf.hf_smartcloud.log;

/**
 *
 */
public interface LogProxy {
    /* package */static final String DEFAULT_LOG_NAME = "TBWY_LOG";

    void verbose(String message, Throwable tr);

    void verbose(String message);

    void debug(String message, Throwable tr);

    void debug(String message);

    void info(String message, Throwable tr);

    void info(String message);

    void warn(String message, Throwable tr);

    void warn(String message);

    void error(String message, Throwable tr);

    void error(String message);

    /**
     * ��ȡLog����ơ�
     * 
     * @return Log���
     */
    String getLogName();

    /**
     * ����Log����ƣ���writeLog�����н��Զ�д��Logger��ơ�
     * 
     * @param
     *            �µ�Log��ƣ�������ƺ������õ������ͬ���߸����Ϊ���ַ��null�򱣳������õ���Ʋ��䡣
     */
    void setLogName(String newLogName);
}
