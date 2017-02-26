package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai.zzf;
import com.google.android.gms.internal.zzai.zzj;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;

public interface zzafu {

    public static final class zza extends zzaru<zza> {
        public long aMu;
        public zzj aMv;
        public zzf zzxv;

        public zza() {
            zzckt();
        }

        public static zza zzap(byte[] bArr) throws zzarz {
            return (zza) zzasa.zza(new zza(), bArr);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzafu_zza = (zza) obj;
            if (this.aMu != com_google_android_gms_internal_zzafu_zza.aMu) {
                return false;
            }
            if (this.zzxv == null) {
                if (com_google_android_gms_internal_zzafu_zza.zzxv != null) {
                    return false;
                }
            } else if (!this.zzxv.equals(com_google_android_gms_internal_zzafu_zza.zzxv)) {
                return false;
            }
            if (this.aMv == null) {
                if (com_google_android_gms_internal_zzafu_zza.aMv != null) {
                    return false;
                }
            } else if (!this.aMv.equals(com_google_android_gms_internal_zzafu_zza.aMv)) {
                return false;
            }
            return (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzafu_zza.btG == null || com_google_android_gms_internal_zzafu_zza.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzafu_zza.btG);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.aMv == null ? 0 : this.aMv.hashCode()) + (((this.zzxv == null ? 0 : this.zzxv.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.aMu ^ (this.aMu >>> 32)))) * 31)) * 31)) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i = this.btG.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            com_google_android_gms_internal_zzart.zzb(1, this.aMu);
            if (this.zzxv != null) {
                com_google_android_gms_internal_zzart.zza(2, this.zzxv);
            }
            if (this.aMv != null) {
                com_google_android_gms_internal_zzart.zza(3, this.aMv);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public zza zzaw(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                        this.aMu = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        if (this.zzxv == null) {
                            this.zzxv = new zzf();
                        }
                        com_google_android_gms_internal_zzars.zza(this.zzxv);
                        continue;
                    case C1569R.styleable.Toolbar_logoDescription /*26*/:
                        if (this.aMv == null) {
                            this.aMv = new zzj();
                        }
                        com_google_android_gms_internal_zzars.zza(this.aMv);
                        continue;
                    default:
                        if (!super.zza(com_google_android_gms_internal_zzars, bU)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzaw(com_google_android_gms_internal_zzars);
        }

        public zza zzckt() {
            this.aMu = 0;
            this.zzxv = null;
            this.aMv = null;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        protected int zzx() {
            int zzx = super.zzx() + zzart.zzf(1, this.aMu);
            if (this.zzxv != null) {
                zzx += zzart.zzc(2, this.zzxv);
            }
            return this.aMv != null ? zzx + zzart.zzc(3, this.aMv) : zzx;
        }
    }
}
