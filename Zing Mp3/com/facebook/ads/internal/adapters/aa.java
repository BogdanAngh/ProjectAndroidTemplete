package com.facebook.ads.internal.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.ads.internal.p000i.C0727i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0651l;
import com.google.android.gms.tagmanager.DataLayer;
import java.io.Serializable;

public class aa extends BroadcastReceiver {
    private Context f290a;
    private C0727i f291b;

    public aa(C0727i c0727i, Context context) {
        this.f291b = c0727i;
        this.f290a = context;
    }

    public void m339a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.facebook.ads.interstitial.displayed:" + this.f291b.getUniqueId());
        intentFilter.addAction("videoInterstitalEvent:" + this.f291b.getUniqueId());
        LocalBroadcastManager.getInstance(this.f290a).registerReceiver(this, intentFilter);
    }

    public void m340b() {
        try {
            LocalBroadcastManager.getInstance(this.f290a).unregisterReceiver(this);
        } catch (Exception e) {
        }
    }

    public void onReceive(Context context, Intent intent) {
        String[] split = intent.getAction().split(":");
        if (split.length != 2 || !split[1].equals(this.f291b.getUniqueId())) {
            return;
        }
        if (split[0].equals("com.facebook.ads.interstitial.displayed")) {
            if (this.f291b.getListener() != null) {
                this.f291b.getListener().onEnterFullscreen(this.f291b.getMediaView());
                this.f291b.getListener().onVolumeChange(this.f291b.getMediaView(), 1.0f);
            }
        } else if (split[0].equals("videoInterstitalEvent")) {
            Serializable serializableExtra = intent.getSerializableExtra(DataLayer.EVENT_KEY);
            if (serializableExtra instanceof C0651l) {
                if (this.f291b.getListener() != null) {
                    this.f291b.getListener().onExitFullscreen(this.f291b.getMediaView());
                    this.f291b.getListener().onVolumeChange(this.f291b.getMediaView(), 0.0f);
                }
                this.f291b.m1388a(((C0651l) serializableExtra).m1208b());
                this.f291b.setVisibility(0);
                this.f291b.m1407d();
            } else if (serializableExtra instanceof C0646g) {
                if (this.f291b.getListener() != null) {
                    this.f291b.getListener().onPlay(this.f291b.getMediaView());
                }
            } else if ((serializableExtra instanceof C0644e) && this.f291b.getListener() != null) {
                this.f291b.getListener().onPause(this.f291b.getMediaView());
            }
        }
    }
}
