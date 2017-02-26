package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

@zzji
class zzb extends RelativeLayout {
    private static final float[] zzbmt;
    private final RelativeLayout zzbmu;
    @Nullable
    private AnimationDrawable zzbmv;

    static {
        zzbmt = new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
    }

    public zzb(Context context, zza com_google_android_gms_ads_internal_formats_zza) {
        super(context);
        zzaa.zzy(com_google_android_gms_ads_internal_formats_zza);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        switch (com_google_android_gms_ads_internal_formats_zza.zzml()) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                layoutParams.addRule(10);
                layoutParams.addRule(9);
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                layoutParams.addRule(12);
                layoutParams.addRule(11);
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                layoutParams.addRule(12);
                layoutParams.addRule(9);
                break;
            default:
                layoutParams.addRule(10);
                layoutParams.addRule(11);
                break;
        }
        Drawable shapeDrawable = new ShapeDrawable(new RoundRectShape(zzbmt, null, null));
        shapeDrawable.getPaint().setColor(com_google_android_gms_ads_internal_formats_zza.getBackgroundColor());
        this.zzbmu = new RelativeLayout(context);
        this.zzbmu.setLayoutParams(layoutParams);
        zzu.zzgo().zza(this.zzbmu, shapeDrawable);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        if (!TextUtils.isEmpty(com_google_android_gms_ads_internal_formats_zza.getText())) {
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            View textView = new TextView(context);
            textView.setLayoutParams(layoutParams2);
            textView.setId(1195835393);
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText(com_google_android_gms_ads_internal_formats_zza.getText());
            textView.setTextColor(com_google_android_gms_ads_internal_formats_zza.getTextColor());
            textView.setTextSize((float) com_google_android_gms_ads_internal_formats_zza.getTextSize());
            textView.setPadding(zzm.zzkr().zzb(context, 4), 0, zzm.zzkr().zzb(context, 4), 0);
            this.zzbmu.addView(textView);
            layoutParams.addRule(1, textView.getId());
        }
        View imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setId(1195835394);
        List<Drawable> zzmj = com_google_android_gms_ads_internal_formats_zza.zzmj();
        if (zzmj.size() > 1) {
            this.zzbmv = new AnimationDrawable();
            for (Drawable addFrame : zzmj) {
                this.zzbmv.addFrame(addFrame, com_google_android_gms_ads_internal_formats_zza.zzmk());
            }
            zzu.zzgo().zza(imageView, this.zzbmv);
        } else if (zzmj.size() == 1) {
            imageView.setImageDrawable((Drawable) zzmj.get(0));
        }
        this.zzbmu.addView(imageView);
        addView(this.zzbmu);
    }

    public void onAttachedToWindow() {
        if (this.zzbmv != null) {
            this.zzbmv.start();
        }
        super.onAttachedToWindow();
    }

    public ViewGroup zzmm() {
        return this.zzbmu;
    }
}
