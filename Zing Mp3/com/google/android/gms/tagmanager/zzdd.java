package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

class zzdd {

    /* renamed from: com.google.android.gms.tagmanager.zzdd.1 */
    class C15451 implements Runnable {
        final /* synthetic */ Editor aHz;

        C15451(Editor editor) {
            this.aHz = editor;
        }

        public void run() {
            this.aHz.commit();
        }
    }

    static void zza(Editor editor) {
        if (VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            new Thread(new C15451(editor)).start();
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    static void zzc(Context context, String str, String str2, String str3) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        zza(edit);
    }
}
