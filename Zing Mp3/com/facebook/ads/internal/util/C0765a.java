package com.facebook.ads.internal.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.StatFs;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.facebook.ads.internal.util.a */
public class C0765a {
    private static SensorManager f1327a;
    private static Sensor f1328b;
    private static Sensor f1329c;
    private static volatile float[] f1330d;
    private static volatile float[] f1331e;
    private static Map<String, String> f1332f;
    private static String[] f1333g;

    /* renamed from: com.facebook.ads.internal.util.a.a */
    private static class C0764a implements SensorEventListener {
        private C0764a() {
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor == C0765a.f1328b) {
                C0765a.f1330d = sensorEvent.values;
            } else if (sensorEvent.sensor == C0765a.f1329c) {
                C0765a.f1331e = sensorEvent.values;
            }
            C0765a.m1533a(this);
        }
    }

    static {
        f1327a = null;
        f1328b = null;
        f1329c = null;
        f1332f = new ConcurrentHashMap();
        f1333g = new String[]{"x", "y", "z"};
    }

    public static Map<String, String> m1531a() {
        Map hashMap = new HashMap();
        hashMap.putAll(f1332f);
        C0765a.m1534a(hashMap);
        return hashMap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m1532a(android.content.Context r5) {
        /*
        r1 = com.facebook.ads.internal.util.C0765a.class;
        monitor-enter(r1);
        com.facebook.ads.internal.util.C0765a.m1537b(r5);	 Catch:{ all -> 0x005f }
        com.facebook.ads.internal.util.C0765a.m1540c(r5);	 Catch:{ all -> 0x005f }
        com.facebook.ads.internal.util.C0765a.m1541d(r5);	 Catch:{ all -> 0x005f }
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x0020;
    L_0x0010:
        r0 = "sensor";
        r0 = r5.getSystemService(r0);	 Catch:{ all -> 0x005f }
        r0 = (android.hardware.SensorManager) r0;	 Catch:{ all -> 0x005f }
        f1327a = r0;	 Catch:{ all -> 0x005f }
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x0020;
    L_0x001e:
        monitor-exit(r1);
        return;
    L_0x0020:
        r0 = f1328b;	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x002d;
    L_0x0024:
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        r2 = 1;
        r0 = r0.getDefaultSensor(r2);	 Catch:{ all -> 0x005f }
        f1328b = r0;	 Catch:{ all -> 0x005f }
    L_0x002d:
        r0 = f1329c;	 Catch:{ all -> 0x005f }
        if (r0 != 0) goto L_0x003a;
    L_0x0031:
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        r2 = 4;
        r0 = r0.getDefaultSensor(r2);	 Catch:{ all -> 0x005f }
        f1329c = r0;	 Catch:{ all -> 0x005f }
    L_0x003a:
        r0 = f1328b;	 Catch:{ all -> 0x005f }
        if (r0 == 0) goto L_0x004c;
    L_0x003e:
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        r2 = new com.facebook.ads.internal.util.a$a;	 Catch:{ all -> 0x005f }
        r3 = 0;
        r2.<init>();	 Catch:{ all -> 0x005f }
        r3 = f1328b;	 Catch:{ all -> 0x005f }
        r4 = 3;
        r0.registerListener(r2, r3, r4);	 Catch:{ all -> 0x005f }
    L_0x004c:
        r0 = f1329c;	 Catch:{ all -> 0x005f }
        if (r0 == 0) goto L_0x001e;
    L_0x0050:
        r0 = f1327a;	 Catch:{ all -> 0x005f }
        r2 = new com.facebook.ads.internal.util.a$a;	 Catch:{ all -> 0x005f }
        r3 = 0;
        r2.<init>();	 Catch:{ all -> 0x005f }
        r3 = f1329c;	 Catch:{ all -> 0x005f }
        r4 = 3;
        r0.registerListener(r2, r3, r4);	 Catch:{ all -> 0x005f }
        goto L_0x001e;
    L_0x005f:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.ads.internal.util.a.a(android.content.Context):void");
    }

    public static synchronized void m1533a(C0764a c0764a) {
        synchronized (C0765a.class) {
            if (f1327a != null) {
                f1327a.unregisterListener(c0764a);
            }
        }
    }

    private static void m1534a(Map<String, String> map) {
        int i;
        int i2 = 0;
        float[] fArr = f1330d;
        float[] fArr2 = f1331e;
        if (fArr != null) {
            int min = Math.min(f1333g.length, fArr.length);
            for (i = 0; i < min; i++) {
                map.put("accelerometer_" + f1333g[i], String.valueOf(fArr[i]));
            }
        }
        if (fArr2 != null) {
            i = Math.min(f1333g.length, fArr2.length);
            while (i2 < i) {
                map.put("rotation_" + f1333g[i2], String.valueOf(fArr2[i2]));
                i2++;
            }
        }
    }

    private static void m1537b(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        f1332f.put("available_memory", String.valueOf(memoryInfo.availMem));
    }

    private static void m1540c(Context context) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long availableBlocks = (long) statFs.getAvailableBlocks();
        f1332f.put("free_space", String.valueOf(availableBlocks * ((long) statFs.getBlockSize())));
    }

    private static void m1541d(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra("scale", -1);
            int intExtra3 = registerReceiver.getIntExtra(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, -1);
            Object obj = (intExtra3 == 2 || intExtra3 == 5) ? 1 : null;
            float f = 0.0f;
            if (intExtra2 > 0) {
                f = (((float) intExtra) / ((float) intExtra2)) * 100.0f;
            }
            f1332f.put("battery", String.valueOf(f));
            f1332f.put("charging", obj != null ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        }
    }
}
