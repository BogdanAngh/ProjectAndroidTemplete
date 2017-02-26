package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.internal.zzae;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final UncaughtExceptionHandler aD;
    private final Tracker aE;
    private ExceptionParser aF;
    private GoogleAnalytics aG;
    private final Context mContext;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.aD = uncaughtExceptionHandler;
            this.aE = tracker;
            this.aF = new StandardExceptionParser(context, new ArrayList());
            this.mContext = context.getApplicationContext();
            String str = "ExceptionReporter created, original handler is ";
            String valueOf = String.valueOf(uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName());
            zzae.m1696v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    public ExceptionParser getExceptionParser() {
        return this.aF;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.aF = exceptionParser;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str = "UncaughtException";
        if (this.aF != null) {
            str = this.aF.getDescription(thread != null ? thread.getName() : null, th);
        }
        String str2 = "Reporting uncaught exception: ";
        String valueOf = String.valueOf(str);
        zzae.m1696v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.aE.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        GoogleAnalytics zzza = zzza();
        zzza.dispatchLocalHits();
        zzza.zzzf();
        if (this.aD != null) {
            zzae.m1696v("Passing exception to the original handler");
            this.aD.uncaughtException(thread, th);
        }
    }

    GoogleAnalytics zzza() {
        if (this.aG == null) {
            this.aG = GoogleAnalytics.getInstance(this.mContext);
        }
        return this.aG;
    }

    UncaughtExceptionHandler zzzb() {
        return this.aD;
    }
}
