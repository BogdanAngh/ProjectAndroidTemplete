package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.media.TransportMediator;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.C1569R;

@zzji
class zzw implements SensorEventListener {
    private final SensorManager zzccu;
    private final Object zzccv;
    private final Display zzccw;
    private final float[] zzccx;
    private final float[] zzccy;
    private float[] zzccz;
    private Handler zzcda;
    private zza zzcdb;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzw.1 */
    class C10931 implements Runnable {
        final /* synthetic */ zzw zzcdc;

        C10931(zzw com_google_android_gms_ads_internal_overlay_zzw) {
            this.zzcdc = com_google_android_gms_ads_internal_overlay_zzw;
        }

        public void run() {
            Looper.myLooper().quit();
        }
    }

    interface zza {
        void zzpr();
    }

    zzw(Context context) {
        this.zzccu = (SensorManager) context.getSystemService("sensor");
        this.zzccw = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        this.zzccx = new float[9];
        this.zzccy = new float[9];
        this.zzccv = new Object();
    }

    private void zzh(int i, int i2) {
        float f = this.zzccy[i];
        this.zzccy[i] = this.zzccy[i2];
        this.zzccy[i2] = f;
    }

    int getRotation() {
        return this.zzccw.getRotation();
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        zza(sensorEvent.values);
    }

    void start() {
        if (this.zzcda == null) {
            Sensor defaultSensor = this.zzccu.getDefaultSensor(11);
            if (defaultSensor == null) {
                zzb.m1695e("No Sensor of TYPE_ROTATION_VECTOR");
                return;
            }
            HandlerThread handlerThread = new HandlerThread("OrientationMonitor");
            handlerThread.start();
            this.zzcda = new Handler(handlerThread.getLooper());
            if (!this.zzccu.registerListener(this, defaultSensor, 0, this.zzcda)) {
                zzb.m1695e("SensorManager.registerListener failed.");
                stop();
            }
        }
    }

    void stop() {
        if (this.zzcda != null) {
            this.zzccu.unregisterListener(this);
            this.zzcda.post(new C10931(this));
            this.zzcda = null;
        }
    }

    void zza(zza com_google_android_gms_ads_internal_overlay_zzw_zza) {
        this.zzcdb = com_google_android_gms_ads_internal_overlay_zzw_zza;
    }

    void zza(float[] fArr) {
        if (fArr[0] != 0.0f || fArr[1] != 0.0f || fArr[2] != 0.0f) {
            synchronized (this.zzccv) {
                if (this.zzccz == null) {
                    this.zzccz = new float[9];
                }
            }
            SensorManager.getRotationMatrixFromVector(this.zzccx, fArr);
            switch (getRotation()) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    SensorManager.remapCoordinateSystem(this.zzccx, 2, 129, this.zzccy);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    SensorManager.remapCoordinateSystem(this.zzccx, 129, TransportMediator.KEYCODE_MEDIA_RECORD, this.zzccy);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    SensorManager.remapCoordinateSystem(this.zzccx, TransportMediator.KEYCODE_MEDIA_RECORD, 1, this.zzccy);
                    break;
                default:
                    System.arraycopy(this.zzccx, 0, this.zzccy, 0, 9);
                    break;
            }
            zzh(1, 3);
            zzh(2, 6);
            zzh(5, 7);
            synchronized (this.zzccv) {
                System.arraycopy(this.zzccy, 0, this.zzccz, 0, 9);
            }
            if (this.zzcdb != null) {
                this.zzcdb.zzpr();
            }
        }
    }

    boolean zzb(float[] fArr) {
        boolean z = false;
        synchronized (this.zzccv) {
            if (this.zzccz == null) {
            } else {
                System.arraycopy(this.zzccz, 0, fArr, 0, this.zzccz.length);
                z = true;
            }
        }
        return z;
    }
}
