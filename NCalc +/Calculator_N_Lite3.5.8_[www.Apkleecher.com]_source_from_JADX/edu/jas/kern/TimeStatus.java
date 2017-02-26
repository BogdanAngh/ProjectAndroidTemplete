package edu.jas.kern;

import io.github.kexanie.library.BuildConfig;
import java.util.concurrent.Callable;

public class TimeStatus {
    private static boolean allowTime;
    private static Callable<Boolean> callBack;
    private static long limitTime;
    private static long startTime;

    static {
        allowTime = false;
        limitTime = Long.MAX_VALUE;
        startTime = System.currentTimeMillis();
        callBack = null;
    }

    protected TimeStatus() {
    }

    public static boolean isActive() {
        return allowTime;
    }

    public static void setActive() {
        allowTime = true;
    }

    public static void setNotActive() {
        allowTime = false;
    }

    public static void setLimit(long t) {
        limitTime = t;
    }

    public static void restart() {
        startTime = System.currentTimeMillis();
    }

    public static void setCallBack(Callable<Boolean> cb) {
        callBack = cb;
    }

    public static void checkTime(String msg) {
        if (allowTime && limitTime != Long.MAX_VALUE) {
            long tt = (System.currentTimeMillis() - startTime) - limitTime;
            if (tt > 0) {
                if (callBack != null) {
                    try {
                        if (((Boolean) callBack.call()).booleanValue()) {
                            return;
                        }
                    } catch (Exception e) {
                    }
                }
                if (msg == null) {
                    msg = BuildConfig.FLAVOR;
                }
                throw new TimeExceededException(msg + " over time = " + tt);
            }
        }
    }
}
