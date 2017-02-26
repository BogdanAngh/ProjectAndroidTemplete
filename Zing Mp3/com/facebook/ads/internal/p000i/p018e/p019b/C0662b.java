package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0649j;
import com.google.android.exoplayer.util.MimeTypes;

/* renamed from: com.facebook.ads.internal.i.e.b.b */
public class C0662b extends C0638m implements OnAudioFocusChangeListener {
    @Nullable
    private AudioManager f978b;
    @NonNull
    private final C0649j f979c;

    /* renamed from: com.facebook.ads.internal.i.e.b.b.1 */
    class C06611 extends C0649j {
        final /* synthetic */ C0662b f977a;

        C06611(C0662b c0662b) {
            this.f977a = c0662b;
        }

        public void m1222a(C0648i c0648i) {
            if (this.f977a.f978b == null) {
                this.f977a.f978b = (AudioManager) this.f977a.getContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
                this.f977a.f978b.requestAudioFocus(this.f977a, 3, 1);
            }
        }
    }

    public C0662b(Context context) {
        super(context);
        this.f979c = new C06611(this);
    }

    protected void m1225a(@NonNull C0726m c0726m) {
        c0726m.getEventBus().m915a(this.f979c);
        super.m1199a(c0726m);
    }

    protected void finalize() {
        this.f978b.abandonAudioFocus(this);
        this.f978b = null;
        super.finalize();
    }

    public void onAudioFocusChange(int i) {
        if (getVideoView() != null && i <= 0) {
            getVideoView().m1396e();
        }
    }
}
