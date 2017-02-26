package org.apache.commons.math4.random;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.MathParseException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.FastMath;

public class SobolSequenceGenerator implements RandomVectorGenerator {
    private static final int BITS = 52;
    private static final String FILE_CHARSET = "US-ASCII";
    private static final int MAX_DIMENSION = 1000;
    private static final String RESOURCE_NAME = "/assets/org/apache/commons/math4/random/new-joe-kuo-6.1000";
    private static final double SCALE;
    private int count;
    private final int dimension;
    private final long[][] direction;
    private final long[] x;

    static {
        SCALE = FastMath.pow(2.0d, (int) BITS);
    }

    public SobolSequenceGenerator(int dimension) throws OutOfRangeException {
        this.count = 0;
        if (dimension < 1 || dimension > MAX_DIMENSION) {
            throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(MAX_DIMENSION));
        }
        InputStream is = getClass().getResourceAsStream(RESOURCE_NAME);
        if (is == null) {
            throw new MathInternalError();
        }
        this.dimension = dimension;
        this.direction = (long[][]) Array.newInstance(Long.TYPE, new int[]{dimension, 53});
        this.x = new long[dimension];
        try {
            initFromStream(is);
            try {
                is.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            throw new MathInternalError();
        } catch (MathParseException e3) {
            throw new MathInternalError();
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e4) {
            }
        }
    }

    public SobolSequenceGenerator(int dimension, InputStream is) throws NotStrictlyPositiveException, MathParseException, IOException {
        this.count = 0;
        if (dimension < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(dimension));
        }
        this.dimension = dimension;
        this.direction = (long[][]) Array.newInstance(Long.TYPE, new int[]{dimension, 53});
        this.x = new long[dimension];
        int lastDimension = initFromStream(is);
        if (lastDimension < dimension) {
            throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(lastDimension));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int initFromStream(java.io.InputStream r21) throws org.apache.commons.math4.exception.MathParseException, java.io.IOException {
        /*
        r20 = this;
        r7 = 1;
    L_0x0001:
        r16 = 52;
        r0 = r16;
        if (r7 <= r0) goto L_0x0031;
    L_0x0007:
        r16 = "US-ASCII";
        r3 = java.nio.charset.Charset.forName(r16);
        r13 = new java.io.BufferedReader;
        r16 = new java.io.InputStreamReader;
        r0 = r16;
        r1 = r21;
        r0.<init>(r1, r3);
        r0 = r16;
        r13.<init>(r0);
        r4 = -1;
        r13.readLine();	 Catch:{ all -> 0x00ad }
        r11 = 2;
        r8 = 1;
        r10 = 0;
        r9 = r8;
    L_0x0025:
        r10 = r13.readLine();	 Catch:{ all -> 0x00ad }
        if (r10 != 0) goto L_0x0046;
    L_0x002b:
        r13.close();
        r8 = r9;
        r5 = r4;
    L_0x0030:
        return r5;
    L_0x0031:
        r0 = r20;
        r0 = r0.direction;
        r16 = r0;
        r17 = 0;
        r16 = r16[r17];
        r18 = 1;
        r17 = 52 - r7;
        r18 = r18 << r17;
        r16[r7] = r18;
        r7 = r7 + 1;
        goto L_0x0001;
    L_0x0046:
        r15 = new java.util.StringTokenizer;	 Catch:{ all -> 0x00ad }
        r16 = " ";
        r0 = r16;
        r15.<init>(r10, r0);	 Catch:{ all -> 0x00ad }
        r16 = r15.nextToken();	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r4 = java.lang.Integer.parseInt(r16);	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r16 = 2;
        r0 = r16;
        if (r4 < r0) goto L_0x00c5;
    L_0x005d:
        r0 = r20;
        r0 = r0.dimension;	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r16 = r0;
        r0 = r16;
        if (r4 > r0) goto L_0x00c5;
    L_0x0067:
        r16 = r15.nextToken();	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r14 = java.lang.Integer.parseInt(r16);	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r16 = r15.nextToken();	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r2 = java.lang.Integer.parseInt(r16);	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r16 = r14 + 1;
        r0 = r16;
        r12 = new int[r0];	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r7 = 1;
    L_0x007e:
        if (r7 <= r14) goto L_0x0096;
    L_0x0080:
        r8 = r9 + 1;
        r0 = r20;
        r0.initDirectionVector(r9, r2, r12);	 Catch:{ NoSuchElementException -> 0x00c3, NumberFormatException -> 0x00c1 }
    L_0x0087:
        r0 = r20;
        r0 = r0.dimension;	 Catch:{ NoSuchElementException -> 0x00c3, NumberFormatException -> 0x00c1 }
        r16 = r0;
        r0 = r16;
        if (r4 <= r0) goto L_0x00bc;
    L_0x0091:
        r13.close();
        r5 = r4;
        goto L_0x0030;
    L_0x0096:
        r16 = r15.nextToken();	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r16 = java.lang.Integer.parseInt(r16);	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r12[r7] = r16;	 Catch:{ NoSuchElementException -> 0x00a3, NumberFormatException -> 0x00b2 }
        r7 = r7 + 1;
        goto L_0x007e;
    L_0x00a3:
        r6 = move-exception;
        r8 = r9;
    L_0x00a5:
        r16 = new org.apache.commons.math4.exception.MathParseException;	 Catch:{ all -> 0x00ad }
        r0 = r16;
        r0.<init>(r10, r11);	 Catch:{ all -> 0x00ad }
        throw r16;	 Catch:{ all -> 0x00ad }
    L_0x00ad:
        r16 = move-exception;
        r13.close();
        throw r16;
    L_0x00b2:
        r6 = move-exception;
        r8 = r9;
    L_0x00b4:
        r16 = new org.apache.commons.math4.exception.MathParseException;	 Catch:{ all -> 0x00ad }
        r0 = r16;
        r0.<init>(r10, r11);	 Catch:{ all -> 0x00ad }
        throw r16;	 Catch:{ all -> 0x00ad }
    L_0x00bc:
        r11 = r11 + 1;
        r9 = r8;
        goto L_0x0025;
    L_0x00c1:
        r6 = move-exception;
        goto L_0x00b4;
    L_0x00c3:
        r6 = move-exception;
        goto L_0x00a5;
    L_0x00c5:
        r8 = r9;
        goto L_0x0087;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.random.SobolSequenceGenerator.initFromStream(java.io.InputStream):int");
    }

    private void initDirectionVector(int d, int a, int[] m) {
        int i;
        int s = m.length - 1;
        for (i = 1; i <= s; i++) {
            this.direction[d][i] = ((long) m[i]) << (52 - i);
        }
        for (i = s + 1; i <= BITS; i++) {
            this.direction[d][i] = this.direction[d][i - s] ^ (this.direction[d][i - s] >> s);
            for (int k = 1; k <= s - 1; k++) {
                long[] jArr = this.direction[d];
                jArr[i] = jArr[i] ^ (((long) ((a >> ((s - 1) - k)) & 1)) * this.direction[d][i - k]);
            }
        }
    }

    public double[] nextVector() {
        double[] v = new double[this.dimension];
        if (this.count == 0) {
            this.count++;
        } else {
            int c = 1;
            int value = this.count - 1;
            while ((value & 1) == 1) {
                value >>= 1;
                c++;
            }
            for (int i = 0; i < this.dimension; i++) {
                long[] jArr = this.x;
                jArr[i] = jArr[i] ^ this.direction[i][c];
                v[i] = ((double) this.x[i]) / SCALE;
            }
            this.count++;
        }
        return v;
    }

    public double[] skipTo(int index) throws NotPositiveException {
        if (index == 0) {
            Arrays.fill(this.x, 0);
        } else {
            int i = index - 1;
            long grayCode = (long) ((i >> 1) ^ i);
            for (int j = 0; j < this.dimension; j++) {
                long result = 0;
                for (int k = 1; k <= BITS; k++) {
                    long shift = grayCode >> (k - 1);
                    if (shift == 0) {
                        break;
                    }
                    result ^= this.direction[j][k] * (shift & 1);
                }
                this.x[j] = result;
            }
        }
        this.count = index;
        return nextVector();
    }

    public int getNextIndex() {
        return this.count;
    }
}
