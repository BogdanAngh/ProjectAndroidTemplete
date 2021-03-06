package com.google.android.gms.internal;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.CalendarContract.Events;
import android.text.TextUtils;
import com.google.android.gms.C0074R;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.plus.PlusShare;
import java.util.Map;

@zzgd
public class zzeo extends zzeu {
    private final Context mContext;
    private final Map<String, String> zzyn;
    private String zzyo;
    private long zzyp;
    private long zzyq;
    private String zzyr;
    private String zzys;

    /* renamed from: com.google.android.gms.internal.zzeo.1 */
    class C02211 implements OnClickListener {
        final /* synthetic */ zzeo zzyt;

        C02211(zzeo com_google_android_gms_internal_zzeo) {
            this.zzyt = com_google_android_gms_internal_zzeo;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzyt.mContext.startActivity(this.zzyt.createIntent());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzeo.2 */
    class C02222 implements OnClickListener {
        final /* synthetic */ zzeo zzyt;

        C02222(zzeo com_google_android_gms_internal_zzeo) {
            this.zzyt = com_google_android_gms_internal_zzeo;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzyt.zzae("Operation denied by user.");
        }
    }

    public zzeo(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
        super(com_google_android_gms_internal_zzid, "createCalendarEvent");
        this.zzyn = map;
        this.mContext = com_google_android_gms_internal_zzid.zzgB();
        zzeb();
    }

    private String zzab(String str) {
        return TextUtils.isEmpty((CharSequence) this.zzyn.get(str)) ? "" : (String) this.zzyn.get(str);
    }

    private long zzac(String str) {
        String str2 = (String) this.zzyn.get(str);
        if (str2 == null) {
            return -1;
        }
        try {
            return Long.parseLong(str2);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void zzeb() {
        this.zzyo = zzab(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        this.zzyr = zzab("summary");
        this.zzyp = zzac("start_ticks");
        this.zzyq = zzac("end_ticks");
        this.zzys = zzab("location");
    }

    Intent createIntent() {
        Intent data = new Intent("android.intent.action.EDIT").setData(Events.CONTENT_URI);
        data.putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, this.zzyo);
        data.putExtra("eventLocation", this.zzys);
        data.putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, this.zzyr);
        if (this.zzyp > -1) {
            data.putExtra("beginTime", this.zzyp);
        }
        if (this.zzyq > -1) {
            data.putExtra("endTime", this.zzyq);
        }
        data.setFlags(DriveFile.MODE_READ_ONLY);
        return data;
    }

    public void execute() {
        if (this.mContext == null) {
            zzae("Activity context is not available.");
        } else if (zzo.zzbv().zzK(this.mContext).zzcV()) {
            Builder zzJ = zzo.zzbv().zzJ(this.mContext);
            zzJ.setTitle(zzo.zzby().zzc(C0074R.string.create_calendar_title, "Create calendar event"));
            zzJ.setMessage(zzo.zzby().zzc(C0074R.string.create_calendar_message, "Allow Ad to create a calendar event?"));
            zzJ.setPositiveButton(zzo.zzby().zzc(C0074R.string.accept, "Accept"), new C02211(this));
            zzJ.setNegativeButton(zzo.zzby().zzc(C0074R.string.decline, "Decline"), new C02222(this));
            zzJ.create().show();
        } else {
            zzae("This feature is not available on the device.");
        }
    }
}
