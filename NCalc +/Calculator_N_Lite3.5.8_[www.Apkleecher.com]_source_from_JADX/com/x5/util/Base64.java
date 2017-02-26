package com.x5.util;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.google.common.base.Ascii;
import io.github.kexanie.library.BuildConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class Base64 {
    private static final byte[] ALPHABET;
    private static final byte[] DECODABET;
    public static final boolean DECODE = false;
    public static final boolean ENCODE = true;
    private static final byte EQUALS_SIGN = (byte) 61;
    private static final byte EQUALS_SIGN_ENC = (byte) -1;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = (byte) 10;
    private static final byte WHITE_SPACE_ENC = (byte) -5;

    public static class InputStream extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int position;

        public InputStream(java.io.InputStream in) {
            this(in, Base64.DECODE);
        }

        public InputStream(java.io.InputStream in, boolean encode) {
            this(in, encode, Base64.ENCODE);
        }

        public InputStream(java.io.InputStream in, boolean encode, boolean breakLines) {
            super(in);
            this.breakLines = breakLines;
            this.encode = encode;
            this.bufferLength = encode ? 4 : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
        }

        public int read() throws IOException {
            int b;
            if (this.position < 0) {
                int i;
                if (this.encode) {
                    byte[] b3 = new byte[3];
                    int numBinaryBytes = 0;
                    for (i = 0; i < 3; i++) {
                        try {
                            b = this.in.read();
                            if (b >= 0) {
                                b3[i] = (byte) b;
                                numBinaryBytes++;
                            }
                        } catch (IOException e) {
                            if (i == 0) {
                                throw e;
                            }
                        }
                    }
                    if (numBinaryBytes <= 0) {
                        return -1;
                    }
                    Base64.encode3to4(b3, 0, numBinaryBytes, this.buffer, 0);
                    this.position = 0;
                    this.numSigBytes = 4;
                } else {
                    byte[] b4 = new byte[4];
                    i = 0;
                    while (i < 4) {
                        do {
                            b = this.in.read();
                            if (b < 0) {
                                break;
                            }
                        } while (Base64.DECODABET[b & TransportMediator.KEYCODE_MEDIA_PAUSE] <= -5);
                        if (b < 0) {
                            break;
                        }
                        b4[i] = (byte) b;
                        i++;
                    }
                    if (i == 4) {
                        this.numSigBytes = Base64.decode4to3(b4, 0, this.buffer, 0);
                        this.position = 0;
                    } else if (i == 0) {
                        return -1;
                    } else {
                        throw new IOException("Improperly padded Base64 input.");
                    }
                }
            }
            if (this.position < 0) {
                throw new IOException("Error in Base64 code reading stream.");
            } else if (this.position >= this.numSigBytes) {
                return -1;
            } else {
                if (this.encode && this.breakLines && this.lineLength >= Base64.MAX_LINE_LENGTH) {
                    this.lineLength = 0;
                    return 10;
                }
                this.lineLength++;
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                b = bArr[i2];
                if (this.position >= this.bufferLength) {
                    this.position = -1;
                }
                return b & MotionEventCompat.ACTION_MASK;
            }
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i = 0;
            while (i < len) {
                int b = read();
                if (b >= 0) {
                    dest[off + i] = (byte) b;
                    i++;
                } else if (i == 0) {
                    return -1;
                } else {
                    return i;
                }
            }
            return i;
        }
    }

    public static class OutputStream extends FilterOutputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private boolean encode;
        private int lineLength;
        private int position;

        public OutputStream(java.io.OutputStream out) {
            this(out, Base64.ENCODE);
        }

        public OutputStream(java.io.OutputStream out, boolean encode) {
            this(out, encode, Base64.ENCODE);
        }

        public OutputStream(java.io.OutputStream out, boolean encode, boolean breakLines) {
            super(out);
            this.breakLines = breakLines;
            this.encode = encode;
            this.bufferLength = encode ? 3 : 4;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
        }

        public void write(int theByte) throws IOException {
            byte[] bArr;
            int i;
            if (this.encode) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.buffer, this.bufferLength));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= Base64.MAX_LINE_LENGTH) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else if (Base64.DECODABET[theByte & TransportMediator.KEYCODE_MEDIA_PAUSE] > Base64.WHITE_SPACE_ENC) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64.decode4to3(this.buffer));
                    this.position = 0;
                }
            } else if (Base64.DECODABET[theByte & TransportMediator.KEYCODE_MEDIA_PAUSE] != Base64.WHITE_SPACE_ENC) {
                throw new IOException("Invalid character in Base64 data.");
            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            for (int i = 0; i < len; i++) {
                write(theBytes[off + i]);
            }
        }

        public void flush() throws IOException {
            super.flush();
            if (this.position > 0) {
                if (this.encode) {
                    this.out.write(Base64.encode3to4(this.buffer, this.position));
                    this.position = 0;
                } else {
                    throw new IOException("Base64 input not properly padded.");
                }
            }
            this.out.flush();
        }

        public void close() throws IOException {
            super.close();
            this.out.close();
            this.buffer = null;
            this.out = null;
        }
    }

    static {
        ALPHABET = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        DECODABET = new byte[]{(byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, (byte) -9, (byte) -9, WHITE_SPACE_ENC, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, WHITE_SPACE_ENC, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) 62, (byte) -9, (byte) -9, (byte) -9, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, EQUALS_SIGN, (byte) -9, (byte) -9, (byte) -9, EQUALS_SIGN_ENC, (byte) -9, (byte) -9, (byte) -9, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, NEW_LINE, Ascii.VT, Ascii.FF, Ascii.CR, Ascii.SO, Ascii.SI, Ascii.DLE, Ascii.XON, Ascii.DC2, Ascii.XOFF, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, (byte) -9, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, Ascii.SPACE, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) -9, (byte) -9, (byte) -9, (byte) -9};
    }

    private Base64() {
    }

    private static byte[] encode3to4(byte[] threeBytes) {
        return encode3to4(threeBytes, 3);
    }

    private static byte[] encode3to4(byte[] threeBytes, int numSigBytes) {
        byte[] dest = new byte[4];
        encode3to4(threeBytes, 0, numSigBytes, dest, 0);
        return dest;
    }

    private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset) {
        int i;
        int i2 = 0;
        if (numSigBytes > 0) {
            i = (source[srcOffset] << 24) >>> 8;
        } else {
            i = 0;
        }
        int i3 = (numSigBytes > 1 ? (source[srcOffset + 1] << 24) >>> 16 : 0) | i;
        if (numSigBytes > 2) {
            i2 = (source[srcOffset + 2] << 24) >>> 24;
        }
        int inBuff = i3 | i2;
        switch (numSigBytes) {
            case ValueServer.REPLAY_MODE /*1*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case IExpr.DOUBLEID /*2*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                break;
        }
        return destination;
    }

    public static String encodeObject(Serializable serializableObject) {
        return encodeObject(serializableObject, ENCODE);
    }

    public static String encodeObject(Serializable serializableObject, boolean breakLines) {
        IOException e;
        Throwable th;
        ByteArrayOutputStream baos = null;
        java.io.OutputStream b64os = null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                java.io.OutputStream b64os2 = new OutputStream(baos2, ENCODE, breakLines);
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(b64os2);
                    try {
                        oos2.writeObject(serializableObject);
                        try {
                            oos2.close();
                        } catch (Exception e2) {
                        }
                        try {
                            b64os2.close();
                        } catch (Exception e3) {
                        }
                        try {
                            baos2.close();
                        } catch (Exception e4) {
                        }
                        oos = oos2;
                        b64os = b64os2;
                        baos = baos2;
                        return new String(baos2.toByteArray());
                    } catch (IOException e5) {
                        e = e5;
                        oos = oos2;
                        b64os = b64os2;
                        baos = baos2;
                        try {
                            e.printStackTrace();
                            try {
                                oos.close();
                            } catch (Exception e6) {
                            }
                            try {
                                b64os.close();
                            } catch (Exception e7) {
                            }
                            try {
                                baos.close();
                                return null;
                            } catch (Exception e8) {
                                return null;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                oos.close();
                            } catch (Exception e9) {
                            }
                            try {
                                b64os.close();
                            } catch (Exception e10) {
                            }
                            try {
                                baos.close();
                            } catch (Exception e11) {
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        oos = oos2;
                        b64os = b64os2;
                        baos = baos2;
                        oos.close();
                        b64os.close();
                        baos.close();
                        throw th;
                    }
                } catch (IOException e12) {
                    e = e12;
                    b64os = b64os2;
                    baos = baos2;
                    e.printStackTrace();
                    oos.close();
                    b64os.close();
                    baos.close();
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    b64os = b64os2;
                    baos = baos2;
                    oos.close();
                    b64os.close();
                    baos.close();
                    throw th;
                }
            } catch (IOException e13) {
                e = e13;
                baos = baos2;
                e.printStackTrace();
                oos.close();
                b64os.close();
                baos.close();
                return null;
            } catch (Throwable th5) {
                th = th5;
                baos = baos2;
                oos.close();
                b64os.close();
                baos.close();
                throw th;
            }
        } catch (IOException e14) {
            e = e14;
            e.printStackTrace();
            oos.close();
            b64os.close();
            baos.close();
            return null;
        }
    }

    public static String encodeBytes(byte[] source) {
        return encodeBytes(source, ENCODE);
    }

    public static String encodeBytes(byte[] source, boolean breakLines) {
        return encodeBytes(source, 0, source.length, breakLines);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        return encodeBytes(source, off, len, ENCODE);
    }

    public static String encodeBytes(byte[] source, int off, int len, boolean breakLines) {
        int i;
        int len43 = (len * 4) / 3;
        if (len % 3 > 0) {
            i = 4;
        } else {
            i = 0;
        }
        int i2 = len43 + i;
        if (breakLines) {
            i = len43 / MAX_LINE_LENGTH;
        } else {
            i = 0;
        }
        byte[] outBuff = new byte[(i + i2)];
        int d = 0;
        int e = 0;
        int len2 = len - 2;
        int lineLength = 0;
        while (d < len2) {
            encode3to4(source, d + off, 3, outBuff, e);
            lineLength += 4;
            if (breakLines && lineLength == MAX_LINE_LENGTH) {
                outBuff[e + 4] = NEW_LINE;
                e++;
                lineLength = 0;
            }
            d += 3;
            e += 4;
        }
        if (d < len) {
            encode3to4(source, d + off, len - d, outBuff, e);
            e += 4;
        }
        return new String(outBuff, 0, e);
    }

    public static String encodeString(String s) {
        return encodeString(s, ENCODE);
    }

    public static String encodeString(String s, boolean breakLines) {
        return encodeBytes(s.getBytes(), breakLines);
    }

    private static byte[] decode4to3(byte[] fourBytes) {
        byte[] outBuff1 = new byte[3];
        int count = decode4to3(fourBytes, 0, outBuff1, 0);
        byte[] outBuff2 = new byte[count];
        for (int i = 0; i < count; i++) {
            outBuff2[i] = outBuff1[i];
        }
        return outBuff2;
    }

    private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset) {
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            destination[destOffset] = (byte) ((((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + 1]] & MotionEventCompat.ACTION_MASK) << 12)) >>> 16);
            return 1;
        } else if (source[srcOffset + 3] == EQUALS_SIGN) {
            outBuff = (((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + 1]] & MotionEventCompat.ACTION_MASK) << 12)) | ((DECODABET[source[srcOffset + 2]] & MotionEventCompat.ACTION_MASK) << 6);
            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        } else {
            try {
                outBuff = ((((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + 1]] & MotionEventCompat.ACTION_MASK) << 12)) | ((DECODABET[source[srcOffset + 2]] & MotionEventCompat.ACTION_MASK) << 6)) | (DECODABET[source[srcOffset + 3]] & MotionEventCompat.ACTION_MASK);
                destination[destOffset] = (byte) (outBuff >> 16);
                destination[destOffset + 1] = (byte) (outBuff >> 8);
                destination[destOffset + 2] = (byte) outBuff;
                return 3;
            } catch (Exception e) {
                System.out.println(BuildConfig.FLAVOR + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
                System.out.println(BuildConfig.FLAVOR + source[srcOffset + 1] + ": " + DECODABET[source[srcOffset + 1]]);
                System.out.println(BuildConfig.FLAVOR + source[srcOffset + 2] + ": " + DECODABET[source[srcOffset + 2]]);
                System.out.println(BuildConfig.FLAVOR + source[srcOffset + 3] + ": " + DECODABET[source[srcOffset + 3]]);
                return -1;
            }
        }
    }

    public static byte[] decode(String s) {
        byte[] bytes = s.getBytes();
        return decode(bytes, 0, bytes.length);
    }

    public static String decodeToString(String s) {
        return new String(decode(s));
    }

    public static Object decodeToObject(String encodedObject) {
        IOException e;
        Throwable th;
        ClassNotFoundException e2;
        Object obj = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            ObjectInputStream ois2;
            ByteArrayInputStream bais2 = new ByteArrayInputStream(decode(encodedObject));
            try {
                ois2 = new ObjectInputStream(bais2);
            } catch (IOException e3) {
                e = e3;
                bais = bais2;
                try {
                    e.printStackTrace();
                    try {
                        bais.close();
                    } catch (Exception e4) {
                    }
                    try {
                        ois.close();
                    } catch (Exception e5) {
                    }
                    return obj;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        bais.close();
                    } catch (Exception e6) {
                    }
                    try {
                        ois.close();
                    } catch (Exception e7) {
                    }
                    throw th;
                }
            } catch (ClassNotFoundException e8) {
                e2 = e8;
                bais = bais2;
                e2.printStackTrace();
                try {
                    bais.close();
                } catch (Exception e9) {
                }
                try {
                    ois.close();
                } catch (Exception e10) {
                }
                return obj;
            } catch (Throwable th3) {
                th = th3;
                bais = bais2;
                bais.close();
                ois.close();
                throw th;
            }
            try {
                obj = ois2.readObject();
                try {
                    bais2.close();
                } catch (Exception e11) {
                }
                try {
                    ois2.close();
                } catch (Exception e12) {
                }
                ois = ois2;
                bais = bais2;
            } catch (IOException e13) {
                e = e13;
                ois = ois2;
                bais = bais2;
                e.printStackTrace();
                bais.close();
                ois.close();
                return obj;
            } catch (ClassNotFoundException e14) {
                e2 = e14;
                ois = ois2;
                bais = bais2;
                e2.printStackTrace();
                bais.close();
                ois.close();
                return obj;
            } catch (Throwable th4) {
                th = th4;
                ois = ois2;
                bais = bais2;
                bais.close();
                ois.close();
                throw th;
            }
        } catch (IOException e15) {
            e = e15;
            e.printStackTrace();
            bais.close();
            ois.close();
            return obj;
        } catch (ClassNotFoundException e16) {
            e2 = e16;
            e2.printStackTrace();
            bais.close();
            ois.close();
            return obj;
        }
        return obj;
    }

    public static byte[] decode(byte[] source, int off, int len) {
        int b4Posn;
        byte[] outBuff = new byte[((len * 3) / 4)];
        int outBuffPosn = 0;
        byte[] b4 = new byte[4];
        int i = 0;
        int b4Posn2 = 0;
        while (i < len) {
            byte sbiCrop = (byte) (source[i] & TransportMediator.KEYCODE_MEDIA_PAUSE);
            byte sbiDecode = DECODABET[sbiCrop];
            if (sbiDecode >= -5) {
                if (sbiDecode >= -1) {
                    b4Posn = b4Posn2 + 1;
                    b4[b4Posn2] = sbiCrop;
                    if (b4Posn > 3) {
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
                        b4Posn = 0;
                        if (sbiCrop == 61) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    b4Posn = b4Posn2;
                }
                i++;
                b4Posn2 = b4Posn;
            } else {
                System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
                b4Posn = b4Posn2;
                return null;
            }
        }
        b4Posn = b4Posn2;
        byte[] out = new byte[outBuffPosn];
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
        return out;
    }
}
