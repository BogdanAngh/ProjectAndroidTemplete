package com.facebook.ads.internal.p005f;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.ads.AdError;
import com.facebook.ads.internal.p005f.C0535e.C0534a;
import com.facebook.ads.internal.p007d.C0498a;
import com.facebook.ads.internal.p007d.C0501c;
import com.facebook.ads.internal.p007d.C0506d;
import com.facebook.ads.internal.p007d.C0509h;
import com.facebook.ads.internal.p008e.C0520g;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0791m;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.upstream.UdpDataSource;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.f.f */
public class C0537f implements C0534a {
    private static final String f755a;
    private static C0537f f756b;
    private static double f757c;
    private static String f758d;
    private final C0535e f759e;
    private final C0506d f760f;

    /* renamed from: com.facebook.ads.internal.f.f.1 */
    class C05361 extends C0498a<String> {
        final /* synthetic */ C0528d f753a;
        final /* synthetic */ C0537f f754b;

        C05361(C0537f c0537f, C0528d c0528d) {
            this.f754b = c0537f;
            this.f753a = c0528d;
        }

        public void m874a(int i, String str) {
            super.m754a(i, str);
            if (!(this.f753a instanceof C0531c)) {
                this.f754b.m883a(str);
            }
        }

        public void m876a(String str) {
            super.m755a(str);
            this.f754b.f759e.m873a(this.f753a.m850i());
        }
    }

    static {
        f755a = C0537f.class.getSimpleName();
    }

    private C0537f(Context context) {
        this.f760f = new C0506d(context);
        this.f759e = new C0535e(context, this);
        this.f759e.m873a(false);
    }

    public static C0537f m878a(Context context) {
        if (f756b == null) {
            Context applicationContext = context.getApplicationContext();
            synchronized (applicationContext) {
                if (f756b == null) {
                    f756b = new C0537f(applicationContext);
                    C0520g.m830a();
                    f757c = C0520g.m831b();
                    f758d = C0520g.m832c();
                }
            }
        }
        return f756b;
    }

    private JSONObject m879a(Cursor cursor) {
        JSONObject jSONObject = new JSONObject();
        while (cursor.moveToNext()) {
            jSONObject.put(cursor.getString(C0509h.f610a.f576a), cursor.getString(C0509h.f611b.f576a));
        }
        return jSONObject;
    }

    private void m880a(C0528d c0528d) {
        this.f760f.m781a(c0528d, new C05361(this, c0528d));
    }

    private JSONArray m881b(Cursor cursor) {
        JSONArray jSONArray = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TtmlNode.ATTR_ID, cursor.getString(C0501c.f580a.f576a));
            jSONObject.put("token_id", cursor.getString(C0501c.f581b.f576a));
            jSONObject.put(ShareConstants.MEDIA_TYPE, cursor.getString(C0501c.f583d.f576a));
            jSONObject.put("time", C0785i.m1617a(cursor.getDouble(C0501c.f584e.f576a)));
            jSONObject.put("session_time", C0785i.m1617a(cursor.getDouble(C0501c.f585f.f576a)));
            jSONObject.put("session_id", cursor.getString(C0501c.f586g.f576a));
            String string = cursor.getString(C0501c.f587h.f576a);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, string != null ? new JSONObject(string) : new JSONObject());
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public JSONObject m882a() {
        Cursor e;
        Cursor d;
        Cursor cursor;
        Throwable th;
        try {
            e = this.f760f.m786e();
            try {
                d = this.f760f.m785d();
            } catch (JSONException e2) {
                cursor = null;
                d = e;
                if (d != null) {
                    d.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                d = null;
                if (e != null) {
                    e.close();
                }
                if (d != null) {
                    d.close();
                }
                throw th;
            }
            try {
                JSONObject jSONObject;
                if (e.getCount() <= 0 || d.getCount() <= 0) {
                    jSONObject = null;
                } else {
                    jSONObject = new JSONObject();
                    jSONObject.put("tokens", m879a(e));
                    jSONObject.put("events", m881b(d));
                }
                if (e != null) {
                    e.close();
                }
                if (d == null) {
                    return jSONObject;
                }
                d.close();
                return jSONObject;
            } catch (JSONException e3) {
                cursor = d;
                d = e;
                if (d != null) {
                    d.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                if (e != null) {
                    e.close();
                }
                if (d != null) {
                    d.close();
                }
                throw th;
            }
        } catch (JSONException e4) {
            cursor = null;
            d = null;
            if (d != null) {
                d.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            d = null;
            e = null;
            if (e != null) {
                e.close();
            }
            if (d != null) {
                d.close();
            }
            throw th;
        }
    }

    public void m883a(String str) {
        Log.e(f755a, "AdEventManager error: " + str);
    }

    public void m884a(String str, C0791m c0791m) {
        m880a(new C0529a(str, f757c, f758d, c0791m));
    }

    public void m885a(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0539h(str, f757c, f758d, map));
        }
    }

    public void m886a(String str, Map<String, String> map, String str2, C0538g c0538g) {
        m880a(new C0542k(str, f757c, f758d, map, str2, c0538g));
    }

    public boolean m887a(JSONArray jSONArray) {
        boolean z = true;
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString(TtmlNode.ATTR_ID);
                int i2 = jSONObject.getInt("code");
                if (i2 == 1) {
                    this.f760f.m782a(string);
                } else if (i2 >= AdError.NETWORK_ERROR_CODE && i2 < UdpDataSource.DEFAULT_MAX_PACKET_SIZE) {
                    z = false;
                } else if (i2 >= UdpDataSource.DEFAULT_MAX_PACKET_SIZE && i2 < 3000) {
                    this.f760f.m782a(string);
                }
            } catch (JSONException e) {
            }
        }
        return z;
    }

    public void m888b() {
        this.f760f.m787f();
        this.f760f.m783b();
    }

    public void m889b(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0540i(str, f757c, f758d, map));
        }
    }

    public void m890c(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0544m(str, f757c, f758d, map));
        }
    }

    public void m891d(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0541j(str, f757c, f758d, map));
        }
    }

    public void m892e(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0530b(str, f757c, f758d, map));
        }
    }

    public void m893f(String str, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            m880a(new C0543l(str, f757c, f758d, map));
        }
    }
}
