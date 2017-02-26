package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.C1044R;

public class zzah {
    private final Resources EP;
    private final String EQ;

    public zzah(Context context) {
        zzaa.zzy(context);
        this.EP = context.getResources();
        this.EQ = this.EP.getResourcePackageName(C1044R.string.common_google_play_services_unknown_issue);
    }

    public String getString(String str) {
        int identifier = this.EP.getIdentifier(str, "string", this.EQ);
        return identifier == 0 ? null : this.EP.getString(identifier);
    }
}
