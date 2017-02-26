package com.facebook.ads.internal.p000i.p018e.p021c;

import android.net.Uri;
import android.view.View;

/* renamed from: com.facebook.ads.internal.i.e.c.e */
public interface C0702e {
    void m1293a();

    void m1294a(boolean z);

    void m1295b();

    int getCurrentPosition();

    int getDuration();

    long getInitialBufferTime();

    C0708f getState();

    C0708f getTargetState();

    View getView();

    float getVolume();

    void pause();

    void seekTo(int i);

    void setControlsAnchorView(View view);

    void setFullScreen(boolean z);

    void setRequestedVolume(float f);

    void setVideoMPD(String str);

    void setVideoStateChangeListener(C0709g c0709g);

    void setup(Uri uri);

    void start();
}
