package com.google.android.gms.internal;

import io.github.kexanie.library.R;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.stat.descriptive.DescriptiveStatistics;
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
import org.matheclipse.core.interfaces.IExpr;

public class zzw implements zzy {
    protected final HttpClient zzaC;

    public static final class zza extends HttpEntityEnclosingRequestBase {
        public zza(String str) {
            setURI(URI.create(str));
        }

        public String getMethod() {
            return "PATCH";
        }
    }

    public zzw(HttpClient httpClient) {
        this.zzaC = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzk<?> com_google_android_gms_internal_zzk_) throws zza {
        byte[] zzm = com_google_android_gms_internal_zzk_.zzm();
        if (zzm != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(zzm));
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
            case DescriptiveStatistics.INFINITE_WINDOW /*-1*/:
                byte[] zzj = com_google_android_gms_internal_zzk_.zzj();
                if (zzj == null) {
                    return new HttpGet(com_google_android_gms_internal_zzk_.getUrl());
                }
                HttpUriRequest httpPost2 = new HttpPost(com_google_android_gms_internal_zzk_.getUrl());
                httpPost2.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzi());
                httpPost2.setEntity(new ByteArrayEntity(zzj));
                return httpPost2;
            case ValueServer.DIGEST_MODE /*0*/:
                return new HttpGet(com_google_android_gms_internal_zzk_.getUrl());
            case ValueServer.REPLAY_MODE /*1*/:
                httpPost = new HttpPost(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzl());
                zza(httpPost, (zzk) com_google_android_gms_internal_zzk_);
                return httpPost;
            case IExpr.DOUBLEID /*2*/:
                httpPost = new HttpPut(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzl());
                zza(httpPost, (zzk) com_google_android_gms_internal_zzk_);
                return httpPost;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new HttpDelete(com_google_android_gms_internal_zzk_.getUrl());
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new HttpHead(com_google_android_gms_internal_zzk_.getUrl());
            case ValueServer.CONSTANT_MODE /*5*/:
                return new HttpOptions(com_google_android_gms_internal_zzk_.getUrl());
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                return new HttpTrace(com_google_android_gms_internal_zzk_.getUrl());
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                httpPost = new zza(com_google_android_gms_internal_zzk_.getUrl());
                httpPost.addHeader("Content-Type", com_google_android_gms_internal_zzk_.zzl());
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
        HttpParams params = zzb.getParams();
        int zzp = com_google_android_gms_internal_zzk_.zzp();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, zzp);
        return this.zzaC.execute(zzb);
    }
}
