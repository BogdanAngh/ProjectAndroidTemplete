package com.google.android.gms.analytics.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.exoplayer.extractor.ts.PtsTimestampAdjuster;
import com.google.android.gms.common.internal.zzaa;
import com.mp3download.zingmp3.BuildConfig;
import java.util.UUID;

public class zzai extends zzd {
    private SharedPreferences fF;
    private long fG;
    private long fH;
    private final zza fI;

    public final class zza {
        private final long fJ;
        final /* synthetic */ zzai fK;
        private final String mName;

        private zza(zzai com_google_android_gms_analytics_internal_zzai, String str, long j) {
            this.fK = com_google_android_gms_analytics_internal_zzai;
            zzaa.zzib(str);
            zzaa.zzbt(j > 0);
            this.mName = str;
            this.fJ = j;
        }

        private void zzagu() {
            long currentTimeMillis = this.fK.zzabz().currentTimeMillis();
            Editor edit = this.fK.fF.edit();
            edit.remove(zzagz());
            edit.remove(zzaha());
            edit.putLong(zzagy(), currentTimeMillis);
            edit.commit();
        }

        private long zzagv() {
            long zzagx = zzagx();
            return zzagx == 0 ? 0 : Math.abs(zzagx - this.fK.zzabz().currentTimeMillis());
        }

        private long zzagx() {
            return this.fK.fF.getLong(zzagy(), 0);
        }

        private String zzagy() {
            return String.valueOf(this.mName).concat(":start");
        }

        private String zzagz() {
            return String.valueOf(this.mName).concat(":count");
        }

        public Pair<String, Long> zzagw() {
            long zzagv = zzagv();
            if (zzagv < this.fJ) {
                return null;
            }
            if (zzagv > this.fJ * 2) {
                zzagu();
                return null;
            }
            String string = this.fK.fF.getString(zzaha(), null);
            zzagv = this.fK.fF.getLong(zzagz(), 0);
            zzagu();
            return (string == null || zzagv <= 0) ? null : new Pair(string, Long.valueOf(zzagv));
        }

        protected String zzaha() {
            return String.valueOf(this.mName).concat(":value");
        }

        public void zzfg(String str) {
            if (zzagx() == 0) {
                zzagu();
            }
            if (str == null) {
                str = BuildConfig.FLAVOR;
            }
            synchronized (this) {
                long j = this.fK.fF.getLong(zzagz(), 0);
                if (j <= 0) {
                    Editor edit = this.fK.fF.edit();
                    edit.putString(zzaha(), str);
                    edit.putLong(zzagz(), 1);
                    edit.apply();
                    return;
                }
                Object obj = (UUID.randomUUID().getLeastSignificantBits() & PtsTimestampAdjuster.DO_NOT_OFFSET) < PtsTimestampAdjuster.DO_NOT_OFFSET / (j + 1) ? 1 : null;
                Editor edit2 = this.fK.fF.edit();
                if (obj != null) {
                    edit2.putString(zzaha(), str);
                }
                edit2.putLong(zzagz(), j + 1);
                edit2.apply();
            }
        }
    }

    protected zzai(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.fH = -1;
        this.fI = new zza("monitoring", zzacb().zzafj(), null);
    }

    public long zzago() {
        zzzx();
        zzacj();
        if (this.fG == 0) {
            long j = this.fF.getLong("first_run", 0);
            if (j != 0) {
                this.fG = j;
            } else {
                j = zzabz().currentTimeMillis();
                Editor edit = this.fF.edit();
                edit.putLong("first_run", j);
                if (!edit.commit()) {
                    zzev("Failed to commit first run time");
                }
                this.fG = j;
            }
        }
        return this.fG;
    }

    public zzal zzagp() {
        return new zzal(zzabz(), zzago());
    }

    public long zzagq() {
        zzzx();
        zzacj();
        if (this.fH == -1) {
            this.fH = this.fF.getLong("last_dispatch", 0);
        }
        return this.fH;
    }

    public void zzagr() {
        zzzx();
        zzacj();
        long currentTimeMillis = zzabz().currentTimeMillis();
        Editor edit = this.fF.edit();
        edit.putLong("last_dispatch", currentTimeMillis);
        edit.apply();
        this.fH = currentTimeMillis;
    }

    public String zzags() {
        zzzx();
        zzacj();
        CharSequence string = this.fF.getString("installation_campaign", null);
        return TextUtils.isEmpty(string) ? null : string;
    }

    public zza zzagt() {
        return this.fI;
    }

    public void zzff(String str) {
        zzzx();
        zzacj();
        Editor edit = this.fF.edit();
        if (TextUtils.isEmpty(str)) {
            edit.remove("installation_campaign");
        } else {
            edit.putString("installation_campaign", str);
        }
        if (!edit.commit()) {
            zzev("Failed to commit campaign data");
        }
    }

    protected void zzzy() {
        this.fF = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }
}
