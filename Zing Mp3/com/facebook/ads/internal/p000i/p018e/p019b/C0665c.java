package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0650k;
import com.facebook.ads.internal.p005f.C0453p;
import com.google.android.exoplayer.hls.HlsChunkSource;
import java.util.concurrent.TimeUnit;

/* renamed from: com.facebook.ads.internal.i.e.b.c */
public class C0665c extends C0664d {
    @NonNull
    private final TextView f987b;
    @NonNull
    private final String f988c;
    @NonNull
    private final C0453p<C0650k> f989d;

    /* renamed from: com.facebook.ads.internal.i.e.b.c.1 */
    class C06631 extends C0453p<C0650k> {
        final /* synthetic */ C0665c f980a;

        C06631(C0665c c0665c) {
            this.f980a = c0665c;
        }

        public Class<C0650k> m1226a() {
            return C0650k.class;
        }

        public void m1228a(C0650k c0650k) {
            this.f980a.f987b.setText(this.f980a.m1233a((long) (this.f980a.getVideoView().getDuration() - this.f980a.getVideoView().getCurrentPosition())));
        }
    }

    public C0665c(@NonNull Context context, @NonNull String str) {
        this(context, str, false);
    }

    public C0665c(@NonNull Context context, @NonNull String str, @NonNull boolean z) {
        super(context, z);
        this.f989d = new C06631(this);
        this.f987b = new TextView(context);
        this.f988c = str;
        addView(this.f987b);
    }

    private String m1233a(long j) {
        if (j <= 0) {
            return "00:00";
        }
        long toMinutes = TimeUnit.MILLISECONDS.toMinutes(j);
        long toSeconds = TimeUnit.MILLISECONDS.toSeconds(j % HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS);
        if (this.f988c.isEmpty()) {
            return String.format("%02d:%02d", new Object[]{Long.valueOf(toMinutes), Long.valueOf(toSeconds)});
        }
        return this.f988c.replace("{{REMAINING_TIME}}", String.format("%02d:%02d", new Object[]{Long.valueOf(toMinutes), Long.valueOf(toSeconds)}));
    }

    protected void m1235a(C0726m c0726m) {
        c0726m.getEventBus().m915a(this.f989d);
        super.m1231a(c0726m);
    }
}
