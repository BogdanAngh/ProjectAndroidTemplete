package com.facebook.ads.internal.p010h.p012b;

import android.text.TextUtils;
import com.google.android.exoplayer.C0989C;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.facebook.ads.internal.h.b.d */
class C0583d {
    private static final Pattern f824d;
    private static final Pattern f825e;
    public final String f826a;
    public final long f827b;
    public final boolean f828c;

    static {
        f824d = Pattern.compile("[R,r]ange:[ ]?bytes=(\\d*)-");
        f825e = Pattern.compile("GET /(.*) HTTP");
    }

    public C0583d(String str) {
        C0599j.m1107a(str);
        long a = m1038a(str);
        this.f827b = Math.max(0, a);
        this.f828c = a >= 0;
        this.f826a = m1040b(str);
    }

    private long m1038a(String str) {
        Matcher matcher = f824d.matcher(str);
        return matcher.find() ? Long.parseLong(matcher.group(1)) : -1;
    }

    public static C0583d m1039a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, C0989C.UTF8_NAME));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            Object readLine = bufferedReader.readLine();
            if (TextUtils.isEmpty(readLine)) {
                return new C0583d(stringBuilder.toString());
            }
            stringBuilder.append(readLine).append('\n');
        }
    }

    private String m1040b(String str) {
        Matcher matcher = f825e.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid request `" + str + "`: url not found!");
    }

    public String toString() {
        return "GetRequest{rangeOffset=" + this.f827b + ", partial=" + this.f828c + ", uri='" + this.f826a + '\'' + '}';
    }
}
