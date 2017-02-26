package com.badlogic.gdx.graphics;

import android.support.v4.view.MotionEventCompat;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DeflaterOutputStream;

public class PixmapIO {

    private static class CIM {
        private static final int BUFFER_SIZE = 32000;
        private static final byte[] readBuffer;
        private static final byte[] writeBuffer;

        private CIM() {
        }

        static {
            writeBuffer = new byte[BUFFER_SIZE];
            readBuffer = new byte[BUFFER_SIZE];
        }

        public static void write(FileHandle file, Pixmap pixmap) {
            Exception e;
            Throwable th;
            DataOutputStream dataOutputStream = null;
            try {
                DataOutputStream out = new DataOutputStream(new DeflaterOutputStream(file.write(false)));
                try {
                    out.writeInt(pixmap.getWidth());
                    out.writeInt(pixmap.getHeight());
                    out.writeInt(Format.toGdx2DPixmapFormat(pixmap.getFormat()));
                    ByteBuffer pixelBuf = pixmap.getPixels();
                    pixelBuf.position(0);
                    pixelBuf.limit(pixelBuf.capacity());
                    int remainingBytes = pixelBuf.capacity() % BUFFER_SIZE;
                    int iterations = pixelBuf.capacity() / BUFFER_SIZE;
                    synchronized (writeBuffer) {
                        for (int i = 0; i < iterations; i++) {
                            pixelBuf.get(writeBuffer);
                            out.write(writeBuffer);
                        }
                        pixelBuf.get(writeBuffer, 0, remainingBytes);
                        out.write(writeBuffer, 0, remainingBytes);
                    }
                    pixelBuf.position(0);
                    pixelBuf.limit(pixelBuf.capacity());
                    StreamUtils.closeQuietly(out);
                } catch (Exception e2) {
                    e = e2;
                    dataOutputStream = out;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = out;
                    StreamUtils.closeQuietly(dataOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    throw new GdxRuntimeException("Couldn't write Pixmap to file '" + file + "'", e);
                } catch (Throwable th3) {
                    th = th3;
                    StreamUtils.closeQuietly(dataOutputStream);
                    throw th;
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.badlogic.gdx.graphics.Pixmap read(com.badlogic.gdx.files.FileHandle r12) {
            /*
            r3 = 0;
            r4 = new java.io.DataInputStream;	 Catch:{ Exception -> 0x0087 }
            r9 = new java.util.zip.InflaterInputStream;	 Catch:{ Exception -> 0x0087 }
            r10 = new java.io.BufferedInputStream;	 Catch:{ Exception -> 0x0087 }
            r11 = r12.read();	 Catch:{ Exception -> 0x0087 }
            r10.<init>(r11);	 Catch:{ Exception -> 0x0087 }
            r9.<init>(r10);	 Catch:{ Exception -> 0x0087 }
            r4.<init>(r9);	 Catch:{ Exception -> 0x0087 }
            r8 = r4.readInt();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r2 = r4.readInt();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r9 = r4.readInt();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r1 = com.badlogic.gdx.graphics.Pixmap.Format.fromGdx2DPixmapFormat(r9);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r6 = new com.badlogic.gdx.graphics.Pixmap;	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r6.<init>(r8, r2, r1);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r5 = r6.getPixels();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r9 = 0;
            r5.position(r9);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r9 = r5.capacity();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r5.limit(r9);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r10 = readBuffer;	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            monitor-enter(r10);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r7 = 0;
        L_0x003c:
            r9 = readBuffer;	 Catch:{ all -> 0x004b }
            r7 = r4.read(r9);	 Catch:{ all -> 0x004b }
            if (r7 <= 0) goto L_0x0074;
        L_0x0044:
            r9 = readBuffer;	 Catch:{ all -> 0x004b }
            r11 = 0;
            r5.put(r9, r11, r7);	 Catch:{ all -> 0x004b }
            goto L_0x003c;
        L_0x004b:
            r9 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x004b }
            throw r9;	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
        L_0x004e:
            r0 = move-exception;
            r3 = r4;
        L_0x0050:
            r9 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x006f }
            r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006f }
            r10.<init>();	 Catch:{ all -> 0x006f }
            r11 = "Couldn't read Pixmap from file '";
            r10 = r10.append(r11);	 Catch:{ all -> 0x006f }
            r10 = r10.append(r12);	 Catch:{ all -> 0x006f }
            r11 = "'";
            r10 = r10.append(r11);	 Catch:{ all -> 0x006f }
            r10 = r10.toString();	 Catch:{ all -> 0x006f }
            r9.<init>(r10, r0);	 Catch:{ all -> 0x006f }
            throw r9;	 Catch:{ all -> 0x006f }
        L_0x006f:
            r9 = move-exception;
        L_0x0070:
            com.badlogic.gdx.utils.StreamUtils.closeQuietly(r3);
            throw r9;
        L_0x0074:
            monitor-exit(r10);	 Catch:{ all -> 0x004b }
            r9 = 0;
            r5.position(r9);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r9 = r5.capacity();	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            r5.limit(r9);	 Catch:{ Exception -> 0x004e, all -> 0x0084 }
            com.badlogic.gdx.utils.StreamUtils.closeQuietly(r4);
            return r6;
        L_0x0084:
            r9 = move-exception;
            r3 = r4;
            goto L_0x0070;
        L_0x0087:
            r0 = move-exception;
            goto L_0x0050;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.PixmapIO.CIM.read(com.badlogic.gdx.files.FileHandle):com.badlogic.gdx.graphics.Pixmap");
        }
    }

    private static class PNG {
        static final int ZLIB_BLOCK_SIZE = 32000;
        static int[] crcTable;

        private PNG() {
        }

        static byte[] write(Pixmap pixmap) throws IOException {
            byte[] signature = new byte[]{(byte) -119, (byte) 80, (byte) 78, (byte) 71, (byte) 13, (byte) 10, (byte) 26, (byte) 10};
            byte[] header = createHeaderChunk(pixmap.getWidth(), pixmap.getHeight());
            byte[] data = createDataChunk(pixmap);
            byte[] trailer = createTrailerChunk();
            ByteArrayOutputStream png = new ByteArrayOutputStream(((signature.length + header.length) + data.length) + trailer.length);
            png.write(signature);
            png.write(header);
            png.write(data);
            png.write(trailer);
            return png.toByteArray();
        }

        private static byte[] createHeaderChunk(int width, int height) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(13);
            DataOutputStream chunk = new DataOutputStream(baos);
            chunk.writeInt(width);
            chunk.writeInt(height);
            chunk.writeByte(8);
            chunk.writeByte(6);
            chunk.writeByte(0);
            chunk.writeByte(0);
            chunk.writeByte(0);
            return toChunk("IHDR", baos.toByteArray());
        }

        private static byte[] createDataChunk(Pixmap pixmap) throws IOException {
            int width = pixmap.getWidth();
            int height = pixmap.getHeight();
            byte[] raw = new byte[(((width * 4) * height) + height)];
            int dest = 0;
            for (int y = 0; y < height; y++) {
                int dest2 = dest + 1;
                raw[dest] = (byte) 0;
                dest = dest2;
                for (int x = 0; x < width; x++) {
                    int mask = pixmap.getPixel(x, y) & -1;
                    int gg = (mask >> 16) & Keys.F12;
                    int bb = (mask >> 8) & Keys.F12;
                    int aa = mask & Keys.F12;
                    dest2 = dest + 1;
                    raw[dest] = (byte) ((mask >> 24) & Keys.F12);
                    dest = dest2 + 1;
                    raw[dest2] = (byte) gg;
                    dest2 = dest + 1;
                    raw[dest] = (byte) bb;
                    dest = dest2 + 1;
                    raw[dest2] = (byte) aa;
                }
            }
            return toChunk("IDAT", toZLIB(raw));
        }

        private static byte[] createTrailerChunk() throws IOException {
            return toChunk("IEND", new byte[0]);
        }

        private static byte[] toChunk(String id, byte[] raw) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(raw.length + 12);
            DataOutputStream chunk = new DataOutputStream(baos);
            chunk.writeInt(raw.length);
            byte[] bid = new byte[4];
            for (int i = 0; i < 4; i++) {
                bid[i] = (byte) id.charAt(i);
            }
            chunk.write(bid);
            chunk.write(raw);
            chunk.writeInt(updateCRC(updateCRC(-1, bid), raw) ^ -1);
            return baos.toByteArray();
        }

        private static void createCRCTable() {
            crcTable = new int[GL20.GL_DEPTH_BUFFER_BIT];
            for (int i = 0; i < GL20.GL_DEPTH_BUFFER_BIT; i++) {
                int c = i;
                for (int k = 0; k < 8; k++) {
                    c = (c & 1) > 0 ? -306674912 ^ (c >>> 1) : c >>> 1;
                }
                crcTable[i] = c;
            }
        }

        private static int updateCRC(int crc, byte[] raw) {
            if (crcTable == null) {
                createCRCTable();
            }
            for (byte element : raw) {
                crc = crcTable[(crc ^ element) & Keys.F12] ^ (crc >>> 8);
            }
            return crc;
        }

        private static byte[] toZLIB(byte[] raw) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((raw.length + 6) + ((raw.length / ZLIB_BLOCK_SIZE) * 5));
            DataOutputStream zlib = new DataOutputStream(baos);
            zlib.writeByte((byte) 8);
            zlib.writeByte(29);
            int pos = 0;
            while (raw.length - pos > ZLIB_BLOCK_SIZE) {
                writeUncompressedDeflateBlock(zlib, false, raw, pos, '\u7d00');
                pos += ZLIB_BLOCK_SIZE;
            }
            writeUncompressedDeflateBlock(zlib, true, raw, pos, (char) (raw.length - pos));
            zlib.writeInt(calcADLER32(raw));
            return baos.toByteArray();
        }

        private static void writeUncompressedDeflateBlock(DataOutputStream zlib, boolean last, byte[] raw, int off, char len) throws IOException {
            zlib.writeByte((byte) (last ? 1 : 0));
            zlib.writeByte((byte) (len & Keys.F12));
            zlib.writeByte((byte) ((len & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8));
            zlib.writeByte((byte) ((len ^ -1) & Keys.F12));
            zlib.writeByte((byte) (((len ^ -1) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8));
            zlib.write(raw, off, len);
        }

        private static int calcADLER32(byte[] raw) {
            int s1 = 1;
            int s2 = 0;
            int i = 0;
            while (i < raw.length) {
                s1 = (s1 + (raw[i] >= null ? raw[i] : raw[i] + GL20.GL_DEPTH_BUFFER_BIT)) % 65521;
                s2 = (s2 + s1) % 65521;
                i++;
            }
            return (s2 << 16) + s1;
        }
    }

    public static void writeCIM(FileHandle file, Pixmap pixmap) {
        CIM.write(file, pixmap);
    }

    public static Pixmap readCIM(FileHandle file) {
        return CIM.read(file);
    }

    public static void writePNG(FileHandle file, Pixmap pixmap) {
        try {
            file.writeBytes(PNG.write(pixmap), false);
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error writing PNG: " + file, ex);
        }
    }
}
