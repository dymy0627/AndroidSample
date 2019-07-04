package com.example.yulin.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {

    public static boolean DEBUG = true;

    private static String TAG = "Andy";
    private static final String APP_NAME = "Andy-";
    private static final String DIR_NAME = "Andy";
    private static final String FILE_NAME = "demo";
    private boolean mEnableRecord;
    private LogLevel mLogLevel = LogLevel.VERBOSE;
    private SimpleDateFormat mSimpleDateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public enum LogLevel {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6);

        public int type;

        LogLevel(int p) {
            this.type = p;
        }
    }

    private static class SingletonHolder {
        private static final LogUtil sSingleton = new LogUtil();

        private SingletonHolder() {
        }
    }

    public static LogUtil getInstance() {
        return SingletonHolder.sSingleton;
    }

    private LogUtil() {
        this.mEnableRecord = false;
    }

    public void enableRecord(boolean enable) {
        this.mEnableRecord = enable;
    }

    public boolean isEnableRecord() {
        return this.mEnableRecord;
    }

    public void setLogLevel(LogLevel level) {
        this.mLogLevel = level;
    }

    public static void v(String className, String method, String msg) {
        getInstance().writeLog(className, method, msg, LogLevel.VERBOSE);
    }

    public static void d(Class<?> c, String msg) {
        if (DEBUG) {
            d(c.getSimpleName(), msg);
        }
    }

    public static void d(Class<?> c, String method, String msg) {
        if (DEBUG) {
            d(c.getSimpleName(), method, msg);
        }
    }

    public static void d(String className, String msg) {
        getInstance().writeLog(className, null, msg, LogLevel.DEBUG);
    }

    public static void d(String className, String method, String msg) {
        getInstance().writeLog(className, method, msg, LogLevel.DEBUG);
    }

    public static void i(Class<?> c, String msg) {
        if (DEBUG) {
            i(c.getSimpleName(), msg);
        }
    }

    public static void i(Class<?> c, String method, String msg) {
        if (DEBUG) {
            i(c.getSimpleName(), method, msg);
        }
    }

    public static void i(String className, String msg) {
        getInstance().writeLog(className, null, msg, LogLevel.INFO);
    }

    public static void i(String className, String method, String msg) {
        getInstance().writeLog(className, method, msg, LogLevel.INFO);
    }

    public static void w(Class<?> c, String msg) {
        if (DEBUG) {
            w(c.getSimpleName(), msg);
        }
    }

    public static void w(Class<?> c, String method, String msg) {
        if (DEBUG) {
            w(c.getSimpleName(), method, msg);
        }
    }

    public static void w(String className, String msg) {
        getInstance().writeLog(className, null, msg, LogLevel.WARN);
    }

    public static void w(String className, String method, String msg) {
        getInstance().writeLog(className, method, msg, LogLevel.WARN);
    }

    public static void e(Class<?> c, String msg, Throwable tr) {
        if (DEBUG) {
            e(c.getSimpleName(), msg, tr);
        }
    }

    public static void e(Class<?> c, String method, String msg, Throwable tr) {
        if (DEBUG) {
            e(c.getSimpleName(), method, msg, tr);
        }
    }

    public static void e(String className, String msg, Throwable tr) {
        getInstance().writeLog(className, null, msg, LogLevel.ERROR);
    }

    public static void e(String className, String method, String msg, Throwable tr) {
        getInstance().writeLog(className, method, msg, LogLevel.ERROR);
    }

    public boolean writeLog(String className, String methodName, String msg) {
        return write(mEnableRecord, className, methodName, msg, mLogLevel);
    }

    public boolean writeLog(String className, String methodName, byte[] msg, LogLevel logLevel) {
        return write(mEnableRecord, className, methodName, msg, logLevel);
    }

    public boolean writeLog(String className, String methodName, String msg, LogLevel logLevel) {
        return write(mEnableRecord, className, methodName, msg, logLevel);
    }

    public boolean writeLog(String className, String methodName, String msg, LogLevel logLevel, boolean enforceRecord) {
        return write(enforceRecord, className, methodName, msg, logLevel);
    }

    private boolean write(boolean bRecord, String className, String methodName, byte[] msg, LogLevel logLevel) {
        StringBuilder strMsg = new StringBuilder();
        for (int i = 0; i < msg.length - 1; i++) {
            strMsg.append(String.format("%d,", new Object[]{Byte.valueOf(msg[i])}));
        }
        return write(bRecord, className, methodName, strMsg.toString(), logLevel);
    }

    private boolean write(boolean enableRecord, String className, String methodName, String msg, LogLevel logLevel) {
        if (className != null) {
            TAG = APP_NAME + className;
        }
        String mergeMsg = (methodName != null ? "[" + methodName + "]: " : "")
                + msg;
        if (logLevel.ordinal() == LogLevel.VERBOSE.ordinal()) {
            Log.v(TAG, mergeMsg);
        } else if (logLevel.ordinal() == LogLevel.DEBUG.ordinal()) {
            Log.d(TAG, mergeMsg);
        } else if (logLevel.ordinal() == LogLevel.INFO.ordinal()) {
            Log.i(TAG, mergeMsg);
        } else if (logLevel.ordinal() == LogLevel.WARN.ordinal()) {
            Log.w(TAG, mergeMsg);
        } else if (logLevel.ordinal() == LogLevel.ERROR.ordinal()) {
            Log.e(TAG, mergeMsg);
        }
        if (enableRecord) {
            File outDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + this.DIR_NAME);
            if (!outDir.isDirectory()) {
                outDir.mkdir();
            }
            String fileName;
            if (logLevel == LogLevel.ERROR) {
                fileName = FILE_NAME + ".error";
            } else if (logLevel == LogLevel.DEBUG) {
                fileName = FILE_NAME + ".debug";
            } else if (logLevel == LogLevel.INFO) {
                fileName = FILE_NAME + ".info";
            } else {
                fileName = FILE_NAME + ".log";
            }
            try {
                Writer writer = new BufferedWriter(new FileWriter(new File(outDir, fileName), true));
                writer.write("[" + mSimpleDateFormatter.format(new Date()) + "-> " + className + "->" + methodName + "]: " + msg + "\n");
                writer.flush();
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}