package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataBuffer;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange.Builder;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.games.snapshot.Snapshots.CommitSnapshotResult;
import com.google.android.gms.games.snapshot.Snapshots.DeleteSnapshotResult;
import com.google.android.gms.games.snapshot.Snapshots.LoadSnapshotsResult;
import com.google.android.gms.games.snapshot.Snapshots.OpenSnapshotResult;

public final class SnapshotsImpl implements Snapshots {

    private static abstract class CommitImpl extends BaseGamesApiMethodImpl<CommitSnapshotResult> {

        /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.CommitImpl.1 */
        class C07901 implements CommitSnapshotResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ CommitImpl zzask;

            C07901(CommitImpl commitImpl, Status status) {
                this.zzask = commitImpl;
                this.zzOl = status;
            }

            public SnapshotMetadata getSnapshotMetadata() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private CommitImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzau(x0);
        }

        public CommitSnapshotResult zzau(Status status) {
            return new C07901(this, status);
        }
    }

    private static abstract class DeleteImpl extends BaseGamesApiMethodImpl<DeleteSnapshotResult> {

        /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.DeleteImpl.1 */
        class C07911 implements DeleteSnapshotResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ DeleteImpl zzasl;

            C07911(DeleteImpl deleteImpl, Status status) {
                this.zzasl = deleteImpl;
                this.zzOl = status;
            }

            public String getSnapshotId() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private DeleteImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzav(x0);
        }

        public DeleteSnapshotResult zzav(Status status) {
            return new C07911(this, status);
        }
    }

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadSnapshotsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.LoadImpl.1 */
        class C07921 implements LoadSnapshotsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadImpl zzasm;

            C07921(LoadImpl loadImpl, Status status) {
                this.zzasm = loadImpl;
                this.zzOl = status;
            }

            public SnapshotMetadataBuffer getSnapshots() {
                return new SnapshotMetadataBuffer(DataHolder.zzbi(14));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaw(x0);
        }

        public LoadSnapshotsResult zzaw(Status status) {
            return new C07921(this, status);
        }
    }

    private static abstract class OpenImpl extends BaseGamesApiMethodImpl<OpenSnapshotResult> {

        /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.OpenImpl.1 */
        class C07931 implements OpenSnapshotResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ OpenImpl zzasn;

            C07931(OpenImpl openImpl, Status status) {
                this.zzasn = openImpl;
                this.zzOl = status;
            }

            public String getConflictId() {
                return null;
            }

            public Snapshot getConflictingSnapshot() {
                return null;
            }

            public SnapshotContents getResolutionSnapshotContents() {
                return null;
            }

            public Snapshot getSnapshot() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private OpenImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzax(x0);
        }

        public OpenSnapshotResult zzax(Status status) {
            return new C07931(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.1 */
    class C10021 extends LoadImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ SnapshotsImpl zzasa;

        C10021(SnapshotsImpl snapshotsImpl, GoogleApiClient x0, boolean z) {
            this.zzasa = snapshotsImpl;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zze((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.2 */
    class C10032 extends OpenImpl {
        final /* synthetic */ SnapshotsImpl zzasa;
        final /* synthetic */ String zzasb;
        final /* synthetic */ boolean zzasc;
        final /* synthetic */ int zzasd;

        C10032(SnapshotsImpl snapshotsImpl, GoogleApiClient x0, String str, boolean z, int i) {
            this.zzasa = snapshotsImpl;
            this.zzasb = str;
            this.zzasc = z;
            this.zzasd = i;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzasb, this.zzasc, this.zzasd);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.3 */
    class C10043 extends CommitImpl {
        final /* synthetic */ SnapshotsImpl zzasa;
        final /* synthetic */ Snapshot zzase;
        final /* synthetic */ SnapshotMetadataChange zzasf;

        C10043(SnapshotsImpl snapshotsImpl, GoogleApiClient x0, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) {
            this.zzasa = snapshotsImpl;
            this.zzase = snapshot;
            this.zzasf = snapshotMetadataChange;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzase, this.zzasf);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.4 */
    class C10054 extends DeleteImpl {
        final /* synthetic */ SnapshotsImpl zzasa;
        final /* synthetic */ SnapshotMetadata zzasg;

        C10054(SnapshotsImpl snapshotsImpl, GoogleApiClient x0, SnapshotMetadata snapshotMetadata) {
            this.zzasa = snapshotsImpl;
            this.zzasg = snapshotMetadata;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzi(this, this.zzasg.getSnapshotId());
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.5 */
    class C10065 extends OpenImpl {
        final /* synthetic */ SnapshotsImpl zzasa;
        final /* synthetic */ SnapshotMetadataChange zzasf;
        final /* synthetic */ String zzash;
        final /* synthetic */ String zzasi;
        final /* synthetic */ SnapshotContents zzasj;

        C10065(SnapshotsImpl snapshotsImpl, GoogleApiClient x0, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) {
            this.zzasa = snapshotsImpl;
            this.zzash = str;
            this.zzasi = str2;
            this.zzasf = snapshotMetadataChange;
            this.zzasj = snapshotContents;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzash, this.zzasi, this.zzasf, this.zzasj);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.SnapshotsImpl.6 */
    class C10076 extends LoadImpl {
        final /* synthetic */ String zzTE;
        final /* synthetic */ String zzaqA;
        final /* synthetic */ boolean zzaqy;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzTE, this.zzaqA, this.zzaqy);
        }
    }

    public PendingResult<CommitSnapshotResult> commitAndClose(GoogleApiClient apiClient, Snapshot snapshot, SnapshotMetadataChange metadataChange) {
        return apiClient.zzb(new C10043(this, apiClient, snapshot, metadataChange));
    }

    public PendingResult<DeleteSnapshotResult> delete(GoogleApiClient apiClient, SnapshotMetadata metadata) {
        return apiClient.zzb(new C10054(this, apiClient, metadata));
    }

    public void discardAndClose(GoogleApiClient apiClient, Snapshot snapshot) {
        Games.zzd(apiClient).zza(snapshot);
    }

    public int getMaxCoverImageSize(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsQ();
    }

    public int getMaxDataSize(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsP();
    }

    public Intent getSelectSnapshotIntent(GoogleApiClient apiClient, String title, boolean allowAddButton, boolean allowDelete, int maxSnapshots) {
        return Games.zzd(apiClient).zza(title, allowAddButton, allowDelete, maxSnapshots);
    }

    public SnapshotMetadata getSnapshotFromBundle(Bundle extras) {
        return (extras == null || !extras.containsKey(Snapshots.EXTRA_SNAPSHOT_METADATA)) ? null : (SnapshotMetadata) extras.getParcelable(Snapshots.EXTRA_SNAPSHOT_METADATA);
    }

    public PendingResult<LoadSnapshotsResult> load(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.zza(new C10021(this, apiClient, forceReload));
    }

    public PendingResult<OpenSnapshotResult> open(GoogleApiClient apiClient, SnapshotMetadata metadata) {
        return open(apiClient, metadata.getUniqueName(), false);
    }

    public PendingResult<OpenSnapshotResult> open(GoogleApiClient apiClient, SnapshotMetadata metadata, int conflictPolicy) {
        return open(apiClient, metadata.getUniqueName(), false, conflictPolicy);
    }

    public PendingResult<OpenSnapshotResult> open(GoogleApiClient apiClient, String fileName, boolean createIfNotFound) {
        return open(apiClient, fileName, createIfNotFound, -1);
    }

    public PendingResult<OpenSnapshotResult> open(GoogleApiClient apiClient, String fileName, boolean createIfNotFound, int conflictPolicy) {
        return apiClient.zzb(new C10032(this, apiClient, fileName, createIfNotFound, conflictPolicy));
    }

    public PendingResult<OpenSnapshotResult> resolveConflict(GoogleApiClient apiClient, String conflictId, Snapshot snapshot) {
        SnapshotMetadata metadata = snapshot.getMetadata();
        SnapshotMetadataChange build = new Builder().fromMetadata(metadata).build();
        return resolveConflict(apiClient, conflictId, metadata.getSnapshotId(), build, snapshot.getSnapshotContents());
    }

    public PendingResult<OpenSnapshotResult> resolveConflict(GoogleApiClient apiClient, String conflictId, String snapshotId, SnapshotMetadataChange metadataChange, SnapshotContents snapshotContents) {
        return apiClient.zzb(new C10065(this, apiClient, conflictId, snapshotId, metadataChange, snapshotContents));
    }
}
