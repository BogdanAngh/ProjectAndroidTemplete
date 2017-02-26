package com.x5.util;

import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarResource {
    public static InputStream peekInsideJar(String jar, String resourcePath) {
        try {
            InputStream in = new URL(jar + "!" + resourcePath).openStream();
            if (in != null) {
                return in;
            }
        } catch (MalformedURLException e) {
        } catch (IOException e2) {
        } catch (AccessControlException e3) {
        }
        try {
            String zipPath = jar.replaceFirst("^jar:file:", BuildConfig.FLAVOR);
            String zipResourcePath = resourcePath.replaceFirst("^/", BuildConfig.FLAVOR);
            ZipFile zipFile = new ZipFile(zipPath);
            ZipEntry entry = zipFile.getEntry(zipResourcePath);
            if (entry != null) {
                return zipFile.getInputStream(entry);
            }
        } catch (IOException e4) {
        } catch (AccessControlException e5) {
        }
        return null;
    }
}
