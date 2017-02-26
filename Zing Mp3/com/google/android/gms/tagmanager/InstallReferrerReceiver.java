package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;

public final class InstallReferrerReceiver extends CampaignTrackingReceiver {
    protected void zzp(Context context, String str) {
        zzbf.zzpl(str);
        zzbf.zzaf(context, str);
    }

    protected Class<? extends CampaignTrackingService> zzyy() {
        return InstallReferrerService.class;
    }
}
