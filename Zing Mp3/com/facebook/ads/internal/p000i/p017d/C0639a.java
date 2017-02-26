package com.facebook.ads.internal.p000i.p017d;

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
import android.widget.TextView;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0640a;
import com.facebook.ads.internal.p004a.C0429a;
import com.facebook.ads.internal.p004a.C0430b;
import com.mp3download.zingmp3.BuildConfig;
import java.util.HashMap;

/* renamed from: com.facebook.ads.internal.i.d.a */
public class C0639a extends C0638m {
    @NonNull
    private final String f951b;
    @NonNull
    private final Paint f952c;
    @NonNull
    private final RectF f953d;
    @NonNull
    private final Paint f954e;

    /* renamed from: com.facebook.ads.internal.i.d.a.1 */
    class C06371 implements OnClickListener {
        final /* synthetic */ C0726m f947a;
        final /* synthetic */ C0639a f948b;

        C06371(C0639a c0639a, C0726m c0726m) {
            this.f948b = c0639a;
            this.f947a = c0726m;
        }

        public void onClick(View view) {
            try {
                Uri parse = Uri.parse(this.f948b.f951b);
                this.f947a.getEventBus().m914a(new C0640a(parse));
                C0429a a = C0430b.m321a(this.f948b.getContext(), BuildConfig.FLAVOR, parse, new HashMap());
                if (a != null) {
                    a.m320b();
                }
            } catch (Throwable e) {
                Log.e(String.valueOf(C0639a.class), "Error while opening " + this.f948b.f951b, e);
            } catch (Throwable e2) {
                Log.e(String.valueOf(C0639a.class), "Error executing action", e2);
            }
        }
    }

    public C0639a(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        super(context);
        this.f951b = str;
        View textView = new TextView(context);
        textView.setTextColor(-3355444);
        textView.setTextSize(16.0f);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setPadding((int) (displayMetrics.density * 10.0f), (int) (displayMetrics.density * 9.0f), (int) (displayMetrics.density * 10.0f), (int) (displayMetrics.density * 9.0f));
        textView.setText(str2);
        addView(textView);
        this.f952c = new Paint();
        this.f952c.setStyle(Style.FILL);
        this.f952c.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f952c.setAlpha(178);
        this.f953d = new RectF();
        this.f954e = new Paint();
        this.f954e.setStyle(Style.STROKE);
        this.f954e.setColor(-6710887);
        this.f954e.setStrokeWidth(displayMetrics.density * 1.0f);
        this.f954e.setAntiAlias(true);
        setBackgroundColor(0);
    }

    protected void m1202a(@NonNull C0726m c0726m) {
        setOnClickListener(new C06371(this, c0726m));
    }

    protected void onDraw(Canvas canvas) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.f953d.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        canvas.drawRoundRect(this.f953d, 0.0f, 0.0f, this.f952c);
        this.f953d.set(2.0f, 2.0f, (float) (getWidth() - 2), (float) (getHeight() - 2));
        canvas.drawRoundRect(this.f953d, 6.0f * f, f * 6.0f, this.f954e);
        super.onDraw(canvas);
    }
}
