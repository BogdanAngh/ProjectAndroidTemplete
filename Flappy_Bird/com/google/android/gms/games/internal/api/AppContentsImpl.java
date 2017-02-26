package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.appcontent.AppContents;
import com.google.android.gms.games.appcontent.AppContents.LoadAppContentResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class AppContentsImpl implements AppContents {

    private static abstract class LoadsImpl extends BaseGamesApiMethodImpl<LoadAppContentResult> {

        /* renamed from: com.google.android.gms.games.internal.api.AppContentsImpl.LoadsImpl.1 */
        class C07641 implements LoadAppContentResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadsImpl zzaqJ;

            C07641(LoadsImpl loadsImpl, Status status) {
                this.zzaqJ = loadsImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzV(x0);
        }

        public LoadAppContentResult zzV(Status status) {
            return new C07641(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AppContentsImpl.1 */
    class C09571 extends LoadsImpl {
        final /* synthetic */ int zzaqG;
        final /* synthetic */ String zzaqH;
        final /* synthetic */ String[] zzaqI;
        final /* synthetic */ boolean zzaqy;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqG, this.zzaqH, this.zzaqI, this.zzaqy);
        }
    }
}
