package com.facebook.ads.internal.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.Type;
import com.facebook.share.internal.ShareConstants;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.adapters.m */
public class C0471m extends C0470x {
    private C0421y f448b;
    private Context f449c;
    private boolean f450d;
    private String f451e;
    private String f452f;
    private String f453g;
    private String f454h;
    private String f455i;
    private String f456j;
    private String f457k;
    private String f458l;
    private String f459m;
    private String f460n;
    private String f461o;
    private C0481z f462p;

    public C0471m() {
        this.f450d = false;
        this.f451e = UUID.randomUUID().toString();
    }

    private void m564c() {
        LocalBroadcastManager.getInstance(this.f449c).registerReceiver(this.f462p, this.f462p.m698a());
    }

    private void m565d() {
        if (this.f462p != null) {
            try {
                LocalBroadcastManager.getInstance(this.f449c).unregisterReceiver(this.f462p);
            } catch (Exception e) {
            }
        }
    }

    private String m566e() {
        if (this.a == null) {
            return null;
        }
        String urlPrefix = AdSettings.getUrlPrefix();
        if (urlPrefix == null || urlPrefix.isEmpty()) {
            urlPrefix = "https://www.facebook.com/audience_network/server_side_reward";
        } else {
            urlPrefix = String.format("https://www.%s.facebook.com/audience_network/server_side_reward", new Object[]{urlPrefix});
        }
        Uri parse = Uri.parse(urlPrefix);
        Builder builder = new Builder();
        builder.scheme(parse.getScheme());
        builder.authority(parse.getAuthority());
        builder.path(parse.getPath());
        builder.query(parse.getQuery());
        builder.fragment(parse.getFragment());
        builder.appendQueryParameter("puid", this.a.getUserID());
        builder.appendQueryParameter("pc", this.a.getCurrency());
        builder.appendQueryParameter("ptid", this.f451e);
        builder.appendQueryParameter("appid", this.f459m);
        return builder.build().toString();
    }

    private String m567f() {
        return this.f460n;
    }

    public String m568a() {
        return this.f452f;
    }

    public void m569a(Context context, C0421y c0421y, Map<String, Object> map) {
        this.f448b = c0421y;
        this.f449c = context;
        this.f450d = false;
        JSONObject jSONObject = (JSONObject) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA);
        this.f452f = jSONObject.optString("video_url");
        if (this.f452f == null || this.f452f.isEmpty()) {
            this.f448b.m249a(this, AdError.INTERNAL_ERROR);
            return;
        }
        this.f453g = jSONObject.optString("video_play_report_url");
        this.f454h = jSONObject.optString("video_time_report_url");
        this.f455i = jSONObject.optString("impression_report_url");
        this.f456j = jSONObject.optString("close_report_url");
        this.f461o = jSONObject.optString("ct");
        this.f457k = jSONObject.optString("end_card_markup");
        this.f458l = jSONObject.optString("activation_command");
        this.f460n = jSONObject.optString("context_switch", "endvideo");
        String str = (String) map.get("placement_id");
        if (str != null) {
            this.f459m = str.split("_")[0];
        } else {
            this.f459m = BuildConfig.FLAVOR;
        }
        this.f462p = new C0481z(this.f451e, this, c0421y);
        m564c();
        this.f450d = true;
        this.f448b.m248a(this);
    }

    public boolean m570b() {
        if (!this.f450d) {
            return false;
        }
        Intent intent = new Intent(this.f449c, AudienceNetworkActivity.class);
        intent.putExtra(AudienceNetworkActivity.VIEW_TYPE, Type.REWARDED_VIDEO);
        intent.putExtra(AudienceNetworkActivity.VIDEO_URL, this.f452f);
        intent.putExtra(AudienceNetworkActivity.VIDEO_TIME_REPORT_URL, this.f454h);
        intent.putExtra(AudienceNetworkActivity.VIDEO_PLAY_REPORT_URL, this.f453g);
        intent.putExtra(AudienceNetworkActivity.PREDEFINED_ORIENTATION_KEY, 6);
        intent.putExtra(AudienceNetworkActivity.END_CARD_ACTIVATION_COMMAND, this.f458l);
        intent.putExtra(AudienceNetworkActivity.IMPRESSION_REPORT_URL, this.f455i);
        intent.putExtra(AudienceNetworkActivity.AUDIENCE_NETWORK_UNIQUE_ID_EXTRA, this.f451e);
        intent.putExtra(AudienceNetworkActivity.END_CARD_MARKUP, this.f457k);
        intent.putExtra(AudienceNetworkActivity.CLOSE_REPORT_URL, this.f456j);
        intent.putExtra(AudienceNetworkActivity.CLIENT_TOKEN, this.f461o);
        intent.putExtra(AudienceNetworkActivity.REWARD_SERVER_URL, m566e());
        intent.putExtra(AudienceNetworkActivity.CONTEXT_SWITCH_BEHAVIOR, m567f());
        if (!(this.f449c instanceof Activity)) {
            intent.setFlags(intent.getFlags() | 268435456);
        }
        this.f449c.startActivity(intent);
        return true;
    }

    public void onDestroy() {
        m565d();
    }
}
