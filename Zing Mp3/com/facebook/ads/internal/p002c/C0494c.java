package com.facebook.ads.internal.p002c;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.ads.internal.util.C0806x;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: com.facebook.ads.internal.c.c */
public class C0494c {
    private static final String f564a;
    private static C0494c f565b;
    private final Context f566c;

    static {
        f564a = C0494c.class.getSimpleName();
    }

    private C0494c(Context context) {
        this.f566c = context;
    }

    public static C0494c m741a(Context context) {
        if (f565b == null) {
            Context applicationContext = context.getApplicationContext();
            synchronized (applicationContext) {
                if (f565b == null) {
                    f565b = new C0494c(applicationContext);
                }
            }
        }
        return f565b;
    }

    private static void m742a(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    @Nullable
    private Bitmap m743b(String str) {
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(new FileInputStream(str.substring("file://".length())), null, null);
            m746a(str, decodeStream);
            return decodeStream;
        } catch (Throwable e) {
            Log.e(f564a, "Failed to copy local image into cache (url=" + str + ").", e);
            return null;
        }
    }

    @Nullable
    private Bitmap m744c(String str) {
        byte[] d = C0806x.m1679a(this.f566c).m950a(str, null).m993d();
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(d, 0, d.length);
        m746a(str, decodeByteArray);
        return decodeByteArray;
    }

    @Nullable
    public Bitmap m745a(String str) {
        File file = new File(this.f566c.getCacheDir(), str.hashCode() + ".png");
        return !file.exists() ? str.startsWith("file://") ? m743b(str) : m744c(str) : BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public void m746a(String str, Bitmap bitmap) {
        Closeable byteArrayOutputStream;
        Closeable fileOutputStream;
        Throwable e;
        Closeable closeable = null;
        File file = new File(this.f566c.getCacheDir(), str.hashCode() + ".png");
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
                if (byteArrayOutputStream.size() >= 3145728) {
                    Log.d(f564a, "Bitmap size exceeds max size for storage");
                    C0494c.m742a(byteArrayOutputStream);
                    C0494c.m742a(null);
                    return;
                }
                fileOutputStream = new FileOutputStream(file);
                try {
                    byteArrayOutputStream.writeTo(fileOutputStream);
                    fileOutputStream.flush();
                    C0494c.m742a(byteArrayOutputStream);
                    C0494c.m742a(fileOutputStream);
                } catch (FileNotFoundException e2) {
                    e = e2;
                    closeable = fileOutputStream;
                    fileOutputStream = byteArrayOutputStream;
                    try {
                        Log.e(f564a, "Bad output destination (file=" + file.getAbsolutePath() + ").", e);
                        C0494c.m742a(fileOutputStream);
                        C0494c.m742a(closeable);
                    } catch (Throwable th) {
                        e = th;
                        byteArrayOutputStream = fileOutputStream;
                        C0494c.m742a(byteArrayOutputStream);
                        C0494c.m742a(closeable);
                        throw e;
                    }
                } catch (IOException e3) {
                    e = e3;
                    closeable = fileOutputStream;
                    try {
                        Log.e(f564a, "Unable to write bitmap to file (url=" + str + ").", e);
                        C0494c.m742a(byteArrayOutputStream);
                        C0494c.m742a(closeable);
                    } catch (Throwable th2) {
                        e = th2;
                        C0494c.m742a(byteArrayOutputStream);
                        C0494c.m742a(closeable);
                        throw e;
                    }
                } catch (OutOfMemoryError e4) {
                    e = e4;
                    closeable = fileOutputStream;
                    Log.e(f564a, "Unable to write bitmap to output stream", e);
                    C0494c.m742a(byteArrayOutputStream);
                    C0494c.m742a(closeable);
                } catch (Throwable th3) {
                    e = th3;
                    closeable = fileOutputStream;
                    C0494c.m742a(byteArrayOutputStream);
                    C0494c.m742a(closeable);
                    throw e;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                fileOutputStream = byteArrayOutputStream;
                Log.e(f564a, "Bad output destination (file=" + file.getAbsolutePath() + ").", e);
                C0494c.m742a(fileOutputStream);
                C0494c.m742a(closeable);
            } catch (IOException e6) {
                e = e6;
                Log.e(f564a, "Unable to write bitmap to file (url=" + str + ").", e);
                C0494c.m742a(byteArrayOutputStream);
                C0494c.m742a(closeable);
            } catch (OutOfMemoryError e7) {
                e = e7;
                Log.e(f564a, "Unable to write bitmap to output stream", e);
                C0494c.m742a(byteArrayOutputStream);
                C0494c.m742a(closeable);
            }
        } catch (FileNotFoundException e8) {
            e = e8;
            fileOutputStream = null;
            Log.e(f564a, "Bad output destination (file=" + file.getAbsolutePath() + ").", e);
            C0494c.m742a(fileOutputStream);
            C0494c.m742a(closeable);
        } catch (IOException e9) {
            e = e9;
            byteArrayOutputStream = null;
            Log.e(f564a, "Unable to write bitmap to file (url=" + str + ").", e);
            C0494c.m742a(byteArrayOutputStream);
            C0494c.m742a(closeable);
        } catch (OutOfMemoryError e10) {
            e = e10;
            byteArrayOutputStream = null;
            Log.e(f564a, "Unable to write bitmap to output stream", e);
            C0494c.m742a(byteArrayOutputStream);
            C0494c.m742a(closeable);
        } catch (Throwable th4) {
            e = th4;
            byteArrayOutputStream = null;
            C0494c.m742a(byteArrayOutputStream);
            C0494c.m742a(closeable);
            throw e;
        }
    }
}
