package com.badlogic.gdx.files;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.nearby.connection.Connections;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class FileHandle {
    protected File file;
    protected FileType type;

    /* renamed from: com.badlogic.gdx.files.FileHandle.1 */
    static /* synthetic */ class C00541 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$Files$FileType;

        static {
            $SwitchMap$com$badlogic$gdx$Files$FileType = new int[FileType.values().length];
            try {
                $SwitchMap$com$badlogic$gdx$Files$FileType[FileType.Internal.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$Files$FileType[FileType.Classpath.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    protected FileHandle() {
    }

    public FileHandle(String fileName) {
        this.file = new File(fileName);
        this.type = FileType.Absolute;
    }

    public FileHandle(File file) {
        this.file = file;
        this.type = FileType.Absolute;
    }

    protected FileHandle(String fileName, FileType type) {
        this.type = type;
        this.file = new File(fileName);
    }

    protected FileHandle(File file, FileType type) {
        this.file = file;
        this.type = type;
    }

    public String path() {
        return this.file.getPath().replace('\\', '/');
    }

    public String name() {
        return this.file.getName();
    }

    public String extension() {
        String name = this.file.getName();
        int dotIndex = name.lastIndexOf(46);
        if (dotIndex == -1) {
            return "";
        }
        return name.substring(dotIndex + 1);
    }

    public String nameWithoutExtension() {
        String name = this.file.getName();
        int dotIndex = name.lastIndexOf(46);
        return dotIndex == -1 ? name : name.substring(0, dotIndex);
    }

    public String pathWithoutExtension() {
        String path = this.file.getPath().replace('\\', '/');
        int dotIndex = path.lastIndexOf(46);
        return dotIndex == -1 ? path : path.substring(0, dotIndex);
    }

    public FileType type() {
        return this.type;
    }

    public File file() {
        if (this.type == FileType.External) {
            return new File(Gdx.files.getExternalStoragePath(), this.file.getPath());
        }
        return this.file;
    }

    public InputStream read() {
        if (this.type == FileType.Classpath || ((this.type == FileType.Internal && !file().exists()) || (this.type == FileType.Local && !file().exists()))) {
            InputStream resourceAsStream = FileHandle.class.getResourceAsStream("/" + this.file.getPath().replace('\\', '/'));
            if (resourceAsStream != null) {
                return resourceAsStream;
            }
            throw new GdxRuntimeException("File not found: " + this.file + " (" + this.type + ")");
        }
        try {
            return new FileInputStream(file());
        } catch (Exception ex) {
            if (file().isDirectory()) {
                throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ex);
            }
            throw new GdxRuntimeException("Error reading file: " + this.file + " (" + this.type + ")", ex);
        }
    }

    public BufferedInputStream read(int bufferSize) {
        return new BufferedInputStream(read(), bufferSize);
    }

    public Reader reader() {
        return new InputStreamReader(read());
    }

    public Reader reader(String charset) {
        try {
            return new InputStreamReader(read(), charset);
        } catch (UnsupportedEncodingException ex) {
            throw new GdxRuntimeException("Error reading file: " + this, ex);
        }
    }

    public BufferedReader reader(int bufferSize) {
        return new BufferedReader(new InputStreamReader(read()), bufferSize);
    }

    public BufferedReader reader(int bufferSize, String charset) {
        try {
            return new BufferedReader(new InputStreamReader(read(), charset), bufferSize);
        } catch (UnsupportedEncodingException ex) {
            throw new GdxRuntimeException("Error reading file: " + this, ex);
        }
    }

    public String readString() {
        return readString(null);
    }

    public String readString(String charset) {
        InputStreamReader reader;
        int fileLength = (int) length();
        if (fileLength == 0) {
            fileLength = GL20.GL_NEVER;
        }
        StringBuilder output = new StringBuilder(fileLength);
        if (charset == null) {
            try {
                reader = new InputStreamReader(read());
            } catch (IOException ex) {
                throw new GdxRuntimeException("Error reading layout file: " + this, ex);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(null);
            }
        } else {
            reader = new InputStreamReader(read(), charset);
        }
        char[] buffer = new char[GL20.GL_DEPTH_BUFFER_BIT];
        while (true) {
            int length = reader.read(buffer);
            if (length == -1) {
                StreamUtils.closeQuietly(reader);
                return output.toString();
            }
            output.append(buffer, 0, length);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readBytes() {
        /*
        r14 = this;
        r13 = -1;
        r12 = 0;
        r10 = r14.length();
        r5 = (int) r10;
        if (r5 != 0) goto L_0x000b;
    L_0x0009:
        r5 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
    L_0x000b:
        r1 = new byte[r5];
        r7 = 0;
        r4 = r14.read();
    L_0x0012:
        r9 = r1.length;	 Catch:{ IOException -> 0x0043 }
        r9 = r9 - r7;
        r2 = r4.read(r1, r7, r9);	 Catch:{ IOException -> 0x0043 }
        if (r2 != r13) goto L_0x0027;
    L_0x001a:
        com.badlogic.gdx.utils.StreamUtils.closeQuietly(r4);
        r9 = r1.length;
        if (r7 >= r9) goto L_0x0026;
    L_0x0020:
        r6 = new byte[r7];
        java.lang.System.arraycopy(r1, r12, r6, r12, r7);
        r1 = r6;
    L_0x0026:
        return r1;
    L_0x0027:
        r7 = r7 + r2;
        r9 = r1.length;	 Catch:{ IOException -> 0x0043 }
        if (r7 != r9) goto L_0x0012;
    L_0x002b:
        r0 = r4.read();	 Catch:{ IOException -> 0x0043 }
        if (r0 == r13) goto L_0x001a;
    L_0x0031:
        r9 = r1.length;	 Catch:{ IOException -> 0x0043 }
        r9 = r9 * 2;
        r6 = new byte[r9];	 Catch:{ IOException -> 0x0043 }
        r9 = 0;
        r10 = 0;
        java.lang.System.arraycopy(r1, r9, r6, r10, r7);	 Catch:{ IOException -> 0x0043 }
        r1 = r6;
        r8 = r7 + 1;
        r9 = (byte) r0;
        r1[r7] = r9;	 Catch:{ IOException -> 0x0065, all -> 0x0062 }
        r7 = r8;
        goto L_0x0012;
    L_0x0043:
        r3 = move-exception;
    L_0x0044:
        r9 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x005d }
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005d }
        r10.<init>();	 Catch:{ all -> 0x005d }
        r11 = "Error reading file: ";
        r10 = r10.append(r11);	 Catch:{ all -> 0x005d }
        r10 = r10.append(r14);	 Catch:{ all -> 0x005d }
        r10 = r10.toString();	 Catch:{ all -> 0x005d }
        r9.<init>(r10, r3);	 Catch:{ all -> 0x005d }
        throw r9;	 Catch:{ all -> 0x005d }
    L_0x005d:
        r9 = move-exception;
    L_0x005e:
        com.badlogic.gdx.utils.StreamUtils.closeQuietly(r4);
        throw r9;
    L_0x0062:
        r9 = move-exception;
        r7 = r8;
        goto L_0x005e;
    L_0x0065:
        r3 = move-exception;
        r7 = r8;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.files.FileHandle.readBytes():byte[]");
    }

    public int readBytes(byte[] bytes, int offset, int size) {
        InputStream input = read();
        int position = 0;
        while (true) {
            try {
                int count = input.read(bytes, offset + position, size - position);
                if (count <= 0) {
                    StreamUtils.closeQuietly(input);
                    return position - offset;
                }
                position += count;
            } catch (IOException ex) {
                throw new GdxRuntimeException("Error reading file: " + this, ex);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(input);
            }
        }
    }

    public OutputStream write(boolean append) {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + this.file);
        } else if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + this.file);
        } else {
            parent().mkdirs();
            try {
                return new FileOutputStream(file(), append);
            } catch (Exception ex) {
                if (file().isDirectory()) {
                    throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ex);
                }
                throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ex);
            }
        }
    }

    public void write(InputStream input, boolean append) {
        OutputStream output = null;
        try {
            output = write(append);
            byte[] buffer = new byte[Connections.MAX_RELIABLE_MESSAGE_LEN];
            while (true) {
                int length = input.read(buffer);
                if (length == -1) {
                    StreamUtils.closeQuietly(input);
                    StreamUtils.closeQuietly(output);
                    return;
                }
                output.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error stream writing to file: " + this.file + " (" + this.type + ")", ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(input);
            StreamUtils.closeQuietly(output);
        }
    }

    public Writer writer(boolean append) {
        return writer(append, null);
    }

    public Writer writer(boolean append, String charset) {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + this.file);
        } else if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + this.file);
        } else {
            parent().mkdirs();
            try {
                FileOutputStream output = new FileOutputStream(file(), append);
                if (charset == null) {
                    return new OutputStreamWriter(output);
                }
                return new OutputStreamWriter(output, charset);
            } catch (IOException ex) {
                if (file().isDirectory()) {
                    throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ex);
                }
                throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ex);
            }
        }
    }

    public void writeString(String string, boolean append) {
        writeString(string, append, null);
    }

    public void writeString(String string, boolean append, String charset) {
        Writer writer = null;
        try {
            writer = writer(append, charset);
            writer.write(string);
            StreamUtils.closeQuietly(writer);
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(writer);
        }
    }

    public void writeBytes(byte[] bytes, boolean append) {
        OutputStream output = write(append);
        try {
            output.write(bytes);
            StreamUtils.closeQuietly(output);
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(output);
        }
    }

    public void writeBytes(byte[] bytes, int offset, int length, boolean append) {
        OutputStream output = write(append);
        try {
            output.write(bytes, offset, length);
            StreamUtils.closeQuietly(output);
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ex);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(output);
        }
    }

    public FileHandle[] list() {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }
        String[] relativePaths = file().list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int n = relativePaths.length;
        for (int i = 0; i < n; i++) {
            handles[i] = child(relativePaths[i]);
        }
        return handles;
    }

    public FileHandle[] list(String suffix) {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }
        String[] relativePaths = file().list();
        if (relativePaths == null) {
            return new FileHandle[0];
        }
        FileHandle[] handles = new FileHandle[relativePaths.length];
        int count = 0;
        for (String path : relativePaths) {
            if (path.endsWith(suffix)) {
                handles[count] = child(path);
                count++;
            }
        }
        if (count >= relativePaths.length) {
            return handles;
        }
        FileHandle[] newHandles = new FileHandle[count];
        System.arraycopy(handles, 0, newHandles, 0, count);
        return newHandles;
    }

    public boolean isDirectory() {
        if (this.type == FileType.Classpath) {
            return false;
        }
        return file().isDirectory();
    }

    public FileHandle child(String name) {
        if (this.file.getPath().length() == 0) {
            return new FileHandle(new File(name), this.type);
        }
        return new FileHandle(new File(this.file, name), this.type);
    }

    public FileHandle sibling(String name) {
        if (this.file.getPath().length() != 0) {
            return new FileHandle(new File(this.file.getParent(), name), this.type);
        }
        throw new GdxRuntimeException("Cannot get the sibling of the root.");
    }

    public FileHandle parent() {
        File parent = this.file.getParentFile();
        if (parent == null) {
            if (this.type == FileType.Absolute) {
                parent = new File("/");
            } else {
                parent = new File("");
            }
        }
        return new FileHandle(parent, this.type);
    }

    public void mkdirs() {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot mkdirs with a classpath file: " + this.file);
        } else if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot mkdirs with an internal file: " + this.file);
        } else {
            file().mkdirs();
        }
    }

    public boolean exists() {
        switch (C00541.$SwitchMap$com$badlogic$gdx$Files$FileType[this.type.ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                if (this.file.exists()) {
                    return true;
                }
                break;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                break;
            default:
                return file().exists();
        }
        return FileHandle.class.getResource(new StringBuilder().append("/").append(this.file.getPath().replace('\\', '/')).toString()) != null;
    }

    public boolean delete() {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        } else if (this.type != FileType.Internal) {
            return file().delete();
        } else {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        }
    }

    public boolean deleteDirectory() {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        } else if (this.type != FileType.Internal) {
            return deleteDirectory(file());
        } else {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        }
    }

    public void emptyDirectory() {
        emptyDirectory(false);
    }

    public void emptyDirectory(boolean preserveTree) {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        } else if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        } else {
            emptyDirectory(file(), preserveTree);
        }
    }

    public void copyTo(FileHandle dest) {
        boolean sourceDir = isDirectory();
        if (sourceDir) {
            if (!dest.exists()) {
                dest.mkdirs();
                if (!dest.isDirectory()) {
                    throw new GdxRuntimeException("Destination directory cannot be created: " + dest);
                }
            } else if (!dest.isDirectory()) {
                throw new GdxRuntimeException("Destination exists but is not a directory: " + dest);
            }
            if (!sourceDir) {
                dest = dest.child(name());
            }
            copyDirectory(this, dest);
            return;
        }
        if (dest.isDirectory()) {
            dest = dest.child(name());
        }
        copyFile(this, dest);
    }

    public void moveTo(FileHandle dest) {
        if (this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot move a classpath file: " + this.file);
        } else if (this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot move an internal file: " + this.file);
        } else {
            copyTo(dest);
            delete();
            if (exists() && isDirectory()) {
                deleteDirectory();
            }
        }
    }

    public long length() {
        FileType fileType = this.type;
        long j = FileType.Classpath;
        if (fileType != j) {
            fileType = this.type;
            j = FileType.Internal;
            if (fileType != j || this.file.exists()) {
                return file().length();
            }
        }
        InputStream input = read();
        try {
            j = (long) input.available();
            return j;
        } catch (Exception e) {
            return 0;
        } finally {
            StreamUtils.closeQuietly(input);
        }
    }

    public long lastModified() {
        return file().lastModified();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FileHandle)) {
            return false;
        }
        FileHandle other = (FileHandle) obj;
        if (this.type == other.type && path().equals(other.path())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.type.hashCode() + 37) * 67) + path().hashCode();
    }

    public String toString() {
        return this.file.getPath().replace('\\', '/');
    }

    public static FileHandle tempFile(String prefix) {
        try {
            return new FileHandle(File.createTempFile(prefix, null));
        } catch (IOException ex) {
            throw new GdxRuntimeException("Unable to create temp file.", ex);
        }
    }

    public static FileHandle tempDirectory(String prefix) {
        try {
            File file = File.createTempFile(prefix, null);
            if (!file.delete()) {
                throw new IOException("Unable to delete temp file: " + file);
            } else if (file.mkdir()) {
                return new FileHandle(file);
            } else {
                throw new IOException("Unable to create temp directory: " + file);
            }
        } catch (IOException ex) {
            throw new GdxRuntimeException("Unable to create temp file.", ex);
        }
    }

    private static void emptyDirectory(File file, boolean preserveTree) {
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                int n = files.length;
                for (int i = 0; i < n; i++) {
                    if (!files[i].isDirectory()) {
                        files[i].delete();
                    } else if (preserveTree) {
                        emptyDirectory(files[i], true);
                    } else {
                        deleteDirectory(files[i]);
                    }
                }
            }
        }
    }

    private static boolean deleteDirectory(File file) {
        emptyDirectory(file, false);
        return file.delete();
    }

    private static void copyFile(FileHandle source, FileHandle dest) {
        try {
            dest.write(source.read(), false);
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error copying source file: " + source.file + " (" + source.type + ")\n" + "To destination: " + dest.file + " (" + dest.type + ")", ex);
        }
    }

    private static void copyDirectory(FileHandle sourceDir, FileHandle destDir) {
        destDir.mkdirs();
        for (FileHandle srcFile : sourceDir.list()) {
            FileHandle destFile = destDir.child(srcFile.name());
            if (srcFile.isDirectory()) {
                copyDirectory(srcFile, destFile);
            } else {
                copyFile(srcFile, destFile);
            }
        }
    }
}
