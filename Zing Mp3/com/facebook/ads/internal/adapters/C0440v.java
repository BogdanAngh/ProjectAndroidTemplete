package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.view.View;
import com.facebook.ads.AdNetwork;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAd.Image;
import com.facebook.ads.NativeAd.Rating;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.ah;
import java.util.List;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.adapters.v */
public abstract class C0440v implements AdAdapter {
    public abstract List<NativeAd> m341A();

    public abstract String m342B();

    public abstract AdNetwork m343C();

    public abstract void m344a();

    public abstract void m345a(int i);

    public abstract void m346a(Context context, C0415w c0415w, Map<String, Object> map);

    public abstract void m347a(View view, List<View> list);

    public abstract void m348a(Map<String, String> map);

    public abstract void m349b(Map<String, String> map);

    public abstract boolean m350b();

    public abstract boolean m351c();

    public abstract boolean m352d();

    public abstract boolean m353e();

    public abstract boolean m354f();

    public abstract boolean m355g();

    public final AdPlacementType getPlacementType() {
        return AdPlacementType.NATIVE;
    }

    public abstract int m356h();

    public abstract int m357i();

    public abstract int m358j();

    public abstract Image m359k();

    public abstract Image m360l();

    public abstract NativeAdViewAttributes m361m();

    public abstract String m362n();

    public abstract String m363o();

    public abstract String m364p();

    public abstract String m365q();

    public abstract String m366r();

    public abstract Rating m367s();

    public abstract Image m368t();

    public abstract String m369u();

    public abstract String m370v();

    public abstract String m371w();

    public abstract String m372x();

    public abstract ah m373y();

    public abstract String m374z();
}
