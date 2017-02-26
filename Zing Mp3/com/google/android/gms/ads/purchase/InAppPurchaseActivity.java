package com.google.android.gms.ads.purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzih;

public class InAppPurchaseActivity extends Activity {
    public static final String CLASS_NAME = "com.google.android.gms.ads.purchase.InAppPurchaseActivity";
    public static final String SIMPLE_CLASS_NAME = "InAppPurchaseActivity";
    private zzih f1517Y;

    protected void onActivityResult(int i, int i2, Intent intent) {
        try {
            if (this.f1517Y != null) {
                this.f1517Y.onActivityResult(i, i2, intent);
            }
        } catch (Throwable e) {
            zzb.zzc("Could not forward onActivityResult to in-app purchase manager:", e);
        }
        super.onActivityResult(i, i2, intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1517Y = zzm.zzks().zzb((Activity) this);
        if (this.f1517Y == null) {
            zzb.zzdi("Could not create in-app purchase manager.");
            finish();
            return;
        }
        try {
            this.f1517Y.onCreate();
        } catch (Throwable e) {
            zzb.zzc("Could not forward onCreate to in-app purchase manager:", e);
            finish();
        }
    }

    protected void onDestroy() {
        try {
            if (this.f1517Y != null) {
                this.f1517Y.onDestroy();
            }
        } catch (Throwable e) {
            zzb.zzc("Could not forward onDestroy to in-app purchase manager:", e);
        }
        super.onDestroy();
    }
}
