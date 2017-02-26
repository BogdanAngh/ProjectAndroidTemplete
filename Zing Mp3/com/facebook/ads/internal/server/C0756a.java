package com.facebook.ads.internal.server;

import android.content.Context;
import com.facebook.ads.internal.AdErrorType;
import com.facebook.ads.internal.C0488b;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.p008e.C0515d;
import com.facebook.ads.internal.p008e.C0519f;
import com.facebook.ads.internal.p008e.C0522i;
import com.facebook.ads.internal.p010h.p011a.C0554a;
import com.facebook.ads.internal.p010h.p011a.C0555b;
import com.facebook.ads.internal.p010h.p011a.C0566m;
import com.facebook.ads.internal.p010h.p011a.C0567n;
import com.facebook.ads.internal.server.C0760d.C0759a;
import com.facebook.ads.internal.util.C0765a;
import com.facebook.ads.internal.util.C0780e;
import com.facebook.ads.internal.util.C0806x;
import com.facebook.ads.internal.util.C0806x.C0805a;
import com.facebook.ads.internal.util.aa;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONException;

/* renamed from: com.facebook.ads.internal.server.a */
public class C0756a {
    private static final aa f1306i;
    private static final ThreadPoolExecutor f1307j;
    Map<String, String> f1308a;
    private final Context f1309b;
    private final C0758c f1310c;
    private final C0604h f1311d;
    private C0428a f1312e;
    private C0519f f1313f;
    private C0554a f1314g;
    private final String f1315h;

    /* renamed from: com.facebook.ads.internal.server.a.a */
    public interface C0428a {
        void m264a(C0488b c0488b);

        void m265a(C0761e c0761e);
    }

    /* renamed from: com.facebook.ads.internal.server.a.1 */
    class C07531 implements Runnable {
        final /* synthetic */ C0519f f1302a;
        final /* synthetic */ C0756a f1303b;

        C07531(C0756a c0756a, C0519f c0519f) {
            this.f1303b = c0756a;
            this.f1302a = c0519f;
        }

        public void run() {
            C0522i.m834b(this.f1303b.f1309b);
            this.f1303b.f1308a = this.f1302a.m829e();
            try {
                this.f1303b.f1314g = C0806x.m1686b(this.f1303b.f1309b, this.f1302a.f678e);
                this.f1303b.f1314g.m954a(this.f1303b.f1315h, this.f1303b.f1314g.m961b().m997a(this.f1303b.f1308a), this.f1303b.m1511b());
            } catch (Exception e) {
                this.f1303b.m1506a(AdErrorType.AD_REQUEST_FAILED.getAdErrorWrapper(e.getMessage()));
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.server.a.2 */
    class C07542 extends C0555b {
        final /* synthetic */ C0756a f1304a;

        C07542(C0756a c0756a) {
            this.f1304a = c0756a;
        }

        public void m1501a(C0566m c0566m) {
            C0780e.m1604b(this.f1304a.f1313f);
            this.f1304a.f1314g = null;
            try {
                C0567n a = c0566m.m989a();
                if (a != null) {
                    String e = a.m994e();
                    C0760d a2 = this.f1304a.f1310c.m1526a(e);
                    if (a2.m1527a() == C0759a.ERROR) {
                        C0762f c0762f = (C0762f) a2;
                        String c = c0762f.m1529c();
                        this.f1304a.m1506a(AdErrorType.adErrorTypeFromCode(c0762f.m1530d(), AdErrorType.ERROR_MESSAGE).getAdErrorWrapper(c == null ? e : c));
                        return;
                    }
                }
            } catch (JSONException e2) {
            }
            this.f1304a.m1506a(new C0488b(AdErrorType.NETWORK_ERROR, c0566m.getMessage()));
        }

        public void m1502a(C0567n c0567n) {
            if (c0567n != null) {
                String e = c0567n.m994e();
                C0780e.m1604b(this.f1304a.f1313f);
                this.f1304a.f1314g = null;
                this.f1304a.m1510a(e);
            }
        }

        public void m1503a(Exception exception) {
            if (C0566m.class.equals(exception.getClass())) {
                m1501a((C0566m) exception);
            } else {
                this.f1304a.m1506a(new C0488b(AdErrorType.NETWORK_ERROR, exception.getMessage()));
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.server.a.3 */
    static /* synthetic */ class C07553 {
        static final /* synthetic */ int[] f1305a;

        static {
            f1305a = new int[C0759a.values().length];
            try {
                f1305a[C0759a.ADS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1305a[C0759a.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static {
        f1306i = new aa();
        f1307j = (ThreadPoolExecutor) Executors.newCachedThreadPool(f1306i);
    }

    public C0756a(Context context) {
        this.f1309b = context.getApplicationContext();
        this.f1310c = C0758c.m1522a();
        this.f1311d = new C0604h(this.f1309b);
        this.f1315h = C0757b.m1520a();
    }

    private void m1506a(C0488b c0488b) {
        if (this.f1312e != null) {
            this.f1312e.m264a(c0488b);
        }
        m1517a();
    }

    private void m1509a(C0761e c0761e) {
        if (this.f1312e != null) {
            this.f1312e.m265a(c0761e);
        }
        m1517a();
    }

    private void m1510a(String str) {
        try {
            C0760d a = this.f1310c.m1526a(str);
            C0515d b = a.m1528b();
            if (b != null) {
                this.f1311d.m1131a(b.m806b());
                C0780e.m1601a(b.m804a().m812c(), this.f1313f);
            }
            switch (C07553.f1305a[a.m1527a().ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    C0761e c0761e = (C0761e) a;
                    if (b != null && b.m804a().m813d()) {
                        C0780e.m1602a(str, this.f1313f);
                    }
                    m1509a(c0761e);
                    return;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    C0762f c0762f = (C0762f) a;
                    String c = c0762f.m1529c();
                    AdErrorType adErrorTypeFromCode = AdErrorType.adErrorTypeFromCode(c0762f.m1530d(), AdErrorType.ERROR_MESSAGE);
                    if (c != null) {
                        str = c;
                    }
                    m1506a(adErrorTypeFromCode.getAdErrorWrapper(str));
                    return;
                default:
                    m1506a(AdErrorType.UNKNOWN_RESPONSE.getAdErrorWrapper(str));
                    return;
            }
        } catch (Exception e) {
            m1506a(AdErrorType.PARSER_FAILURE.getAdErrorWrapper(e.getMessage()));
        }
        m1506a(AdErrorType.PARSER_FAILURE.getAdErrorWrapper(e.getMessage()));
    }

    private C0555b m1511b() {
        return new C07542(this);
    }

    public void m1517a() {
        if (this.f1314g != null) {
            this.f1314g.m964c(1);
            this.f1314g.m962b(1);
            this.f1314g = null;
        }
    }

    public void m1518a(C0519f c0519f) {
        m1517a();
        if (C0806x.m1687c(this.f1309b) == C0805a.NONE) {
            m1506a(new C0488b(AdErrorType.NETWORK_ERROR, "No network connection"));
            return;
        }
        this.f1313f = c0519f;
        C0765a.m1532a(this.f1309b);
        if (C0780e.m1603a(c0519f)) {
            String c = C0780e.m1605c(c0519f);
            if (c != null) {
                m1510a(c);
                return;
            } else {
                m1506a(AdErrorType.LOAD_TOO_FREQUENTLY.getAdErrorWrapper(null));
                return;
            }
        }
        f1307j.submit(new C07531(this, c0519f));
    }

    public void m1519a(C0428a c0428a) {
        this.f1312e = c0428a;
    }
}
