package com.x5.util;

import io.github.kexanie.library.BuildConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class DataCapsuleReader {
    private static Hashtable<String, DataCapsuleReader> readerCache;
    private String[] bareLabels;
    private Class capsuleClass;
    private String[] labels;
    private String[] methodNames;
    private Method[] methods;

    static {
        readerCache = new Hashtable();
    }

    public static DataCapsuleReader getReader(DataCapsule[] dataCapsules) {
        DataCapsuleReader reader = getReaderFromCache(dataCapsules);
        if (reader != null) {
            return reader;
        }
        reader = new DataCapsuleReader(dataCapsules);
        readerCache.put(reader.getDataClassName(), reader);
        return reader;
    }

    public static DataCapsuleReader getReader(DataCapsule dataCapsule) {
        if (dataCapsule == null) {
            return null;
        }
        DataCapsuleReader reader = getReaderFromCache(dataCapsule);
        if (reader != null) {
            return reader;
        }
        reader = new DataCapsuleReader(new DataCapsule[]{dataCapsule});
        readerCache.put(reader.getDataClassName(), reader);
        return reader;
    }

    private static DataCapsuleReader getReaderFromCache(DataCapsule[] dataCapsules) {
        for (DataCapsule x : dataCapsules) {
            if (x != null) {
                return (DataCapsuleReader) readerCache.get(x.getClass().getName());
            }
        }
        return null;
    }

    private static DataCapsuleReader getReaderFromCache(DataCapsule dataCapsule) {
        return (DataCapsuleReader) readerCache.get(dataCapsule.getClass().getName());
    }

    public DataCapsuleReader(DataCapsule[] dataCapsules) {
        extractLegend(dataCapsules);
    }

    private void extractLegend(DataCapsule[] dataCapsules) {
        for (DataCapsule x : dataCapsules) {
            if (x != null) {
                this.capsuleClass = x.getClass();
                extractLegend(x);
                return;
            }
        }
    }

    private void extractLegend(DataCapsule dataCapsule) {
        String[] exports = dataCapsule.getExports();
        String exportPrefix = dataCapsule.getExportPrefix();
        this.labels = new String[exports.length];
        this.bareLabels = new String[exports.length];
        this.methodNames = new String[exports.length];
        for (int i = 0; i < exports.length; i++) {
            parseExportMap(i, exportPrefix, exports[i]);
        }
    }

    private void parseExportMap(int i, String exportPrefix, String directive) {
        String methodName;
        int spacePos = directive.indexOf(32);
        String exportName = directive;
        if (spacePos > -1) {
            exportName = directive.substring(spacePos + 1).trim();
            methodName = directive.substring(0, spacePos);
        } else {
            methodName = directive;
            exportName = transmogrify(directive);
        }
        String prefixedName = exportName;
        if (exportPrefix != null) {
            prefixedName = exportPrefix + "_" + exportName;
        }
        this.labels[i] = prefixedName;
        this.bareLabels[i] = exportName;
        this.methodNames[i] = methodName;
    }

    private static String transmogrify(String s) {
        return ObjectDataMap.splitCamelCase(s).replaceFirst("^get_", BuildConfig.FLAVOR);
    }

    public String[] getColumnLabels(String altPrefix) {
        if (altPrefix == null) {
            return getColumnLabels();
        }
        String[] altLabels = new String[this.bareLabels.length];
        for (int i = 0; i < altLabels.length; i++) {
            altLabels[i] = altPrefix + "." + this.labels[i];
        }
        return altLabels;
    }

    public String[] getColumnLabels() {
        return this.labels;
    }

    public void overrideColumnLabels(String[] newLabels) {
        this.labels = newLabels;
    }

    public Object[] extractData(DataCapsule data) {
        if (this.methods == null) {
            this.methods = grokMethods(data);
        }
        Object[] rawOutput = new Object[this.methods.length];
        for (int i = 0; i < this.methods.length; i++) {
            Method m = this.methods[i];
            if (m != null) {
                try {
                    rawOutput[i] = m.invoke(data, (Object[]) null);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(System.err);
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace(System.err);
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace(System.err);
                }
            }
        }
        return rawOutput;
    }

    public String getDataClassName() {
        return this.capsuleClass.getName();
    }

    private Method[] grokMethods(DataCapsule data) {
        Method[] methods = new Method[this.methodNames.length];
        for (int i = 0; i < methods.length; i++) {
            try {
                methods[i] = this.capsuleClass.getMethod(this.methodNames[i], (Class[]) null);
            } catch (NoSuchMethodException e) {
                System.err.println("Class " + this.capsuleClass.getName() + " does not provide method " + this.methodNames[i] + "() as described in getExports() !!");
                e.printStackTrace(System.err);
            }
        }
        return methods;
    }

    private Method[] grokSimpleMethods(DataCapsule data) {
        int i;
        Method[] allMethods = this.capsuleClass.getMethods();
        boolean[] isMatch = new boolean[allMethods.length];
        int matchCount = 0;
        for (i = 0; i < allMethods.length; i++) {
            Method m = allMethods[i];
            if (m.getReturnType() == String.class && m.getParameterTypes() == null) {
                String simpleMethodName = m.getName();
                isMatch[i] = true;
                matchCount++;
            }
        }
        Method[] simpleMethods = new Method[matchCount];
        for (i = allMethods.length - 1; i >= 0 && matchCount > 0; i--) {
            if (isMatch[i]) {
                matchCount--;
                simpleMethods[matchCount] = allMethods[i];
            }
        }
        return simpleMethods;
    }
}
