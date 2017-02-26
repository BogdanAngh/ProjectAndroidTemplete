package com.google.android.gms.internal;

import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;

public interface zzaj {

    public static final class zza extends zzaru<zza> {
        private static volatile zza[] zzxx;
        public String string;
        public int type;
        public zza[] zzxy;
        public zza[] zzxz;
        public zza[] zzya;
        public String zzyb;
        public String zzyc;
        public long zzyd;
        public boolean zzye;
        public zza[] zzyf;
        public int[] zzyg;
        public boolean zzyh;

        public zza() {
            zzas();
        }

        public static zza[] zzar() {
            if (zzxx == null) {
                synchronized (zzary.btO) {
                    if (zzxx == null) {
                        zzxx = new zza[0];
                    }
                }
            }
            return zzxx;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzaj_zza = (zza) obj;
            if (this.type != com_google_android_gms_internal_zzaj_zza.type) {
                return false;
            }
            if (this.string == null) {
                if (com_google_android_gms_internal_zzaj_zza.string != null) {
                    return false;
                }
            } else if (!this.string.equals(com_google_android_gms_internal_zzaj_zza.string)) {
                return false;
            }
            if (!zzary.equals(this.zzxy, com_google_android_gms_internal_zzaj_zza.zzxy) || !zzary.equals(this.zzxz, com_google_android_gms_internal_zzaj_zza.zzxz) || !zzary.equals(this.zzya, com_google_android_gms_internal_zzaj_zza.zzya)) {
                return false;
            }
            if (this.zzyb == null) {
                if (com_google_android_gms_internal_zzaj_zza.zzyb != null) {
                    return false;
                }
            } else if (!this.zzyb.equals(com_google_android_gms_internal_zzaj_zza.zzyb)) {
                return false;
            }
            if (this.zzyc == null) {
                if (com_google_android_gms_internal_zzaj_zza.zzyc != null) {
                    return false;
                }
            } else if (!this.zzyc.equals(com_google_android_gms_internal_zzaj_zza.zzyc)) {
                return false;
            }
            return (this.zzyd == com_google_android_gms_internal_zzaj_zza.zzyd && this.zzye == com_google_android_gms_internal_zzaj_zza.zzye && zzary.equals(this.zzyf, com_google_android_gms_internal_zzaj_zza.zzyf) && zzary.equals(this.zzyg, com_google_android_gms_internal_zzaj_zza.zzyg) && this.zzyh == com_google_android_gms_internal_zzaj_zza.zzyh) ? (this.btG == null || this.btG.isEmpty()) ? com_google_android_gms_internal_zzaj_zza.btG == null || com_google_android_gms_internal_zzaj_zza.btG.isEmpty() : this.btG.equals(com_google_android_gms_internal_zzaj_zza.btG) : false;
        }

        public int hashCode() {
            int i = 1231;
            int i2 = 0;
            int hashCode = ((((((this.zzye ? 1231 : 1237) + (((((this.zzyc == null ? 0 : this.zzyc.hashCode()) + (((this.zzyb == null ? 0 : this.zzyb.hashCode()) + (((((((((this.string == null ? 0 : this.string.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31)) * 31) + zzary.hashCode(this.zzxy)) * 31) + zzary.hashCode(this.zzxz)) * 31) + zzary.hashCode(this.zzya)) * 31)) * 31)) * 31) + ((int) (this.zzyd ^ (this.zzyd >>> 32)))) * 31)) * 31) + zzary.hashCode(this.zzyf)) * 31) + zzary.hashCode(this.zzyg)) * 31;
            if (!this.zzyh) {
                i = 1237;
            }
            hashCode = (hashCode + i) * 31;
            if (!(this.btG == null || this.btG.isEmpty())) {
                i2 = this.btG.hashCode();
            }
            return hashCode + i2;
        }

