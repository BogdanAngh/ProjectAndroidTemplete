package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.internal.zzad.zza;
import com.mp3download.zingmp3.C1569R;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zzar implements zzaq {
    protected MotionEvent zzagj;
    protected LinkedList<MotionEvent> zzagk;
    protected long zzagl;
    protected long zzagm;
    protected long zzagn;
    protected long zzago;
    protected long zzagp;
    protected long zzagq;
    protected long zzagr;
    private boolean zzags;
    protected boolean zzagt;
    protected DisplayMetrics zzagu;

    protected zzar(Context context) {
        this.zzagk = new LinkedList();
        this.zzagl = 0;
        this.zzagm = 0;
        this.zzagn = 0;
        this.zzago = 0;
        this.zzagp = 0;
        this.zzagq = 0;
        this.zzagr = 0;
        this.zzags = false;
        this.zzagt = false;
        try {
            zzan.zzau();
            this.zzagu = context.getResources().getDisplayMetrics();
        } catch (Throwable th) {
        }
    }

    private String zza(Context context, String str, boolean z, View view, byte[] bArr) {
        boolean z2 = true;
        zza com_google_android_gms_internal_zzad_zza = null;
        if (bArr != null && bArr.length > 0) {
            try {
                com_google_android_gms_internal_zzad_zza = zza.zzc(bArr);
            } catch (zzarz e) {
            }
        }
        if (z) {
            try {
                zzaf.zza zza = zza(context, view);
                this.zzags = true;
            } catch (NoSuchAlgorithmException e2) {
                return Integer.toString(7);
            } catch (UnsupportedEncodingException e3) {
                return Integer.toString(7);
            } catch (Throwable th) {
                return Integer.toString(3);
            }
        }
        zza = zza(context, com_google_android_gms_internal_zzad_zza);
        if (zza == null || zza.cz() == 0) {
            return Integer.toString(5);
        }
        if (zzb(z)) {
            z2 = false;
        }
        return zzan.zza(zza, str, z2);
    }

    private static boolean zza(zzbd com_google_android_gms_internal_zzbd) {
        return (com_google_android_gms_internal_zzbd == null || com_google_android_gms_internal_zzbd.zzff == null || com_google_android_gms_internal_zzbd.zzair == null) ? false : true;
    }

    private boolean zzb(zzbd com_google_android_gms_internal_zzbd) {
        return (this.zzagu == null || com_google_android_gms_internal_zzbd == null || com_google_android_gms_internal_zzbd.zzfd == null || com_google_android_gms_internal_zzbd.zzais == null) ? false : true;
    }

    private static boolean zzb(boolean z) {
        return !((Boolean) zzdr.zzbho.get()).booleanValue() ? true : ((Boolean) zzdr.zzbij.get()).booleanValue() && z;
    }

    protected abstract long zza(StackTraceElement[] stackTraceElementArr) throws zzaz;

    protected abstract zzaf.zza zza(Context context, View view);

    protected abstract zzaf.zza zza(Context context, zza com_google_android_gms_internal_zzad_zza);

    public String zza(Context context, String str, View view) {
        return zza(context, str, true, view, null);
    }

    public String zza(Context context, byte[] bArr) {
        if (!zzbe.zzdg() || !((Boolean) zzdr.zzbii.get()).booleanValue()) {
            return zza(context, null, false, null, bArr);
        }
        throw new IllegalStateException("The caller must not be called from the UI thread.");
    }

    public void zza(int i, int i2, int i3) {
        if (this.zzagj != null) {
            this.zzagj.recycle();
        }
        if (this.zzagu != null) {
            this.zzagj = MotionEvent.obtain(0, (long) i3, 1, ((float) i) * this.zzagu.density, ((float) i2) * this.zzagu.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
        } else {
            this.zzagj = null;
        }
        this.zzagt = false;
    }

    public void zza(MotionEvent motionEvent) {
        if (this.zzags) {
            this.zzago = 0;
            this.zzagn = 0;
            this.zzagm = 0;
            this.zzagl = 0;
            this.zzagp = 0;
            this.zzagr = 0;
            this.zzagq = 0;
            Iterator it = this.zzagk.iterator();
            while (it.hasNext()) {
                ((MotionEvent) it.next()).recycle();
            }
            this.zzagk.clear();
            this.zzagj = null;
            this.zzags = false;
        }
        switch (motionEvent.getAction()) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                this.zzagl++;
                break;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                this.zzagj = MotionEvent.obtain(motionEvent);
                this.zzagk.add(this.zzagj);
                if (this.zzagk.size() > 6) {
                    ((MotionEvent) this.zzagk.remove()).recycle();
                }
                this.zzagn++;
                try {
                    this.zzagp = zza(new Throwable().getStackTrace());
                    break;
                } catch (zzaz e) {
                    break;
                }
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                this.zzagm += (long) (motionEvent.getHistorySize() + 1);
                if (((Boolean) zzdr.zzbhw.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
                    try {
                        zzbd zzb = zzb(motionEvent);
                        if (zza(zzb)) {
                            this.zzagq += zzb.zzff.longValue() + zzb.zzair.longValue();
                        }
                        if (zzb(zzb)) {
                            this.zzagr = (zzb.zzais.longValue() + zzb.zzfd.longValue()) + this.zzagr;
                            break;
                        }
                    } catch (zzaz e2) {
                        break;
                    }
                }
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                this.zzago++;
                break;
        }
        this.zzagt = true;
    }

    protected abstract zzbd zzb(MotionEvent motionEvent) throws zzaz;

    public String zzb(Context context) {
        if (!zzbe.zzdg() || !((Boolean) zzdr.zzbii.get()).booleanValue()) {
            return zza(context, null, false, null, null);
        }
        throw new IllegalStateException("The caller must not be called from the UI thread.");
    }

    public String zzb(Context context, String str) {
        return zza(context, str, null);
    }
}
