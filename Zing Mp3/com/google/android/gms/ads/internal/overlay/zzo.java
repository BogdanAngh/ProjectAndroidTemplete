package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.internal.zzji;

@zzji
public class zzo extends FrameLayout implements OnClickListener {
    private final ImageButton zzccn;
    private final zzu zzcco;

    public zzo(Context context, int i, zzu com_google_android_gms_ads_internal_overlay_zzu) {
        super(context);
        this.zzcco = com_google_android_gms_ads_internal_overlay_zzu;
        setOnClickListener(this);
        this.zzccn = new ImageButton(context);
        this.zzccn.setImageResource(17301527);
        this.zzccn.setBackgroundColor(0);
        this.zzccn.setOnClickListener(this);
        this.zzccn.setPadding(0, 0, 0, 0);
        this.zzccn.setContentDescription("Interstitial close button");
        int zzb = zzm.zzkr().zzb(context, i);
        addView(this.zzccn, new LayoutParams(zzb, zzb, 17));
    }

    public void onClick(View view) {
        if (this.zzcco != null) {
            this.zzcco.zzpm();
        }
    }

    public void zza(boolean z, boolean z2) {
        if (!z2) {
            this.zzccn.setVisibility(0);
        } else if (z) {
            this.zzccn.setVisibility(4);
        } else {
            this.zzccn.setVisibility(8);
        }
    }
}
