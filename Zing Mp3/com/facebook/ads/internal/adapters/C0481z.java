package com.facebook.ads.internal.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.facebook.ads.AdError;
import com.facebook.ads.internal.C0752j;

/* renamed from: com.facebook.ads.internal.adapters.z */
public class C0481z extends BroadcastReceiver {
    private String f518a;
    private C0421y f519b;
    private C0470x f520c;

    public C0481z(String str, C0470x c0470x, C0421y c0421y) {
        this.f520c = c0470x;
        this.f519b = c0421y;
        this.f518a = str;
    }

    public IntentFilter m698a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(C0752j.REWARDED_VIDEO_COMPLETE.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARDED_VIDEO_ERROR.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARDED_VIDEO_AD_CLICK.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARDED_VIDEO_IMPRESSION.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARDED_VIDEO_CLOSED.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARD_SERVER_SUCCESS.m1500a(this.f518a));
        intentFilter.addAction(C0752j.REWARD_SERVER_FAILED.m1500a(this.f518a));
        return intentFilter;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (C0752j.REWARDED_VIDEO_COMPLETE.m1500a(this.f518a).equals(action)) {
            this.f519b.m252d(this.f520c);
        } else if (C0752j.REWARDED_VIDEO_ERROR.m1500a(this.f518a).equals(action)) {
            this.f519b.m249a(this.f520c, AdError.INTERNAL_ERROR);
        } else if (C0752j.REWARDED_VIDEO_AD_CLICK.m1500a(this.f518a).equals(action)) {
            this.f519b.m250b(this.f520c);
        } else if (C0752j.REWARDED_VIDEO_IMPRESSION.m1500a(this.f518a).equals(action)) {
            this.f519b.m251c(this.f520c);
        } else if (C0752j.REWARDED_VIDEO_CLOSED.m1500a(this.f518a).equals(action)) {
            this.f519b.m247a();
        } else if (C0752j.REWARD_SERVER_FAILED.m1500a(this.f518a).equals(action)) {
            this.f519b.m253e(this.f520c);
        } else if (C0752j.REWARD_SERVER_SUCCESS.m1500a(this.f518a).equals(action)) {
            this.f519b.m254f(this.f520c);
        }
    }
}
