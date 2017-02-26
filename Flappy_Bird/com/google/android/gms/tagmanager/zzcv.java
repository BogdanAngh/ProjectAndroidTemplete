package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

class zzcv {

    /* renamed from: com.google.android.gms.tagmanager.zzcv.1 */
    static class C02981 implements Runnable {
        final /* synthetic */ Editor zzaNQ;

        C02981(Editor editor) {
            this.zzaNQ = editor;
        }

        public void run() {
            this.zzaNQ.commit();
        }
    }

    static void zza(Context context, String str, String str2, String str3) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        zza(edit);
    }

    static void zza(Editor editor) {
        if (VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            new Thread(new C02981(editor)).start();
        }
    }
}
