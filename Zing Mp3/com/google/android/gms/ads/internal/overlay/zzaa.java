package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.internal.zzji;

@zzji
@TargetApi(14)
public class zzaa implements OnAudioFocusChangeListener {
    private final AudioManager mAudioManager;
    private boolean zzccd;
    private final zza zzceq;
    private boolean zzcer;
    private boolean zzces;
    private float zzcet;

    interface zza {
        void zzpk();
    }

    public zzaa(Context context, zza com_google_android_gms_ads_internal_overlay_zzaa_zza) {
        this.zzcet = 1.0f;
        this.mAudioManager = (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.zzceq = com_google_android_gms_ads_internal_overlay_zzaa_zza;
    }

    private void zzri() {
        Object obj = (!this.zzccd || this.zzces || this.zzcet <= 0.0f) ? null : 1;
        if (obj != null && !this.zzcer) {
            zzrj();
            this.zzceq.zzpk();
        } else if (obj == null && this.zzcer) {
            zzrk();
            this.zzceq.zzpk();
        }
    }

    private void zzrj() {
        boolean z = true;
        if (this.mAudioManager != null && !this.zzcer) {
            if (this.mAudioManager.requestAudioFocus(this, 3, 2) != 1) {
                z = false;
            }
            this.zzcer = z;
        }
    }

    private void zzrk() {
        if (this.mAudioManager != null && this.zzcer) {
            this.zzcer = this.mAudioManager.abandonAudioFocus(this) == 0;
        }
    }

    public void onAudioFocusChange(int i) {
        this.zzcer = i > 0;
        this.zzceq.zzpk();
    }

    public void setMuted(boolean z) {
        this.zzces = z;
        zzri();
    }

    public void zzb(float f) {
        this.zzcet = f;
        zzri();
    }

    public void zzre() {
        this.zzccd = true;
        zzri();
    }

    public void zzrf() {
        this.zzccd = false;
        zzri();
    }

    public float zzrh() {
        return this.zzcer ? this.zzces ? 0.0f : this.zzcet : 0.0f;
    }
}
