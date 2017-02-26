package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.facebook.share.internal.ShareConstants;
import org.json.JSONObject;

@zzji
public class zzid extends Handler {
    private final zzic zzceu;

    public zzid(Context context) {
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        this(new zzie(context));
    }

    public zzid(zzic com_google_android_gms_internal_zzic) {
        this.zzceu = com_google_android_gms_internal_zzic;
    }

    private void zzd(JSONObject jSONObject) {
        try {
            this.zzceu.zza(jSONObject.getString("request_id"), jSONObject.getString("base_url"), jSONObject.getString("html"));
        } catch (Exception e) {
        }
    }

    public void handleMessage(Message message) {
        try {
            Bundle data = message.getData();
            if (data != null) {
                JSONObject jSONObject = new JSONObject(data.getString(ShareConstants.WEB_DIALOG_PARAM_DATA));
                if ("fetch_html".equals(jSONObject.getString("message_name"))) {
                    zzd(jSONObject);
                }
            }
        } catch (Exception e) {
        }
    }
}
