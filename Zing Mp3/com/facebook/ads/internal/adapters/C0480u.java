package com.facebook.ads.internal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdView.Type;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.mp3download.zingmp3.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.u */
public class C0480u extends C0435a {
    private final C0440v f511c;
    private Type f512d;
    private boolean f513e;
    private boolean f514f;
    private boolean f515g;
    private View f516h;
    private List<View> f517i;

    public C0480u(Context context, C0400b c0400b, C0749a c0749a, C0440v c0440v) {
        super(context, c0400b, c0749a);
        this.f511c = c0440v;
    }

    private String m688b(View view) {
        try {
            return m689c(view).toString();
        } catch (JSONException e) {
            return "Json exception";
        }
    }

    private JSONObject m689c(View view) {
        boolean z = true;
        int i = 0;
        JSONObject jSONObject = new JSONObject();
        jSONObject.putOpt(TtmlNode.ATTR_ID, Integer.valueOf(view.getId()));
        jSONObject.putOpt("class", view.getClass());
        jSONObject.putOpt(TtmlNode.ATTR_TTS_ORIGIN, String.format("{x:%d, y:%d}", new Object[]{Integer.valueOf(view.getTop()), Integer.valueOf(view.getLeft())}));
        jSONObject.putOpt("size", String.format("{h:%d, w:%d}", new Object[]{Integer.valueOf(view.getHeight()), Integer.valueOf(view.getWidth())}));
        if (this.f517i == null || !this.f517i.contains(view)) {
            z = false;
        }
        jSONObject.putOpt("clickable", Boolean.valueOf(z));
        Object obj = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN;
        if (view instanceof Button) {
            obj = "button";
        } else if (view instanceof TextView) {
            obj = MimeTypes.BASE_TYPE_TEXT;
        } else if (view instanceof ImageView) {
            obj = "image";
        } else if (view instanceof MediaView) {
            obj = "mediaview";
        } else if (view instanceof ViewGroup) {
            obj = "viewgroup";
        }
        jSONObject.putOpt(ShareConstants.MEDIA_TYPE, obj);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            JSONArray jSONArray = new JSONArray();
            while (i < viewGroup.getChildCount()) {
                jSONArray.put(m689c(viewGroup.getChildAt(i)));
                i++;
            }
            jSONObject.putOpt("list", jSONArray);
        }
        return jSONObject;
    }

    private String m690d(View view) {
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return BuildConfig.FLAVOR;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            createBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
            view.draw(new Canvas(createBitmap));
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            createBitmap.compress(CompressFormat.JPEG, this.f511c.m356h(), byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    public void m691a(View view) {
        this.f516h = view;
    }

    public void m692a(Type type) {
        this.f512d = type;
    }

    public void m693a(List<View> list) {
        this.f517i = list;
    }

    protected void m694a(Map<String, String> map) {
        if (this.f511c != null) {
            if (this.a != null) {
                map.put("mil", String.valueOf(this.a.m144a()));
                map.put("eil", String.valueOf(this.a.m145b()));
                map.put("eil_source", this.a.m146c());
            }
            if (this.f512d != null) {
                map.put("nti", String.valueOf(this.f512d.getValue()));
            }
            if (this.f513e) {
                map.put("nhs", Boolean.TRUE.toString());
            }
            if (this.f514f) {
                map.put("nmv", Boolean.TRUE.toString());
            }
            if (this.f515g) {
                map.put("nmvap", Boolean.TRUE.toString());
            }
            if (this.f516h != null && this.f511c.m353e()) {
                map.put(Promotion.ACTION_VIEW, m688b(this.f516h));
            }
            if (this.f516h != null && this.f511c.m352d()) {
                map.put("snapshot", m690d(this.f516h));
            }
            this.f511c.m348a((Map) map);
        }
    }

    public void m695a(boolean z) {
        this.f513e = z;
    }

    public void m696b(boolean z) {
        this.f514f = z;
    }

    public void m697c(boolean z) {
        this.f515g = z;
    }
}
