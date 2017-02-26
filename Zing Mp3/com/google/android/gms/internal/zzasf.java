package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;
import java.util.Arrays;

public interface zzasf {

    public static final class zza extends zzaru<zza> implements Cloneable {
        public int bub;
        public String buc;
        public String version;

        public zza() {
            cB();
        }

        public zza cB() {
            this.bub = 0;
            this.buc = BuildConfig.FLAVOR;
            this.version = BuildConfig.FLAVOR;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public zza cC() {
            try {
                return (zza) super.cn();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return cC();
        }

        public /* synthetic */ zzaru cn() throws CloneNotSupportedException {
            return (zza) clone();
        }

        public /* synthetic */ zzasa co() throws CloneNotSupportedException {
            return (zza) clone();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzasf_zza = (zza) obj;
            if (this.bub != com_google_android_gms_internal_zzasf_zza.bub) {
                return false;
            }
            if (this.buc == null) {
                if (com_google_android_gms_internal_zzasf_zza.buc != null) {
                    return false;
                }
            } else if (!this.buc.equals(com_google_android_gms_internal_zzasf_zza.buc)) {
                return false;
            }
            if (this.version == null) {
                if (com_google_android_gms_internal_zzasf_zza.version != null) {
                    return false;
                }
            } else if (!this.version.equals(com_google_android_gms_internal_zzasf_zza.version)) {
                return false;
            }
            return (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzasf_zza.btG == null || com_google_android_gms_internal_zzasf_zza.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzasf_zza.btG);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.version == null ? 0 : this.version.hashCode()) + (((this.buc == null ? 0 : this.buc.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.bub) * 31)) * 31)) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i = this.btG.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            if (this.bub != 0) {
                com_google_android_gms_internal_zzart.zzaf(1, this.bub);
            }
            if (!(this.buc == null || this.buc.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(2, this.buc);
            }
            if (!(this.version == null || this.version.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(3, this.version);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzco(com_google_android_gms_internal_zzars);
        }

        public zza zzco(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                        this.bub = com_google_android_gms_internal_zzars.bY();
                        continue;
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        this.buc = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.Toolbar_logoDescription /*26*/:
                        this.version = com_google_android_gms_internal_zzars.readString();
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

        protected int zzx() {
            int zzx = super.zzx();
            if (this.bub != 0) {
                zzx += zzart.zzah(1, this.bub);
            }
            if (!(this.buc == null || this.buc.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(2, this.buc);
            }
            return (this.version == null || this.version.equals(BuildConfig.FLAVOR)) ? zzx : zzx + zzart.zzr(3, this.version);
        }
    }

    public static final class zzb extends zzaru<zzb> implements Cloneable {
        public byte[] bud;
        public String bue;
        public byte[][] buf;
        public boolean bug;

        public zzb() {
            cD();
        }

        public zzb cD() {
            this.bud = zzasd.btY;
            this.bue = BuildConfig.FLAVOR;
            this.buf = zzasd.btX;
            this.bug = false;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public zzb cE() {
            try {
                zzb com_google_android_gms_internal_zzasf_zzb = (zzb) super.cn();
                if (this.buf != null && this.buf.length > 0) {
                    com_google_android_gms_internal_zzasf_zzb.buf = (byte[][]) this.buf.clone();
                }
                return com_google_android_gms_internal_zzasf_zzb;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return cE();
        }

        public /* synthetic */ zzaru cn() throws CloneNotSupportedException {
            return (zzb) clone();
        }

        public /* synthetic */ zzasa co() throws CloneNotSupportedException {
            return (zzb) clone();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzasf_zzb = (zzb) obj;
            if (!Arrays.equals(this.bud, com_google_android_gms_internal_zzasf_zzb.bud)) {
                return false;
            }
            if (this.bue == null) {
                if (com_google_android_gms_internal_zzasf_zzb.bue != null) {
                    return false;
                }
            } else if (!this.bue.equals(com_google_android_gms_internal_zzasf_zzb.bue)) {
                return false;
            }
            return (zzary.zza(this.buf, com_google_android_gms_internal_zzasf_zzb.buf) && this.bug == com_google_android_gms_internal_zzasf_zzb.bug) ? (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzasf_zzb.btG == null || com_google_android_gms_internal_zzasf_zzb.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzasf_zzb.btG) : false;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.bug ? 1231 : 1237) + (((((this.bue == null ? 0 : this.bue.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.bud)) * 31)) * 31) + zzary.zzb(this.buf)) * 31)) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i = this.btG.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            if (!Arrays.equals(this.bud, zzasd.btY)) {
                com_google_android_gms_internal_zzart.zzb(1, this.bud);
            }
            if (this.buf != null && this.buf.length > 0) {
                for (byte[] bArr : this.buf) {
                    if (bArr != null) {
                        com_google_android_gms_internal_zzart.zzb(2, bArr);
                    }
                }
            }
            if (this.bug) {
                com_google_android_gms_internal_zzart.zzg(3, this.bug);
            }
            if (!(this.bue == null || this.bue.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(4, this.bue);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzcp(com_google_android_gms_internal_zzars);
        }

        public zzb zzcp(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                        this.bud = com_google_android_gms_internal_zzars.readBytes();
                        continue;
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        int zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 18);
                        bU = this.buf == null ? 0 : this.buf.length;
                        Object obj = new byte[(zzc + bU)][];
                        if (bU != 0) {
                            System.arraycopy(this.buf, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = com_google_android_gms_internal_zzars.readBytes();
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = com_google_android_gms_internal_zzars.readBytes();
                        this.buf = obj;
                        continue;
                    case C1569R.styleable.Toolbar_navigationIcon /*24*/:
                        this.bug = com_google_android_gms_internal_zzars.ca();
                        continue;
                    case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                        this.bue = com_google_android_gms_internal_zzars.readString();
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

        protected int zzx() {
            int i = 0;
            int zzx = super.zzx();
            if (!Arrays.equals(this.bud, zzasd.btY)) {
                zzx += zzart.zzc(1, this.bud);
            }
            if (this.buf != null && this.buf.length > 0) {
                int i2 = 0;
                int i3 = 0;
                while (i < this.buf.length) {
                    byte[] bArr = this.buf[i];
                    if (bArr != null) {
                        i3++;
                        i2 += zzart.zzbg(bArr);
                    }
                    i++;
                }
                zzx = (zzx + i2) + (i3 * 1);
            }
            if (this.bug) {
                zzx += zzart.zzh(3, this.bug);
            }
            return (this.bue == null || this.bue.equals(BuildConfig.FLAVOR)) ? zzx : zzx + zzart.zzr(4, this.bue);
        }
    }

    public static final class zzc extends zzaru<zzc> implements Cloneable {
        public boolean bdw;
        public long buh;
        public long bui;
        public long buj;
        public int buk;
        public zzd[] bul;
        public byte[] bum;
        public zza bun;
        public byte[] buo;
        public String bup;
        public String buq;
        public String bur;
        public long bus;
        public zzb but;
        public byte[] buu;
        public String buv;
        public int buw;
        public int[] bux;
        public long buy;
        public zze buz;
        public String tag;
        public int zzajo;

        public zzc() {
            cF();
        }

        public zzc cF() {
            this.buh = 0;
            this.bui = 0;
            this.buj = 0;
            this.tag = BuildConfig.FLAVOR;
            this.buk = 0;
            this.zzajo = 0;
            this.bdw = false;
            this.bul = zzd.cH();
            this.bum = zzasd.btY;
            this.bun = null;
            this.buo = zzasd.btY;
            this.bup = BuildConfig.FLAVOR;
            this.buq = BuildConfig.FLAVOR;
            this.bur = BuildConfig.FLAVOR;
            this.bus = 180000;
            this.but = null;
            this.buu = zzasd.btY;
            this.buv = BuildConfig.FLAVOR;
            this.buw = 0;
            this.bux = zzasd.btR;
            this.buy = 0;
            this.buz = null;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public zzc cG() {
            try {
                zzc com_google_android_gms_internal_zzasf_zzc = (zzc) super.cn();
                if (this.bul != null && this.bul.length > 0) {
                    com_google_android_gms_internal_zzasf_zzc.bul = new zzd[this.bul.length];
                    for (int i = 0; i < this.bul.length; i++) {
                        if (this.bul[i] != null) {
                            com_google_android_gms_internal_zzasf_zzc.bul[i] = (zzd) this.bul[i].clone();
                        }
                    }
                }
                if (this.bun != null) {
                    com_google_android_gms_internal_zzasf_zzc.bun = (zza) this.bun.clone();
                }
                if (this.but != null) {
                    com_google_android_gms_internal_zzasf_zzc.but = (zzb) this.but.clone();
                }
                if (this.bux != null && this.bux.length > 0) {
                    com_google_android_gms_internal_zzasf_zzc.bux = (int[]) this.bux.clone();
                }
                if (this.buz != null) {
                    com_google_android_gms_internal_zzasf_zzc.buz = (zze) this.buz.clone();
                }
                return com_google_android_gms_internal_zzasf_zzc;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return cG();
        }

        public /* synthetic */ zzaru cn() throws CloneNotSupportedException {
            return (zzc) clone();
        }

        public /* synthetic */ zzasa co() throws CloneNotSupportedException {
            return (zzc) clone();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzc)) {
                return false;
            }
            zzc com_google_android_gms_internal_zzasf_zzc = (zzc) obj;
            if (this.buh != com_google_android_gms_internal_zzasf_zzc.buh || this.bui != com_google_android_gms_internal_zzasf_zzc.bui || this.buj != com_google_android_gms_internal_zzasf_zzc.buj) {
                return false;
            }
            if (this.tag == null) {
                if (com_google_android_gms_internal_zzasf_zzc.tag != null) {
                    return false;
                }
            } else if (!this.tag.equals(com_google_android_gms_internal_zzasf_zzc.tag)) {
                return false;
            }
            if (this.buk != com_google_android_gms_internal_zzasf_zzc.buk || this.zzajo != com_google_android_gms_internal_zzasf_zzc.zzajo || this.bdw != com_google_android_gms_internal_zzasf_zzc.bdw || !zzary.equals(this.bul, com_google_android_gms_internal_zzasf_zzc.bul) || !Arrays.equals(this.bum, com_google_android_gms_internal_zzasf_zzc.bum)) {
                return false;
            }
            if (this.bun == null) {
                if (com_google_android_gms_internal_zzasf_zzc.bun != null) {
                    return false;
                }
            } else if (!this.bun.equals(com_google_android_gms_internal_zzasf_zzc.bun)) {
                return false;
            }
            if (!Arrays.equals(this.buo, com_google_android_gms_internal_zzasf_zzc.buo)) {
                return false;
            }
            if (this.bup == null) {
                if (com_google_android_gms_internal_zzasf_zzc.bup != null) {
                    return false;
                }
            } else if (!this.bup.equals(com_google_android_gms_internal_zzasf_zzc.bup)) {
                return false;
            }
            if (this.buq == null) {
                if (com_google_android_gms_internal_zzasf_zzc.buq != null) {
                    return false;
                }
            } else if (!this.buq.equals(com_google_android_gms_internal_zzasf_zzc.buq)) {
                return false;
            }
            if (this.bur == null) {
                if (com_google_android_gms_internal_zzasf_zzc.bur != null) {
                    return false;
                }
            } else if (!this.bur.equals(com_google_android_gms_internal_zzasf_zzc.bur)) {
                return false;
            }
            if (this.bus != com_google_android_gms_internal_zzasf_zzc.bus) {
                return false;
            }
            if (this.but == null) {
                if (com_google_android_gms_internal_zzasf_zzc.but != null) {
                    return false;
                }
            } else if (!this.but.equals(com_google_android_gms_internal_zzasf_zzc.but)) {
                return false;
            }
            if (!Arrays.equals(this.buu, com_google_android_gms_internal_zzasf_zzc.buu)) {
                return false;
            }
            if (this.buv == null) {
                if (com_google_android_gms_internal_zzasf_zzc.buv != null) {
                    return false;
                }
            } else if (!this.buv.equals(com_google_android_gms_internal_zzasf_zzc.buv)) {
                return false;
            }
            if (this.buw != com_google_android_gms_internal_zzasf_zzc.buw || !zzary.equals(this.bux, com_google_android_gms_internal_zzasf_zzc.bux) || this.buy != com_google_android_gms_internal_zzasf_zzc.buy) {
                return false;
            }
            if (this.buz == null) {
                if (com_google_android_gms_internal_zzasf_zzc.buz != null) {
                    return false;
                }
            } else if (!this.buz.equals(com_google_android_gms_internal_zzasf_zzc.buz)) {
                return false;
            }
            return (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzasf_zzc.btG == null || com_google_android_gms_internal_zzasf_zzc.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzasf_zzc.btG);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.buz == null ? 0 : this.buz.hashCode()) + (((((((((this.buv == null ? 0 : this.buv.hashCode()) + (((((this.but == null ? 0 : this.but.hashCode()) + (((((this.bur == null ? 0 : this.bur.hashCode()) + (((this.buq == null ? 0 : this.buq.hashCode()) + (((this.bup == null ? 0 : this.bup.hashCode()) + (((((this.bun == null ? 0 : this.bun.hashCode()) + (((((((this.bdw ? 1231 : 1237) + (((((((this.tag == null ? 0 : this.tag.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.buh ^ (this.buh >>> 32)))) * 31) + ((int) (this.bui ^ (this.bui >>> 32)))) * 31) + ((int) (this.buj ^ (this.buj >>> 32)))) * 31)) * 31) + this.buk) * 31) + this.zzajo) * 31)) * 31) + zzary.hashCode(this.bul)) * 31) + Arrays.hashCode(this.bum)) * 31)) * 31) + Arrays.hashCode(this.buo)) * 31)) * 31)) * 31)) * 31) + ((int) (this.bus ^ (this.bus >>> 32)))) * 31)) * 31) + Arrays.hashCode(this.buu)) * 31)) * 31) + this.buw) * 31) + zzary.hashCode(this.bux)) * 31) + ((int) (this.buy ^ (this.buy >>> 32)))) * 31)) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i = this.btG.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            int i = 0;
            if (this.buh != 0) {
                com_google_android_gms_internal_zzart.zzb(1, this.buh);
            }
            if (!(this.tag == null || this.tag.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(2, this.tag);
            }
            if (this.bul != null && this.bul.length > 0) {
                for (zzasa com_google_android_gms_internal_zzasa : this.bul) {
                    if (com_google_android_gms_internal_zzasa != null) {
                        com_google_android_gms_internal_zzart.zza(3, com_google_android_gms_internal_zzasa);
                    }
                }
            }
            if (!Arrays.equals(this.bum, zzasd.btY)) {
                com_google_android_gms_internal_zzart.zzb(4, this.bum);
            }
            if (!Arrays.equals(this.buo, zzasd.btY)) {
                com_google_android_gms_internal_zzart.zzb(6, this.buo);
            }
            if (!(this.bup == null || this.bup.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(8, this.bup);
            }
            if (this.bun != null) {
                com_google_android_gms_internal_zzart.zza(9, this.bun);
            }
            if (this.bdw) {
                com_google_android_gms_internal_zzart.zzg(10, this.bdw);
            }
            if (this.buk != 0) {
                com_google_android_gms_internal_zzart.zzaf(11, this.buk);
            }
            if (this.zzajo != 0) {
                com_google_android_gms_internal_zzart.zzaf(12, this.zzajo);
            }
            if (!(this.buq == null || this.buq.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(13, this.buq);
            }
            if (!(this.bur == null || this.bur.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(14, this.bur);
            }
            if (this.bus != 180000) {
                com_google_android_gms_internal_zzart.zzd(15, this.bus);
            }
            if (this.but != null) {
                com_google_android_gms_internal_zzart.zza(16, this.but);
            }
            if (this.bui != 0) {
                com_google_android_gms_internal_zzart.zzb(17, this.bui);
            }
            if (!Arrays.equals(this.buu, zzasd.btY)) {
                com_google_android_gms_internal_zzart.zzb(18, this.buu);
            }
            if (this.buw != 0) {
                com_google_android_gms_internal_zzart.zzaf(19, this.buw);
            }
            if (this.bux != null && this.bux.length > 0) {
                while (i < this.bux.length) {
                    com_google_android_gms_internal_zzart.zzaf(20, this.bux[i]);
                    i++;
                }
            }
            if (this.buj != 0) {
                com_google_android_gms_internal_zzart.zzb(21, this.buj);
            }
            if (this.buy != 0) {
                com_google_android_gms_internal_zzart.zzb(22, this.buy);
            }
            if (this.buz != null) {
                com_google_android_gms_internal_zzart.zza(23, this.buz);
            }
            if (!(this.buv == null || this.buv.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(24, this.buv);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzcq(com_google_android_gms_internal_zzars);
        }

        public zzc zzcq(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                int zzc;
                Object obj;
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                        this.buh = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        this.tag = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.Toolbar_logoDescription /*26*/:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 26);
                        bU = this.bul == null ? 0 : this.bul.length;
                        obj = new zzd[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.bul, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = new zzd();
                            com_google_android_gms_internal_zzars.zza(obj[bU]);
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = new zzd();
                        com_google_android_gms_internal_zzars.zza(obj[bU]);
                        this.bul = obj;
                        continue;
                    case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                        this.bum = com_google_android_gms_internal_zzars.readBytes();
                        continue;
                    case C1569R.styleable.AppCompatTheme_actionButtonStyle /*50*/:
                        this.buo = com_google_android_gms_internal_zzars.readBytes();
                        continue;
                    case C1569R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /*66*/:
                        this.bup = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.AppCompatTheme_listPreferredItemPaddingRight /*74*/:
                        if (this.bun == null) {
                            this.bun = new zza();
                        }
                        com_google_android_gms_internal_zzars.zza(this.bun);
                        continue;
                    case C1569R.styleable.AppCompatTheme_panelMenuListWidth /*80*/:
                        this.bdw = com_google_android_gms_internal_zzars.ca();
                        continue;
                    case C1569R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
                        this.buk = com_google_android_gms_internal_zzars.bY();
                        continue;
                    case C1569R.styleable.AppCompatTheme_alertDialogTheme /*96*/:
                        this.zzajo = com_google_android_gms_internal_zzars.bY();
                        continue;
                    case C1569R.styleable.AppCompatTheme_editTextStyle /*106*/:
                        this.buq = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.AppCompatTheme_listMenuViewStyle /*114*/:
                        this.bur = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case 120:
                        this.bus = com_google_android_gms_internal_zzars.cc();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        if (this.but == null) {
                            this.but = new zzb();
                        }
                        com_google_android_gms_internal_zzars.zza(this.but);
                        continue;
                    case 136:
                        this.bui = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case 146:
                        this.buu = com_google_android_gms_internal_zzars.readBytes();
                        continue;
                    case 152:
                        bU = com_google_android_gms_internal_zzars.bY();
                        switch (bU) {
                            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                                this.buw = bU;
                                break;
                            default:
                                continue;
                        }
                    case 160:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 160);
                        bU = this.bux == null ? 0 : this.bux.length;
                        obj = new int[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.bux, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = com_google_android_gms_internal_zzars.bY();
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = com_google_android_gms_internal_zzars.bY();
                        this.bux = obj;
                        continue;
                    case 162:
                        int zzagt = com_google_android_gms_internal_zzars.zzagt(com_google_android_gms_internal_zzars.cd());
                        zzc = com_google_android_gms_internal_zzars.getPosition();
                        bU = 0;
                        while (com_google_android_gms_internal_zzars.ci() > 0) {
                            com_google_android_gms_internal_zzars.bY();
                            bU++;
                        }
                        com_google_android_gms_internal_zzars.zzagv(zzc);
                        zzc = this.bux == null ? 0 : this.bux.length;
                        Object obj2 = new int[(bU + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.bux, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzars.bY();
                            zzc++;
                        }
                        this.bux = obj2;
                        com_google_android_gms_internal_zzars.zzagu(zzagt);
                        continue;
                    case 168:
                        this.buj = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case 176:
                        this.buy = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case 186:
                        if (this.buz == null) {
                            this.buz = new zze();
                        }
                        com_google_android_gms_internal_zzars.zza(this.buz);
                        continue;
                    case 194:
                        this.buv = com_google_android_gms_internal_zzars.readString();
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

        protected int zzx() {
            int i;
            int i2 = 0;
            int zzx = super.zzx();
            if (this.buh != 0) {
                zzx += zzart.zzf(1, this.buh);
            }
            if (!(this.tag == null || this.tag.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(2, this.tag);
            }
            if (this.bul != null && this.bul.length > 0) {
                i = zzx;
                for (zzasa com_google_android_gms_internal_zzasa : this.bul) {
                    if (com_google_android_gms_internal_zzasa != null) {
                        i += zzart.zzc(3, com_google_android_gms_internal_zzasa);
                    }
                }
                zzx = i;
            }
            if (!Arrays.equals(this.bum, zzasd.btY)) {
                zzx += zzart.zzc(4, this.bum);
            }
            if (!Arrays.equals(this.buo, zzasd.btY)) {
                zzx += zzart.zzc(6, this.buo);
            }
            if (!(this.bup == null || this.bup.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(8, this.bup);
            }
            if (this.bun != null) {
                zzx += zzart.zzc(9, this.bun);
            }
            if (this.bdw) {
                zzx += zzart.zzh(10, this.bdw);
            }
            if (this.buk != 0) {
                zzx += zzart.zzah(11, this.buk);
            }
            if (this.zzajo != 0) {
                zzx += zzart.zzah(12, this.zzajo);
            }
            if (!(this.buq == null || this.buq.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(13, this.buq);
            }
            if (!(this.bur == null || this.bur.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(14, this.bur);
            }
            if (this.bus != 180000) {
                zzx += zzart.zzh(15, this.bus);
            }
            if (this.but != null) {
                zzx += zzart.zzc(16, this.but);
            }
            if (this.bui != 0) {
                zzx += zzart.zzf(17, this.bui);
            }
            if (!Arrays.equals(this.buu, zzasd.btY)) {
                zzx += zzart.zzc(18, this.buu);
            }
            if (this.buw != 0) {
                zzx += zzart.zzah(19, this.buw);
            }
            if (this.bux != null && this.bux.length > 0) {
                i = 0;
                while (i2 < this.bux.length) {
                    i += zzart.zzagz(this.bux[i2]);
                    i2++;
                }
                zzx = (zzx + i) + (this.bux.length * 2);
            }
            if (this.buj != 0) {
                zzx += zzart.zzf(21, this.buj);
            }
            if (this.buy != 0) {
                zzx += zzart.zzf(22, this.buy);
            }
            if (this.buz != null) {
                zzx += zzart.zzc(23, this.buz);
            }
            return (this.buv == null || this.buv.equals(BuildConfig.FLAVOR)) ? zzx : zzx + zzart.zzr(24, this.buv);
        }
    }

    public static final class zzd extends zzaru<zzd> implements Cloneable {
        private static volatile zzd[] buA;
        public String value;
        public String zzcb;

        public zzd() {
            cI();
        }

        public static zzd[] cH() {
            if (buA == null) {
                synchronized (zzary.btO) {
                    if (buA == null) {
                        buA = new zzd[0];
                    }
                }
            }
            return buA;
        }

        public zzd cI() {
            this.zzcb = BuildConfig.FLAVOR;
            this.value = BuildConfig.FLAVOR;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public zzd cJ() {
            try {
                return (zzd) super.cn();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return cJ();
        }

        public /* synthetic */ zzaru cn() throws CloneNotSupportedException {
            return (zzd) clone();
        }

        public /* synthetic */ zzasa co() throws CloneNotSupportedException {
            return (zzd) clone();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzd)) {
                return false;
            }
            zzd com_google_android_gms_internal_zzasf_zzd = (zzd) obj;
            if (this.zzcb == null) {
                if (com_google_android_gms_internal_zzasf_zzd.zzcb != null) {
                    return false;
                }
            } else if (!this.zzcb.equals(com_google_android_gms_internal_zzasf_zzd.zzcb)) {
                return false;
            }
            if (this.value == null) {
                if (com_google_android_gms_internal_zzasf_zzd.value != null) {
                    return false;
                }
            } else if (!this.value.equals(com_google_android_gms_internal_zzasf_zzd.value)) {
                return false;
            }
            return (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzasf_zzd.btG == null || com_google_android_gms_internal_zzasf_zzd.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzasf_zzd.btG);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.zzcb == null ? 0 : this.zzcb.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i = this.btG.hashCode();
            }
            return hashCode + i;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            if (!(this.zzcb == null || this.zzcb.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(1, this.zzcb);
            }
            if (!(this.value == null || this.value.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(2, this.value);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzcr(com_google_android_gms_internal_zzars);
        }

        public zzd zzcr(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                        this.zzcb = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        this.value = com_google_android_gms_internal_zzars.readString();
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

        protected int zzx() {
            int zzx = super.zzx();
            if (!(this.zzcb == null || this.zzcb.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(1, this.zzcb);
            }
            return (this.value == null || this.value.equals(BuildConfig.FLAVOR)) ? zzx : zzx + zzart.zzr(2, this.value);
        }
    }

    public static final class zze extends zzaru<zze> implements Cloneable {
        public int buB;
        public int buC;

        public zze() {
            cK();
        }

        public zze cK() {
            this.buB = -1;
            this.buC = 0;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public zze cL() {
            try {
                return (zze) super.cn();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return cL();
        }

        public /* synthetic */ zzaru cn() throws CloneNotSupportedException {
            return (zze) clone();
        }

        public /* synthetic */ zzasa co() throws CloneNotSupportedException {
            return (zze) clone();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zze)) {
                return false;
            }
            zze com_google_android_gms_internal_zzasf_zze = (zze) obj;
            return (this.buB == com_google_android_gms_internal_zzasf_zze.buB && this.buC == com_google_android_gms_internal_zzasf_zze.buC) ? (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzasf_zze.btG == null || com_google_android_gms_internal_zzasf_zze.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzasf_zze.btG) : false;
        }

        public int hashCode() {
            int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + this.buB) * 31) + this.buC) * 31;
            int hashCode2 = (this.btG == null || this.btG.isEmpty()) ? 0 : this.btG.hashCode();
            return hashCode2 + hashCode;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            if (this.buB != -1) {
                com_google_android_gms_internal_zzart.zzaf(1, this.buB);
            }
            if (this.buC != 0) {
                com_google_android_gms_internal_zzart.zzaf(2, this.buC);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzcs(com_google_android_gms_internal_zzars);
        }

        public zze zzcs(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                        bU = com_google_android_gms_internal_zzars.bY();
                        switch (bU) {
                            case CommonStatusCodes.SUCCESS_CACHE /*-1*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                            case C1569R.styleable.Toolbar_popupTheme /*11*/:
                            case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                            case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                            case C1569R.styleable.Toolbar_titleMargin /*14*/:
                            case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                            case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                            case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                                this.buB = bU;
                                break;
                            default:
                                continue;
                        }
                    case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                        bU = com_google_android_gms_internal_zzars.bY();
                        switch (bU) {
                            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                            case C1569R.styleable.Toolbar_popupTheme /*11*/:
                            case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                            case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                            case C1569R.styleable.Toolbar_titleMargin /*14*/:
                            case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                            case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                            case C1569R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                                this.buC = bU;
                                break;
                            default:
                                continue;
                        }
                    default:
                        if (!super.zza(com_google_android_gms_internal_zzars, bU)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected int zzx() {
            int zzx = super.zzx();
            if (this.buB != -1) {
                zzx += zzart.zzah(1, this.buB);
            }
            return this.buC != 0 ? zzx + zzart.zzah(2, this.buC) : zzx;
        }
    }
}
