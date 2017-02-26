package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.C1044R;
import com.mp3download.zingmp3.C1569R;

public final class zzaf extends Button {
    public zzaf(Context context) {
        this(context, null);
    }

    public zzaf(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private void zza(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
    }

    private void zzb(Resources resources, int i, int i2) {
        setBackgroundDrawable(resources.getDrawable(zze(i, zzg(i2, C1044R.drawable.common_google_signin_btn_icon_dark, C1044R.drawable.common_google_signin_btn_icon_light, C1044R.drawable.common_google_signin_btn_icon_light), zzg(i2, C1044R.drawable.common_google_signin_btn_text_dark, C1044R.drawable.common_google_signin_btn_text_light, C1044R.drawable.common_google_signin_btn_text_light))));
    }

    private void zzc(Resources resources, int i, int i2) {
        setTextColor((ColorStateList) zzaa.zzy(resources.getColorStateList(zzg(i2, C1044R.color.common_google_signin_btn_text_dark, C1044R.color.common_google_signin_btn_text_light, C1044R.color.common_google_signin_btn_text_light))));
        switch (i) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                setText(resources.getString(C1044R.string.common_signin_button_text));
                break;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                setText(resources.getString(C1044R.string.common_signin_button_text_long));
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                setText(null);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        setTransformationMethod(null);
    }

    private int zze(int i, int i2, int i3) {
        switch (i) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return i3;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return i2;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
    }

    private int zzg(int i, int i2, int i3, int i4) {
        switch (i) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return i2;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return i3;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return i4;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    public void zza(Resources resources, int i, int i2) {
        zza(resources);
        zzb(resources, i, i2);
        zzc(resources, i, i2);
    }
}
