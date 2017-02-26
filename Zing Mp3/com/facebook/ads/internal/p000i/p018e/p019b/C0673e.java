package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.ads.C0411R;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0640a;
import com.facebook.ads.internal.util.C0785i;

/* renamed from: com.facebook.ads.internal.i.e.b.e */
public class C0673e extends C0638m {
    @NonNull
    private final Context f998b;
    @NonNull
    private final String f999c;
    @NonNull
    private final TextView f1000d;
    @NonNull
    private final String f1001e;
    @NonNull
    private final Paint f1002f;
    @NonNull
    private final RectF f1003g;

    /* renamed from: com.facebook.ads.internal.i.e.b.e.1 */
    class C06721 implements OnClickListener {
        final /* synthetic */ C0726m f996a;
        final /* synthetic */ C0673e f997b;

        C06721(C0673e c0673e, C0726m c0726m) {
            this.f997b = c0673e;
            this.f996a = c0726m;
        }

        public void onClick(View view) {
            try {
                this.f996a.getEventBus().m914a(new C0640a(Uri.parse(this.f997b.f999c)));
                C0785i.m1627a(this.f997b.f998b, Uri.parse(this.f997b.f999c), this.f997b.f1001e);
            } catch (Throwable e) {
                Log.e("LearnMorePlugin", "Error while opening " + this.f997b.f999c, e);
            }
        }
    }

    public C0673e(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        super(context);
        this.f998b = context;
        this.f999c = str;
        this.f1001e = str2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.f1000d = new TextView(getContext());
        this.f1000d.setTextColor(-3355444);
        this.f1000d.setTextSize(16.0f);
        this.f1000d.setPadding((int) (displayMetrics.density * 6.0f), (int) (displayMetrics.density * 4.0f), (int) (displayMetrics.density * 6.0f), (int) (displayMetrics.density * 4.0f));
        this.f1002f = new Paint();
        this.f1002f.setStyle(Style.FILL);
        this.f1002f.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1002f.setAlpha(178);
        this.f1003g = new RectF();
        setBackgroundColor(0);
        this.f1000d.setText(getResources().getString(C0411R.string.com_facebook_ads_learn_more));
        addView(this.f1000d, new LayoutParams(-2, -2));
    }

    protected void m1248a(C0726m c0726m) {
        this.f1000d.setOnClickListener(new C06721(this, c0726m));
    }

    protected void onDraw(Canvas canvas) {
        this.f1003g.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        canvas.drawRoundRect(this.f1003g, 0.0f, 0.0f, this.f1002f);
        super.onDraw(canvas);
    }
}
