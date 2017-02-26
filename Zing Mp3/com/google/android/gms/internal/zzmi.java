package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.client.zzab.zza;
import com.google.android.gms.ads.internal.client.zzac;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzf;
import java.util.HashMap;
import java.util.Map;

@zzji
public class zzmi extends zza {
    private zzac f1548A;
    private boolean f1549B;
    private boolean f1550C;
    private float f1551D;
    private float f1552E;
    private final float f1553w;
    private int f1554z;
    private final Object zzako;
    private boolean zzakr;
    private final zzmd zzbnz;

    /* renamed from: com.google.android.gms.internal.zzmi.1 */
    class C14651 implements Runnable {
        final /* synthetic */ Map f1543F;
        final /* synthetic */ zzmi f1544G;

        C14651(zzmi com_google_android_gms_internal_zzmi, Map map) {
            this.f1544G = com_google_android_gms_internal_zzmi;
            this.f1543F = map;
        }

        public void run() {
            this.f1544G.zzbnz.zza("pubVideoCmd", this.f1543F);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmi.2 */
    class C14662 implements Runnable {
        final /* synthetic */ zzmi f1545G;
        final /* synthetic */ int f1546H;
        final /* synthetic */ int f1547I;

        C14662(zzmi com_google_android_gms_internal_zzmi, int i, int i2) {
            this.f1545G = com_google_android_gms_internal_zzmi;
            this.f1546H = i;
            this.f1547I = i2;
        }

        public void run() {
            boolean z = false;
            synchronized (this.f1545G.zzako) {
                boolean z2 = this.f1546H != this.f1547I;
                boolean z3 = !this.f1545G.f1549B && this.f1547I == 1;
                boolean z4 = z2 && this.f1547I == 1;
                boolean z5 = z2 && this.f1547I == 2;
                z2 = z2 && this.f1547I == 3;
                zzmi com_google_android_gms_internal_zzmi = this.f1545G;
                if (this.f1545G.f1549B || z3) {
                    z = true;
                }
                com_google_android_gms_internal_zzmi.f1549B = z;
                if (this.f1545G.f1548A == null) {
                    return;
                }
                if (z3) {
                    try {
                        this.f1545G.f1548A.zzkw();
                    } catch (Throwable e) {
                        zzb.zzc("Unable to call onVideoStart()", e);
                    }
                }
                if (z4) {
                    try {
                        this.f1545G.f1548A.zzkx();
                    } catch (Throwable e2) {
                        zzb.zzc("Unable to call onVideoPlay()", e2);
                    }
                }
                if (z5) {
                    try {
                        this.f1545G.f1548A.zzky();
                    } catch (Throwable e22) {
                        zzb.zzc("Unable to call onVideoPause()", e22);
                    }
                }
                if (z2) {
                    try {
                        this.f1545G.f1548A.onVideoEnd();
                    } catch (Throwable e222) {
                        zzb.zzc("Unable to call onVideoEnd()", e222);
                    }
                }
            }
        }
    }

    public zzmi(zzmd com_google_android_gms_internal_zzmd, float f) {
        this.zzako = new Object();
        this.zzakr = true;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.f1553w = f;
    }

    private void zzdo(String str) {
        zze(str, null);
    }

    private void zze(String str, @Nullable Map<String, String> map) {
        Map hashMap = map == null ? new HashMap() : new HashMap(map);
        hashMap.put(NativeProtocol.WEB_DIALOG_ACTION, str);
        zzu.zzgm().runOnUiThread(new C14651(this, hashMap));
    }

    private void zzk(int i, int i2) {
        zzu.zzgm().runOnUiThread(new C14662(this, i, i2));
    }

    public float getAspectRatio() {
        float f;
        synchronized (this.zzako) {
            f = this.f1552E;
        }
        return f;
    }

    public int getPlaybackState() {
        int i;
        synchronized (this.zzako) {
            i = this.f1554z;
        }
        return i;
    }

    public boolean isMuted() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1550C;
        }
        return z;
    }

    public void pause() {
        zzdo("pause");
    }

    public void play() {
        zzdo("play");
    }

    public void zza(float f, int i, boolean z, float f2) {
        int i2;
        synchronized (this.zzako) {
            this.f1551D = f;
            this.f1550C = z;
            i2 = this.f1554z;
            this.f1554z = i;
            this.f1552E = f2;
        }
        zzk(i2, i);
    }

    public void zza(zzac com_google_android_gms_ads_internal_client_zzac) {
        synchronized (this.zzako) {
            this.f1548A = com_google_android_gms_ads_internal_client_zzac;
        }
    }

    public void zzaq(boolean z) {
        synchronized (this.zzako) {
            this.zzakr = z;
        }
        zze("initialState", zzf.zze("muteStart", z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    public float zzku() {
        return this.f1553w;
    }

    public float zzkv() {
        float f;
        synchronized (this.zzako) {
            f = this.f1551D;
        }
        return f;
    }

    public void zzn(boolean z) {
        zzdo(z ? "mute" : "unmute");
    }
}
