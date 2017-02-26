package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.quest.Milestone;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestBuffer;
import com.google.android.gms.games.quest.QuestUpdateListener;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.games.quest.Quests.AcceptQuestResult;
import com.google.android.gms.games.quest.Quests.ClaimMilestoneResult;
import com.google.android.gms.games.quest.Quests.LoadQuestsResult;

public final class QuestsImpl implements Quests {

    private static abstract class AcceptImpl extends BaseGamesApiMethodImpl<AcceptQuestResult> {

        /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.AcceptImpl.1 */
        class C07831 implements AcceptQuestResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ AcceptImpl zzarL;

            C07831(AcceptImpl acceptImpl, Status status) {
                this.zzarL = acceptImpl;
                this.zzOl = status;
            }

            public Quest getQuest() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private AcceptImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzan(x0);
        }

        public AcceptQuestResult zzan(Status status) {
            return new C07831(this, status);
        }
    }

    private static abstract class ClaimImpl extends BaseGamesApiMethodImpl<ClaimMilestoneResult> {

        /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.ClaimImpl.1 */
        class C07841 implements ClaimMilestoneResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ ClaimImpl zzarM;

            C07841(ClaimImpl claimImpl, Status status) {
                this.zzarM = claimImpl;
                this.zzOl = status;
            }

            public Milestone getMilestone() {
                return null;
            }

            public Quest getQuest() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private ClaimImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzao(x0);
        }

        public ClaimMilestoneResult zzao(Status status) {
            return new C07841(this, status);
        }
    }

    private static abstract class LoadsImpl extends BaseGamesApiMethodImpl<LoadQuestsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.LoadsImpl.1 */
        class C07851 implements LoadQuestsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadsImpl zzarN;

            C07851(LoadsImpl loadsImpl, Status status) {
                this.zzarN = loadsImpl;
                this.zzOl = status;
            }

            public QuestBuffer getQuests() {
                return new QuestBuffer(DataHolder.zzbi(this.zzOl.getStatusCode()));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadsImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzap(x0);
        }

        public LoadQuestsResult zzap(Status status) {
            return new C07851(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.1 */
    class C09881 extends AcceptImpl {
        final /* synthetic */ String zzarF;
        final /* synthetic */ QuestsImpl zzarG;

        C09881(QuestsImpl questsImpl, GoogleApiClient x0, String str) {
            this.zzarG = questsImpl;
            this.zzarF = str;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzh((zzb) this, this.zzarF);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.2 */
    class C09892 extends ClaimImpl {
        final /* synthetic */ String zzarF;
        final /* synthetic */ QuestsImpl zzarG;
        final /* synthetic */ String zzarH;

        C09892(QuestsImpl questsImpl, GoogleApiClient x0, String str, String str2) {
            this.zzarG = questsImpl;
            this.zzarF = str;
            this.zzarH = str2;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarF, this.zzarH);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.3 */
    class C09903 extends LoadsImpl {
        final /* synthetic */ int zzaqV;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ QuestsImpl zzarG;
        final /* synthetic */ int[] zzarI;

        C09903(QuestsImpl questsImpl, GoogleApiClient x0, int[] iArr, int i, boolean z) {
            this.zzarG = questsImpl;
            this.zzarI = iArr;
            this.zzaqV = i;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarI, this.zzaqV, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.4 */
    class C09914 extends LoadsImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ QuestsImpl zzarG;
        final /* synthetic */ String[] zzarJ;

        C09914(QuestsImpl questsImpl, GoogleApiClient x0, boolean z, String[] strArr) {
            this.zzarG = questsImpl;
            this.zzaqy = z;
            this.zzarJ = strArr;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzaqy, this.zzarJ);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.5 */
    class C09925 extends LoadsImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ int zzaqV;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int[] zzarI;
        final /* synthetic */ String zzarK;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarK, this.zzarI, this.zzaqV, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.QuestsImpl.6 */
    class C09936 extends LoadsImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ String[] zzarJ;
        final /* synthetic */ String zzarK;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarK, this.zzaqy, this.zzarJ);
        }
    }

    public PendingResult<AcceptQuestResult> accept(GoogleApiClient apiClient, String questId) {
        return apiClient.zzb(new C09881(this, apiClient, questId));
    }

    public PendingResult<ClaimMilestoneResult> claim(GoogleApiClient apiClient, String questId, String milestoneId) {
        return apiClient.zzb(new C09892(this, apiClient, questId, milestoneId));
    }

    public Intent getQuestIntent(GoogleApiClient apiClient, String questId) {
        return Games.zzd(apiClient).zzcO(questId);
    }

    public Intent getQuestsIntent(GoogleApiClient apiClient, int[] questSelectors) {
        return Games.zzd(apiClient).zzb(questSelectors);
    }

    public PendingResult<LoadQuestsResult> load(GoogleApiClient apiClient, int[] questSelectors, int sortOrder, boolean forceReload) {
        return apiClient.zza(new C09903(this, apiClient, questSelectors, sortOrder, forceReload));
    }

    public PendingResult<LoadQuestsResult> loadByIds(GoogleApiClient apiClient, boolean forceReload, String... questIds) {
        return apiClient.zza(new C09914(this, apiClient, forceReload, questIds));
    }

    public void registerQuestUpdateListener(GoogleApiClient apiClient, QuestUpdateListener listener) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzc(apiClient.zzo(listener));
        }
    }

    public void showStateChangedPopup(GoogleApiClient apiClient, String questId) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzcP(questId);
        }
    }

    public void unregisterQuestUpdateListener(GoogleApiClient apiClient) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzsF();
        }
    }
}
