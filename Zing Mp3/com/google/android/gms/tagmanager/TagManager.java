package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.PendingResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager aHJ;
    private final DataLayer aDZ;
    private final zzt aGC;
    private final zza aHG;
    private final zzdb aHH;
    private final ConcurrentMap<String, zzo> aHI;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.TagManager.1 */
    class C15251 implements zzb {
        final /* synthetic */ TagManager aHK;

        C15251(TagManager tagManager) {
            this.aHK = tagManager;
        }

        public void zzaz(Map<String, Object> map) {
            Object obj = map.get(DataLayer.EVENT_KEY);
            if (obj != null) {
                this.aHK.zzpt(obj.toString());
            }
        }
    }

    public interface zza {
        zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzt com_google_android_gms_tagmanager_zzt);
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.2 */
    class C15262 implements zza {
        C15262() {
        }

        public zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzt com_google_android_gms_tagmanager_zzt) {
            return new zzp(context, tagManager, looper, str, i, com_google_android_gms_tagmanager_zzt);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.3 */
    class C15273 implements ComponentCallbacks2 {
        final /* synthetic */ TagManager aHK;

        C15273(TagManager tagManager) {
            this.aHK = tagManager;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
        }

        public void onTrimMemory(int i) {
            if (i == 20) {
                this.aHK.dispatch();
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.TagManager.4 */
    static /* synthetic */ class C15284 {
        static final /* synthetic */ int[] aHL;

        static {
            aHL = new int[zza.values().length];
            try {
                aHL[zza.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                aHL[zza.CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                aHL[zza.CONTAINER_DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    TagManager(Context context, zza com_google_android_gms_tagmanager_TagManager_zza, DataLayer dataLayer, zzdb com_google_android_gms_tagmanager_zzdb) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.aHH = com_google_android_gms_tagmanager_zzdb;
        this.aHG = com_google_android_gms_tagmanager_TagManager_zza;
        this.aHI = new ConcurrentHashMap();
        this.aDZ = dataLayer;
        this.aDZ.zza(new C15251(this));
        this.aDZ.zza(new zzd(this.mContext));
        this.aGC = new zzt();
        zzcgz();
        zzcha();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (aHJ == null) {
                if (context == null) {
                    zzbo.m1698e("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                aHJ = new TagManager(context, new C15262(), new DataLayer(new zzx(context)), zzdc.zzcgt());
            }
            tagManager = aHJ;
        }
        return tagManager;
    }

    @TargetApi(14)
    private void zzcgz() {
        if (VERSION.SDK_INT >= 14) {
            this.mContext.registerComponentCallbacks(new C15273(this));
        }
    }

    private void zzcha() {
        zza.zzdw(this.mContext);
    }

    private void zzpt(String str) {
        for (zzo zzov : this.aHI.values()) {
            zzov.zzov(str);
        }
    }

    public void dispatch() {
        this.aHH.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.aDZ;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i) {
        PendingResult zza = this.aHG.zza(this.mContext, this, null, str, i, this.aGC);
        zza.zzced();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.aHG.zza(this.mContext, this, handler.getLooper(), str, i, this.aGC);
        zza.zzced();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i) {
        PendingResult zza = this.aHG.zza(this.mContext, this, null, str, i, this.aGC);
        zza.zzcef();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.aHG.zza(this.mContext, this, handler.getLooper(), str, i, this.aGC);
        zza.zzcef();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i) {
        PendingResult zza = this.aHG.zza(this.mContext, this, null, str, i, this.aGC);
        zza.zzcee();
        return zza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i, Handler handler) {
        PendingResult zza = this.aHG.zza(this.mContext, this, handler.getLooper(), str, i, this.aGC);
        zza.zzcee();
        return zza;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzbo.setLogLevel(z ? 2 : 5);
    }

    public int zza(zzo com_google_android_gms_tagmanager_zzo) {
        this.aHI.put(com_google_android_gms_tagmanager_zzo.getContainerId(), com_google_android_gms_tagmanager_zzo);
        return this.aHI.size();
    }

    public boolean zzb(zzo com_google_android_gms_tagmanager_zzo) {
        return this.aHI.remove(com_google_android_gms_tagmanager_zzo.getContainerId()) != null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    synchronized boolean zzv(android.net.Uri r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r2 = com.google.android.gms.tagmanager.zzcj.zzcfz();	 Catch:{ all -> 0x0033 }
        r0 = r2.zzv(r6);	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0073;
    L_0x000b:
        r3 = r2.getContainerId();	 Catch:{ all -> 0x0033 }
        r0 = com.google.android.gms.tagmanager.TagManager.C15284.aHL;	 Catch:{ all -> 0x0033 }
        r1 = r2.zzcga();	 Catch:{ all -> 0x0033 }
        r1 = r1.ordinal();	 Catch:{ all -> 0x0033 }
        r0 = r0[r1];	 Catch:{ all -> 0x0033 }
        switch(r0) {
            case 1: goto L_0x0021;
            case 2: goto L_0x0036;
            case 3: goto L_0x0036;
            default: goto L_0x001e;
        };
    L_0x001e:
        r0 = 1;
    L_0x001f:
        monitor-exit(r5);
        return r0;
    L_0x0021:
        r0 = r5.aHI;	 Catch:{ all -> 0x0033 }
        r0 = r0.get(r3);	 Catch:{ all -> 0x0033 }
        r0 = (com.google.android.gms.tagmanager.zzo) r0;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x001e;
    L_0x002b:
        r1 = 0;
        r0.zzox(r1);	 Catch:{ all -> 0x0033 }
        r0.refresh();	 Catch:{ all -> 0x0033 }
        goto L_0x001e;
    L_0x0033:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
    L_0x0036:
        r0 = r5.aHI;	 Catch:{ all -> 0x0033 }
        r0 = r0.keySet();	 Catch:{ all -> 0x0033 }
        r4 = r0.iterator();	 Catch:{ all -> 0x0033 }
    L_0x0040:
        r0 = r4.hasNext();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x001e;
    L_0x0046:
        r0 = r4.next();	 Catch:{ all -> 0x0033 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x0033 }
        r1 = r5.aHI;	 Catch:{ all -> 0x0033 }
        r1 = r1.get(r0);	 Catch:{ all -> 0x0033 }
        r1 = (com.google.android.gms.tagmanager.zzo) r1;	 Catch:{ all -> 0x0033 }
        r0 = r0.equals(r3);	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0065;
    L_0x005a:
        r0 = r2.zzcgb();	 Catch:{ all -> 0x0033 }
        r1.zzox(r0);	 Catch:{ all -> 0x0033 }
        r1.refresh();	 Catch:{ all -> 0x0033 }
        goto L_0x0040;
    L_0x0065:
        r0 = r1.zzcea();	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x0040;
    L_0x006b:
        r0 = 0;
        r1.zzox(r0);	 Catch:{ all -> 0x0033 }
        r1.refresh();	 Catch:{ all -> 0x0033 }
        goto L_0x0040;
    L_0x0073:
        r0 = 0;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.TagManager.zzv(android.net.Uri):boolean");
    }
}
