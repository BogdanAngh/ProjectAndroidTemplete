package android.support.customtabs;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.customtabs.ICustomTabsService.Stub;

public abstract class CustomTabsServiceConnection implements ServiceConnection {

    /* renamed from: android.support.customtabs.CustomTabsServiceConnection.1 */
    class C00051 extends CustomTabsClient {
        C00051(ICustomTabsService service, ComponentName componentName) {
            super(service, componentName);
        }
    }

    public abstract void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient);

    public final void onServiceConnected(ComponentName name, IBinder service) {
        onCustomTabsServiceConnected(name, new C00051(Stub.asInterface(service), name));
    }
}
