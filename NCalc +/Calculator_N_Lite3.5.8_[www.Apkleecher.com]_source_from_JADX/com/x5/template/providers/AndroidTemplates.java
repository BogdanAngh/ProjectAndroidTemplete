package com.x5.template.providers;

import android.content.Context;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.util.Scanner;

public class AndroidTemplates extends TemplateProvider {
    private Context context;
    private String themeFolder;

    public AndroidTemplates(Context androidContext) {
        this.context = null;
        this.themeFolder = "themes";
        this.context = androidContext;
    }

    public AndroidTemplates(Context androidContext, String themeFolder) {
        this.context = null;
        this.themeFolder = "themes";
        this.context = androidContext;
        this.themeFolder = themeFolder;
    }

    public String getProtocol() {
        return "android";
    }

    public String loadContainerDoc(String docName) throws IOException {
        Scanner s = new Scanner(this.context.getAssets().open(this.themeFolder + "/" + docName)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : BuildConfig.FLAVOR;
    }
}
