package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzko.zza;

@zzji
@TargetApi(19)
public class zzix extends zziv {
    private Object zzcgz;
    private PopupWindow zzcha;
    private boolean zzchb;

    zzix(Context context, zza com_google_android_gms_internal_zzko_zza, zzmd com_google_android_gms_internal_zzmd, zziu.zza com_google_android_gms_internal_zziu_zza) {
        super(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza);
        this.zzcgz = new Object();
        this.zzchb = false;
    }

    private void zzsf() {
        synchronized (this.zzcgz) {
            this.zzchb = true;
            if ((this.mContext instanceof Activity) && ((Activity) this.mContext).isDestroyed()) {
                this.zzcha = null;
            }
            if (this.zzcha != null) {
                if (this.zzcha.isShowing()) {
                    this.zzcha.dismiss();
                }
                this.zzcha = null;
            }
        }
    }

    public void cancel() {
        zzsf();
        super.cancel();
    }

    protected void zzao(int i) {
        zzsf();
        super.zzao(i);
    }

    protected void zzse() {
        Window window = this.mContext instanceof Activity ? ((Activity) this.mContext).getWindow() : null;
        if (window != null && window.getDecorView() != null && !((Activity) this.mContext).isDestroyed()) {
            View frameLayout = new FrameLayout(this.mContext);
            frameLayout.setLayoutParams(new LayoutParams(-1, -1));
            frameLayout.addView(this.zzbnz.getView(), -1, -1);
            synchronized (this.zzcgz) {
                if (this.zzchb) {
                    return;
                }
                this.zzcha = new PopupWindow(frameLayout, 1, 1, false);
                this.zzcha.setOutsideTouchable(true);
                this.zzcha.setClippingEnabled(false);
                zzb.zzdg("Displaying the 1x1 popup off the screen.");
                try {
                    this.zzcha.showAtLocation(window.getDecorView(), 0, -1, -1);
                } catch (Exception e) {
                    this.zzcha = null;
                }
            }
        }
    }
}
