package com.google.android.gms.internal;

import com.mp3download.zingmp3.C1569R;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

public final class zzaqn {
    public static final zzapl bqA;
    public static final zzapk<Character> bqB;
    public static final zzapl bqC;
    public static final zzapk<String> bqD;
    public static final zzapk<BigDecimal> bqE;
    public static final zzapk<BigInteger> bqF;
    public static final zzapl bqG;
    public static final zzapk<StringBuilder> bqH;
    public static final zzapl bqI;
    public static final zzapk<StringBuffer> bqJ;
    public static final zzapl bqK;
    public static final zzapk<URL> bqL;
    public static final zzapl bqM;
    public static final zzapk<URI> bqN;
    public static final zzapl bqO;
    public static final zzapk<InetAddress> bqP;
    public static final zzapl bqQ;
    public static final zzapk<UUID> bqR;
    public static final zzapl bqS;
    public static final zzapl bqT;
    public static final zzapk<Calendar> bqU;
    public static final zzapl bqV;
    public static final zzapk<Locale> bqW;
    public static final zzapl bqX;
    public static final zzapk<zzaoy> bqY;
    public static final zzapl bqZ;
    public static final zzapk<Class> bqj;
    public static final zzapl bqk;
    public static final zzapk<BitSet> bql;
    public static final zzapl bqm;
    public static final zzapk<Boolean> bqn;
    public static final zzapk<Boolean> bqo;
    public static final zzapl bqp;
    public static final zzapk<Number> bqq;
    public static final zzapl bqr;
    public static final zzapk<Number> bqs;
    public static final zzapl bqt;
    public static final zzapk<Number> bqu;
    public static final zzapl bqv;
    public static final zzapk<Number> bqw;
    public static final zzapk<Number> bqx;
    public static final zzapk<Number> bqy;
    public static final zzapk<Number> bqz;
    public static final zzapl bra;

    /* renamed from: com.google.android.gms.internal.zzaqn.1 */
    static class C12441 extends zzapk<Class> {
        C12441() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, Class cls) throws IOException {
            if (cls == null) {
                com_google_android_gms_internal_zzaqr.bA();
            } else {
                String valueOf = String.valueOf(cls.getName());
                throw new UnsupportedOperationException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Attempted to serialize java.lang.Class: ").append(valueOf).append(". Forgot to register a type adapter?").toString());
            }
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzo(com_google_android_gms_internal_zzaqp);
        }

