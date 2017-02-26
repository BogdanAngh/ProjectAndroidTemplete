package com.google.android.gms.ads.mediation;

import com.google.android.gms.ads.formats.NativeAd.Image;
import java.util.List;

public abstract class NativeContentAdMapper extends NativeAdMapper {
    private Image f1507R;
    private String zzbmy;
    private List<Image> zzbmz;
    private String zzbna;
    private String zzbnc;
    private String zzbnl;

    public final String getAdvertiser() {
        return this.zzbnl;
    }

    public final String getBody() {
        return this.zzbna;
    }

    public final String getCallToAction() {
        return this.zzbnc;
    }

    public final String getHeadline() {
        return this.zzbmy;
    }

    public final List<Image> getImages() {
        return this.zzbmz;
    }

    public final Image getLogo() {
        return this.f1507R;
    }

    public final void setAdvertiser(String str) {
        this.zzbnl = str;
    }

    public final void setBody(String str) {
        this.zzbna = str;
    }

    public final void setCallToAction(String str) {
        this.zzbnc = str;
    }

    public final void setHeadline(String str) {
        this.zzbmy = str;
    }

    public final void setImages(List<Image> list) {
        this.zzbmz = list;
    }

    public final void setLogo(Image image) {
        this.f1507R = image;
    }
}
