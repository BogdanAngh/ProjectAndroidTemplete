package android.support.v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresApi;
import io.github.kexanie.library.R;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

@TargetApi(13)
@RequiresApi(13)
class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case ValueServer.DIGEST_MODE /*0*/:
            case IExpr.DOUBLEID /*2*/:
            case ValueServer.EXPONENTIAL_MODE /*3*/:
            case IExpr.DOUBLECOMPLEXID /*4*/:
            case ValueServer.CONSTANT_MODE /*5*/:
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                return true;
            case ValueServer.REPLAY_MODE /*1*/:
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
            case R.styleable.TextAppearance_textAllCaps /*9*/:
                return false;
            default:
                return true;
        }
    }
}
