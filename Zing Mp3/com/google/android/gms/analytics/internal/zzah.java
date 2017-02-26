package com.google.android.gms.analytics.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.common.internal.zzaa;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

class zzah extends zzd {
    private static final byte[] fB;
    private final zzal fA;
    private final String zzbre;

    private class zza {
        private int fC;
        private ByteArrayOutputStream fD;
        final /* synthetic */ zzah fE;

        public zza(zzah com_google_android_gms_analytics_internal_zzah) {
            this.fE = com_google_android_gms_analytics_internal_zzah;
            this.fD = new ByteArrayOutputStream();
        }

        public byte[] getPayload() {
            return this.fD.toByteArray();
        }

        public int zzagn() {
            return this.fC;
        }

        public boolean zzj(zzab com_google_android_gms_analytics_internal_zzab) {
            zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
            if (this.fC + 1 > this.fE.zzacb().zzaeq()) {
                return false;
            }
            String zza = this.fE.zza(com_google_android_gms_analytics_internal_zzab, false);
            if (zza == null) {
                this.fE.zzaca().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit");
                return true;
            }
            byte[] bytes = zza.getBytes();
            int length = bytes.length;
            if (length > this.fE.zzacb().zzaei()) {
                this.fE.zzaca().zza(com_google_android_gms_analytics_internal_zzab, "Hit size exceeds the maximum size limit");
                return true;
            }
            if (this.fD.size() > 0) {
                length++;
            }
            if (length + this.fD.size() > this.fE.zzacb().zzaek()) {
                return false;
            }
            try {
                if (this.fD.size() > 0) {
                    this.fD.write(zzah.fB);
                }
                this.fD.write(bytes);
                this.fC++;
                return true;
            } catch (IOException e) {
                this.fE.zze("Failed to write payload when batching hits", e);
                return true;
            }
        }
    }

    static {
        fB = "\n".getBytes();
    }

    zzah(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzbre = zza("GoogleAnalytics", zze.VERSION, VERSION.RELEASE, zzao.zza(Locale.getDefault()), Build.MODEL, Build.ID);
        this.fA = new zzal(com_google_android_gms_analytics_internal_zzf.zzabz());
    }

