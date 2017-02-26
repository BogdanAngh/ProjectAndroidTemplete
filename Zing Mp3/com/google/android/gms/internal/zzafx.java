package com.google.android.gms.internal;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class zzafx {
    public static final Integer aMD;
    public static final Integer aME;
    private final ExecutorService aGI;
    private final Context mContext;

    static {
        aMD = Integer.valueOf(0);
        aME = Integer.valueOf(1);
    }

    public zzafx(Context context) {
        this(context, Executors.newSingleThreadExecutor());
    }

    zzafx(Context context, ExecutorService executorService) {
        this.mContext = context;
        this.aGI = executorService;
    }
}
