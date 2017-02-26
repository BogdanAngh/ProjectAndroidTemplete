package com.badlogic.gdx.utils;

import com.google.android.gms.nearby.connection.Connections;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SharedLibraryLoader {
    public static boolean is64Bit;
    public static boolean isAndroid;
    public static boolean isIos;
    public static boolean isLinux;
    public static boolean isMac;
    public static boolean isWindows;
    private static final HashSet<String> loadedLibraries;
    private String nativesJar;

    static {
        isWindows = System.getProperty("os.name").contains("Windows");
        isLinux = System.getProperty("os.name").contains("Linux");
        isMac = System.getProperty("os.name").contains("Mac");
        isIos = false;
        isAndroid = false;
        is64Bit = System.getProperty("os.arch").equals("amd64");
        String vm = System.getProperty("java.vm.name");
        if (vm != null && vm.contains("Dalvik")) {
            isAndroid = true;
            isWindows = false;
            isLinux = false;
            isMac = false;
            is64Bit = false;
        }
        if (!(isAndroid || isWindows || isLinux || isMac)) {
            isIos = true;
            is64Bit = false;
        }
        loadedLibraries = new HashSet();
    }

    public SharedLibraryLoader(String nativesJar) {
        this.nativesJar = nativesJar;
    }

    public String crc(InputStream input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        CRC32 crc = new CRC32();
        byte[] buffer = new byte[Connections.MAX_RELIABLE_MESSAGE_LEN];
        while (true) {
            try {
                int length = input.read(buffer);
                if (length == -1) {
                    break;
                }
                crc.update(buffer, 0, length);
            } catch (Exception e) {
                StreamUtils.closeQuietly(input);
            }
        }
        return Long.toString(crc.getValue(), 16);
    }

    public String mapLibraryName(String libraryName) {
        if (isWindows) {
            return libraryName + (is64Bit ? "64.dll" : ".dll");
        } else if (isLinux) {
            return "lib" + libraryName + (is64Bit ? "64.so" : ".so");
        } else if (isMac) {
            return "lib" + libraryName + ".dylib";
        } else {
            return libraryName;
        }
    }

    public synchronized void load(String libraryName) {
        if (!isIos) {
            libraryName = mapLibraryName(libraryName);
            if (!loadedLibraries.contains(libraryName)) {
                try {
                    if (isAndroid) {
                        System.loadLibrary(libraryName);
                    } else {
                        loadFile(libraryName);
                    }
                    loadedLibraries.add(libraryName);
                } catch (Throwable ex) {
                    GdxRuntimeException gdxRuntimeException = new GdxRuntimeException("Couldn't load shared library '" + libraryName + "' for target: " + System.getProperty("os.name") + (is64Bit ? ", 64-bit" : ", 32-bit"), ex);
                }
            }
        }
    }

    private InputStream readFile(String path) {
        InputStream resourceAsStream;
        if (this.nativesJar == null) {
            resourceAsStream = SharedLibraryLoader.class.getResourceAsStream("/" + path);
            if (resourceAsStream == null) {
                throw new GdxRuntimeException("Unable to read file for extraction: " + path);
            }
        }
        try {
            ZipFile file = new ZipFile(this.nativesJar);
            ZipEntry entry = file.getEntry(path);
            if (entry == null) {
                throw new GdxRuntimeException("Couldn't find '" + path + "' in JAR: " + this.nativesJar);
            }
            resourceAsStream = file.getInputStream(entry);
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error reading '" + path + "' in JAR: " + this.nativesJar, ex);
        }
        return resourceAsStream;
    }

    public File extractFile(String sourcePath, String dirName) throws IOException {
        File extractFile;
        try {
            String sourceCrc = crc(readFile(sourcePath));
            if (dirName == null) {
                dirName = sourceCrc;
            }
            extractFile = extractFile(sourcePath, sourceCrc, getExtractedFile(dirName, new File(sourcePath).getName()));
        } catch (RuntimeException ex) {
            extractFile = new File(System.getProperty("java.library.path"), sourcePath);
            if (!extractFile.exists()) {
                throw ex;
            }
        }
        return extractFile;
    }

    private File getExtractedFile(String dirName, String fileName) {
        File idealFile = new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + dirName, fileName);
        if (canWrite(idealFile)) {
            return idealFile;
        }
        File file;
        try {
            file = File.createTempFile(dirName, null);
            if (file.delete()) {
                File file2 = new File(file, fileName);
                if (canWrite(file2)) {
                    return file2;
                }
            }
        } catch (IOException e) {
        }
        file = new File(System.getProperty("user.home") + "/.libgdx/" + dirName, fileName);
        if (canWrite(file)) {
            return file;
        }
        file = new File(".temp/" + dirName, fileName);
        if (canWrite(file)) {
            return file;
        }
        return idealFile;
    }

    private boolean canWrite(File file) {
        if (file.canWrite()) {
            return true;
        }
        File parent = file.getParentFile();
        parent.mkdirs();
        if (!parent.isDirectory()) {
            return false;
        }
        try {
            new FileOutputStream(file).close();
            file.delete();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private File extractFile(String sourcePath, String sourceCrc, File extractedFile) throws IOException {
        String extractedCrc = null;
        if (extractedFile.exists()) {
            try {
                extractedCrc = crc(new FileInputStream(extractedFile));
            } catch (FileNotFoundException e) {
            }
        }
        if (extractedCrc == null || !extractedCrc.equals(sourceCrc)) {
            try {
                InputStream input = readFile(sourcePath);
                extractedFile.getParentFile().mkdirs();
                FileOutputStream output = new FileOutputStream(extractedFile);
                byte[] buffer = new byte[Connections.MAX_RELIABLE_MESSAGE_LEN];
                while (true) {
                    int length = input.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
            } catch (IOException ex) {
                throw new GdxRuntimeException("Error extracting file: " + sourcePath + "\nTo: " + extractedFile.getAbsolutePath(), ex);
            }
        }
        return extractedFile;
    }

    private void loadFile(String sourcePath) {
        String sourceCrc = crc(readFile(sourcePath));
        String fileName = new File(sourcePath).getName();
        Throwable ex = loadFile(sourcePath, sourceCrc, new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + sourceCrc, fileName));
        if (ex != null) {
            File file;
            try {
                file = File.createTempFile(sourceCrc, null);
                if (file.delete() && loadFile(sourcePath, sourceCrc, file) == null) {
                    return;
                }
            } catch (Throwable th) {
            }
            if (loadFile(sourcePath, sourceCrc, new File(System.getProperty("user.home") + "/.libgdx/" + sourceCrc, fileName)) != null && loadFile(sourcePath, sourceCrc, new File(".temp/" + sourceCrc, fileName)) != null) {
                file = new File(System.getProperty("java.library.path"), sourcePath);
                if (file.exists()) {
                    System.load(file.getAbsolutePath());
                    return;
                }
                throw new GdxRuntimeException(ex);
            }
        }
    }

    private Throwable loadFile(String sourcePath, String sourceCrc, File extractedFile) {
        try {
            System.load(extractFile(sourcePath, sourceCrc, extractedFile).getAbsolutePath());
            return null;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex;
        }
    }
}