        public Class zzo(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.20 */
    static class AnonymousClass20 implements zzapl {
        final /* synthetic */ zzaqo bpf;
        final /* synthetic */ zzapk brd;

        AnonymousClass20(zzaqo com_google_android_gms_internal_zzaqo, zzapk com_google_android_gms_internal_zzapk) {
            this.bpf = com_google_android_gms_internal_zzaqo;
            this.brd = com_google_android_gms_internal_zzapk;
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            return com_google_android_gms_internal_zzaqo_T.equals(this.bpf) ? this.brd : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.21 */
    static class AnonymousClass21 implements zzapl {
        final /* synthetic */ zzapk brd;
        final /* synthetic */ Class bre;

        AnonymousClass21(Class cls, zzapk com_google_android_gms_internal_zzapk) {
            this.bre = cls;
            this.brd = com_google_android_gms_internal_zzapk;
        }

        public String toString() {
            String valueOf = String.valueOf(this.bre.getName());
            String valueOf2 = String.valueOf(this.brd);
            return new StringBuilder((String.valueOf(valueOf).length() + 23) + String.valueOf(valueOf2).length()).append("Factory[type=").append(valueOf).append(",adapter=").append(valueOf2).append("]").toString();
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            return com_google_android_gms_internal_zzaqo_T.bB() == this.bre ? this.brd : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.22 */
    static class AnonymousClass22 implements zzapl {
        final /* synthetic */ zzapk brd;
        final /* synthetic */ Class brf;
        final /* synthetic */ Class brg;

        AnonymousClass22(Class cls, Class cls2, zzapk com_google_android_gms_internal_zzapk) {
            this.brf = cls;
            this.brg = cls2;
            this.brd = com_google_android_gms_internal_zzapk;
        }

        public String toString() {
            String valueOf = String.valueOf(this.brg.getName());
            String valueOf2 = String.valueOf(this.brf.getName());
            String valueOf3 = String.valueOf(this.brd);
            return new StringBuilder(((String.valueOf(valueOf).length() + 24) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()).append("Factory[type=").append(valueOf).append("+").append(valueOf2).append(",adapter=").append(valueOf3).append("]").toString();
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            Class bB = com_google_android_gms_internal_zzaqo_T.bB();
            return (bB == this.brf || bB == this.brg) ? this.brd : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.24 */
    static class AnonymousClass24 implements zzapl {
        final /* synthetic */ zzapk brd;
        final /* synthetic */ Class brh;
        final /* synthetic */ Class bri;

        AnonymousClass24(Class cls, Class cls2, zzapk com_google_android_gms_internal_zzapk) {
            this.brh = cls;
            this.bri = cls2;
            this.brd = com_google_android_gms_internal_zzapk;
        }

        public String toString() {
            String valueOf = String.valueOf(this.brh.getName());
            String valueOf2 = String.valueOf(this.bri.getName());
            String valueOf3 = String.valueOf(this.brd);
            return new StringBuilder(((String.valueOf(valueOf).length() + 24) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()).append("Factory[type=").append(valueOf).append("+").append(valueOf2).append(",adapter=").append(valueOf3).append("]").toString();
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            Class bB = com_google_android_gms_internal_zzaqo_T.bB();
            return (bB == this.brh || bB == this.bri) ? this.brd : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.25 */
    static class AnonymousClass25 implements zzapl {
        final /* synthetic */ zzapk brd;
        final /* synthetic */ Class brj;

        AnonymousClass25(Class cls, zzapk com_google_android_gms_internal_zzapk) {
            this.brj = cls;
            this.brd = com_google_android_gms_internal_zzapk;
        }

        public String toString() {
            String valueOf = String.valueOf(this.brj.getName());
            String valueOf2 = String.valueOf(this.brd);
            return new StringBuilder((String.valueOf(valueOf).length() + 32) + String.valueOf(valueOf2).length()).append("Factory[typeHierarchy=").append(valueOf).append(",adapter=").append(valueOf2).append("]").toString();
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            return this.brj.isAssignableFrom(com_google_android_gms_internal_zzaqo_T.bB()) ? this.brd : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.26 */
    static /* synthetic */ class AnonymousClass26 {
        static final /* synthetic */ int[] bpW;

        static {
            bpW = new int[zzaqq.values().length];
            try {
                bpW[zzaqq.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bpW[zzaqq.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bpW[zzaqq.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bpW[zzaqq.NULL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                bpW[zzaqq.BEGIN_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                bpW[zzaqq.BEGIN_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                bpW[zzaqq.END_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                bpW[zzaqq.NAME.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                bpW[zzaqq.END_OBJECT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                bpW[zzaqq.END_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.2 */
    static class C12452 extends zzapk<Number> {
        C12452() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
            com_google_android_gms_internal_zzaqr.zza(number);
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzg(com_google_android_gms_internal_zzaqp);
        }

        public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                return Double.valueOf(com_google_android_gms_internal_zzaqp.nextDouble());
            }
            com_google_android_gms_internal_zzaqp.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.3 */
    static class C12463 extends zzapk<Number> {
        C12463() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
            com_google_android_gms_internal_zzaqr.zza(number);
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzg(com_google_android_gms_internal_zzaqp);
        }

        public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            zzaqq bq = com_google_android_gms_internal_zzaqp.bq();
            switch (AnonymousClass26.bpW[bq.ordinal()]) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    return new zzapv(com_google_android_gms_internal_zzaqp.nextString());
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                default:
                    String valueOf = String.valueOf(bq);
                    throw new zzaph(new StringBuilder(String.valueOf(valueOf).length() + 23).append("Expecting number, got: ").append(valueOf).toString());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.4 */
    static class C12474 extends zzapk<Character> {
        C12474() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, Character ch) throws IOException {
            com_google_android_gms_internal_zzaqr.zzut(ch == null ? null : String.valueOf(ch));
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzp(com_google_android_gms_internal_zzaqp);
        }

        public Character zzp(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
            String nextString = com_google_android_gms_internal_zzaqp.nextString();
            if (nextString.length() == 1) {
                return Character.valueOf(nextString.charAt(0));
            }
            String str = "Expecting character, got: ";
            nextString = String.valueOf(nextString);
            throw new zzaph(nextString.length() != 0 ? str.concat(nextString) : new String(str));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.5 */
    static class C12485 extends zzapk<String> {
        C12485() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, String str) throws IOException {
            com_google_android_gms_internal_zzaqr.zzut(str);
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzq(com_google_android_gms_internal_zzaqp);
        }

        public String zzq(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            zzaqq bq = com_google_android_gms_internal_zzaqp.bq();
            if (bq != zzaqq.NULL) {
                return bq == zzaqq.BOOLEAN ? Boolean.toString(com_google_android_gms_internal_zzaqp.nextBoolean()) : com_google_android_gms_internal_zzaqp.nextString();
            } else {
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.6 */
    static class C12496 extends zzapk<BigDecimal> {
        C12496() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, BigDecimal bigDecimal) throws IOException {
            com_google_android_gms_internal_zzaqr.zza(bigDecimal);
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzr(com_google_android_gms_internal_zzaqp);
        }

        public BigDecimal zzr(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
            try {
                return new BigDecimal(com_google_android_gms_internal_zzaqp.nextString());
            } catch (Throwable e) {
                throw new zzaph(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.7 */
    static class C12507 extends zzapk<BigInteger> {
        C12507() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, BigInteger bigInteger) throws IOException {
            com_google_android_gms_internal_zzaqr.zza(bigInteger);
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzs(com_google_android_gms_internal_zzaqp);
        }

        public BigInteger zzs(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
            try {
                return new BigInteger(com_google_android_gms_internal_zzaqp.nextString());
            } catch (Throwable e) {
                throw new zzaph(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.8 */
    static class C12518 extends zzapk<StringBuilder> {
        C12518() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, StringBuilder stringBuilder) throws IOException {
            com_google_android_gms_internal_zzaqr.zzut(stringBuilder == null ? null : stringBuilder.toString());
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzt(com_google_android_gms_internal_zzaqp);
        }

        public StringBuilder zzt(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                return new StringBuilder(com_google_android_gms_internal_zzaqp.nextString());
            }
            com_google_android_gms_internal_zzaqp.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqn.9 */
    static class C12529 extends zzapk<StringBuffer> {
        C12529() {
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, StringBuffer stringBuffer) throws IOException {
            com_google_android_gms_internal_zzaqr.zzut(stringBuffer == null ? null : stringBuffer.toString());
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzu(com_google_android_gms_internal_zzaqp);
        }

        public StringBuffer zzu(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                return new StringBuffer(com_google_android_gms_internal_zzaqp.nextString());
            }
            com_google_android_gms_internal_zzaqp.nextNull();
            return null;
        }
    }

    private static final class zza<T extends Enum<T>> extends zzapk<T> {
        private final Map<String, T> brk;
        private final Map<T, String> brl;

        public zza(Class<T> cls) {
            this.brk = new HashMap();
            this.brl = new HashMap();
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    String name = enumR.name();
                    zzapn com_google_android_gms_internal_zzapn = (zzapn) cls.getField(name).getAnnotation(zzapn.class);
                    if (com_google_android_gms_internal_zzapn != null) {
                        name = com_google_android_gms_internal_zzapn.value();
                        for (Object put : com_google_android_gms_internal_zzapn.bh()) {
                            this.brk.put(put, enumR);
                        }
                    }
                    String str = name;
                    this.brk.put(str, enumR);
                    this.brl.put(enumR, str);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, T t) throws IOException {
            com_google_android_gms_internal_zzaqr.zzut(t == null ? null : (String) this.brl.get(t));
        }

        public T zzaf(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                return (Enum) this.brk.get(com_google_android_gms_internal_zzaqp.nextString());
            }
            com_google_android_gms_internal_zzaqp.nextNull();
            return null;
        }

        public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            return zzaf(com_google_android_gms_internal_zzaqp);
        }
    }

    static {
        bqj = new C12441();
        bqk = zza(Class.class, bqj);
        bql = new zzapk<BitSet>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, BitSet bitSet) throws IOException {
                if (bitSet == null) {
                    com_google_android_gms_internal_zzaqr.bA();
                    return;
                }
                com_google_android_gms_internal_zzaqr.bw();
                for (int i = 0; i < bitSet.length(); i++) {
                    com_google_android_gms_internal_zzaqr.zzcs((long) (bitSet.get(i) ? 1 : 0));
                }
                com_google_android_gms_internal_zzaqr.bx();
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzx(com_google_android_gms_internal_zzaqp);
            }

            public BitSet zzx(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                BitSet bitSet = new BitSet();
                com_google_android_gms_internal_zzaqp.beginArray();
                zzaqq bq = com_google_android_gms_internal_zzaqp.bq();
                int i = 0;
                while (bq != zzaqq.END_ARRAY) {
                    boolean z;
                    String valueOf;
                    switch (AnonymousClass26.bpW[bq.ordinal()]) {
                        case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                            if (com_google_android_gms_internal_zzaqp.nextInt() == 0) {
                                z = false;
                                break;
                            }
                            z = true;
                            break;
                        case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                            z = com_google_android_gms_internal_zzaqp.nextBoolean();
                            break;
                        case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                            Object nextString = com_google_android_gms_internal_zzaqp.nextString();
                            try {
                                if (Integer.parseInt(nextString) == 0) {
                                    z = false;
                                    break;
                                }
                                z = true;
                                break;
                            } catch (NumberFormatException e) {
                                String str = "Error: Expecting: bitset number value (1, 0), Found: ";
                                valueOf = String.valueOf(nextString);
                                throw new zzaph(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                            }
                        default:
                            valueOf = String.valueOf(bq);
                            throw new zzaph(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Invalid bitset value type: ").append(valueOf).toString());
                    }
                    if (z) {
                        bitSet.set(i);
                    }
                    i++;
                    bq = com_google_android_gms_internal_zzaqp.bq();
                }
                com_google_android_gms_internal_zzaqp.endArray();
                return bitSet;
            }
        };
        bqm = zza(BitSet.class, bql);
        bqn = new zzapk<Boolean>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Boolean bool) throws IOException {
                if (bool == null) {
                    com_google_android_gms_internal_zzaqr.bA();
                } else {
                    com_google_android_gms_internal_zzaqr.zzdh(bool.booleanValue());
                }
            }

            public Boolean zzae(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                    return com_google_android_gms_internal_zzaqp.bq() == zzaqq.STRING ? Boolean.valueOf(Boolean.parseBoolean(com_google_android_gms_internal_zzaqp.nextString())) : Boolean.valueOf(com_google_android_gms_internal_zzaqp.nextBoolean());
                } else {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzae(com_google_android_gms_internal_zzaqp);
            }
        };
        bqo = new zzapk<Boolean>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Boolean bool) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(bool == null ? "null" : bool.toString());
            }

            public Boolean zzae(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                    return Boolean.valueOf(com_google_android_gms_internal_zzaqp.nextString());
                }
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzae(com_google_android_gms_internal_zzaqp);
            }
        };
        bqp = zza(Boolean.TYPE, Boolean.class, bqn);
        bqq = new zzapk<Number>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
                com_google_android_gms_internal_zzaqr.zza(number);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzg(com_google_android_gms_internal_zzaqp);
            }

            public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                try {
                    return Byte.valueOf((byte) com_google_android_gms_internal_zzaqp.nextInt());
                } catch (Throwable e) {
                    throw new zzaph(e);
                }
            }
        };
        bqr = zza(Byte.TYPE, Byte.class, bqq);
        bqs = new zzapk<Number>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
                com_google_android_gms_internal_zzaqr.zza(number);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzg(com_google_android_gms_internal_zzaqp);
            }

            public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                try {
                    return Short.valueOf((short) com_google_android_gms_internal_zzaqp.nextInt());
                } catch (Throwable e) {
                    throw new zzaph(e);
                }
            }
        };
        bqt = zza(Short.TYPE, Short.class, bqs);
        bqu = new zzapk<Number>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
                com_google_android_gms_internal_zzaqr.zza(number);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzg(com_google_android_gms_internal_zzaqp);
            }

            public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                try {
                    return Integer.valueOf(com_google_android_gms_internal_zzaqp.nextInt());
                } catch (Throwable e) {
                    throw new zzaph(e);
                }
            }
        };
        bqv = zza(Integer.TYPE, Integer.class, bqu);
        bqw = new zzapk<Number>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
                com_google_android_gms_internal_zzaqr.zza(number);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzg(com_google_android_gms_internal_zzaqp);
            }

            public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                try {
                    return Long.valueOf(com_google_android_gms_internal_zzaqp.nextLong());
                } catch (Throwable e) {
                    throw new zzaph(e);
                }
            }
        };
        bqx = new zzapk<Number>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Number number) throws IOException {
                com_google_android_gms_internal_zzaqr.zza(number);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzg(com_google_android_gms_internal_zzaqp);
            }

            public Number zzg(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                    return Float.valueOf((float) com_google_android_gms_internal_zzaqp.nextDouble());
                }
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
        };
        bqy = new C12452();
        bqz = new C12463();
        bqA = zza(Number.class, bqz);
        bqB = new C12474();
        bqC = zza(Character.TYPE, Character.class, bqB);
        bqD = new C12485();
        bqE = new C12496();
        bqF = new C12507();
        bqG = zza(String.class, bqD);
        bqH = new C12518();
        bqI = zza(StringBuilder.class, bqH);
        bqJ = new C12529();
        bqK = zza(StringBuffer.class, bqJ);
        bqL = new zzapk<URL>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, URL url) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(url == null ? null : url.toExternalForm());
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzv(com_google_android_gms_internal_zzaqp);
            }

            public URL zzv(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                String nextString = com_google_android_gms_internal_zzaqp.nextString();
                return !"null".equals(nextString) ? new URL(nextString) : null;
            }
        };
        bqM = zza(URL.class, bqL);
        bqN = new zzapk<URI>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, URI uri) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(uri == null ? null : uri.toASCIIString());
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzw(com_google_android_gms_internal_zzaqp);
            }

            public URI zzw(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                URI uri = null;
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                } else {
                    try {
                        String nextString = com_google_android_gms_internal_zzaqp.nextString();
                        if (!"null".equals(nextString)) {
                            uri = new URI(nextString);
                        }
                    } catch (Throwable e) {
                        throw new zzaoz(e);
                    }
                }
                return uri;
            }
        };
        bqO = zza(URI.class, bqN);
        bqP = new zzapk<InetAddress>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, InetAddress inetAddress) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(inetAddress == null ? null : inetAddress.getHostAddress());
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzy(com_google_android_gms_internal_zzaqp);
            }

            public InetAddress zzy(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                    return InetAddress.getByName(com_google_android_gms_internal_zzaqp.nextString());
                }
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
        };
        bqQ = zzb(InetAddress.class, bqP);
        bqR = new zzapk<UUID>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, UUID uuid) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(uuid == null ? null : uuid.toString());
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzz(com_google_android_gms_internal_zzaqp);
            }

