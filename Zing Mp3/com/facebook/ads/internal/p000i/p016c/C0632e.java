package com.facebook.ads.internal.p000i.p016c;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* renamed from: com.facebook.ads.internal.i.c.e */
public class C0632e extends RecyclerView implements OnTouchListener {
    protected int f931a;
    protected int f932b;
    private int f933c;
    private boolean f934d;
    private boolean f935e;
    private LinearLayoutManager f936f;
    private C0633a f937g;

    /* renamed from: com.facebook.ads.internal.i.c.e.a */
    public interface C0633a {
        int m1186a(int i);
    }

    public C0632e(Context context) {
        super(context);
        this.f931a = 0;
        this.f933c = 0;
        this.f934d = true;
        this.f935e = false;
        m1183a(context);
    }

    private int m1181a(int i) {
        int i2 = this.f933c - i;
        int a = this.f937g.m1186a(i2);
        return i2 > this.f932b ? m1182a(this.f931a, a) : i2 < (-this.f932b) ? m1184b(this.f931a, a) : this.f931a;
    }

    private int m1182a(int i, int i2) {
        return Math.min(i + i2, getItemCount() - 1);
    }

    private void m1183a(Context context) {
        setOnTouchListener(this);
        this.f932b = ((int) context.getResources().getDisplayMetrics().density) * 10;
    }

    private int m1184b(int i, int i2) {
        return Math.max(i - i2, 0);
    }

    private int getItemCount() {
        return getAdapter() == null ? 0 : getAdapter().getItemCount();
    }

    protected void m1185a(int i, boolean z) {
        if (getAdapter() != null) {
            this.f931a = i;
            if (z) {
                smoothScrollToPosition(i);
            } else {
                scrollToPosition(i);
            }
        }
    }

    public int getCurrentPosition() {
        return this.f931a;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int rawX = (int) motionEvent.getRawX();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 6 || actionMasked == 3 || actionMasked == 4) {
            if (this.f935e) {
                m1185a(m1181a(rawX), true);
            }
            this.f934d = true;
            this.f935e = false;
            return true;
        } else if (actionMasked != 0 && actionMasked != 5 && (!this.f934d || actionMasked != 2)) {
            return false;
        } else {
            this.f933c = rawX;
            if (this.f934d) {
                this.f934d = false;
            }
            this.f935e = true;
            return false;
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            super.setLayoutManager(layoutManager);
            this.f936f = (LinearLayoutManager) layoutManager;
            return;
        }
        throw new IllegalArgumentException("SnapRecyclerView only supports LinearLayoutManager");
    }

    public void setSnapDelegate(C0633a c0633a) {
        this.f937g = c0633a;
    }
}
