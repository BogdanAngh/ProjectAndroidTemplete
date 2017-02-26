package com.badlogic.gdx.backends.android;

import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.NetJavaImpl;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class AndroidNet implements Net {
    final AndroidApplication app;
    NetJavaImpl netJavaImpl;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidNet.1 */
    class C00471 implements Runnable {
        final /* synthetic */ Uri val$uri;

        C00471(Uri uri) {
            this.val$uri = uri;
        }

        public void run() {
            AndroidNet.this.app.startActivity(new Intent("android.intent.action.VIEW", this.val$uri));
        }
    }

    public AndroidNet(AndroidApplication activity) {
        this.app = activity;
        this.netJavaImpl = new NetJavaImpl();
    }

    public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {
        this.netJavaImpl.sendHttpRequest(httpRequest, httpResponseListener);
    }

    public ServerSocket newServerSocket(Protocol protocol, int port, ServerSocketHints hints) {
        return new AndroidServerSocket(protocol, port, hints);
    }

    public Socket newClientSocket(Protocol protocol, String host, int port, SocketHints hints) {
        return new AndroidSocket(protocol, host, port, hints);
    }

    public void openURI(String URI) {
        if (this.app == null) {
            Gdx.app.log("AndroidNet", "Can't open browser activity from livewallpaper");
            return;
        }
        this.app.runOnUiThread(new C00471(Uri.parse(URI)));
    }
}
