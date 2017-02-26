package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.NodeApi.NodeListener;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.MessageEventParcelable;
import com.google.android.gms.wearable.internal.NodeParcelable;
import java.util.List;

public abstract class WearableListenerService extends Service implements CapabilityListener, ChannelListener, DataListener, MessageListener, NodeListener, com.google.android.gms.wearable.NodeApi.zza {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private boolean zzJA;
    private String zzMZ;
    private volatile int zzZN;
    private IBinder zzZQ;
    private Handler zzaTd;
    private Object zzaTe;

    private class zza extends com.google.android.gms.wearable.internal.zzas.zza {
        boolean zzaTf;
        final /* synthetic */ WearableListenerService zzaTg;

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.1 */
        class C03141 implements Runnable {
            final /* synthetic */ DataHolder zzaTh;
            final /* synthetic */ zza zzaTi;

            C03141(zza com_google_android_gms_wearable_WearableListenerService_zza, DataHolder dataHolder) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTh = dataHolder;
            }

            public void run() {
                DataEventBuffer dataEventBuffer = new DataEventBuffer(this.zzaTh);
                try {
                    this.zzaTi.zzaTg.onDataChanged(dataEventBuffer);
                } finally {
                    dataEventBuffer.release();
                }
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.2 */
        class C03152 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ MessageEventParcelable zzaTj;

            C03152(zza com_google_android_gms_wearable_WearableListenerService_zza, MessageEventParcelable messageEventParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTj = messageEventParcelable;
            }

            public void run() {
                this.zzaTi.zzaTg.onMessageReceived(this.zzaTj);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.3 */
        class C03163 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ NodeParcelable zzaTk;

            C03163(zza com_google_android_gms_wearable_WearableListenerService_zza, NodeParcelable nodeParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTk = nodeParcelable;
            }

            public void run() {
                this.zzaTi.zzaTg.onPeerConnected(this.zzaTk);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.4 */
        class C03174 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ NodeParcelable zzaTk;

            C03174(zza com_google_android_gms_wearable_WearableListenerService_zza, NodeParcelable nodeParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTk = nodeParcelable;
            }

            public void run() {
                this.zzaTi.zzaTg.onPeerDisconnected(this.zzaTk);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.5 */
        class C03185 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ List zzaTl;

            C03185(zza com_google_android_gms_wearable_WearableListenerService_zza, List list) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTl = list;
            }

            public void run() {
                this.zzaTi.zzaTg.onConnectedNodes(this.zzaTl);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.6 */
        class C03196 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ CapabilityInfoParcelable zzaTm;

            C03196(zza com_google_android_gms_wearable_WearableListenerService_zza, CapabilityInfoParcelable capabilityInfoParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTm = capabilityInfoParcelable;
            }

            public void run() {
                this.zzaTi.zzaTg.onCapabilityChanged(this.zzaTm);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.7 */
        class C03207 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ zzh zzaTn;
            final /* synthetic */ AncsNotificationParcelable zzaTo;

            C03207(zza com_google_android_gms_wearable_WearableListenerService_zza, zzh com_google_android_gms_wearable_zzh, AncsNotificationParcelable ancsNotificationParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTn = com_google_android_gms_wearable_zzh;
                this.zzaTo = ancsNotificationParcelable;
            }

            public void run() {
                this.zzaTn.zza(this.zzaTo);
            }
        }

        /* renamed from: com.google.android.gms.wearable.WearableListenerService.zza.8 */
        class C03218 implements Runnable {
            final /* synthetic */ zza zzaTi;
            final /* synthetic */ ChannelEventParcelable zzaTp;

            C03218(zza com_google_android_gms_wearable_WearableListenerService_zza, ChannelEventParcelable channelEventParcelable) {
                this.zzaTi = com_google_android_gms_wearable_WearableListenerService_zza;
                this.zzaTp = channelEventParcelable;
            }

            public void run() {
                this.zzaTp.zza(this.zzaTi.zzaTg);
            }
        }

        zza(WearableListenerService wearableListenerService) {
            this.zzaTg = wearableListenerService;
            this.zzaTf = false;
            this.zzaTf = wearableListenerService instanceof zzh;
        }

        public void onConnectedNodes(List<NodeParcelable> connectedNodes) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onConnectedNodes: " + this.zzaTg.zzMZ + ": " + connectedNodes);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03185(this, connectedNodes));
            }
        }

        public void zza(AncsNotificationParcelable ancsNotificationParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onNotificationReceived: " + ancsNotificationParcelable);
            }
            if (this.zzaTf) {
                this.zzaTg.zzAS();
                zzh com_google_android_gms_wearable_zzh = (zzh) this.zzaTg;
                synchronized (this.zzaTg.zzaTe) {
                    if (this.zzaTg.zzJA) {
                        return;
                    }
                    this.zzaTg.zzaTd.post(new C03207(this, com_google_android_gms_wearable_zzh, ancsNotificationParcelable));
                }
            }
        }

        public void zza(CapabilityInfoParcelable capabilityInfoParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onConnectedCapabilityChanged: " + capabilityInfoParcelable);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03196(this, capabilityInfoParcelable));
            }
        }