        public void zza(zzart com_google_android_gms_internal_zzart) throws IOException {
            int i = 0;
            com_google_android_gms_internal_zzart.zzaf(1, this.type);
            if (!(this.string == null || this.string.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(2, this.string);
            }
            if (this.zzxy != null && this.zzxy.length > 0) {
                for (zzasa com_google_android_gms_internal_zzasa : this.zzxy) {
                    if (com_google_android_gms_internal_zzasa != null) {
                        com_google_android_gms_internal_zzart.zza(3, com_google_android_gms_internal_zzasa);
                    }
                }
            }
            if (this.zzxz != null && this.zzxz.length > 0) {
                for (zzasa com_google_android_gms_internal_zzasa2 : this.zzxz) {
                    if (com_google_android_gms_internal_zzasa2 != null) {
                        com_google_android_gms_internal_zzart.zza(4, com_google_android_gms_internal_zzasa2);
                    }
                }
            }
            if (this.zzya != null && this.zzya.length > 0) {
                for (zzasa com_google_android_gms_internal_zzasa22 : this.zzya) {
                    if (com_google_android_gms_internal_zzasa22 != null) {
                        com_google_android_gms_internal_zzart.zza(5, com_google_android_gms_internal_zzasa22);
                    }
                }
            }
            if (!(this.zzyb == null || this.zzyb.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(6, this.zzyb);
            }
            if (!(this.zzyc == null || this.zzyc.equals(BuildConfig.FLAVOR))) {
                com_google_android_gms_internal_zzart.zzq(7, this.zzyc);
            }
            if (this.zzyd != 0) {
                com_google_android_gms_internal_zzart.zzb(8, this.zzyd);
            }
            if (this.zzyh) {
                com_google_android_gms_internal_zzart.zzg(9, this.zzyh);
            }
            if (this.zzyg != null && this.zzyg.length > 0) {
                for (int zzaf : this.zzyg) {
                    com_google_android_gms_internal_zzart.zzaf(10, zzaf);
                }
            }
            if (this.zzyf != null && this.zzyf.length > 0) {
                while (i < this.zzyf.length) {
                    zzasa com_google_android_gms_internal_zzasa3 = this.zzyf[i];
                    if (com_google_android_gms_internal_zzasa3 != null) {
                        com_google_android_gms_internal_zzart.zza(11, com_google_android_gms_internal_zzasa3);
                    }
                    i++;
                }
            }
            if (this.zzye) {
                com_google_android_gms_internal_zzart.zzg(12, this.zzye);
            }
            super.zza(com_google_android_gms_internal_zzart);
        }

        public zza zzas() {
            this.type = 1;
            this.string = BuildConfig.FLAVOR;
            this.zzxy = zzar();
            this.zzxz = zzar();
            this.zzya = zzar();
            this.zzyb = BuildConfig.FLAVOR;
            this.zzyc = BuildConfig.FLAVOR;
            this.zzyd = 0;
            this.zzye = false;
            this.zzyf = zzar();
            this.zzyg = zzasd.btR;
            this.zzyh = false;
            this.btG = null;
            this.btP = -1;
            return this;
        }

        public /* synthetic */ zzasa zzb(zzars com_google_android_gms_internal_zzars) throws IOException {
            return zzx(com_google_android_gms_internal_zzars);
        }

        protected int zzx() {
            int i;
            int i2 = 0;
            int zzx = super.zzx() + zzart.zzah(1, this.type);
            if (!(this.string == null || this.string.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(2, this.string);
            }
            if (this.zzxy != null && this.zzxy.length > 0) {
                i = zzx;
                for (zzasa com_google_android_gms_internal_zzasa : this.zzxy) {
                    if (com_google_android_gms_internal_zzasa != null) {
                        i += zzart.zzc(3, com_google_android_gms_internal_zzasa);
                    }
                }
                zzx = i;
            }
            if (this.zzxz != null && this.zzxz.length > 0) {
                i = zzx;
                for (zzasa com_google_android_gms_internal_zzasa2 : this.zzxz) {
                    if (com_google_android_gms_internal_zzasa2 != null) {
                        i += zzart.zzc(4, com_google_android_gms_internal_zzasa2);
                    }
                }
                zzx = i;
            }
            if (this.zzya != null && this.zzya.length > 0) {
                i = zzx;
                for (zzasa com_google_android_gms_internal_zzasa22 : this.zzya) {
                    if (com_google_android_gms_internal_zzasa22 != null) {
                        i += zzart.zzc(5, com_google_android_gms_internal_zzasa22);
                    }
                }
                zzx = i;
            }
            if (!(this.zzyb == null || this.zzyb.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(6, this.zzyb);
            }
            if (!(this.zzyc == null || this.zzyc.equals(BuildConfig.FLAVOR))) {
                zzx += zzart.zzr(7, this.zzyc);
            }
            if (this.zzyd != 0) {
                zzx += zzart.zzf(8, this.zzyd);
            }
            if (this.zzyh) {
                zzx += zzart.zzh(9, this.zzyh);
            }
            if (this.zzyg != null && this.zzyg.length > 0) {
                int i3 = 0;
                for (int zzagz : this.zzyg) {
                    i3 += zzart.zzagz(zzagz);
                }
                zzx = (zzx + i3) + (this.zzyg.length * 1);
            }
            if (this.zzyf != null && this.zzyf.length > 0) {
                while (i2 < this.zzyf.length) {
                    zzasa com_google_android_gms_internal_zzasa3 = this.zzyf[i2];
                    if (com_google_android_gms_internal_zzasa3 != null) {
                        zzx += zzart.zzc(11, com_google_android_gms_internal_zzasa3);
                    }
                    i2++;
                }
            }
            return this.zzye ? zzx + zzart.zzh(12, this.zzye) : zzx;
        }

        public zza zzx(zzars com_google_android_gms_internal_zzars) throws IOException {
            while (true) {
                int bU = com_google_android_gms_internal_zzars.bU();
                int zzc;
                Object obj;
                int i;
                switch (bU) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        break;
                    case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                        bU = com_google_android_gms_internal_zzars.bY();
                        switch (bU) {
                            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                                this.type = bU;
                                break;
                            default:
                                continue;
                        }
                    case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                        this.string = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.Toolbar_logoDescription /*26*/:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 26);
                        bU = this.zzxy == null ? 0 : this.zzxy.length;
                        obj = new zza[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.zzxy, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = new zza();
                            com_google_android_gms_internal_zzars.zza(obj[bU]);
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = new zza();
                        com_google_android_gms_internal_zzars.zza(obj[bU]);
                        this.zzxy = obj;
                        continue;
                    case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 34);
                        bU = this.zzxz == null ? 0 : this.zzxz.length;
                        obj = new zza[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.zzxz, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = new zza();
                            com_google_android_gms_internal_zzars.zza(obj[bU]);
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = new zza();
                        com_google_android_gms_internal_zzars.zza(obj[bU]);
                        this.zzxz = obj;
                        continue;
                    case C1569R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 42);
                        bU = this.zzya == null ? 0 : this.zzya.length;
                        obj = new zza[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.zzya, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = new zza();
                            com_google_android_gms_internal_zzars.zza(obj[bU]);
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = new zza();
                        com_google_android_gms_internal_zzars.zza(obj[bU]);
                        this.zzya = obj;
                        continue;
                    case C1569R.styleable.AppCompatTheme_actionButtonStyle /*50*/:
                        this.zzyb = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.AppCompatTheme_activityChooserViewStyle /*58*/:
                        this.zzyc = com_google_android_gms_internal_zzars.readString();
                        continue;
                    case C1569R.styleable.AppCompatTheme_editTextBackground /*64*/:
                        this.zzyd = com_google_android_gms_internal_zzars.bX();
                        continue;
                    case C1569R.styleable.AppCompatTheme_listPreferredItemHeightLarge /*72*/:
                        this.zzyh = com_google_android_gms_internal_zzars.ca();
                        continue;
                    case C1569R.styleable.AppCompatTheme_panelMenuListWidth /*80*/:
                        int zzc2 = zzasd.zzc(com_google_android_gms_internal_zzars, 80);
                        Object obj2 = new int[zzc2];
                        i = 0;
                        zzc = 0;
                        while (i < zzc2) {
                            if (i != 0) {
                                com_google_android_gms_internal_zzars.bU();
                            }
                            int bY = com_google_android_gms_internal_zzars.bY();
                            switch (bY) {
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
                                    bU = zzc + 1;
                                    obj2[zzc] = bY;
                                    break;
                                default:
                                    bU = zzc;
                                    break;
                            }
                            i++;
                            zzc = bU;
                        }
                        if (zzc != 0) {
                            bU = this.zzyg == null ? 0 : this.zzyg.length;
                            if (bU != 0 || zzc != obj2.length) {
                                Object obj3 = new int[(bU + zzc)];
                                if (bU != 0) {
                                    System.arraycopy(this.zzyg, 0, obj3, 0, bU);
                                }
                                System.arraycopy(obj2, 0, obj3, bU, zzc);
                                this.zzyg = obj3;
                                break;
                            }
                            this.zzyg = obj2;
                            break;
                        }
                        continue;
                    case C1569R.styleable.AppCompatTheme_listChoiceBackgroundIndicator /*82*/:
                        i = com_google_android_gms_internal_zzars.zzagt(com_google_android_gms_internal_zzars.cd());
                        zzc = com_google_android_gms_internal_zzars.getPosition();
                        bU = 0;
                        while (com_google_android_gms_internal_zzars.ci() > 0) {
                            switch (com_google_android_gms_internal_zzars.bY()) {
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
                                    bU++;
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (bU != 0) {
                            com_google_android_gms_internal_zzars.zzagv(zzc);
                            zzc = this.zzyg == null ? 0 : this.zzyg.length;
                            Object obj4 = new int[(bU + zzc)];
                            if (zzc != 0) {
                                System.arraycopy(this.zzyg, 0, obj4, 0, zzc);
                            }
                            while (com_google_android_gms_internal_zzars.ci() > 0) {
                                int bY2 = com_google_android_gms_internal_zzars.bY();
                                switch (bY2) {
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
                                        bU = zzc + 1;
                                        obj4[zzc] = bY2;
                                        zzc = bU;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            this.zzyg = obj4;
                        }
                        com_google_android_gms_internal_zzars.zzagu(i);
                        continue;
                    case C1569R.styleable.AppCompatTheme_colorSwitchThumbNormal /*90*/:
                        zzc = zzasd.zzc(com_google_android_gms_internal_zzars, 90);
                        bU = this.zzyf == null ? 0 : this.zzyf.length;
                        obj = new zza[(zzc + bU)];
                        if (bU != 0) {
                            System.arraycopy(this.zzyf, 0, obj, 0, bU);
                        }
                        while (bU < obj.length - 1) {
                            obj[bU] = new zza();
                            com_google_android_gms_internal_zzars.zza(obj[bU]);
                            com_google_android_gms_internal_zzars.bU();
                            bU++;
                        }
                        obj[bU] = new zza();
                        com_google_android_gms_internal_zzars.zza(obj[bU]);
                        this.zzyf = obj;
                        continue;
                    case C1569R.styleable.AppCompatTheme_alertDialogTheme /*96*/:
                        this.zzye = com_google_android_gms_internal_zzars.ca();
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
    }
}
