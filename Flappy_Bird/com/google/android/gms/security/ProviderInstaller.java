package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static Method zzaJM;
    private static final Object zzoW;

    /* renamed from: com.google.android.gms.security.ProviderInstaller.1 */
    static class C02831 extends AsyncTask<Void, Void, Integer> {
        final /* synthetic */ ProviderInstallListener zzaJN;
        final /* synthetic */ Context zzqV;

        C02831(Context context, ProviderInstallListener providerInstallListener) {
            this.zzqV = context;
            this.zzaJN = providerInstallListener;
        }

        protected /* synthetic */ Object doInBackground(Object[] x0) {
            return zzc((Void[]) x0);
        }

        protected /* synthetic */ void onPostExecute(Object x0) {
            zze((Integer) x0);
        }

        protected Integer zzc(Void... voidArr) {
            try {
                ProviderInstaller.installIfNeeded(this.zzqV);
                return Integer.valueOf(0);
            } catch (GooglePlayServicesRepairableException e) {
                return Integer.valueOf(e.getConnectionStatusCode());
            } catch (GooglePlayServicesNotAvailableException e2) {
                return Integer.valueOf(e2.errorCode);
            }
        }

        protected void zze(Integer num) {
            if (num.intValue() == 0) {
                this.zzaJN.onProviderInstalled();
                return;
            }
            this.zzaJN.onProviderInstallFailed(num.intValue(), GooglePlayServicesUtil.zzaT(num.intValue()));
        }
    }

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }

    static {
        zzoW = new Object();
        zzaJM = null;
    }

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzu.zzb((Object) context, (Object) "Context must not be null");
        GooglePlayServicesUtil.zzY(context);
        Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
        if (remoteContext == null) {
            Log.e("ProviderInstaller", "Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        synchronized (zzoW) {
            try {
                if (zzaJM == null) {
                    zzaD(remoteContext);
                }
                zzaJM.invoke(null, new Object[]{remoteContext});
            } catch (Exception e) {
                Log.e("ProviderInstaller", "Failed to install provider: " + e.getMessage());
                throw new GooglePlayServicesNotAvailableException(8);
            }
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener listener) {
        zzu.zzb((Object) context, (Object) "Context must not be null");
        zzu.zzb((Object) listener, (Object) "Listener must not be null");
        zzu.zzbY("Must be called on the UI thread");
        new C02831(context, listener).execute(new Void[0]);
    }

    private static void zzaD(Context context) throws ClassNotFoundException, NoSuchMethodException {
        zzaJM = context.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
    }
}
