package com.badlogic.gdx.net;

import com.badlogic.gdx.utils.Disposable;
import java.io.InputStream;
import java.io.OutputStream;

public interface Socket extends Disposable {
    InputStream getInputStream();

    OutputStream getOutputStream();

    boolean isConnected();
}
