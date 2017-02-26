package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.CalendarContract.Events;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.C1044R;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Map;

@zzji
public class zzhp extends zzhv {
    private final Context mContext;
    private final Map<String, String> zzbly;
    private String zzbxx;
    private long zzbxy;
    private long zzbxz;
    private String zzbya;
    private String zzbyb;

    /* renamed from: com.google.android.gms.internal.zzhp.1 */
    class C13771 implements OnClickListener {
        final /* synthetic */ zzhp zzbyc;

        C13771(zzhp com_google_android_gms_internal_zzhp) {
            this.zzbyc = com_google_android_gms_internal_zzhp;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            zzu.zzgm().zzb(this.zzbyc.mContext, this.zzbyc.createIntent());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhp.2 */
    class C13782 implements OnClickListener {
        final /* synthetic */ zzhp zzbyc;

        C13782(zzhp com_google_android_gms_internal_zzhp) {
            this.zzbyc = com_google_android_gms_internal_zzhp;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.zzbyc.zzcb("Operation denied by user.");
        }
    }

    public zzhp(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        super(com_google_android_gms_internal_zzmd, "createCalendarEvent");
        this.zzbly = map;
        this.mContext = com_google_android_gms_internal_zzmd.zzwy();
        zzor();
    }

    private String zzby(String str) {
        return TextUtils.isEmpty((CharSequence) this.zzbly.get(str)) ? BuildConfig.FLAVOR : (String) this.zzbly.get(str);
    }

    private long zzbz(String str) {
        String str2 = (String) this.zzbly.get(str);
        if (str2 == null) {
            return -1;
        }
        try {
            return Long.parseLong(str2);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void zzor() {
        this.zzbxx = zzby(ShareConstants.WEB_DIALOG_PARAM_DESCRIPTION);
        this.zzbya = zzby("summary");
        this.zzbxy = zzbz("start_ticks");
        this.zzbxz = zzbz("end_ticks");
        this.zzbyb = zzby("location");
    }

    @TargetApi(14)
    Intent createIntent() {
        Intent data = new Intent("android.intent.action.EDIT").setData(Events.CONTENT_URI);
        data.putExtra(ShareConstants.WEB_DIALOG_PARAM_TITLE, this.zzbxx);
        data.putExtra("eventLocation", this.zzbyb);
        data.putExtra(ShareConstants.WEB_DIALOG_PARAM_DESCRIPTION, this.zzbya);
        if (this.zzbxy > -1) {
            data.putExtra("beginTime", this.zzbxy);
        }
        if (this.zzbxz > -1) {
            data.putExtra("endTime", this.zzbxz);
        }
        data.setFlags(268435456);
        return data;
    }

    public void execute() {
        if (this.mContext == null) {
            zzcb("Activity context is not available.");
        } else if (zzu.zzgm().zzac(this.mContext).zzln()) {
            Builder zzab = zzu.zzgm().zzab(this.mContext);
            Resources resources = zzu.zzgq().getResources();
            zzab.setTitle(resources != null ? resources.getString(C1044R.string.create_calendar_title) : "Create calendar event");
            zzab.setMessage(resources != null ? resources.getString(C1044R.string.create_calendar_message) : "Allow Ad to create a calendar event?");
            zzab.setPositiveButton(resources != null ? resources.getString(C1044R.string.accept) : "Accept", new C13771(this));
            zzab.setNegativeButton(resources != null ? resources.getString(C1044R.string.decline) : "Decline", new C13782(this));
            zzab.create().show();
        } else {
            zzcb("This feature is not available on the device.");
        }
    }
}
