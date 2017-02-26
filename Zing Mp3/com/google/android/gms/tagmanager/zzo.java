package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;

class zzo implements ContainerHolder {
    private boolean Kf;
    private Container aEg;
    private Container aEh;
    private zzb aEi;
    private zza aEj;
    private TagManager aEk;
    private Status hv;
    private final Looper zzajy;

    public interface zza {
        String zzcea();

        void zzcec();

        void zzox(String str);
    }

    private class zzb extends Handler {
        private final ContainerAvailableListener aEl;
        final /* synthetic */ zzo aEm;

        public zzb(zzo com_google_android_gms_tagmanager_zzo, ContainerAvailableListener containerAvailableListener, Looper looper) {
            this.aEm = com_google_android_gms_tagmanager_zzo;
            super(looper);
            this.aEl = containerAvailableListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    zzoz((String) message.obj);
                default:
                    zzbo.m1698e("Don't know how to handle this message.");
            }
        }

        public void zzoy(String str) {
            sendMessage(obtainMessage(1, str));
        }

        protected void zzoz(String str) {
            this.aEl.onContainerAvailable(this.aEm, str);
        }
    }

    public zzo(Status status) {
        this.hv = status;
        this.zzajy = null;
    }

    public zzo(TagManager tagManager, Looper looper, Container container, zza com_google_android_gms_tagmanager_zzo_zza) {
        this.aEk = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzajy = looper;
        this.aEg = container;
        this.aEj = com_google_android_gms_tagmanager_zzo_zza;
        this.hv = Status.xZ;
        tagManager.zza(this);
    }

    private void zzceb() {
        if (this.aEi != null) {
            this.aEi.zzoy(this.aEh.zzcdy());
        }
    }

    public synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.Kf) {
                zzbo.m1698e("ContainerHolder is released.");
            } else {
                if (this.aEh != null) {
                    this.aEg = this.aEh;
                    this.aEh = null;
                }
                container = this.aEg;
            }
        }
        return container;
    }

    String getContainerId() {
        if (!this.Kf) {
            return this.aEg.getContainerId();
        }
        zzbo.m1698e("getContainerId called on a released ContainerHolder.");
        return BuildConfig.FLAVOR;
    }

    public Status getStatus() {
        return this.hv;
    }

    public synchronized void refresh() {
        if (this.Kf) {
            zzbo.m1698e("Refreshing a released ContainerHolder.");
        } else {
            this.aEj.zzcec();
        }
    }

    public synchronized void release() {
        if (this.Kf) {
            zzbo.m1698e("Releasing a released ContainerHolder.");
        } else {
            this.Kf = true;
            this.aEk.zzb(this);
            this.aEg.release();
            this.aEg = null;
            this.aEh = null;
            this.aEj = null;
            this.aEi = null;
        }
    }

    public synchronized void setContainerAvailableListener(ContainerAvailableListener containerAvailableListener) {
        if (this.Kf) {
            zzbo.m1698e("ContainerHolder is released.");
        } else if (containerAvailableListener == null) {
            this.aEi = null;
        } else {
            this.aEi = new zzb(this, containerAvailableListener, this.zzajy);
            if (this.aEh != null) {
                zzceb();
            }
        }
    }

    public synchronized void zza(Container container) {
        if (!this.Kf) {
            if (container == null) {
                zzbo.m1698e("Unexpected null container.");
            } else {
                this.aEh = container;
                zzceb();
            }
        }
    }

    String zzcea() {
        if (!this.Kf) {
            return this.aEj.zzcea();
        }
        zzbo.m1698e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return BuildConfig.FLAVOR;
    }

    public synchronized void zzov(String str) {
        if (!this.Kf) {
            this.aEg.zzov(str);
        }
    }

    void zzox(String str) {
        if (this.Kf) {
            zzbo.m1698e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.aEj.zzox(str);
        }
    }
}
