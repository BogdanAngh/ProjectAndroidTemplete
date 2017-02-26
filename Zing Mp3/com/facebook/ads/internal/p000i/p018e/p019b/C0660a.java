package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
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
import com.facebook.ads.C0411R;
import com.facebook.ads.internal.util.C0785i;

/* renamed from: com.facebook.ads.internal.i.e.b.a */
public class C0660a extends C0638m {
    @NonNull
    private final C0659a f976b;

    /* renamed from: com.facebook.ads.internal.i.e.b.a.a */
    private static class C0659a extends RelativeLayout {
        private final String f969a;
        private final String f970b;
        private final String f971c;
        private final DisplayMetrics f972d;
        private ImageView f973e;
        private TextView f974f;
        private boolean f975g;

        /* renamed from: com.facebook.ads.internal.i.e.b.a.a.1 */
        class C06541 implements OnTouchListener {
            final /* synthetic */ C0659a f960a;

            C06541(C0659a c0659a) {
                this.f960a = c0659a;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                if (!this.f960a.f975g) {
                    this.f960a.m1218d();
                } else if (!TextUtils.isEmpty(this.f960a.f970b)) {
                    C0785i.m1627a(this.f960a.getContext(), Uri.parse(this.f960a.f970b), this.f960a.f971c);
                }
                return true;
            }
        }

        /* renamed from: com.facebook.ads.internal.i.e.b.a.a.2 */
        class C06552 extends Animation {
            final /* synthetic */ int f961a;
            final /* synthetic */ int f962b;
            final /* synthetic */ C0659a f963c;

            C06552(C0659a c0659a, int i, int i2) {
                this.f963c = c0659a;
                this.f961a = i;
                this.f962b = i2;
            }

            protected void applyTransformation(float f, Transformation transformation) {
                int i = (int) (((float) this.f961a) + (((float) (this.f962b - this.f961a)) * f));
                this.f963c.getLayoutParams().width = i;
                this.f963c.requestLayout();
                this.f963c.f974f.getLayoutParams().width = i - this.f961a;
                this.f963c.f974f.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        }

        /* renamed from: com.facebook.ads.internal.i.e.b.a.a.3 */
        class C06583 implements AnimationListener {
            final /* synthetic */ int f966a;
            final /* synthetic */ int f967b;
            final /* synthetic */ C0659a f968c;

            /* renamed from: com.facebook.ads.internal.i.e.b.a.a.3.1 */
            class C06571 implements Runnable {
                final /* synthetic */ C06583 f965a;

                /* renamed from: com.facebook.ads.internal.i.e.b.a.a.3.1.1 */
                class C06561 extends Animation {
                    final /* synthetic */ C06571 f964a;

                    C06561(C06571 c06571) {
                        this.f964a = c06571;
                    }

                    protected void applyTransformation(float f, Transformation transformation) {
                        int i = (int) (((float) this.f964a.f965a.f966a) + (((float) (this.f964a.f965a.f967b - this.f964a.f965a.f966a)) * f));
                        this.f964a.f965a.f968c.getLayoutParams().width = i;
                        this.f964a.f965a.f968c.requestLayout();
                        this.f964a.f965a.f968c.f974f.getLayoutParams().width = i - this.f964a.f965a.f967b;
                        this.f964a.f965a.f968c.f974f.requestLayout();
                    }

                    public boolean willChangeBounds() {
                        return true;
                    }
                }

                C06571(C06583 c06583) {
                    this.f965a = c06583;
                }

                public void run() {
                    if (this.f965a.f968c.f975g) {
                        this.f965a.f968c.f975g = false;
                        Animation c06561 = new C06561(this);
                        c06561.setDuration(300);
                        c06561.setFillAfter(true);
                        this.f965a.f968c.startAnimation(c06561);
                    }
                }
            }

            C06583(C0659a c0659a, int i, int i2) {
                this.f968c = c0659a;
                this.f966a = i;
                this.f967b = i2;
            }

            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new C06571(this), 3000);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        }

        public C0659a(Context context, String str, String str2, String str3) {
            super(context);
            this.f975g = false;
            this.f969a = str;
            this.f970b = str2;
            this.f971c = str3;
            this.f972d = context.getResources().getDisplayMetrics();
            m1211a();
            m1215b();
            m1217c();
        }

        private void m1211a() {
            setOnTouchListener(new C06541(this));
        }

        private void m1215b() {
            this.f973e = new ImageView(getContext());
            this.f973e.setImageResource(C0411R.mipmap.ic_ad_choices);
            addView(this.f973e);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(Math.round(this.f972d.density * 16.0f), Math.round(this.f972d.density * 16.0f));
            layoutParams.addRule(9);
            layoutParams.addRule(15, -1);
            layoutParams.setMargins(Math.round(4.0f * this.f972d.density), Math.round(this.f972d.density * 2.0f), Math.round(this.f972d.density * 2.0f), Math.round(this.f972d.density * 2.0f));
            this.f973e.setLayoutParams(layoutParams);
        }

        private void m1217c() {
            this.f974f = new TextView(getContext());
            addView(this.f974f);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.width = 0;
            layoutParams.addRule(11, this.f973e.getId());
            layoutParams.addRule(15, -1);
            this.f974f.setLayoutParams(layoutParams);
            this.f974f.setSingleLine();
            this.f974f.setText(this.f969a);
            this.f974f.setTextSize(10.0f);
            this.f974f.setTextColor(-4341303);
        }

        private void m1218d() {
            Paint paint = new Paint();
            paint.setTextSize(this.f974f.getTextSize());
            int round = Math.round(paint.measureText(this.f969a) + (4.0f * this.f972d.density));
            int width = getWidth();
            round += width;
            this.f975g = true;
            Animation c06552 = new C06552(this, width, round);
            c06552.setAnimationListener(new C06583(this, round, width));
            c06552.setDuration(300);
            c06552.setFillAfter(true);
            startAnimation(c06552);
        }
    }

    public C0660a(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull float[] fArr) {
        super(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.f976b = new C0659a(context, "AdChoices", str, str2);
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ViewCompat.MEASURED_STATE_MASK);
        gradientDrawable.setAlpha(178);
        gradientDrawable.setCornerRadii(new float[]{fArr[0] * displayMetrics.density, fArr[0] * displayMetrics.density, fArr[1] * displayMetrics.density, fArr[1] * displayMetrics.density, fArr[2] * displayMetrics.density, fArr[2] * displayMetrics.density, fArr[3] * displayMetrics.density, fArr[3] * displayMetrics.density});
        if (VERSION.SDK_INT >= 16) {
            this.f976b.setBackground(gradientDrawable);
        } else {
            this.f976b.setBackgroundDrawable(gradientDrawable);
        }
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = Math.round(20.0f * displayMetrics.density);
        layoutParams.height = Math.round(displayMetrics.density * 18.0f);
        addView(this.f976b, layoutParams);
    }
}
