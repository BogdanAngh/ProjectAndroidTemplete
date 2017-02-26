package com.google.android.gms.drive.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.ChangesAvailableEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.ProgressEvent;
import com.google.android.gms.drive.events.QueryResultEventParcelable;
import com.google.android.gms.drive.events.zzc;
import com.google.android.gms.drive.events.zzf;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.drive.events.zzj;
import com.google.android.gms.drive.events.zzl;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.ArrayList;
import java.util.List;

public class zzac extends com.google.android.gms.drive.internal.zzam.zza {
    private final int zzaca;
    private final zzf zzafC;
    private final zza zzafD;
    private final List<Integer> zzafE;

    private static class zza extends Handler {
        private final Context mContext;

        /* renamed from: com.google.android.gms.drive.internal.zzac.zza.1 */
        static class C04171 implements zzj {
            final /* synthetic */ MetadataBuffer zzafF;

            C04171(MetadataBuffer metadataBuffer) {
                this.zzafF = metadataBuffer;
            }
        }

        private zza(Looper looper, Context context) {
            super(looper);
            this.mContext = context;
        }

        private static void zza(zzl com_google_android_gms_drive_events_zzl, QueryResultEventParcelable queryResultEventParcelable) {
            DataHolder zzpx = queryResultEventParcelable.zzpx();
            if (zzpx != null) {
                com_google_android_gms_drive_events_zzl.zza(new C04171(new MetadataBuffer(zzpx)));
            }
            if (queryResultEventParcelable.zzpy()) {
                com_google_android_gms_drive_events_zzl.zzck(queryResultEventParcelable.zzpz());
            }
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    Pair pair = (Pair) msg.obj;
                    zzf com_google_android_gms_drive_events_zzf = (zzf) pair.first;
                    DriveEvent driveEvent = (DriveEvent) pair.second;
                    switch (driveEvent.getType()) {
                        case CompletionEvent.STATUS_FAILURE /*1*/:
                            ((ChangeListener) com_google_android_gms_drive_events_zzf).onChange((ChangeEvent) driveEvent);
                        case CompletionEvent.STATUS_CONFLICT /*2*/:
                            ((CompletionListener) com_google_android_gms_drive_events_zzf).onCompletion((CompletionEvent) driveEvent);
                        case CompletionEvent.STATUS_CANCELED /*3*/:
                            zza((zzl) com_google_android_gms_drive_events_zzf, (QueryResultEventParcelable) driveEvent);
                        case GameHelper.CLIENT_APPSTATE /*4*/:
                            ((zzc) com_google_android_gms_drive_events_zzf).zza((ChangesAvailableEvent) driveEvent);
                        case Place.TYPE_ART_GALLERY /*5*/:
                        case Place.TYPE_ATM /*6*/:
                            ((zzi) com_google_android_gms_drive_events_zzf).zza((ProgressEvent) driveEvent);
                        default:
                            zzx.zzu("EventCallback", "Unexpected event: " + driveEvent);
                    }
                default:
                    zzx.zze(this.mContext, "EventCallback", "Don't know how to handle this event");
            }
        }

        public void zza(zzf com_google_android_gms_drive_events_zzf, DriveEvent driveEvent) {
            sendMessage(obtainMessage(1, new Pair(com_google_android_gms_drive_events_zzf, driveEvent)));
        }
    }

    public zzac(Looper looper, Context context, int i, zzf com_google_android_gms_drive_events_zzf) {
        this.zzafE = new ArrayList();
        this.zzaca = i;
        this.zzafC = com_google_android_gms_drive_events_zzf;
        this.zzafD = new zza(context, null);
    }

    public void zzc(OnEventResponse onEventResponse) throws RemoteException {
        DriveEvent zzpO = onEventResponse.zzpO();
        zzu.zzU(this.zzaca == zzpO.getType());
        zzu.zzU(this.zzafE.contains(Integer.valueOf(zzpO.getType())));
        this.zzafD.zza(this.zzafC, zzpO);
    }

    public void zzcA(int i) {
        this.zzafE.add(Integer.valueOf(i));
    }

    public boolean zzcB(int i) {
        return this.zzafE.contains(Integer.valueOf(i));
    }
}
