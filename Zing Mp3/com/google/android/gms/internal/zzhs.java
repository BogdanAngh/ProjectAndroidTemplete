package com.google.android.gms.internal;

import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.google.android.gms.C1044R;
import com.google.android.gms.ads.internal.zzu;
import java.util.Map;

@zzji
public class zzhs extends zzhv {
    private final Context mContext;
    private final Map<String, String> zzbly;

    /* renamed from: com.google.android.gms.internal.zzhs.1 */
    class C13801 implements OnClickListener {
        final /* synthetic */ String zzbyt;
        final /* synthetic */ String zzbyu;
        final /* synthetic */ zzhs zzbyv;

        C13801(zzhs com_google_android_gms_internal_zzhs, String str, String str2) {
            this.zzbyv = com_google_android_gms_internal_zzhs;
            this.zzbyt = str;
            this.zzbyu = str2;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                ((DownloadManager) this.zzbyv.mContext.getSystemService("download")).enqueue(this.zzbyv.zzj(this.zzbyt, this.zzbyu));
            } catch (IllegalStateException e) {
                this.zzbyv.zzcb("Could not store picture.");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhs.2 */
    class C13812 implements OnClickListener {
        final /* synthetic */ zzhs zzbyv;

        C13812(zzhs com_google_android_gms_internal_zzhs) {
            this.zzbyv = com_google_android_gms_internal_zzhs;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.zzbyv.zzcb("User canceled the download.");
        }
    }

    public zzhs(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        super(com_google_android_gms_internal_zzmd, "storePicture");
        this.zzbly = map;
        this.mContext = com_google_android_gms_internal_zzmd.zzwy();
    }

    public void execute() {
        if (this.mContext == null) {
            zzcb("Activity context is not available");
        } else if (zzu.zzgm().zzac(this.mContext).zzll()) {
            String str = (String) this.zzbly.get("iurl");
            if (TextUtils.isEmpty(str)) {
                zzcb("Image url cannot be empty.");
            } else if (URLUtil.isValidUrl(str)) {
                String zzca = zzca(str);
                if (zzu.zzgm().zzdb(zzca)) {
                    Resources resources = zzu.zzgq().getResources();
                    Builder zzab = zzu.zzgm().zzab(this.mContext);
                    zzab.setTitle(resources != null ? resources.getString(C1044R.string.store_picture_title) : "Save image");
                    zzab.setMessage(resources != null ? resources.getString(C1044R.string.store_picture_message) : "Allow Ad to store image in Picture gallery?");
                    zzab.setPositiveButton(resources != null ? resources.getString(C1044R.string.accept) : "Accept", new C13801(this, str, zzca));
                    zzab.setNegativeButton(resources != null ? resources.getString(C1044R.string.decline) : "Decline", new C13812(this));
                    zzab.create().show();
                    return;
                }
                r1 = "Image type not recognized: ";
                str = String.valueOf(zzca);
                zzcb(str.length() != 0 ? r1.concat(str) : new String(r1));
            } else {
                r1 = "Invalid image url: ";
                str = String.valueOf(str);
                zzcb(str.length() != 0 ? r1.concat(str) : new String(r1));
            }
        } else {
            zzcb("Feature is not supported by the device.");
        }
    }

    String zzca(String str) {
        return Uri.parse(str).getLastPathSegment();
    }

    Request zzj(String str, String str2) {
        Request request = new Request(Uri.parse(str));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, str2);
        zzu.zzgo().zza(request);
        return request;
    }
}
