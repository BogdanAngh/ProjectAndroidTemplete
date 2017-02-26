package com.badlogic.gdx.backends.android;

import android.content.Context;
import com.badlogic.gdx.utils.Clipboard;

public class AndroidClipboard implements Clipboard {
    private String contents;
    Context context;

    protected AndroidClipboard(Context context) {
        this.context = context;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
