package com.facebook.ads.internal.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.Type;
import com.facebook.ads.InterstitialAdActivity;
import com.facebook.ads.internal.C0523e;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.p000i.p014a.C0619d;
import com.google.android.exoplayer.util.MpegAudioHeader;
import com.mp3download.zingmp3.BuildConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.util.i */
public class C0785i {
    private static final Uri f1427a;
    private static final String f1428b;
    private static final Map<AdSize, C0523e> f1429c;

    /* renamed from: com.facebook.ads.internal.util.i.a */
    public static class C0784a {
        public String f1424a;
        public String f1425b;
        public boolean f1426c;

        public C0784a(String str, String str2, boolean z) {
            this.f1424a = str;
            this.f1425b = str2;
            this.f1426c = z;
        }
    }

    static {
        f1427a = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
        f1428b = C0785i.class.getSimpleName();
        f1429c = new HashMap();
        f1429c.put(AdSize.INTERSTITIAL, C0523e.WEBVIEW_INTERSTITIAL_UNKNOWN);
        f1429c.put(AdSize.RECTANGLE_HEIGHT_250, C0523e.WEBVIEW_BANNER_250);
        f1429c.put(AdSize.BANNER_HEIGHT_90, C0523e.WEBVIEW_BANNER_90);
        f1429c.put(AdSize.BANNER_HEIGHT_50, C0523e.WEBVIEW_BANNER_50);
    }

    public static final <P, PR, R> AsyncTask<P, PR, R> m1613a(AsyncTask<P, PR, R> asyncTask, P... pArr) {
        if (VERSION.SDK_INT >= 11) {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pArr);
        } else {
            asyncTask.execute(pArr);
        }
        return asyncTask;
    }

    public static C0523e m1614a(AdSize adSize) {
        C0523e c0523e = (C0523e) f1429c.get(adSize);
        return c0523e == null ? C0523e.WEBVIEW_BANNER_LEGACY : c0523e;
    }

    public static C0784a m1615a(ContentResolver contentResolver) {
        C0784a c0784a;
        Throwable th;
        Cursor query;
        try {
            ContentResolver contentResolver2 = contentResolver;
            query = contentResolver2.query(f1427a, new String[]{"aid", "androidid", "limit_tracking"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        c0784a = new C0784a(query.getString(query.getColumnIndex("aid")), query.getString(query.getColumnIndex("androidid")), Boolean.valueOf(query.getString(query.getColumnIndex("limit_tracking"))).booleanValue());
                        if (query != null) {
                            query.close();
                        }
                        return c0784a;
                    }
                } catch (Exception e) {
                    try {
                        c0784a = new C0784a(null, null, false);
                        if (query != null) {
                            query.close();
                        }
                        return c0784a;
                    } catch (Throwable th2) {
                        th = th2;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
            }
            c0784a = new C0784a(null, null, false);
            if (query != null) {
                query.close();
            }
        } catch (Exception e2) {
            query = null;
            c0784a = new C0784a(null, null, false);
            if (query != null) {
                query.close();
            }
            return c0784a;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return c0784a;
    }

    public static Object m1616a(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String m1617a(double d) {
        return String.format(Locale.US, "%.3f", new Object[]{Double.valueOf(d)});
    }

    public static String m1618a(long j) {
        return C0785i.m1617a(((double) j) / 1000.0d);
    }

    public static String m1619a(InputStream inputStream) {
        StringWriter stringWriter = new StringWriter();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] cArr = new char[MpegAudioHeader.MAX_FRAME_SIZE_BYTES];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read != -1) {
                stringWriter.write(cArr, 0, read);
            } else {
                String stringWriter2 = stringWriter.toString();
                stringWriter.close();
                inputStreamReader.close();
                return stringWriter2;
            }
        }
    }

    public static String m1620a(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                try {
                    jSONObject.put((String) entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONObject.toString();
    }

    public static String m1621a(JSONObject jSONObject, String str) {
        return C0785i.m1622a(jSONObject, str, null);
    }

    public static String m1622a(JSONObject jSONObject, String str, String str2) {
        String optString = jSONObject.optString(str, str2);
        return "null".equals(optString) ? null : optString;
    }

    public static String m1623a(byte[] bArr) {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            InputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            String a = C0785i.m1619a(gZIPInputStream);
            gZIPInputStream.close();
            byteArrayInputStream.close();
            return a;
        } catch (Throwable e) {
            C0778d.m1599a(C0777c.m1596a(e, "Error decompressing data"));
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }

    public static Method m1624a(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method m1625a(String str, String str2, Class<?>... clsArr) {
        try {
            return C0785i.m1624a(Class.forName(str), str2, (Class[]) clsArr);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static void m1626a(Context context, Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.addFlags(268435456);
        intent.putExtra("com.android.browser.application_id", context.getPackageName());
        intent.putExtra("create_new_tab", false);
        context.startActivity(intent);
    }

    public static void m1627a(Context context, Uri uri, String str) {
        if (C0619d.m1154a(uri.getScheme()) && C0604h.m1121b(context)) {
            C0785i.m1633b(context, uri, str);
        } else {
            C0785i.m1626a(context, uri);
        }
    }

    public static void m1628a(Context context, String str) {
        if (AdSettings.isTestMode(context)) {
            Log.d("FBAudienceNetworkLog", str + " (displayed for test ads only)");
        }
    }

    public static void m1629a(DisplayMetrics displayMetrics, View view, AdSize adSize) {
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(((int) (((float) displayMetrics.widthPixels) / displayMetrics.density)) >= adSize.getWidth() ? displayMetrics.widthPixels : (int) Math.ceil((double) (((float) adSize.getWidth()) * displayMetrics.density)), (int) Math.ceil((double) (((float) adSize.getHeight()) * displayMetrics.density)));
        layoutParams.addRule(14, -1);
        view.setLayoutParams(layoutParams);
    }

    public static boolean m1630a(Context context) {
        try {
            RunningTaskInfo runningTaskInfo = (RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(2).get(0);
            String str = runningTaskInfo.topActivity.getPackageName() + "." + "UnityPlayerActivity";
            boolean z = runningTaskInfo.topActivity.getClassName() == str || C0785i.m1634b(str);
            Boolean valueOf = Boolean.valueOf(z);
            Log.d("IS_UNITY", Boolean.toString(valueOf.booleanValue()));
            return valueOf.booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean m1631a(String str, String str2) {
        try {
            Class.forName(str + "." + str2);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static byte[] m1632a(String str) {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.length());
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.close();
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return toByteArray;
        } catch (Throwable e) {
            C0778d.m1599a(C0777c.m1596a(e, "Error compressing data"));
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static void m1633b(Context context, Uri uri, String str) {
        Intent intent = new Intent(context, AudienceNetworkActivity.class);
        intent.addFlags(268435456);
        intent.putExtra(AudienceNetworkActivity.VIEW_TYPE, Type.BROWSER);
        intent.putExtra(AudienceNetworkActivity.BROWSER_URL, uri.toString());
        intent.putExtra(AudienceNetworkActivity.CLIENT_TOKEN, str);
        intent.putExtra(AudienceNetworkActivity.HANDLER_TIME, System.currentTimeMillis());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent.setClass(context, InterstitialAdActivity.class);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                C0785i.m1626a(context, uri);
            }
        }
    }

    public static boolean m1634b(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
