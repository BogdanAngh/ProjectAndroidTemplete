package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzji;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@zzji
public final class LargeParcelTeleporter extends AbstractSafeParcelable {
    public static final Creator<LargeParcelTeleporter> CREATOR;
    final int mVersionCode;
    ParcelFileDescriptor zzcme;
    private Parcelable zzcmf;
    private boolean zzcmg;

    /* renamed from: com.google.android.gms.ads.internal.request.LargeParcelTeleporter.1 */
    class C10961 implements Runnable {
        final /* synthetic */ OutputStream zzcmh;
        final /* synthetic */ byte[] zzcmi;
        final /* synthetic */ LargeParcelTeleporter zzcmj;

        C10961(LargeParcelTeleporter largeParcelTeleporter, OutputStream outputStream, byte[] bArr) {
            this.zzcmj = largeParcelTeleporter;
            this.zzcmh = outputStream;
            this.zzcmi = bArr;
        }

        public void run() {
            Closeable dataOutputStream;
            Throwable e;
            try {
                dataOutputStream = new DataOutputStream(this.zzcmh);
                try {
                    dataOutputStream.writeInt(this.zzcmi.length);
                    dataOutputStream.write(this.zzcmi);
                    zzo.zzb(dataOutputStream);
                } catch (IOException e2) {
                    e = e2;
                    try {
                        zzb.zzb("Error transporting the ad response", e);
                        zzu.zzgq().zza(e, "LargeParcelTeleporter.pipeData.1");
                        if (dataOutputStream != null) {
                            zzo.zzb(this.zzcmh);
                        } else {
                            zzo.zzb(dataOutputStream);
                        }
                    } catch (Throwable th) {
                        e = th;
                        if (dataOutputStream != null) {
                            zzo.zzb(this.zzcmh);
                        } else {
                            zzo.zzb(dataOutputStream);
                        }
                        throw e;
                    }
                }
            } catch (IOException e3) {
                e = e3;
                dataOutputStream = null;
                zzb.zzb("Error transporting the ad response", e);
                zzu.zzgq().zza(e, "LargeParcelTeleporter.pipeData.1");
                if (dataOutputStream != null) {
                    zzo.zzb(dataOutputStream);
                } else {
                    zzo.zzb(this.zzcmh);
                }
            } catch (Throwable th2) {
                e = th2;
                dataOutputStream = null;
                if (dataOutputStream != null) {
                    zzo.zzb(dataOutputStream);
                } else {
                    zzo.zzb(this.zzcmh);
                }
                throw e;
            }
        }
    }

    static {
        CREATOR = new zzm();
    }

    LargeParcelTeleporter(int i, ParcelFileDescriptor parcelFileDescriptor) {
        this.mVersionCode = i;
        this.zzcme = parcelFileDescriptor;
        this.zzcmf = null;
        this.zzcmg = true;
    }

    public LargeParcelTeleporter(SafeParcelable safeParcelable) {
        this.mVersionCode = 1;
        this.zzcme = null;
        this.zzcmf = safeParcelable;
        this.zzcmg = false;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzcme == null) {
            Parcel obtain = Parcel.obtain();
            try {
                this.zzcmf.writeToParcel(obtain, 0);
                byte[] marshall = obtain.marshall();
                this.zzcme = zzj(marshall);
            } finally {
                obtain.recycle();
            }
        }
        zzm.zza(this, parcel, i);
    }

    public <T extends SafeParcelable> T zza(Creator<T> creator) {
        if (this.zzcmg) {
            if (this.zzcme == null) {
                zzb.m1695e("File descriptor is empty, returning null.");
                return null;
            }
            Closeable dataInputStream = new DataInputStream(new AutoCloseInputStream(this.zzcme));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr, 0, bArr.length);
                zzo.zzb(dataInputStream);
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.unmarshall(bArr, 0, bArr.length);
                    obtain.setDataPosition(0);
                    this.zzcmf = (SafeParcelable) creator.createFromParcel(obtain);
                    this.zzcmg = false;
                } finally {
                    obtain.recycle();
                }
            } catch (Throwable e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th) {
                zzo.zzb(dataInputStream);
            }
        }
        return (SafeParcelable) this.zzcmf;
    }

    protected <T> ParcelFileDescriptor zzj(byte[] bArr) {
        Closeable autoCloseOutputStream;
        Throwable e;
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            autoCloseOutputStream = new AutoCloseOutputStream(createPipe[1]);
            try {
                new Thread(new C10961(this, autoCloseOutputStream, bArr)).start();
                return createPipe[0];
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            autoCloseOutputStream = parcelFileDescriptor;
            zzb.zzb("Error transporting the ad response", e);
            zzu.zzgq().zza(e, "LargeParcelTeleporter.pipeData.2");
            zzo.zzb(autoCloseOutputStream);
            return parcelFileDescriptor;
        }
    }
}