        public void zza(ChannelEventParcelable channelEventParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onChannelEvent: " + channelEventParcelable);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03218(this, channelEventParcelable));
            }
        }

        public void zza(MessageEventParcelable messageEventParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onMessageReceived: " + messageEventParcelable);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03152(this, messageEventParcelable));
            }
        }

        public void zza(NodeParcelable nodeParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerConnected: " + this.zzaTg.zzMZ + ": " + nodeParcelable);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03163(this, nodeParcelable));
            }
        }

        public void zzac(DataHolder dataHolder) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onDataItemChanged: " + this.zzaTg.zzMZ + ": " + dataHolder);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    dataHolder.close();
                    return;
                }
                this.zzaTg.zzaTd.post(new C03141(this, dataHolder));
            }
        }

        public void zzb(NodeParcelable nodeParcelable) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerDisconnected: " + this.zzaTg.zzMZ + ": " + nodeParcelable);
            }
            this.zzaTg.zzAS();
            synchronized (this.zzaTg.zzaTe) {
                if (this.zzaTg.zzJA) {
                    return;
                }
                this.zzaTg.zzaTd.post(new C03174(this, nodeParcelable));
            }
        }
    }

    public WearableListenerService() {
        this.zzZN = -1;
        this.zzaTe = new Object();
    }

    private void zzAS() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.zzZN) {
            if (GooglePlayServicesUtil.zzd(this, callingUid)) {
                this.zzZN = callingUid;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices");
        }
    }

    public final IBinder onBind(Intent intent) {
        return BIND_LISTENER_INTENT_ACTION.equals(intent.getAction()) ? this.zzZQ : null;
    }

    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    public void onChannelClosed(Channel channel, int closeReason, int appSpecificErrorCode) {
    }

    public void onChannelOpened(Channel channel) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onCreate: " + getPackageName());
        }
        this.zzMZ = getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.zzaTd = new Handler(handlerThread.getLooper());
        this.zzZQ = new zza(this);
    }

    public void onDataChanged(DataEventBuffer dataEvents) {
    }

    public void onDestroy() {
        synchronized (this.zzaTe) {
            this.zzJA = true;
            if (this.zzaTd == null) {
                throw new IllegalStateException("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()?");
            }
            this.zzaTd.getLooper().quit();
        }
        super.onDestroy();
    }

    public void onInputClosed(Channel channel, int closeReason, int appSpecificErrorCode) {
    }

    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onOutputClosed(Channel channel, int closeReason, int appSpecificErrorCode) {
    }

    public void onPeerConnected(Node peer) {
    }

    public void onPeerDisconnected(Node peer) {
    }
}
