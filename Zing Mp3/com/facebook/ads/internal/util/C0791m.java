package com.facebook.ads.internal.util;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.util.m */
public class C0791m {
    public final String f1444a;
    public final long f1445b;
    public final long f1446c;
    public final long f1447d;
    public final long f1448e;
    public final long f1449f;
    public final long f1450g;
    public final long f1451h;

    /* renamed from: com.facebook.ads.internal.util.m.a */
    public static class C0790a {
        private final String f1436a;
        private long f1437b;
        private long f1438c;
        private long f1439d;
        private long f1440e;
        private long f1441f;
        private long f1442g;
        private long f1443h;

        public C0790a(String str) {
            this.f1437b = -1;
            this.f1438c = -1;
            this.f1439d = -1;
            this.f1440e = -1;
            this.f1441f = -1;
            this.f1442g = -1;
            this.f1443h = -1;
            this.f1436a = str;
        }

        public C0790a m1643a(long j) {
            this.f1437b = j;
            return this;
        }

        public C0791m m1644a() {
            return new C0791m(this.f1437b, this.f1438c, this.f1439d, this.f1440e, this.f1441f, this.f1442g, this.f1443h, null);
        }

        public C0790a m1645b(long j) {
            this.f1438c = j;
            return this;
        }

        public C0790a m1646c(long j) {
            this.f1439d = j;
            return this;
        }

        public C0790a m1647d(long j) {
            this.f1440e = j;
            return this;
        }

        public C0790a m1648e(long j) {
            this.f1441f = j;
            return this;
        }

        public C0790a m1649f(long j) {
            this.f1442g = j;
            return this;
        }

        public C0790a m1650g(long j) {
            this.f1443h = j;
            return this;
        }
    }

    private C0791m(String str, long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        this.f1444a = str;
        this.f1445b = j;
        this.f1446c = j2;
        this.f1447d = j3;
        this.f1448e = j4;
        this.f1449f = j5;
        this.f1450g = j6;
        this.f1451h = j7;
    }

    public Map<String, String> m1651a() {
        Map<String, String> hashMap = new HashMap(7);
        hashMap.put("initial_url", this.f1444a);
        hashMap.put("handler_time_ms", String.valueOf(this.f1445b));
        hashMap.put("load_start_ms", String.valueOf(this.f1446c));
        hashMap.put("response_end_ms", String.valueOf(this.f1447d));
        hashMap.put("dom_content_loaded_ms", String.valueOf(this.f1448e));
        hashMap.put("scroll_ready_ms", String.valueOf(this.f1449f));
        hashMap.put("load_finish_ms", String.valueOf(this.f1450g));
        hashMap.put("session_finish_ms", String.valueOf(this.f1451h));
        return hashMap;
    }
}
