package com.facebook.ads.internal.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.facebook.ads.internal.p005f.C0537f;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;

/* renamed from: com.facebook.ads.internal.util.g */
public class C0782g {

    /* renamed from: com.facebook.ads.internal.util.g.a */
    public interface C0468a {
        String m510B();

        C0781f m511D();

        String m512E();

        Collection<String> m513F();
    }

    public static Collection<String> m1608a(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        Set hashSet = new HashSet();
        for (int i = 0; i < jSONArray.length(); i++) {
            hashSet.add(jSONArray.optString(i));
        }
        return hashSet;
    }

    public static boolean m1609a(Context context, C0468a c0468a) {
        C0781f D = c0468a.m511D();
        if (D == null || D == C0781f.NONE) {
            return false;
        }
        Collection<String> F = c0468a.m513F();
        if (F == null || F.isEmpty()) {
            return false;
        }
        for (String a : F) {
            if (C0782g.m1610a(context, a)) {
                int i = 1;
                break;
            }
        }
        boolean z = false;
        if (D == C0781f.INSTALLED) {
            int i2 = 1;
        } else {
            boolean z2 = false;
        }
        if (i != i2) {
            return false;
        }
        CharSequence E = c0468a.m512E();
        Object B = c0468a.m510B();
        if (!TextUtils.isEmpty(B)) {
            C0537f.m878a(context).m889b(B, null);
            return true;
        } else if (TextUtils.isEmpty(E)) {
            return true;
        } else {
            new C0807y().execute(new String[]{E});
            return false;
        }
    }

    public static boolean m1610a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        } catch (RuntimeException e2) {
            return false;
        }
    }
}
