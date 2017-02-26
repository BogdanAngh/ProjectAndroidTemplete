package com.google.android.gms.cast;

import android.app.Presentation;
import android.content.Context;
import android.view.Display;
import android.view.Window;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.location.places.Place;

public abstract class CastPresentation extends Presentation {
    public CastPresentation(Context serviceContext, Display display) {
        super(serviceContext, display);
        zzlf();
    }

    public CastPresentation(Context serviceContext, Display display, int theme) {
        super(serviceContext, display, theme);
        zzlf();
    }

    private void zzlf() {
        Window window = getWindow();
        if (window != null) {
            window.setType(2030);
            window.addFlags(DriveFile.MODE_READ_ONLY);
            window.addFlags(16777216);
            window.addFlags(Place.TYPE_SUBLOCALITY_LEVEL_2);
        }
    }
}
