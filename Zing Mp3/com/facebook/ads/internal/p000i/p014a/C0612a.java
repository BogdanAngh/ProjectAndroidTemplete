package com.facebook.ads.internal.p000i.p014a;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.facebook.ads.internal.util.C0796r;
import com.facebook.ads.internal.util.C0798t;
import com.facebook.internal.NativeProtocol;
import com.google.android.exoplayer.extractor.ts.PsExtractor;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

@TargetApi(19)
/* renamed from: com.facebook.ads.internal.i.a.a */
public class C0612a extends LinearLayout {
    private static final int f879a;
    private static final Uri f880b;
    private static final OnTouchListener f881c;
    private static final int f882d;
    private ImageView f883e;
    private C0614c f884f;
    private ImageView f885g;
    private C0611a f886h;
    private String f887i;

    /* renamed from: com.facebook.ads.internal.i.a.a.1 */
    static class C06081 implements OnTouchListener {
        C06081() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    view.setBackgroundColor(C0612a.f882d);
                    break;
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    view.setBackgroundColor(0);
                    break;
            }
            return false;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.a.a.2 */
    class C06092 implements OnClickListener {
        final /* synthetic */ C0612a f877a;

        C06092(C0612a c0612a) {
            this.f877a = c0612a;
        }

        public void onClick(View view) {
            if (this.f877a.f886h != null) {
                this.f877a.f886h.m1137a();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.a.a.3 */
    class C06103 implements OnClickListener {
        final /* synthetic */ C0612a f878a;

        C06103(C0612a c0612a) {
            this.f878a = c0612a;
        }

        public void onClick(View view) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this.f878a.f887i));
            intent.addFlags(268435456);
            this.f878a.getContext().startActivity(intent);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.a.a.a */
    public interface C0611a {
        void m1137a();
    }

    static {
        f879a = Color.rgb(PsExtractor.VIDEO_STREAM, PsExtractor.VIDEO_STREAM, PsExtractor.VIDEO_STREAM);
        f880b = Uri.parse("http://www.facebook.com");
        f881c = new C06081();
        f882d = Color.argb(34, 0, 0, 0);
    }

    public C0612a(Context context) {
        super(context);
        m1140a(context);
    }

    private void m1140a(Context context) {
        float f = getResources().getDisplayMetrics().density;
        int i = (int) (50.0f * f);
        int i2 = (int) (f * 4.0f);
        setBackgroundColor(-1);
        setGravity(16);
        this.f883e = new ImageView(context);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i);
        this.f883e.setScaleType(ScaleType.CENTER);
        this.f883e.setImageBitmap(C0798t.m1671a(context, C0796r.BROWSER_CLOSE));
        this.f883e.setOnTouchListener(f881c);
        this.f883e.setOnClickListener(new C06092(this));
        addView(this.f883e, layoutParams);
        this.f884f = new C0614c(context);
        layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        this.f884f.setPadding(0, i2, 0, i2);
        addView(this.f884f, layoutParams);
        this.f885g = new ImageView(context);
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, i);
        this.f885g.setScaleType(ScaleType.CENTER);
        this.f885g.setOnTouchListener(f881c);
        this.f885g.setOnClickListener(new C06103(this));
        addView(this.f885g, layoutParams2);
        setupDefaultNativeBrowser(context);
    }

    private void setupDefaultNativeBrowser(Context context) {
        Bitmap bitmap;
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", f880b), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (queryIntentActivities.size() == 0) {
            this.f885g.setVisibility(8);
            bitmap = null;
        } else {
            bitmap = (queryIntentActivities.size() == 1 && "com.android.chrome".equals(((ResolveInfo) queryIntentActivities.get(0)).activityInfo.packageName)) ? C0798t.m1671a(context, C0796r.BROWSER_LAUNCH_CHROME) : C0798t.m1671a(context, C0796r.BROWSER_LAUNCH_NATIVE);
        }
        this.f885g.setImageBitmap(bitmap);
    }

    public void setListener(C0611a c0611a) {
        this.f886h = c0611a;
    }

    public void setTitle(String str) {
        this.f884f.setTitle(str);
    }

    public void setUrl(String str) {
        this.f887i = str;
        this.f884f.setSubtitle(str);
        if (TextUtils.isEmpty(str)) {
            this.f885g.setEnabled(false);
            this.f885g.setColorFilter(new PorterDuffColorFilter(f879a, Mode.SRC_IN));
            return;
        }
        this.f885g.setEnabled(true);
        this.f885g.setColorFilter(null);
    }
}
