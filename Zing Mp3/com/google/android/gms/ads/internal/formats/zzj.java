package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.formats.zzi.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzav;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzja;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzj implements zzi {
    private final Context mContext;
    private final Object zzako;
    @Nullable
    private final VersionInfoParcel zzanu;
    private final zzq zzbnr;
    @Nullable
    private final JSONObject zzbnu;
    @Nullable
    private final zzja zzbnv;
    @Nullable
    private final zza zzbnw;
    private final zzav zzbnx;
    boolean zzbny;
    private zzmd zzbnz;
    private String zzboa;
    @Nullable
    private String zzbob;
    private WeakReference<View> zzboc;

    /* renamed from: com.google.android.gms.ads.internal.formats.zzj.1 */
    class C10681 extends zzja.zza {
        final /* synthetic */ JSONObject zzbod;
        final /* synthetic */ zzj zzboe;

        C10681(zzj com_google_android_gms_ads_internal_formats_zzj, JSONObject jSONObject) {
            this.zzboe = com_google_android_gms_ads_internal_formats_zzj;
            this.zzbod = jSONObject;
        }

        public void zze(zzgi com_google_android_gms_internal_zzgi) {
            com_google_android_gms_internal_zzgi.zza("google.afma.nativeAds.handleClickGmsg", this.zzbod);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.formats.zzj.2 */
    class C10692 extends zzja.zza {
        final /* synthetic */ JSONObject zzbod;
        final /* synthetic */ zzj zzboe;

        C10692(zzj com_google_android_gms_ads_internal_formats_zzj, JSONObject jSONObject) {
            this.zzboe = com_google_android_gms_ads_internal_formats_zzj;
            this.zzbod = jSONObject;
        }

        public void zze(zzgi com_google_android_gms_internal_zzgi) {
            com_google_android_gms_internal_zzgi.zza("google.afma.nativeAds.handleImpressionPing", this.zzbod);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3 */
    class C10763 extends zzja.zza {
        final /* synthetic */ zzj zzboe;

        /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.1 */
        class C10711 implements zzfe {
            final /* synthetic */ zzgi zzbof;
            final /* synthetic */ C10763 zzbog;

            /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.1.1 */
            class C10701 implements zzme.zza {
                final /* synthetic */ Map zzboh;
                final /* synthetic */ C10711 zzboi;

                C10701(C10711 c10711, Map map) {
                    this.zzboi = c10711;
                    this.zzboh = map;
                }

                public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
                    this.zzboi.zzbog.zzboe.zzboa = (String) this.zzboh.get(TtmlNode.ATTR_ID);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("messageType", "htmlLoaded");
                        jSONObject.put(TtmlNode.ATTR_ID, this.zzboi.zzbog.zzboe.zzboa);
                        this.zzboi.zzbof.zzb("sendMessageToNativeJs", jSONObject);
                    } catch (Throwable e) {
                        zzb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
                    }
                }
            }

            C10711(C10763 c10763, zzgi com_google_android_gms_internal_zzgi) {
                this.zzbog = c10763;
                this.zzbof = com_google_android_gms_internal_zzgi;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                this.zzbog.zzboe.zzbnz.zzxc().zza(new C10701(this, map));
                String str = (String) map.get("overlayHtml");
                String str2 = (String) map.get("baseUrl");
                if (TextUtils.isEmpty(str2)) {
                    this.zzbog.zzboe.zzbnz.loadData(str, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME);
                } else {
                    this.zzbog.zzboe.zzbnz.loadDataWithBaseURL(str2, str, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
                }
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.2 */
        class C10722 implements zzfe {
            final /* synthetic */ C10763 zzbog;

            C10722(C10763 c10763) {
                this.zzbog = c10763;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                this.zzbog.zzboe.zzbnz.getView().setVisibility(0);
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.3 */
        class C10733 implements zzfe {
            final /* synthetic */ C10763 zzbog;

            C10733(C10763 c10763) {
                this.zzbog = c10763;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                this.zzbog.zzboe.zzbnz.getView().setVisibility(8);
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.4 */
        class C10744 implements zzfe {
            final /* synthetic */ C10763 zzbog;

            C10744(C10763 c10763) {
                this.zzbog = c10763;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                this.zzbog.zzboe.zzbnz.getView().setVisibility(8);
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.formats.zzj.3.5 */
        class C10755 implements zzfe {
            final /* synthetic */ zzgi zzbof;
            final /* synthetic */ C10763 zzbog;

            C10755(C10763 c10763, zzgi com_google_android_gms_internal_zzgi) {
                this.zzbog = c10763;
                this.zzbof = com_google_android_gms_internal_zzgi;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                JSONObject jSONObject = new JSONObject();
                try {
                    for (String str : map.keySet()) {
                        jSONObject.put(str, map.get(str));
                    }
                    jSONObject.put(TtmlNode.ATTR_ID, this.zzbog.zzboe.zzboa);
                    this.zzbof.zzb("sendMessageToNativeJs", jSONObject);
                } catch (Throwable e) {
                    zzb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
                }
            }
        }

        C10763(zzj com_google_android_gms_ads_internal_formats_zzj) {
            this.zzboe = com_google_android_gms_ads_internal_formats_zzj;
        }

        public void zze(zzgi com_google_android_gms_internal_zzgi) {
            com_google_android_gms_internal_zzgi.zza("/loadHtml", new C10711(this, com_google_android_gms_internal_zzgi));
            com_google_android_gms_internal_zzgi.zza("/showOverlay", new C10722(this));
            com_google_android_gms_internal_zzgi.zza("/hideOverlay", new C10733(this));
            this.zzboe.zzbnz.zzxc().zza("/hideOverlay", new C10744(this));
            this.zzboe.zzbnz.zzxc().zza("/sendMessageToSdk", new C10755(this, com_google_android_gms_internal_zzgi));
        }
    }

    public zzj(Context context, zzq com_google_android_gms_ads_internal_zzq, @Nullable zzja com_google_android_gms_internal_zzja, zzav com_google_android_gms_internal_zzav, @Nullable JSONObject jSONObject, @Nullable zza com_google_android_gms_ads_internal_formats_zzi_zza, @Nullable VersionInfoParcel versionInfoParcel, @Nullable String str) {
        this.zzako = new Object();
        this.zzboc = null;
        this.mContext = context;
        this.zzbnr = com_google_android_gms_ads_internal_zzq;
        this.zzbnv = com_google_android_gms_internal_zzja;
        this.zzbnx = com_google_android_gms_internal_zzav;
        this.zzbnu = jSONObject;
        this.zzbnw = com_google_android_gms_ads_internal_formats_zzi_zza;
        this.zzanu = versionInfoParcel;
        this.zzbob = str;
    }

    private JSONObject zza(Map<String, WeakReference<View>> map, View view) {
        JSONObject jSONObject = new JSONObject();
        if (map == null || view == null) {
            return jSONObject;
        }
        try {
            int[] zzk = zzk(view);
            for (Entry entry : map.entrySet()) {
                View view2 = (View) ((WeakReference) entry.getValue()).get();
                if (view2 != null) {
                    int[] zzk2 = zzk(view2);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("width", zzab(zzl(view2)));
                    jSONObject2.put("height", zzab(zzm(view2)));
                    jSONObject2.put("x", zzab(zzk2[0] - zzk[0]));
                    jSONObject2.put("y", zzab(zzk2[1] - zzk[1]));
                    jSONObject.put((String) entry.getKey(), jSONObject2);
                }
            }
        } catch (JSONException e) {
            zzb.zzdi("Unable to get all view rectangles");
        }
        return jSONObject;
    }

    private JSONObject zzb(Rect rect) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("x", zzab(rect.left));
        jSONObject.put("y", zzab(rect.top));
        jSONObject.put("width", zzab(rect.right - rect.left));
        jSONObject.put("height", zzab(rect.bottom - rect.top));
        jSONObject.put("relative_to", "self");
        return jSONObject;
    }

    private JSONObject zzb(Map<String, WeakReference<View>> map, View view) {
        JSONObject jSONObject = new JSONObject();
        if (map == null || view == null) {
            return jSONObject;
        }
        int[] zzk = zzk(view);
        for (Entry entry : map.entrySet()) {
            View view2 = (View) ((WeakReference) entry.getValue()).get();
            if (view2 != null) {
                int[] zzk2 = zzk(view2);
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                try {
                    Object zzb;
                    jSONObject3.put("width", zzab(zzl(view2)));
                    jSONObject3.put("height", zzab(zzm(view2)));
                    jSONObject3.put("x", zzab(zzk2[0] - zzk[0]));
                    jSONObject3.put("y", zzab(zzk2[1] - zzk[1]));
                    jSONObject3.put("relative_to", "ad_view");
                    jSONObject2.put("frame", jSONObject3);
                    Rect rect = new Rect();
                    if (view2.getLocalVisibleRect(rect)) {
                        zzb = zzb(rect);
                    } else {
                        zzb = new JSONObject();
                        zzb.put("x", zzab(zzk2[0] - zzk[0]));
                        zzb.put("y", zzab(zzk2[1] - zzk[1]));
                        zzb.put("width", 0);
                        zzb.put("height", 0);
                        zzb.put("relative_to", "ad_view");
                    }
                    jSONObject2.put("visible_bounds", zzb);
                    if (view2 instanceof TextView) {
                        TextView textView = (TextView) view2;
                        jSONObject2.put("text_color", textView.getCurrentTextColor());
                        jSONObject2.put("font_size", (double) textView.getTextSize());
                        jSONObject2.put(MimeTypes.BASE_TYPE_TEXT, textView.getText());
                    }
                    jSONObject.put((String) entry.getKey(), jSONObject2);
                } catch (JSONException e) {
                    zzb.zzdi("Unable to get asset views information");
                }
            }
        }
        return jSONObject;
    }

    private JSONObject zzn(View view) {
        JSONObject jSONObject = new JSONObject();
        if (view != null) {
            try {
                jSONObject.put("width", zzab(zzl(view)));
                jSONObject.put("height", zzab(zzm(view)));
            } catch (Exception e) {
                zzb.zzdi("Unable to get native ad view bounding box");
            }
        }
        return jSONObject;
    }

    private JSONObject zzo(View view) {
        JSONObject jSONObject = new JSONObject();
        if (view != null) {
            try {
                Object zzb;
                int[] zzk = zzk(view);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("width", zzab(zzl(view)));
                jSONObject2.put("height", zzab(zzm(view)));
                jSONObject2.put("x", zzab(zzk[0]));
                jSONObject2.put("y", zzab(zzk[1]));
                jSONObject2.put("relative_to", "window");
                jSONObject.put("frame", jSONObject2);
                Rect rect = new Rect();
                if (view.getGlobalVisibleRect(rect)) {
                    zzb = zzb(rect);
                } else {
                    zzb = new JSONObject();
                    zzb.put("x", zzab(zzk[0]));
                    zzb.put("y", zzab(zzk[1]));
                    zzb.put("width", 0);
                    zzb.put("height", 0);
                    zzb.put("relative_to", "window");
                }
                jSONObject.put("visible_bounds", zzb);
            } catch (Exception e) {
                zzb.zzdi("Unable to get native ad view bounding box");
            }
        }
        return jSONObject;
    }

    public Context getContext() {
        return this.mContext;
    }

    public zzb zza(OnClickListener onClickListener) {
        zza zzmr = this.zzbnw.zzmr();
        if (zzmr == null) {
            return null;
        }
        zzb com_google_android_gms_ads_internal_formats_zzb = new zzb(this.mContext, zzmr);
        com_google_android_gms_ads_internal_formats_zzb.setLayoutParams(new LayoutParams(-1, -1));
        com_google_android_gms_ads_internal_formats_zzb.zzmm().setOnClickListener(onClickListener);
        com_google_android_gms_ads_internal_formats_zzb.zzmm().setContentDescription((CharSequence) zzdr.zzbjg.get());
        return com_google_android_gms_ads_internal_formats_zzb;
    }

    public void zza(View view, zzg com_google_android_gms_ads_internal_formats_zzg) {
        if (this.zzbnw instanceof zzd) {
            zzd com_google_android_gms_ads_internal_formats_zzd = (zzd) this.zzbnw;
            ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -1);
            if (com_google_android_gms_ads_internal_formats_zzd.zzms() != null) {
                ((FrameLayout) view).addView(com_google_android_gms_ads_internal_formats_zzd.zzms(), layoutParams);
                this.zzbnr.zza(com_google_android_gms_ads_internal_formats_zzg);
            } else if (com_google_android_gms_ads_internal_formats_zzd.getImages() != null && com_google_android_gms_ads_internal_formats_zzd.getImages().size() > 0) {
                zzeg zze = zze(com_google_android_gms_ads_internal_formats_zzd.getImages().get(0));
                if (zze != null) {
                    try {
                        zzd zzmn = zze.zzmn();
                        if (zzmn != null) {
                            Drawable drawable = (Drawable) zze.zzae(zzmn);
                            View zznb = zznb();
                            zznb.setImageDrawable(drawable);
                            zznb.setScaleType(ScaleType.CENTER_INSIDE);
                            ((FrameLayout) view).addView(zznb, layoutParams);
                        }
                    } catch (RemoteException e) {
                        zzb.zzdi("Could not get drawable from image");
                    }
                }
            }
        }
    }

    public void zza(View view, String str, @Nullable JSONObject jSONObject, Map<String, WeakReference<View>> map, View view2) {
        zzaa.zzhs("performClick must be called on the main UI thread.");
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("asset", str);
            jSONObject2.put("template", this.zzbnw.zzmq());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("ad", this.zzbnu);
            jSONObject3.put(Promotion.ACTION_CLICK, jSONObject2);
            jSONObject3.put("has_custom_click_handler", this.zzbnr.zzaa(this.zzbnw.getCustomTemplateId()) != null);
            if (((Boolean) zzdr.zzbji.get()).booleanValue()) {
                if (((Boolean) zzdr.zzbjj.get()).booleanValue()) {
                    jSONObject3.put("asset_view_signal", zzb((Map) map, view2));
                    jSONObject3.put("ad_view_signal", zzo(view2));
                } else {
                    jSONObject3.put("view_rectangles", zza((Map) map, view2));
                    jSONObject3.put("native_view_rectangle", zzn(view2));
                }
            }
            if (jSONObject != null) {
                jSONObject3.put("click_point", jSONObject);
            }
            try {
                JSONObject optJSONObject = this.zzbnu.optJSONObject("tracking_urls_and_actions");
                if (optJSONObject == null) {
                    optJSONObject = new JSONObject();
                }
                jSONObject2.put("click_signals", this.zzbnx.zzaz().zza(this.mContext, optJSONObject.optString("click_string"), view));
            } catch (Throwable e) {
                zzb.zzb("Exception obtaining click signals", e);
            }
            jSONObject3.put("ads_id", this.zzbob);
            this.zzbnv.zza(new C10681(this, jSONObject3));
        } catch (Throwable e2) {
            zzb.zzb("Unable to create click JSON.", e2);
        }
    }

    public void zza(View view, Map<String, WeakReference<View>> map, OnTouchListener onTouchListener, OnClickListener onClickListener) {
        if (((Boolean) zzdr.zzbjd.get()).booleanValue()) {
            view.setOnTouchListener(onTouchListener);
            view.setClickable(true);
            view.setOnClickListener(onClickListener);
            for (Entry value : map.entrySet()) {
                View view2 = (View) ((WeakReference) value.getValue()).get();
                if (view2 != null) {
                    view2.setOnTouchListener(onTouchListener);
                    view2.setClickable(true);
                    view2.setOnClickListener(onClickListener);
                }
            }
        }
    }

    public void zza(View view, Map<String, WeakReference<View>> map, JSONObject jSONObject, View view2) {
        zzaa.zzhs("performClick must be called on the main UI thread.");
        for (Entry entry : map.entrySet()) {
            if (view.equals((View) ((WeakReference) entry.getValue()).get())) {
                zza(view, (String) entry.getKey(), jSONObject, map, view2);
                return;
            }
        }
        if ("2".equals(this.zzbnw.zzmq())) {
            zza(view, "2099", jSONObject, map, view2);
        } else if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(this.zzbnw.zzmq())) {
            zza(view, "1099", jSONObject, map, view2);
        }
    }

    int zzab(int i) {
        return zzm.zzkr().zzc(this.mContext, i);
    }

    public void zzb(View view, Map<String, WeakReference<View>> map) {
        zzaa.zzhs("recordImpression must be called on the main UI thread.");
        zzr(true);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ad", this.zzbnu);
            jSONObject.put("ads_id", this.zzbob);
            if (((Boolean) zzdr.zzbji.get()).booleanValue()) {
                if (((Boolean) zzdr.zzbjj.get()).booleanValue()) {
                    jSONObject.put("asset_view_signal", zzb((Map) map, view));
                    jSONObject.put("ad_view_signal", zzo(view));
                } else {
                    jSONObject.put("view_rectangles", zza((Map) map, view));
                    jSONObject.put("native_view_rectangle", zzn(view));
                }
            }
            this.zzbnv.zza(new C10692(this, jSONObject));
        } catch (Throwable e) {
            zzb.zzb("Unable to create impression JSON.", e);
        }
        this.zzbnr.zza((zzi) this);
    }

    public void zzc(View view, Map<String, WeakReference<View>> map) {
        if (!((Boolean) zzdr.zzbjc.get()).booleanValue()) {
            view.setOnTouchListener(null);
            view.setClickable(false);
            view.setOnClickListener(null);
            for (Entry value : map.entrySet()) {
                View view2 = (View) ((WeakReference) value.getValue()).get();
                if (view2 != null) {
                    view2.setOnTouchListener(null);
                    view2.setClickable(false);
                    view2.setOnClickListener(null);
                }
            }
        }
    }

    public void zzd(MotionEvent motionEvent) {
        this.zzbnx.zza(motionEvent);
    }

    public void zzd(View view, Map<String, WeakReference<View>> map) {
        synchronized (this.zzako) {
            if (this.zzbny) {
            } else if (!view.isShown()) {
            } else if (view.getGlobalVisibleRect(new Rect(), null)) {
                zzb(view, (Map) map);
            }
        }
    }

    @Nullable
    zzeg zze(Object obj) {
        return obj instanceof IBinder ? zzeg.zza.zzab((IBinder) obj) : null;
    }

    public void zzj(View view) {
        this.zzboc = new WeakReference(view);
    }

    int[] zzk(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr;
    }

    int zzl(View view) {
        return view.getMeasuredWidth();
    }

    int zzm(View view) {
        return view.getMeasuredHeight();
    }

    public zzmd zzmx() {
        this.zzbnz = zzna();
        this.zzbnz.getView().setVisibility(8);
        this.zzbnv.zza(new C10763(this));
        return this.zzbnz;
    }

    public View zzmy() {
        return this.zzboc != null ? (View) this.zzboc.get() : null;
    }

    public void zzmz() {
        if (this.zzbnw instanceof zzd) {
            this.zzbnr.zzfw();
        }
    }

    zzmd zzna() {
        return zzu.zzgn().zza(this.mContext, AdSizeParcel.zzj(this.mContext), false, false, this.zzbnx, this.zzanu);
    }

    ImageView zznb() {
        return new ImageView(this.mContext);
    }

    protected void zzr(boolean z) {
        this.zzbny = z;
    }
}
