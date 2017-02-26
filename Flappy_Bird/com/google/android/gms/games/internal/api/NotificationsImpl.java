package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Notifications.ContactSettingLoadResult;
import com.google.android.gms.games.Notifications.GameMuteStatusChangeResult;
import com.google.android.gms.games.Notifications.GameMuteStatusLoadResult;
import com.google.android.gms.games.Notifications.InboxCountResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class NotificationsImpl implements Notifications {

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.1 */
    class C08561 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String zzarm;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.1.1 */
        class C07741 implements GameMuteStatusChangeResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ C08561 zzarn;

            C07741(C08561 c08561, Status status) {
                this.zzarn = c08561;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaf(x0);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zzb) this, this.zzarm, true);
        }

        public GameMuteStatusChangeResult zzaf(Status status) {
            return new C07741(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.2 */
    class C08572 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String zzarm;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.2.1 */
        class C07751 implements GameMuteStatusChangeResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ C08572 zzaro;

            C07751(C08572 c08572, Status status) {
                this.zzaro = c08572;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaf(x0);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zzb) this, this.zzarm, false);
        }

        public GameMuteStatusChangeResult zzaf(Status status) {
            return new C07751(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.3 */
    class C08583 extends BaseGamesApiMethodImpl<GameMuteStatusLoadResult> {
        final /* synthetic */ String zzarm;

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.3.1 */
        class C07761 implements GameMuteStatusLoadResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ C08583 zzarp;

            C07761(C08583 c08583, Status status) {
                this.zzarp = c08583;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzag(x0);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzo((zzb) this, this.zzarm);
        }

        public GameMuteStatusLoadResult zzag(Status status) {
            return new C07761(this, status);
        }
    }

    private static abstract class ContactSettingLoadImpl extends BaseGamesApiMethodImpl<ContactSettingLoadResult> {

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.ContactSettingLoadImpl.1 */
        class C07771 implements ContactSettingLoadResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ ContactSettingLoadImpl zzars;

            C07771(ContactSettingLoadImpl contactSettingLoadImpl, Status status) {
                this.zzars = contactSettingLoadImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzah(x0);
        }

        public ContactSettingLoadResult zzah(Status status) {
            return new C07771(this, status);
        }
    }

    private static abstract class ContactSettingUpdateImpl extends BaseGamesApiMethodImpl<Status> {
        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static abstract class InboxCountImpl extends BaseGamesApiMethodImpl<InboxCountResult> {

        /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.InboxCountImpl.1 */
        class C07781 implements InboxCountResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ InboxCountImpl zzart;

            C07781(InboxCountImpl inboxCountImpl, Status status) {
                this.zzart = inboxCountImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzai(x0);
        }

        public InboxCountResult zzai(Status status) {
            return new C07781(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.4 */
    class C09764 extends ContactSettingLoadImpl {
        final /* synthetic */ boolean zzaqy;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzh((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.5 */
    class C09775 extends ContactSettingUpdateImpl {
        final /* synthetic */ boolean zzarq;
        final /* synthetic */ Bundle zzarr;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarq, this.zzarr);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl.6 */
    class C09786 extends InboxCountImpl {
        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzh(this);
        }
    }

    public void clear(GoogleApiClient apiClient, int notificationTypes) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzfD(notificationTypes);
        }
    }

    public void clearAll(GoogleApiClient apiClient) {
        clear(apiClient, 31);
    }
}
