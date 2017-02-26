package com.facebook.ads.internal.util;

import android.graphics.Rect;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import com.mp3download.zingmp3.C1569R;
import java.util.HashMap;
import java.util.Map;

public class ae {
    private static final String f1366a;
    private boolean f1367b;
    private long f1368c;
    private int f1369d;
    private long f1370e;
    private long f1371f;
    private int f1372g;
    private int f1373h;
    private int f1374i;
    private int f1375j;
    private int f1376k;
    private int f1377l;
    private float f1378m;
    private float f1379n;
    private float f1380o;

    static {
        f1366a = ae.class.getSimpleName();
    }

    public ae() {
        this.f1368c = -1;
        this.f1369d = -1;
        this.f1370e = -1;
        this.f1371f = -1;
        this.f1372g = -1;
        this.f1373h = -1;
        this.f1374i = -1;
        this.f1375j = -1;
        this.f1376k = -1;
        this.f1377l = -1;
        this.f1378m = -1.0f;
        this.f1379n = -1.0f;
        this.f1380o = -1.0f;
    }

    public void m1582a() {
        this.f1368c = System.currentTimeMillis();
    }

    public void m1583a(MotionEvent motionEvent, View view, View view2) {
        if (!this.f1367b) {
            this.f1367b = true;
            InputDevice device = motionEvent.getDevice();
            if (device != null) {
                this.f1380o = Math.min(device.getMotionRange(0).getRange(), device.getMotionRange(1).getRange());
            } else {
                this.f1380o = (float) Math.min(view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        view2.getLocationInWindow(iArr2);
        switch (motionEvent.getAction()) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                this.f1369d = 1;
                this.f1370e = System.currentTimeMillis();
                this.f1372g = (((int) (motionEvent.getX() + 0.5f)) + iArr2[0]) - iArr[0];
                this.f1373h = (iArr2[1] + ((int) (motionEvent.getY() + 0.5f))) - iArr[1];
                this.f1378m = motionEvent.getPressure();
                this.f1379n = motionEvent.getSize();
                this.f1376k = view.getWidth();
                this.f1377l = view.getHeight();
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                this.f1371f = System.currentTimeMillis();
                this.f1374i = (((int) (motionEvent.getX() + 0.5f)) + iArr2[0]) - iArr[0];
                this.f1375j = (iArr2[1] + ((int) (motionEvent.getY() + 0.5f))) - iArr[1];
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                this.f1378m -= this.f1378m / ((float) this.f1369d);
                this.f1378m += motionEvent.getPressure() / ((float) this.f1369d);
                this.f1379n -= this.f1379n / ((float) this.f1369d);
                this.f1379n += motionEvent.getSize() / ((float) this.f1369d);
                this.f1369d++;
            default:
        }
    }

    public boolean m1584a(int i) {
        if (!m1587d() || this.f1374i == -1 || this.f1375j == -1 || this.f1376k == -1 || this.f1377l == -1) {
            return false;
        }
        int i2 = (this.f1377l * i) / 100;
        int i3 = (this.f1376k * i) / 100;
        return !new Rect(i3, i2, this.f1376k - i3, this.f1377l - i2).contains(this.f1374i, this.f1375j);
    }

    public boolean m1585b() {
        return this.f1368c != -1;
    }

    public long m1586c() {
        return m1585b() ? System.currentTimeMillis() - this.f1368c : -1;
    }

    public boolean m1587d() {
        return this.f1367b;
    }

    public Map<String, String> m1588e() {
        if (!this.f1367b) {
            return null;
        }
        String valueOf = String.valueOf((this.f1379n * this.f1380o) / 2.0f);
        long j = (this.f1368c <= 0 || this.f1371f <= this.f1368c) ? -1 : this.f1371f - this.f1368c;
        Map<String, String> hashMap = new HashMap(11);
        hashMap.put("clickDelayTime", String.valueOf(j));
        hashMap.put("startTime", String.valueOf(this.f1370e));
        hashMap.put("endTime", String.valueOf(this.f1371f));
        hashMap.put("startX", String.valueOf(this.f1372g));
        hashMap.put("startY", String.valueOf(this.f1373h));
        hashMap.put("clickX", String.valueOf(this.f1374i));
        hashMap.put("clickY", String.valueOf(this.f1375j));
        hashMap.put("endX", String.valueOf(this.f1374i));
        hashMap.put("endY", String.valueOf(this.f1375j));
        hashMap.put("force", String.valueOf(this.f1378m));
        hashMap.put("radiusX", valueOf);
        hashMap.put("radiusY", valueOf);
        hashMap.put("touchedViewWidth", String.valueOf(this.f1376k));
        hashMap.put("touchedViewHeight", String.valueOf(this.f1377l));
        return hashMap;
    }
}
