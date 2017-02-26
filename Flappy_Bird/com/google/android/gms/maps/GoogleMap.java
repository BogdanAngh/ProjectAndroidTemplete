package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzk;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate zzaBp;
    private UiSettings zzaBq;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(IndoorBuilding indoorBuilding);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.10 */
    class AnonymousClass10 extends com.google.android.gms.maps.internal.zzn.zza {
        final /* synthetic */ OnMarkerClickListener zzaBD;
        final /* synthetic */ GoogleMap zzaBs;

        AnonymousClass10(GoogleMap googleMap, OnMarkerClickListener onMarkerClickListener) {
            this.zzaBs = googleMap;
            this.zzaBD = onMarkerClickListener;
        }

        public boolean zza(zzi com_google_android_gms_maps_model_internal_zzi) {
            return this.zzaBD.onMarkerClick(new Marker(com_google_android_gms_maps_model_internal_zzi));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.11 */
    class AnonymousClass11 extends com.google.android.gms.maps.internal.zzo.zza {
        final /* synthetic */ OnMarkerDragListener zzaBE;
        final /* synthetic */ GoogleMap zzaBs;

        AnonymousClass11(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
            this.zzaBs = googleMap;
            this.zzaBE = onMarkerDragListener;
        }

        public void zzb(zzi com_google_android_gms_maps_model_internal_zzi) {
            this.zzaBE.onMarkerDragStart(new Marker(com_google_android_gms_maps_model_internal_zzi));
        }

        public void zzc(zzi com_google_android_gms_maps_model_internal_zzi) {
            this.zzaBE.onMarkerDragEnd(new Marker(com_google_android_gms_maps_model_internal_zzi));
        }

        public void zzd(zzi com_google_android_gms_maps_model_internal_zzi) {
            this.zzaBE.onMarkerDrag(new Marker(com_google_android_gms_maps_model_internal_zzi));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.12 */
    class AnonymousClass12 extends com.google.android.gms.maps.internal.zzh.zza {
        final /* synthetic */ OnInfoWindowClickListener zzaBF;
        final /* synthetic */ GoogleMap zzaBs;

        AnonymousClass12(GoogleMap googleMap, OnInfoWindowClickListener onInfoWindowClickListener) {
            this.zzaBs = googleMap;
            this.zzaBF = onInfoWindowClickListener;
        }

        public void zze(zzi com_google_android_gms_maps_model_internal_zzi) {
            this.zzaBF.onInfoWindowClick(new Marker(com_google_android_gms_maps_model_internal_zzi));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.13 */
    class AnonymousClass13 extends com.google.android.gms.maps.internal.zzd.zza {
        final /* synthetic */ InfoWindowAdapter zzaBG;
        final /* synthetic */ GoogleMap zzaBs;

        AnonymousClass13(GoogleMap googleMap, InfoWindowAdapter infoWindowAdapter) {
            this.zzaBs = googleMap;
            this.zzaBG = infoWindowAdapter;
        }

        public zzd zzf(zzi com_google_android_gms_maps_model_internal_zzi) {
            return zze.zzw(this.zzaBG.getInfoWindow(new Marker(com_google_android_gms_maps_model_internal_zzi)));
        }

        public zzd zzg(zzi com_google_android_gms_maps_model_internal_zzi) {
            return zze.zzw(this.zzaBG.getInfoContents(new Marker(com_google_android_gms_maps_model_internal_zzi)));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.1 */
    class C08181 extends com.google.android.gms.maps.internal.zzg.zza {
        final /* synthetic */ OnIndoorStateChangeListener zzaBr;
        final /* synthetic */ GoogleMap zzaBs;

        C08181(GoogleMap googleMap, OnIndoorStateChangeListener onIndoorStateChangeListener) {
            this.zzaBs = googleMap;
            this.zzaBr = onIndoorStateChangeListener;
        }

        public void onIndoorBuildingFocused() {
            this.zzaBr.onIndoorBuildingFocused();
        }

        public void zza(zzg com_google_android_gms_maps_model_internal_zzg) {
            this.zzaBr.onIndoorLevelActivated(new IndoorBuilding(com_google_android_gms_maps_model_internal_zzg));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.2 */
    class C08192 extends com.google.android.gms.maps.internal.zzq.zza {
        final /* synthetic */ GoogleMap zzaBs;
        final /* synthetic */ OnMyLocationChangeListener zzaBt;

        C08192(GoogleMap googleMap, OnMyLocationChangeListener onMyLocationChangeListener) {
            this.zzaBs = googleMap;
            this.zzaBt = onMyLocationChangeListener;
        }

        public void zzc(Location location) {
            this.zzaBt.onMyLocationChange(location);
        }

        public void zzo(zzd com_google_android_gms_dynamic_zzd) {
            this.zzaBt.onMyLocationChange((Location) zze.zzn(com_google_android_gms_dynamic_zzd));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.3 */
    class C08203 extends com.google.android.gms.maps.internal.zzp.zza {
        final /* synthetic */ GoogleMap zzaBs;
        final /* synthetic */ OnMyLocationButtonClickListener zzaBu;

        C08203(GoogleMap googleMap, OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
            this.zzaBs = googleMap;
            this.zzaBu = onMyLocationButtonClickListener;
        }

        public boolean onMyLocationButtonClick() throws RemoteException {
            return this.zzaBu.onMyLocationButtonClick();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.4 */
    class C08214 extends com.google.android.gms.maps.internal.zzk.zza {
        final /* synthetic */ GoogleMap zzaBs;
        final /* synthetic */ OnMapLoadedCallback zzaBv;

        C08214(GoogleMap googleMap, OnMapLoadedCallback onMapLoadedCallback) {
            this.zzaBs = googleMap;
            this.zzaBv = onMapLoadedCallback;
        }

        public void onMapLoaded() throws RemoteException {
            this.zzaBv.onMapLoaded();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.5 */
    class C08225 extends com.google.android.gms.maps.internal.zzw.zza {
        final /* synthetic */ GoogleMap zzaBs;
        final /* synthetic */ SnapshotReadyCallback zzaBw;

        C08225(GoogleMap googleMap, SnapshotReadyCallback snapshotReadyCallback) {
            this.zzaBs = googleMap;
            this.zzaBw = snapshotReadyCallback;
        }

        public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
            this.zzaBw.onSnapshotReady(snapshot);
        }

        public void zzp(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
            this.zzaBw.onSnapshotReady((Bitmap) zze.zzn(com_google_android_gms_dynamic_zzd));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.6 */
    class C08236 extends com.google.android.gms.maps.internal.ILocationSourceDelegate.zza {
        final /* synthetic */ GoogleMap zzaBs;
        final /* synthetic */ LocationSource zzaBx;

        /* renamed from: com.google.android.gms.maps.GoogleMap.6.1 */
        class C05091 implements OnLocationChangedListener {
            final /* synthetic */ com.google.android.gms.maps.internal.zzi zzaBy;
            final /* synthetic */ C08236 zzaBz;

            C05091(C08236 c08236, com.google.android.gms.maps.internal.zzi com_google_android_gms_maps_internal_zzi) {
                this.zzaBz = c08236;
                this.zzaBy = com_google_android_gms_maps_internal_zzi;
            }

            public void onLocationChanged(Location location) {
                try {
                    this.zzaBy.zzd(location);
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
        }

        C08236(GoogleMap googleMap, LocationSource locationSource) {
            this.zzaBs = googleMap;
            this.zzaBx = locationSource;
        }

        public void activate(com.google.android.gms.maps.internal.zzi listener) {
            this.zzaBx.activate(new C05091(this, listener));
        }

        public void deactivate() {
            this.zzaBx.deactivate();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.7 */
    class C08247 extends com.google.android.gms.maps.internal.zzf.zza {
        final /* synthetic */ OnCameraChangeListener zzaBA;
        final /* synthetic */ GoogleMap zzaBs;

        C08247(GoogleMap googleMap, OnCameraChangeListener onCameraChangeListener) {
            this.zzaBs = googleMap;
            this.zzaBA = onCameraChangeListener;
        }

        public void onCameraChange(CameraPosition position) {
            this.zzaBA.onCameraChange(position);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.8 */
    class C08258 extends com.google.android.gms.maps.internal.zzj.zza {
        final /* synthetic */ OnMapClickListener zzaBB;
        final /* synthetic */ GoogleMap zzaBs;

        C08258(GoogleMap googleMap, OnMapClickListener onMapClickListener) {
            this.zzaBs = googleMap;
            this.zzaBB = onMapClickListener;
        }

        public void onMapClick(LatLng point) {
            this.zzaBB.onMapClick(point);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.9 */
    class C08269 extends com.google.android.gms.maps.internal.zzl.zza {
        final /* synthetic */ OnMapLongClickListener zzaBC;
        final /* synthetic */ GoogleMap zzaBs;

        C08269(GoogleMap googleMap, OnMapLongClickListener onMapLongClickListener) {
            this.zzaBs = googleMap;
            this.zzaBC = onMapLongClickListener;
        }

        public void onMapLongClick(LatLng point) {
            this.zzaBC.onMapLongClick(point);
        }
    }

    private static final class zza extends com.google.android.gms.maps.internal.zzb.zza {
        private final CancelableCallback zzaBH;

        zza(CancelableCallback cancelableCallback) {
            this.zzaBH = cancelableCallback;
        }

        public void onCancel() {
            this.zzaBH.onCancel();
        }

        public void onFinish() {
            this.zzaBH.onFinish();
        }
    }

    protected GoogleMap(IGoogleMapDelegate map) {
        this.zzaBp = (IGoogleMapDelegate) zzu.zzu(map);
    }

    public final Circle addCircle(CircleOptions options) {
        try {
            return new Circle(this.zzaBp.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
        try {
            zzf addGroundOverlay = this.zzaBp.addGroundOverlay(options);
            return addGroundOverlay != null ? new GroundOverlay(addGroundOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions options) {
        try {
            zzi addMarker = this.zzaBp.addMarker(options);
            return addMarker != null ? new Marker(addMarker) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions options) {
        try {
            return new Polygon(this.zzaBp.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions options) {
        try {
            return new Polyline(this.zzaBp.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions options) {
        try {
            zzk addTileOverlay = this.zzaBp.addTileOverlay(options);
            return addTileOverlay != null ? new TileOverlay(addTileOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update) {
        try {
            this.zzaBp.animateCamera(update.zzvg());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
        try {
            this.zzaBp.animateCameraWithDurationAndCallback(update.zzvg(), durationMs, callback == null ? null : new zza(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
        try {
            this.zzaBp.animateCameraWithCallback(update.zzvg(), callback == null ? null : new zza(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.zzaBp.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.zzaBp.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public IndoorBuilding getFocusedBuilding() {
        try {
            zzg focusedBuilding = this.zzaBp.getFocusedBuilding();
            return focusedBuilding != null ? new IndoorBuilding(focusedBuilding) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.zzaBp.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zzaBp.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.zzaBp.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.zzaBp.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.zzaBp.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zzaBq == null) {
                this.zzaBq = new UiSettings(this.zzaBp.getUiSettings());
            }
            return this.zzaBq;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.zzaBp.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.zzaBp.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.zzaBp.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.zzaBp.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate update) {
        try {
            this.zzaBp.moveCamera(update.zzvg());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean enabled) {
        try {
            this.zzaBp.setBuildingsEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setContentDescription(String description) {
        try {
            this.zzaBp.setContentDescription(description);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.zzaBp.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(InfoWindowAdapter adapter) {
        if (adapter == null) {
            try {
                this.zzaBp.setInfoWindowAdapter(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setInfoWindowAdapter(new AnonymousClass13(this, adapter));
    }

    public final void setLocationSource(LocationSource source) {
        if (source == null) {
            try {
                this.zzaBp.setLocationSource(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setLocationSource(new C08236(this, source));
    }

    public final void setMapType(int type) {
        try {
            this.zzaBp.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.zzaBp.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraChangeListener(OnCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnCameraChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnCameraChangeListener(new C08247(this, listener));
    }

    public final void setOnIndoorStateChangeListener(OnIndoorStateChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnIndoorStateChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnIndoorStateChangeListener(new C08181(this, listener));
    }

    public final void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnInfoWindowClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnInfoWindowClickListener(new AnonymousClass12(this, listener));
    }

    public final void setOnMapClickListener(OnMapClickListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMapClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMapClickListener(new C08258(this, listener));
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
        if (callback == null) {
            try {
                this.zzaBp.setOnMapLoadedCallback(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMapLoadedCallback(new C08214(this, callback));
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMapLongClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMapLongClickListener(new C08269(this, listener));
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMarkerClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMarkerClickListener(new AnonymousClass10(this, listener));
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMarkerDragListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMarkerDragListener(new AnonymousClass11(this, listener));
    }

    public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMyLocationButtonClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMyLocationButtonClickListener(new C08203(this, listener));
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaBp.setOnMyLocationChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaBp.setOnMyLocationChangeListener(new C08192(this, listener));
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.zzaBp.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.zzaBp.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback) {
        snapshot(callback, null);
    }

    public final void snapshot(SnapshotReadyCallback callback, Bitmap bitmap) {
        try {
            this.zzaBp.snapshot(new C08225(this, callback), (zze) (bitmap != null ? zze.zzw(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.zzaBp.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    IGoogleMapDelegate zzvi() {
        return this.zzaBp;
    }
}
