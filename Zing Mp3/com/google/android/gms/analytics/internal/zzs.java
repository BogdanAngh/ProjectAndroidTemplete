package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

class zzs implements Logger {
    private boolean aM;
    private int ee;

    zzs() {
        this.ee = 2;
    }

    public void error(Exception exception) {
    }

    public void error(String str) {
    }

    public int getLogLevel() {
        return this.ee;
    }

    public void info(String str) {
    }

    public void setLogLevel(int i) {
        this.ee = i;
        if (!this.aM) {
            String str = (String) zzy.en.get();
            Log.i((String) zzy.en.get(), new StringBuilder(String.valueOf(str).length() + 91).append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.").append(str).append(" DEBUG").toString());
            this.aM = true;
        }
    }

    public void verbose(String str) {
    }

    public void warn(String str) {
    }
}
