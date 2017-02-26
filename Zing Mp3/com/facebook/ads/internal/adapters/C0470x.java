package com.facebook.ads.internal.adapters;

import android.content.Context;
import com.facebook.ads.RewardData;
import com.facebook.ads.internal.server.AdPlacementType;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.adapters.x */
public abstract class C0470x implements AdAdapter {
    protected RewardData f447a;

    public abstract String m560a();

    public abstract void m561a(Context context, C0421y c0421y, Map<String, Object> map);

    public void m562a(RewardData rewardData) {
        this.f447a = rewardData;
    }

    public abstract boolean m563b();

    public AdPlacementType getPlacementType() {
        return AdPlacementType.REWARDED_VIDEO;
    }
}