    private int zza(URL url, byte[] bArr) {
        HttpURLConnection zzc;
        Object e;
        Throwable th;
        OutputStream outputStream = null;
        zzaa.zzy(url);
        zzaa.zzy(bArr);
        zzb("POST bytes, url", Integer.valueOf(bArr.length), url);
        if (zzvo()) {
            zza("Post payload\n", new String(bArr));
        }
        try {
            zzfe(getContext().getPackageName());
            zzc = zzc(url);
            try {
                zzc.setDoOutput(true);
                zzc.setFixedLengthStreamingMode(bArr.length);
                zzc.connect();
                outputStream = zzc.getOutputStream();
                outputStream.write(bArr);
                zzb(zzc);
                int responseCode = zzc.getResponseCode();
                if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    zzzg().zzabw();
                }
                zzb("POST status", Integer.valueOf(responseCode));
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e2) {
                        zze("Error closing http post connection output stream", e2);
                    }
                }
                if (zzc != null) {
                    zzc.disconnect();
                }
                zztt();
                return responseCode;
            } catch (IOException e3) {
                e = e3;
                try {
                    zzd("Network POST connection error", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e4) {
                            zze("Error closing http post connection output stream", e4);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    zztt();
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e22) {
                            zze("Error closing http post connection output stream", e22);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    zztt();
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            zzc = outputStream;
            zzd("Network POST connection error", e);
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            zztt();
            return 0;
        } catch (Throwable th3) {
            th = th3;
            zzc = outputStream;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            zztt();
            throw th;
        }
    }

    private static String zza(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    private void zza(StringBuilder stringBuilder, String str, String str2) throws UnsupportedEncodingException {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('&');
        }
        stringBuilder.append(URLEncoder.encode(str, C0989C.UTF8_NAME));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode(str2, C0989C.UTF8_NAME));
    }

    private URL zzagl() {
        String valueOf = String.valueOf(zzacb().zzaes());
        String valueOf2 = String.valueOf(zzacb().zzaev());
        try {
            return new URL(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private int zzb(URL url) {
        zzaa.zzy(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = zzc(url);
            httpURLConnection.connect();
            zzb(httpURLConnection);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                zzzg().zzabw();
            }
            zzb("GET status", Integer.valueOf(responseCode));
            if (httpURLConnection == null) {
                return responseCode;
            }
            httpURLConnection.disconnect();
            return responseCode;
        } catch (IOException e) {
            zzd("Network GET connection error", e);
            return 0;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private int zzb(URL url, byte[] bArr) {
        HttpURLConnection zzc;
        Object e;
        HttpURLConnection httpURLConnection;
        Throwable th;
        OutputStream outputStream = null;
        zzaa.zzy(url);
        zzaa.zzy(bArr);
        try {
            OutputStream outputStream2;
            zzfe(getContext().getPackageName());
            byte[] zzk = zzk(bArr);
            zza("POST compressed size, ratio %, url", Integer.valueOf(zzk.length), Long.valueOf((100 * ((long) zzk.length)) / ((long) bArr.length)), url);
            if (zzk.length > bArr.length) {
                zzc("Compressed payload is larger then uncompressed. compressed, uncompressed", Integer.valueOf(zzk.length), Integer.valueOf(bArr.length));
            }
            if (zzvo()) {
                String str = "Post payload";
                String str2 = "\n";
                String valueOf = String.valueOf(new String(bArr));
                zza(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            zzc = zzc(url);
            try {
                zzc.setDoOutput(true);
                zzc.addRequestProperty("Content-Encoding", "gzip");
                zzc.setFixedLengthStreamingMode(zzk.length);
                zzc.connect();
                outputStream2 = zzc.getOutputStream();
            } catch (IOException e2) {
                e = e2;
                httpURLConnection = zzc;
                try {
                    zzd("Network compressed POST connection error", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e3) {
                            zze("Error closing http compressed post connection output stream", e3);
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    zztt();
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    zzc = httpURLConnection;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e4) {
                            zze("Error closing http compressed post connection output stream", e4);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    zztt();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzc != null) {
                    zzc.disconnect();
                }
                zztt();
                throw th;
            }
            try {
                outputStream2.write(zzk);
                outputStream2.close();
                zzb(zzc);
                int responseCode = zzc.getResponseCode();
                if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    zzzg().zzabw();
                }
                zzb("POST status", Integer.valueOf(responseCode));
                if (zzc != null) {
                    zzc.disconnect();
                }
                zztt();
                return responseCode;
            } catch (IOException e5) {
                e = e5;
                outputStream = outputStream2;
                httpURLConnection = zzc;
                zzd("Network compressed POST connection error", e);
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zztt();
                return 0;
            } catch (Throwable th4) {
                th = th4;
                outputStream = outputStream2;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzc != null) {
                    zzc.disconnect();
                }
                zztt();
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            httpURLConnection = null;
            zzd("Network compressed POST connection error", e);
            if (outputStream != null) {
                outputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            zztt();
            return 0;
        } catch (Throwable th5) {
            th = th5;
            zzc = null;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            zztt();
            throw th;
        }
    }

    private URL zzb(zzab com_google_android_gms_analytics_internal_zzab, String str) {
        String valueOf;
        String valueOf2;
        if (com_google_android_gms_analytics_internal_zzab.zzagc()) {
            valueOf2 = String.valueOf(zzacb().zzaes());
            valueOf = String.valueOf(zzacb().zzaeu());
            valueOf = new StringBuilder(((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf).length()) + String.valueOf(str).length()).append(valueOf2).append(valueOf).append("?").append(str).toString();
        } else {
            valueOf2 = String.valueOf(zzacb().zzaet());
            valueOf = String.valueOf(zzacb().zzaeu());
            valueOf = new StringBuilder(((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf).length()) + String.valueOf(str).length()).append(valueOf2).append(valueOf).append("?").append(str).toString();
        }
        try {
            return new URL(valueOf);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private void zzb(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            do {
            } while (inputStream.read(new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT]) > 0);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zze("Error closing http connection input stream", e);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    zze("Error closing http connection input stream", e2);
                }
            }
        }
    }

    private boolean zzg(zzab com_google_android_gms_analytics_internal_zzab) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
        String zza = zza(com_google_android_gms_analytics_internal_zzab, !com_google_android_gms_analytics_internal_zzab.zzagc());
        if (zza == null) {
            zzaca().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit for upload");
            return true;
        } else if (zza.length() <= zzacb().zzaeh()) {
            URL zzb = zzb(com_google_android_gms_analytics_internal_zzab, zza);
            if (zzb != null) {
                return zzb(zzb) == Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            } else {
                zzew("Failed to build collect GET endpoint url");
                return false;
            }
        } else {
            zza = zza(com_google_android_gms_analytics_internal_zzab, false);
            if (zza == null) {
                zzaca().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit for POST upload");
                return true;
            }
            byte[] bytes = zza.getBytes();
            if (bytes.length > zzacb().zzaej()) {
                zzaca().zza(com_google_android_gms_analytics_internal_zzab, "Hit payload exceeds size limit");
                return true;
            }
            URL zzh = zzh(com_google_android_gms_analytics_internal_zzab);
            if (zzh != null) {
                return zza(zzh, bytes) == Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            } else {
                zzew("Failed to build collect POST endpoint url");
                return false;
            }
        }
    }

    private URL zzh(zzab com_google_android_gms_analytics_internal_zzab) {
        String valueOf;
        String valueOf2;
        if (com_google_android_gms_analytics_internal_zzab.zzagc()) {
            valueOf = String.valueOf(zzacb().zzaes());
            valueOf2 = String.valueOf(zzacb().zzaeu());
            valueOf = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            valueOf = String.valueOf(zzacb().zzaet());
            valueOf2 = String.valueOf(zzacb().zzaeu());
            valueOf = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        try {
            return new URL(valueOf);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private String zzi(zzab com_google_android_gms_analytics_internal_zzab) {
        return String.valueOf(com_google_android_gms_analytics_internal_zzab.zzafz());
    }

    private static byte[] zzk(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    String zza(zzab com_google_android_gms_analytics_internal_zzab, boolean z) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Entry entry : com_google_android_gms_analytics_internal_zzab.zzmc().entrySet()) {
                String str = (String) entry.getKey();
                if (!("ht".equals(str) || "qt".equals(str) || "AppUID".equals(str) || "z".equals(str) || "_gmsv".equals(str))) {
                    zza(stringBuilder, str, (String) entry.getValue());
                }
            }
            zza(stringBuilder, "ht", String.valueOf(com_google_android_gms_analytics_internal_zzab.zzaga()));
            zza(stringBuilder, "qt", String.valueOf(zzabz().currentTimeMillis() - com_google_android_gms_analytics_internal_zzab.zzaga()));
            zzacb();
            if (z) {
                long zzagd = com_google_android_gms_analytics_internal_zzab.zzagd();
                zza(stringBuilder, "z", zzagd != 0 ? String.valueOf(zzagd) : zzi(com_google_android_gms_analytics_internal_zzab));
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    List<Long> zza(List<zzab> list, boolean z) {
        zzaa.zzbt(!list.isEmpty());
        zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
        zza com_google_android_gms_analytics_internal_zzah_zza = new zza(this);
        List<Long> arrayList = new ArrayList();
        for (zzab com_google_android_gms_analytics_internal_zzab : list) {
            if (!com_google_android_gms_analytics_internal_zzah_zza.zzj(com_google_android_gms_analytics_internal_zzab)) {
                break;
            }
            arrayList.add(Long.valueOf(com_google_android_gms_analytics_internal_zzab.zzafz()));
        }
        if (com_google_android_gms_analytics_internal_zzah_zza.zzagn() == 0) {
            return arrayList;
        }
        URL zzagl = zzagl();
        if (zzagl == null) {
            zzew("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        int zzb = z ? zzb(zzagl, com_google_android_gms_analytics_internal_zzah_zza.getPayload()) : zza(zzagl, com_google_android_gms_analytics_internal_zzah_zza.getPayload());
        if (Callback.DEFAULT_DRAG_ANIMATION_DURATION == zzb) {
            zza("Batched upload completed. Hits batched", Integer.valueOf(com_google_android_gms_analytics_internal_zzah_zza.zzagn()));
            return arrayList;
        }
        zza("Network error uploading hits. status code", Integer.valueOf(zzb));
        if (zzacb().zzaey().contains(Integer.valueOf(zzb))) {
            zzev("Server instructed the client to stop batching");
            this.fA.start();
        }
        return Collections.emptyList();
    }

    public boolean zzagk() {
        NetworkInfo activeNetworkInfo;
        zzzx();
        zzacj();
        try {
            activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException e) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzes("No network connectivity");
        return false;
    }

    HttpURLConnection zzc(URL url) throws IOException {
        URLConnection openConnection = url.openConnection();
        if (openConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(zzacb().zzafg());
            httpURLConnection.setReadTimeout(zzacb().zzafh());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("User-Agent", this.zzbre);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }
        throw new IOException("Failed to obtain http connection");
    }

    protected void zzfe(String str) {
    }

    public List<Long> zzt(List<zzab> list) {
        boolean z;
        boolean z2 = true;
        zzzx();
        zzacj();
        zzaa.zzy(list);
        if (zzacb().zzaey().isEmpty() || !this.fA.zzz(zzacb().zzaer() * 1000)) {
            z2 = false;
            z = false;
        } else {
            z = zzacb().zzaew() != zzm.dN;
            if (zzacb().zzaex() != zzo.GZIP) {
                z2 = false;
            }
        }
        return z ? zza((List) list, z2) : zzu(list);
    }

    protected void zztt() {
    }

    List<Long> zzu(List<zzab> list) {
        List<Long> arrayList = new ArrayList(list.size());
        for (zzab com_google_android_gms_analytics_internal_zzab : list) {
            if (!zzg(com_google_android_gms_analytics_internal_zzab)) {
                break;
            }
            arrayList.add(Long.valueOf(com_google_android_gms_analytics_internal_zzab.zzafz()));
            if (arrayList.size() >= zzacb().zzaep()) {
                break;
            }
        }
        return arrayList;
    }

    protected void zzzy() {
        zza("Network initialized. User agent", this.zzbre);
    }
}
