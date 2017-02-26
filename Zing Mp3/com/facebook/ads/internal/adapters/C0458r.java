package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.os.Bundle;
import com.facebook.ads.internal.server.AdPlacementType;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.ad;
import com.facebook.ads.p001a.C0390a;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.adapters.r */
public abstract class C0458r implements AdAdapter, ad<Bundle> {
    public abstract void m462a(Context context, C0390a c0390a, Map<String, Object> map, C0783h c0783h);

    public abstract boolean m463d();

    public AdPlacementType getPlacementType() {
        return AdPlacementType.INSTREAM;
    }
}
