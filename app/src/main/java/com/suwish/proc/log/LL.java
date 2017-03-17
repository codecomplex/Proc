package com.suwish.proc.log;

import android.util.Log;


/**
 *
 * LL <p/>
 *
 *
 * @author min.su on 2016/12/27.
 */
public final class LL {

    private static boolean LOGGING_ENABLED = true;// BuildConfig.DEBUG;

//    static final String TAG_PREFIX = "proc"; 发布版
    private static final String TAG_PREFIX = "";//调试版
    private static final int LOG_PREFIX_LENGTH = TAG_PREFIX.length();

    private static final int MAX_LOG_TAG_LENGTH = 23;

    private LL(){}

    public static String makeLogTag(String tag){
        if (tag.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return TAG_PREFIX + tag.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }
        return TAG_PREFIX + tag;
    }

    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void d(final String tag, String message) {
        if (LOGGING_ENABLED){
            Log.d(tag, message);
        }
    }

    public static void d(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED){
            Log.d(tag, message, cause);
        }
    }

    public static void v(final String tag, String message) {
        if (LOGGING_ENABLED) {
            if (Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message);
            }
        }
    }

    public static void v(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.v(tag, message, cause);
        }
    }

    public static void i(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.i(tag, message);
        }
    }

    public static void i(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.i(tag, message, cause);
        }
    }

    public static void w(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.w(tag, message);
        }
    }

    public static void w(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.w(tag, message, cause);
        }
    }

    public static void e(final String tag, String message) {
        if (LOGGING_ENABLED){
            Log.e(tag, message);
        }
    }

    public static void e(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.e(tag, message, cause);
        }
    }
}
