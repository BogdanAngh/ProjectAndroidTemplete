package com.facebook.ads;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.ads.NativeAd.Image;
import com.facebook.ads.internal.util.C0785i;

public class AdChoicesView extends RelativeLayout {
    private final Context f21a;
    private final NativeAd f22b;
    private final DisplayMetrics f23c;
    private boolean f24d;
    private TextView f25e;
    private String f26f;

    /* renamed from: com.facebook.ads.AdChoicesView.1 */
    class C03711 implements OnTouchListener {
        final /* synthetic */ NativeAd f11a;
        final /* synthetic */ AdChoicesView f12b;

        C03711(AdChoicesView adChoicesView, NativeAd nativeAd) {
            this.f12b = adChoicesView;
            this.f11a = nativeAd;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!this.f12b.f24d) {
                this.f12b.m16a();
            } else if (!TextUtils.isEmpty(this.f12b.f22b.getAdChoicesLinkUrl())) {
                C0785i.m1627a(this.f12b.f21a, Uri.parse(this.f12b.f22b.getAdChoicesLinkUrl()), this.f11a.m199h());
            }
            return true;
        }
    }

    /* renamed from: com.facebook.ads.AdChoicesView.2 */
    class C03722 extends Animation {
        final /* synthetic */ int f13a;
        final /* synthetic */ int f14b;
        final /* synthetic */ AdChoicesView f15c;

        C03722(AdChoicesView adChoicesView, int i, int i2) {
            this.f15c = adChoicesView;
            this.f13a = i;
            this.f14b = i2;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            int i = (int) (((float) this.f13a) + (((float) (this.f14b - this.f13a)) * f));
            this.f15c.getLayoutParams().width = i;
            this.f15c.requestLayout();
            this.f15c.f25e.getLayoutParams().width = i - this.f13a;
            this.f15c.f25e.requestLayout();
        }

        public boolean willChangeBounds() {
            return true;
        }
    }

    /* renamed from: com.facebook.ads.AdChoicesView.3 */
    class C03753 implements AnimationListener {
        final /* synthetic */ int f18a;
        final /* synthetic */ int f19b;
        final /* synthetic */ AdChoicesView f20c;

        /* renamed from: com.facebook.ads.AdChoicesView.3.1 */
        class C03741 implements Runnable {
            final /* synthetic */ C03753 f17a;

            /* renamed from: com.facebook.ads.AdChoicesView.3.1.1 */
            class C03731 extends Animation {
                final /* synthetic */ C03741 f16a;

                C03731(C03741 c03741) {
                    this.f16a = c03741;
                }

                protected void applyTransformation(float f, Transformation transformation) {
                    int i = (int) (((float) this.f16a.f17a.f18a) + (((float) (this.f16a.f17a.f19b - this.f16a.f17a.f18a)) * f));
                    this.f16a.f17a.f20c.getLayoutParams().width = i;
                    this.f16a.f17a.f20c.requestLayout();
                    this.f16a.f17a.f20c.f25e.getLayoutParams().width = i - this.f16a.f17a.f19b;
                    this.f16a.f17a.f20c.f25e.requestLayout();
                }

                public boolean willChangeBounds() {
                    return true;
                }
            }

            C03741(C03753 c03753) {
                this.f17a = c03753;
            }

            public void run() {
                if (this.f17a.f20c.f24d) {
                    this.f17a.f20c.f24d = false;
                    Animation c03731 = new C03731(this);
                    c03731.setDuration(300);
                    c03731.setFillAfter(true);
                    this.f17a.f20c.startAnimation(c03731);
                }
            }
        }

        C03753(AdChoicesView adChoicesView, int i, int i2) {
            this.f20c = adChoicesView;
            this.f18a = i;
            this.f19b = i2;
        }

        public void onAnimationEnd(Animation animation) {
            new Handler().postDelayed(new C03741(this), 3000);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    public AdChoicesView(Context context, NativeAd nativeAd) {
        this(context, nativeAd, false);
    }

    public AdChoicesView(Context context, NativeAd nativeAd, boolean z) {
        super(context);
        this.f24d = false;
        this.f21a = context;
        this.f22b = nativeAd;
        this.f23c = this.f21a.getResources().getDisplayMetrics();
        if (!this.f22b.isAdLoaded() || this.f22b.m189a().m355g()) {
            this.f26f = this.f22b.m192b();
            if (TextUtils.isEmpty(this.f26f)) {
                this.f26f = "AdChoices";
            }
            Image adChoicesIcon = this.f22b.getAdChoicesIcon();
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            setOnTouchListener(new C03711(this, nativeAd));
            this.f25e = new TextView(this.f21a);
            addView(this.f25e);
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            if (!z || adChoicesIcon == null) {
                this.f24d = true;
            } else {
                layoutParams2.addRule(11, m15a(adChoicesIcon).getId());
                layoutParams2.width = 0;
                layoutParams.width = Math.round(((float) (adChoicesIcon.getWidth() + 4)) * this.f23c.density);
                layoutParams.height = Math.round(((float) (adChoicesIcon.getHeight() + 2)) * this.f23c.density);
                this.f24d = false;
            }
            setLayoutParams(layoutParams);
            layoutParams2.addRule(15, -1);
            this.f25e.setLayoutParams(layoutParams2);
            this.f25e.setSingleLine();
            this.f25e.setText(this.f26f);
            this.f25e.setTextSize(10.0f);
            this.f25e.setTextColor(-4341303);
            return;
        }
        setVisibility(8);
    }

    private ImageView m15a(Image image) {
        View imageView = new ImageView(this.f21a);
        addView(imageView);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(Math.round(((float) image.getWidth()) * this.f23c.density), Math.round(((float) image.getHeight()) * this.f23c.density));
        layoutParams.addRule(9);
        layoutParams.addRule(15, -1);
        layoutParams.setMargins(Math.round(4.0f * this.f23c.density), Math.round(this.f23c.density * 2.0f), Math.round(this.f23c.density * 2.0f), Math.round(this.f23c.density * 2.0f));
        imageView.setLayoutParams(layoutParams);
        NativeAd.downloadAndDisplayImage(image, imageView);
        return imageView;
    }

    private void m16a() {
        Paint paint = new Paint();
        paint.setTextSize(this.f25e.getTextSize());
        int round = Math.round(paint.measureText(this.f26f) + (4.0f * this.f23c.density));
        int width = getWidth();
        round += width;
        this.f24d = true;
        Animation c03722 = new C03722(this, width, round);
        c03722.setAnimationListener(new C03753(this, round, width));
        c03722.setDuration(300);
        c03722.setFillAfter(true);
        startAnimation(c03722);
    }
}
