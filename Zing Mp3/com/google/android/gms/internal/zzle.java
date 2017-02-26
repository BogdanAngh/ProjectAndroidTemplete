package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.google.android.gms.C1044R;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@zzji
public class zzle {
    private final Context mContext;
    private int mState;
    private String zzant;
    private final float zzbzc;
    private String zzcvu;
    private float zzcvv;
    private float zzcvw;
    private float zzcvx;

    /* renamed from: com.google.android.gms.internal.zzle.1 */
    class C14471 implements OnClickListener {
        final /* synthetic */ int zzcvy;
        final /* synthetic */ int zzcvz;
        final /* synthetic */ int zzcwa;
        final /* synthetic */ zzle zzcwb;

        C14471(zzle com_google_android_gms_internal_zzle, int i, int i2, int i3) {
            this.zzcwb = com_google_android_gms_internal_zzle;
            this.zzcvy = i;
            this.zzcvz = i2;
            this.zzcwa = i3;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == this.zzcvy) {
                this.zzcwb.zzwc();
            } else if (i == this.zzcvz && ((Boolean) zzdr.zzbkw.get()).booleanValue()) {
                this.zzcwb.zzwd();
            } else if (i == this.zzcwa && ((Boolean) zzdr.zzbkx.get()).booleanValue()) {
                this.zzcwb.zzwe();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzle.2 */
    class C14482 implements OnClickListener {
        final /* synthetic */ zzle zzcwb;
        final /* synthetic */ String zzcwc;

        C14482(zzle com_google_android_gms_internal_zzle, String str) {
            this.zzcwb = com_google_android_gms_internal_zzle;
            this.zzcwc = str;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            zzu.zzgm().zzb(this.zzcwb.mContext, Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", this.zzcwc), "Share via"));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzle.3 */
    class C14493 implements OnClickListener {
        final /* synthetic */ zzle zzcwb;

        C14493(zzle com_google_android_gms_internal_zzle) {
            this.zzcwb = com_google_android_gms_internal_zzle;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzle.4 */
    class C14504 implements Runnable {
        final /* synthetic */ zzle zzcwb;

        C14504(zzle com_google_android_gms_internal_zzle) {
            this.zzcwb = com_google_android_gms_internal_zzle;
        }

        public void run() {
            zzu.zzgu().zzj(this.zzcwb.mContext, this.zzcwb.zzant);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzle.5 */
    class C14515 implements Runnable {
        final /* synthetic */ zzle zzcwb;

        C14515(zzle com_google_android_gms_internal_zzle) {
            this.zzcwb = com_google_android_gms_internal_zzle;
        }

        public void run() {
            zzu.zzgu().zzk(this.zzcwb.mContext, this.zzcwb.zzant);
        }
    }

    public zzle(Context context) {
        this.mState = 0;
        this.mContext = context;
        this.zzbzc = context.getResources().getDisplayMetrics().density;
    }

    public zzle(Context context, String str) {
        this(context);
        this.zzcvu = str;
    }

    private int zza(List<String> list, String str, boolean z) {
        if (!z) {
            return -1;
        }
        list.add(str);
        return list.size() - 1;
    }

    static String zzdd(String str) {
        if (TextUtils.isEmpty(str)) {
            return "No debug information";
        }
        Uri build = new Builder().encodedQuery(str.replaceAll("\\+", "%20")).build();
        StringBuilder stringBuilder = new StringBuilder();
        Map zzg = zzu.zzgm().zzg(build);
        for (String str2 : zzg.keySet()) {
            stringBuilder.append(str2).append(" = ").append((String) zzg.get(str2)).append("\n\n");
        }
        Object trim = stringBuilder.toString().trim();
        return TextUtils.isEmpty(trim) ? "No debug information" : trim;
    }

    private void zzwb() {
        if (this.mContext instanceof Activity) {
            CharSequence string;
            Resources resources = zzu.zzgq().getResources();
            if (resources != null) {
                string = resources.getString(C1044R.string.debug_menu_title);
            } else {
                Object obj = "Select a Debug Mode";
            }
            String string2 = resources != null ? resources.getString(C1044R.string.debug_menu_ad_information) : "Ad Information";
            String string3 = resources != null ? resources.getString(C1044R.string.debug_menu_creative_preview) : "Creative Preview";
            String string4 = resources != null ? resources.getString(C1044R.string.debug_menu_troubleshooting) : "Troubleshooting";
            List arrayList = new ArrayList();
            new AlertDialog.Builder(this.mContext).setTitle(string).setItems((CharSequence[]) arrayList.toArray(new String[0]), new C14471(this, zza(arrayList, string2, true), zza(arrayList, string3, ((Boolean) zzdr.zzbkw.get()).booleanValue()), zza(arrayList, string4, ((Boolean) zzdr.zzbkx.get()).booleanValue()))).create().show();
            return;
        }
        zzb.zzdh("Can not create dialog without Activity Context");
    }

    private void zzwc() {
        if (this.mContext instanceof Activity) {
            Object zzdd = zzdd(this.zzcvu);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(zzdd);
            builder.setTitle("Ad Information");
            builder.setPositiveButton("Share", new C14482(this, zzdd));
            builder.setNegativeButton("Close", new C14493(this));
            builder.create().show();
            return;
        }
        zzb.zzdh("Can not create dialog without Activity Context");
    }

    private void zzwd() {
        zzb.zzdg("Debug mode [Creative Preview] selected.");
        zzla.zza(new C14504(this));
    }

    private void zzwe() {
        zzb.zzdg("Debug mode [Troubleshooting] selected.");
        zzla.zza(new C14515(this));
    }

    public void setAdUnitId(String str) {
        this.zzant = str;
    }

    public void showDialog() {
        if (((Boolean) zzdr.zzbkx.get()).booleanValue() || ((Boolean) zzdr.zzbkw.get()).booleanValue()) {
            zzwb();
        } else {
            zzwc();
        }
    }

    void zza(int i, float f, float f2) {
        if (i == 0) {
            this.mState = 0;
            this.zzcvv = f;
            this.zzcvw = f2;
            this.zzcvx = f2;
        } else if (this.mState == -1) {
        } else {
            if (i == 2) {
                if (f2 > this.zzcvw) {
                    this.zzcvw = f2;
                } else if (f2 < this.zzcvx) {
                    this.zzcvx = f2;
                }
                if (this.zzcvw - this.zzcvx > 30.0f * this.zzbzc) {
                    this.mState = -1;
                    return;
                }
                if (this.mState == 0 || this.mState == 2) {
                    if (f - this.zzcvv >= 50.0f * this.zzbzc) {
                        this.zzcvv = f;
                        this.mState++;
                    }
                } else if ((this.mState == 1 || this.mState == 3) && f - this.zzcvv <= -50.0f * this.zzbzc) {
                    this.zzcvv = f;
                    this.mState++;
                }
                if (this.mState == 1 || this.mState == 3) {
                    if (f > this.zzcvv) {
                        this.zzcvv = f;
                    }
                } else if (this.mState == 2 && f < this.zzcvv) {
                    this.zzcvv = f;
                }
            } else if (i == 1 && this.mState == 4) {
                showDialog();
            }
        }
    }

    public void zzdc(String str) {
        this.zzcvu = str;
    }

    public void zzg(MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            zza(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i), motionEvent.getHistoricalY(0, i));
        }
        zza(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }
}