            public UUID zzz(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() != zzaqq.NULL) {
                    return UUID.fromString(com_google_android_gms_internal_zzaqp.nextString());
                }
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            }
        };
        bqS = zza(UUID.class, bqR);
        bqT = new zzapl() {

            /* renamed from: com.google.android.gms.internal.zzaqn.15.1 */
            class C12431 extends zzapk<Timestamp> {
                final /* synthetic */ zzapk brb;
                final /* synthetic */ AnonymousClass15 brc;

                C12431(AnonymousClass15 anonymousClass15, zzapk com_google_android_gms_internal_zzapk) {
                    this.brc = anonymousClass15;
                    this.brb = com_google_android_gms_internal_zzapk;
                }

                public void zza(zzaqr com_google_android_gms_internal_zzaqr, Timestamp timestamp) throws IOException {
                    this.brb.zza(com_google_android_gms_internal_zzaqr, timestamp);
                }

                public Timestamp zzaa(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                    Date date = (Date) this.brb.zzb(com_google_android_gms_internal_zzaqp);
                    return date != null ? new Timestamp(date.getTime()) : null;
                }

                public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                    return zzaa(com_google_android_gms_internal_zzaqp);
                }
            }

            public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
                return com_google_android_gms_internal_zzaqo_T.bB() != Timestamp.class ? null : new C12431(this, com_google_android_gms_internal_zzaos.zzk(Date.class));
            }
        };
        bqU = new zzapk<Calendar>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Calendar calendar) throws IOException {
                if (calendar == null) {
                    com_google_android_gms_internal_zzaqr.bA();
                    return;
                }
                com_google_android_gms_internal_zzaqr.by();
                com_google_android_gms_internal_zzaqr.zzus("year");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(1));
                com_google_android_gms_internal_zzaqr.zzus("month");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(2));
                com_google_android_gms_internal_zzaqr.zzus("dayOfMonth");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(5));
                com_google_android_gms_internal_zzaqr.zzus("hourOfDay");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(11));
                com_google_android_gms_internal_zzaqr.zzus("minute");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(12));
                com_google_android_gms_internal_zzaqr.zzus("second");
                com_google_android_gms_internal_zzaqr.zzcs((long) calendar.get(13));
                com_google_android_gms_internal_zzaqr.bz();
            }

            public Calendar zzab(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                int i = 0;
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                com_google_android_gms_internal_zzaqp.beginObject();
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (com_google_android_gms_internal_zzaqp.bq() != zzaqq.END_OBJECT) {
                    String nextName = com_google_android_gms_internal_zzaqp.nextName();
                    int nextInt = com_google_android_gms_internal_zzaqp.nextInt();
                    if ("year".equals(nextName)) {
                        i6 = nextInt;
                    } else if ("month".equals(nextName)) {
                        i5 = nextInt;
                    } else if ("dayOfMonth".equals(nextName)) {
                        i4 = nextInt;
                    } else if ("hourOfDay".equals(nextName)) {
                        i3 = nextInt;
                    } else if ("minute".equals(nextName)) {
                        i2 = nextInt;
                    } else if ("second".equals(nextName)) {
                        i = nextInt;
                    }
                }
                com_google_android_gms_internal_zzaqp.endObject();
                return new GregorianCalendar(i6, i5, i4, i3, i2, i);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzab(com_google_android_gms_internal_zzaqp);
            }
        };
        bqV = zzb(Calendar.class, GregorianCalendar.class, bqU);
        bqW = new zzapk<Locale>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, Locale locale) throws IOException {
                com_google_android_gms_internal_zzaqr.zzut(locale == null ? null : locale.toString());
            }

            public Locale zzac(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                if (com_google_android_gms_internal_zzaqp.bq() == zzaqq.NULL) {
                    com_google_android_gms_internal_zzaqp.nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(com_google_android_gms_internal_zzaqp.nextString(), "_");
                String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                return (nextToken2 == null && nextToken3 == null) ? new Locale(nextToken) : nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzac(com_google_android_gms_internal_zzaqp);
            }
        };
        bqX = zza(Locale.class, bqW);
        bqY = new zzapk<zzaoy>() {
            public void zza(zzaqr com_google_android_gms_internal_zzaqr, zzaoy com_google_android_gms_internal_zzaoy) throws IOException {
                if (com_google_android_gms_internal_zzaoy == null || com_google_android_gms_internal_zzaoy.aY()) {
                    com_google_android_gms_internal_zzaqr.bA();
                } else if (com_google_android_gms_internal_zzaoy.aX()) {
                    zzape bb = com_google_android_gms_internal_zzaoy.bb();
                    if (bb.be()) {
                        com_google_android_gms_internal_zzaqr.zza(bb.aT());
                    } else if (bb.bd()) {
                        com_google_android_gms_internal_zzaqr.zzdh(bb.getAsBoolean());
                    } else {
                        com_google_android_gms_internal_zzaqr.zzut(bb.aU());
                    }
                } else if (com_google_android_gms_internal_zzaoy.aV()) {
                    com_google_android_gms_internal_zzaqr.bw();
                    Iterator it = com_google_android_gms_internal_zzaoy.ba().iterator();
                    while (it.hasNext()) {
                        zza(com_google_android_gms_internal_zzaqr, (zzaoy) it.next());
                    }
                    com_google_android_gms_internal_zzaqr.bx();
                } else if (com_google_android_gms_internal_zzaoy.aW()) {
                    com_google_android_gms_internal_zzaqr.by();
                    for (Entry entry : com_google_android_gms_internal_zzaoy.aZ().entrySet()) {
                        com_google_android_gms_internal_zzaqr.zzus((String) entry.getKey());
                        zza(com_google_android_gms_internal_zzaqr, (zzaoy) entry.getValue());
                    }
                    com_google_android_gms_internal_zzaqr.bz();
                } else {
                    String valueOf = String.valueOf(com_google_android_gms_internal_zzaoy.getClass());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 15).append("Couldn't write ").append(valueOf).toString());
                }
            }

            public zzaoy zzad(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                zzaoy com_google_android_gms_internal_zzaov;
                switch (AnonymousClass26.bpW[com_google_android_gms_internal_zzaqp.bq().ordinal()]) {
                    case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                        return new zzape(new zzapv(com_google_android_gms_internal_zzaqp.nextString()));
                    case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                        return new zzape(Boolean.valueOf(com_google_android_gms_internal_zzaqp.nextBoolean()));
                    case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                        return new zzape(com_google_android_gms_internal_zzaqp.nextString());
                    case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                        com_google_android_gms_internal_zzaqp.nextNull();
                        return zzapa.bou;
                    case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                        com_google_android_gms_internal_zzaov = new zzaov();
                        com_google_android_gms_internal_zzaqp.beginArray();
                        while (com_google_android_gms_internal_zzaqp.hasNext()) {
                            com_google_android_gms_internal_zzaov.zzc((zzaoy) zzb(com_google_android_gms_internal_zzaqp));
                        }
                        com_google_android_gms_internal_zzaqp.endArray();
                        return com_google_android_gms_internal_zzaov;
                    case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                        com_google_android_gms_internal_zzaov = new zzapb();
                        com_google_android_gms_internal_zzaqp.beginObject();
                        while (com_google_android_gms_internal_zzaqp.hasNext()) {
                            com_google_android_gms_internal_zzaov.zza(com_google_android_gms_internal_zzaqp.nextName(), (zzaoy) zzb(com_google_android_gms_internal_zzaqp));
                        }
                        com_google_android_gms_internal_zzaqp.endObject();
                        return com_google_android_gms_internal_zzaov;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            public /* synthetic */ Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
                return zzad(com_google_android_gms_internal_zzaqp);
            }
        };
        bqZ = zzb(zzaoy.class, bqY);
        bra = new zzapl() {
            public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
                Class bB = com_google_android_gms_internal_zzaqo_T.bB();
                if (!Enum.class.isAssignableFrom(bB) || bB == Enum.class) {
                    return null;
                }
                if (!bB.isEnum()) {
                    bB = bB.getSuperclass();
                }
                return new zza(bB);
            }
        };
    }

    public static <TT> zzapl zza(zzaqo<TT> com_google_android_gms_internal_zzaqo_TT, zzapk<TT> com_google_android_gms_internal_zzapk_TT) {
        return new AnonymousClass20(com_google_android_gms_internal_zzaqo_TT, com_google_android_gms_internal_zzapk_TT);
    }

    public static <TT> zzapl zza(Class<TT> cls, zzapk<TT> com_google_android_gms_internal_zzapk_TT) {
        return new AnonymousClass21(cls, com_google_android_gms_internal_zzapk_TT);
    }

    public static <TT> zzapl zza(Class<TT> cls, Class<TT> cls2, zzapk<? super TT> com_google_android_gms_internal_zzapk__super_TT) {
        return new AnonymousClass22(cls, cls2, com_google_android_gms_internal_zzapk__super_TT);
    }

    public static <TT> zzapl zzb(Class<TT> cls, zzapk<TT> com_google_android_gms_internal_zzapk_TT) {
        return new AnonymousClass25(cls, com_google_android_gms_internal_zzapk_TT);
    }

    public static <TT> zzapl zzb(Class<TT> cls, Class<? extends TT> cls2, zzapk<? super TT> com_google_android_gms_internal_zzapk__super_TT) {
        return new AnonymousClass24(cls, cls2, com_google_android_gms_internal_zzapk__super_TT);
    }
}
