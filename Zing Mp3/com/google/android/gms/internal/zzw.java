package com.google.android.gms.internal;

import com.google.android.exoplayer.ExoPlayer.Factory;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class zzw implements zzy {
    protected final HttpClient zzcd;

    public static final class zza extends HttpEntityEnclosingRequestBase {
        public zza(String str) {
            setURI(URI.create(str));
        }

        public String getMethod() {
            return "PATCH";
        }
    }

    public zzw(HttpClient httpClient) {
        this.zzcd = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzk<?> com_google_android_gms_internal_zzk_) throws zza {
        byte[] zzo = com_google_android_gms_internal_zzk_.zzo();
        if (zzo != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(zzo));
        }
    }

    private static void zza(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, (String) map.get(str));
        }
    }

    static HttpUriRequest zzb(zzk<?> com_google_android_gms_internal_zzk_, Map<String, String> map) throws zza {
        HttpEntityEnclosingRequestBase httpPost;
        switch (com_google_android_gms_internal_zzk_.getMethod()) {
            case CommonStatusCodes.SUCCESS_CACHE /*-1*/:
                byte[] zzl = com_google_android_gms_internal_zzk_.zzl();
                if (zzl == null) {
                    return new HttpGet(com_google_android_gms_internal_zzk_.getUrl());
                }
                HttpUriRequest httpPost2 = new HttpPost(com_google_android_gms_internal_zzk_.getUrl());
                httpPost2.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzk());
                httpPost2.setEntity(new ByteArrayEntity(zzl));
                return httpPost2;
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return new HttpGet(com_google_android_gms_internal_zzk_.getUrl());
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                httpPost = new HttpPost(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzn());
                zza(httpPost, (zzk) com_google_android_gms_internal_zzk_);
                return httpPost;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                httpPost = new HttpPut(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzn());
                zza(httpPost, (zzk) com_google_android_gms_internal_zzk_);
                return httpPost;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return new HttpDelete(com_google_android_gms_internal_zzk_.getUrl());
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return new HttpHead(com_google_android_gms_internal_zzk_.getUrl());
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return new HttpOptions(com_google_android_gms_internal_zzk_.getUrl());
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                return new HttpTrace(com_google_android_gms_internal_zzk_.getUrl());
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                httpPost = new zza(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzn());
                zza(httpPost, (zzk) com_google_android_gms_internal_zzk_);
                return httpPost;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    public HttpResponse zza(zzk<?> com_google_android_gms_internal_zzk_, Map<String, String> map) throws IOException, zza {
        HttpUriRequest zzb = zzb(com_google_android_gms_internal_zzk_, map);
        zza(zzb, (Map) map);
        zza(zzb, com_google_android_gms_internal_zzk_.getHeaders());
        zza(zzb);
        HttpParams params = zzb.getParams();
        int zzr = com_google_android_gms_internal_zzk_.zzr();
        HttpConnectionParams.setConnectionTimeout(params, Factory.DEFAULT_MIN_REBUFFER_MS);
        HttpConnectionParams.setSoTimeout(params, zzr);
        return this.zzcd.execute(zzb);
    }

    protected void zza(HttpUriRequest httpUriRequest) throws IOException {
    }
}
