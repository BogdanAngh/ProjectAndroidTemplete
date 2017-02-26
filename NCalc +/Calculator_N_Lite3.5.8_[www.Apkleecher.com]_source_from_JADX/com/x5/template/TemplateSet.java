package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.TemplateDoc.Doclet;
import com.x5.template.filters.ChunkFilter;
import com.x5.template.filters.RegexFilter;
import com.x5.util.JarResource;
import io.github.kexanie.library.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math4.geometry.VectorFormat;

public class TemplateSet implements ContentSource, ChunkFactory {
    public static final String BLOCKEND_LONGHAND = "{~./";
    public static final String BLOCKEND_SHORTHAND = "{/";
    private static final String DEFAULT_EXTENSION = "chtml";
    private static final int DEFAULT_REFRESH = 15;
    public static String DEFAULT_TAG_END = null;
    public static String DEFAULT_TAG_START = null;
    public static final String INCLUDE_SHORTHAND = "{+";
    private static final long MIN_CACHE = 5000;
    public static final String PROTOCOL_SHORTHAND = "{.";
    private static final long oneMinuteInMillis = 60000;
    private HashSet<ContentSource> altSources;
    private Hashtable<String, Snippet> cache;
    private Hashtable<String, Long> cacheFetch;
    private Class<?> classInJar;
    private String defaultExtension;
    private int dirtyInterval;
    private String expectedEncoding;
    private String layerName;
    private boolean prettyFail;
    private Object resourceContext;
    private String tagEnd;
    private String tagStart;
    private String templatePath;

    static {
        DEFAULT_TAG_START = "{$";
        DEFAULT_TAG_END = VectorFormat.DEFAULT_SUFFIX;
    }

    public TemplateSet() {
        this.cache = new Hashtable();
        this.cacheFetch = new Hashtable();
        this.dirtyInterval = DEFAULT_REFRESH;
        this.defaultExtension = DEFAULT_EXTENSION;
        this.tagStart = DEFAULT_TAG_START;
        this.tagEnd = DEFAULT_TAG_END;
        this.templatePath = System.getProperty("templateset.folder", BuildConfig.FLAVOR);
        this.layerName = null;
        this.classInJar = null;
        this.resourceContext = null;
        this.prettyFail = true;
        this.expectedEncoding = TemplateDoc.getDefaultEncoding();
        this.altSources = null;
    }

    public TemplateSet(String templatePath) {
        this(templatePath, DEFAULT_EXTENSION, DEFAULT_REFRESH);
    }

    public TemplateSet(String templatePath, String extension, int refreshMins) {
        this.cache = new Hashtable();
        this.cacheFetch = new Hashtable();
        this.dirtyInterval = DEFAULT_REFRESH;
        this.defaultExtension = DEFAULT_EXTENSION;
        this.tagStart = DEFAULT_TAG_START;
        this.tagEnd = DEFAULT_TAG_END;
        this.templatePath = System.getProperty("templateset.folder", BuildConfig.FLAVOR);
        this.layerName = null;
        this.classInJar = null;
        this.resourceContext = null;
        this.prettyFail = true;
        this.expectedEncoding = TemplateDoc.getDefaultEncoding();
        this.altSources = null;
        if (templatePath != null) {
            char lastChar = templatePath.charAt(templatePath.length() - 1);
            char fs = System.getProperty("file.separator").charAt(0);
            if (!(lastChar == Letters.BACKSLASH || lastChar == Constants.DIV_UNICODE || lastChar == fs)) {
                templatePath = templatePath + fs;
            }
            this.templatePath = templatePath;
        }
        this.dirtyInterval = refreshMins;
        if (extension == null) {
            extension = DEFAULT_EXTENSION;
        }
        this.defaultExtension = extension;
    }

    public Snippet getSnippet(String name) {
        if (name.charAt(0) != ';') {
            return getSnippet(name, this.defaultExtension);
        }
        int nextSemi = name.indexOf(59, 1);
        if (nextSemi < 0) {
            return getSnippet(name, this.defaultExtension);
        }
        return getSnippet(name.substring(nextSemi + 1), name.substring(1, nextSemi));
    }

    public String fetch(String name) {
        Snippet s = getCleanTemplate(name);
        if (s == null) {
            return null;
        }
        return s.toString();
    }

    public String getProtocol() {
        return "include";
    }

    private Snippet getCleanTemplate(String name) {
        return getSnippet(name, "_CLEAN_:" + this.defaultExtension);
    }

    public Snippet getSnippet(String name, String extension) {
        return _get(name, extension, this.prettyFail);
    }

    private void importTemplates(InputStream in, String stub, String extension) throws IOException {
        for (Doclet doclet : new TemplateDoc(stub, in).parseTemplates(this.expectedEncoding)) {
            cacheTemplate(doclet, extension);
        }
    }

    private Snippet _get(String name, String extension, boolean prettyFail) {
        Snippet template = getFromCache(name, extension);
        String filename = null;
        if (template == null) {
            String stub = TemplateDoc.truncateNameToStub(name);
            filename = getTemplatePath(name, extension);
            char fs = System.getProperty("file.separator").charAt(0);
            filename = filename.replace(Letters.BACKSLASH, fs).replace(Constants.DIV_UNICODE, fs);
            try {
                File templateFile = new File(filename);
                if (templateFile.exists()) {
                    FileInputStream in = new FileInputStream(templateFile);
                    importTemplates(in, stub, extension);
                    in.close();
                    template = getFromCache(name, extension);
                } else {
                    String resourcePath = getResourcePath(name, extension);
                    InputStream inJar = null;
                    if (this.classInJar == null) {
                        this.classInJar = grokCallerClass();
                    }
                    if (this.classInJar != null) {
                        inJar = this.classInJar.getResourceAsStream(resourcePath);
                    }
                    if (inJar == null) {
                        inJar = fishForTemplate(resourcePath);
                    }
                    if (inJar != null) {
                        importTemplates(inJar, stub, extension);
                        template = getFromCache(name, extension);
                        inJar.close();
                    }
                }
            } catch (IOException e) {
                if (!prettyFail) {
                    return null;
                }
                StringBuilder errmsg = new StringBuilder("[error fetching ");
                errmsg.append(extension);
                errmsg.append(" template '");
                errmsg.append(name);
                errmsg.append("']<!-- ");
                StringWriter w = new StringWriter();
                e.printStackTrace(new PrintWriter(w));
                errmsg.append(w.toString());
                errmsg.append(" -->");
                template = Snippet.getSnippet(errmsg.toString());
            }
        }
        if (template == null) {
            if (!prettyFail) {
                return null;
            }
            errmsg = new StringBuilder();
            errmsg.append("[");
            errmsg.append(extension);
            errmsg.append(" template '");
            errmsg.append(name);
            errmsg.append("' not found]<!-- looked in [");
            errmsg.append(filename);
            errmsg.append("] -->");
            template = Snippet.getSnippet(errmsg.toString());
        }
        return template;
    }

    static Class<?> grokCallerClass() {
        Class<?> cls = null;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace != null) {
            for (int i = 4; i < stackTrace.length; i++) {
                StackTraceElement e = stackTrace[i];
                if (!e.getClassName().matches("^com\\.x5\\.template\\.[^\\.]*$")) {
                    try {
                        cls = Class.forName(e.getClassName());
                        break;
                    } catch (ClassNotFoundException e2) {
                    }
                }
            }
        }
        return cls;
    }

    private InputStream fishForTemplate(String resourcePath) {
        InputStream in;
        if (this.resourceContext != null) {
            in = fishForTemplateInContext(resourcePath);
            if (in != null) {
                return in;
            }
        }
        String cp = System.getProperty("java.class.path");
        if (cp == null) {
            return null;
        }
        String[] jars = cp.split(":");
        if (jars == null) {
            return null;
        }
        for (String jar : jars) {
            if (jar.endsWith(".jar")) {
                in = JarResource.peekInsideJar("jar:file:" + jar, resourcePath);
                if (in != null) {
                    return in;
                }
            }
        }
        return null;
    }

    private InputStream fishForTemplateInContext(String resourcePath) {
        Class<?> ctxClass = this.resourceContext.getClass();
        try {
            InputStream in;
            Class[] oneString = new Class[]{String.class};
            Method m = ctxClass.getMethod("getResourceAsStream", oneString);
            if (m != null) {
                in = (InputStream) m.invoke(this.resourceContext, new Object[]{resourcePath});
                if (in != null) {
                    return in;
                }
            }
            m = ctxClass.getMethod("getResourcePaths", oneString);
            if (m != null) {
                Set<String> paths = (Set) m.invoke(this.resourceContext, new Object[]{"/WEB-INF/lib"});
                if (paths != null) {
                    for (String jar : paths) {
                        if (jar.endsWith(".jar")) {
                            in = JarResource.peekInsideJar("jar:" + ((URL) ctxClass.getMethod("getResource", oneString).invoke(this.resourceContext, new Object[]{jar})).toString(), resourcePath);
                            if (in != null) {
                                return in;
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e2) {
        } catch (IllegalArgumentException e3) {
        } catch (IllegalAccessException e4) {
        } catch (InvocationTargetException e5) {
        }
        return null;
    }

    public Chunk makeChunk() {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        shareContentSources(c);
        return c;
    }

    public Chunk makeChunk(String templateName) {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        c.append(getSnippet(templateName));
        shareContentSources(c);
        return c;
    }

    public Chunk makeChunk(String templateName, String extension) {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        c.append(getSnippet(templateName, extension));
        shareContentSources(c);
        return c;
    }

    private void cacheTemplate(Doclet doclet, String extension) {
        String name = doclet.getName().replace(Letters.POUND, '.');
        String ref = extension + "." + name;
        String cleanRef = "_CLEAN_:" + ref;
        String template = doclet.getTemplate();
        this.cache.put(cleanRef, Snippet.makeLiteralSnippet(template));
        this.cacheFetch.put(cleanRef, Long.valueOf(System.currentTimeMillis()));
        StringBuilder tpl = TemplateDoc.expandShorthand(name, new StringBuilder(template));
        if (tpl != null) {
            this.cache.put(ref, Snippet.getSnippet(removeBlockTagIndents(tpl.toString()), doclet.getOrigin()));
            this.cacheFetch.put(ref, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static String removeBlockTagIndents(String template) {
        return RegexFilter.applyRegex(template, "s/^[ \\t]*(\\{(\\% *(\\~\\.)?(end)?|(\\^|\\~\\.)\\/?)(loop|exec|if|else|elseIf|divider|onEmpty|body|data)([^\\}]*|[^\\}]*\\/[^\\/]*\\/[^\\}]*)\\})[ \\t]*$/$1/gmi");
    }

    protected Snippet getFromCache(String name, String extension) {
        String ref = extension + "." + name.replace(Letters.POUND, '.');
        long cacheHowLong = ((long) this.dirtyInterval) * oneMinuteInMillis;
        if (cacheHowLong < MIN_CACHE) {
            cacheHowLong = MIN_CACHE;
        }
        if (!this.cache.containsKey(ref)) {
            return null;
        }
        if (System.currentTimeMillis() < ((Long) this.cacheFetch.get(ref)).longValue() + cacheHowLong) {
            return (Snippet) this.cache.get(ref);
        }
        return null;
    }

    public void clearCache() {
        this.cache.clear();
        this.cacheFetch.clear();
    }

    public void setDirtyInterval(int minutes) {
        this.dirtyInterval = minutes;
    }

    public String convertToMyTags(String withOldTags, String oldTagStart, String oldTagEnd) {
        return convertTags(withOldTags, oldTagStart, oldTagEnd, this.tagStart, this.tagEnd);
    }

    public static String convertTags(String withOldTags, String oldTagStart, String oldTagEnd) {
        return convertTags(withOldTags, oldTagStart, oldTagEnd, DEFAULT_TAG_START, DEFAULT_TAG_END);
    }

    public static String convertTags(String withOldTags, String oldTagStart, String oldTagEnd, String newTagStart, String newTagEnd) {
        StringBuilder converted = new StringBuilder();
        int marker = 0;
        while (true) {
            int j = withOldTags.indexOf(oldTagStart, marker);
            if (j <= -1) {
                break;
            }
            converted.append(withOldTags.substring(marker, j));
            marker = j + oldTagStart.length();
            int k = withOldTags.indexOf(oldTagEnd);
            if (k > -1) {
                converted.append(newTagStart);
                converted.append(withOldTags.substring(marker, k));
                converted.append(newTagEnd);
                marker = k + oldTagEnd.length();
            } else {
                converted.append(oldTagStart);
            }
        }
        if (marker == 0) {
            return withOldTags;
        }
        converted.append(withOldTags.substring(marker));
        return converted.toString();
    }

    public TemplateSet getSubset(String context) {
        return new TemplateSetSlice(this, context);
    }

    public void addProtocol(ContentSource src) {
        if (this.altSources == null) {
            this.altSources = new HashSet();
        }
        this.altSources.add(src);
    }

    private void shareContentSources(Chunk c) {
        if (this.altSources != null) {
            Iterator<ContentSource> iter = this.altSources.iterator();
            while (iter.hasNext()) {
                c.addProtocol((ContentSource) iter.next());
            }
        }
    }

    public void signalFailureWithNull() {
        this.prettyFail = false;
    }

    public String getTemplatePath(String templateName, String ext) {
        String path = this.templatePath + TemplateDoc.truncateNameToStub(templateName);
        if (ext == null || ext.length() <= 0) {
            return path;
        }
        return path + '.' + ext;
    }

    public String getResourcePath(String templateName, String ext) {
        String path;
        String stub = TemplateDoc.truncateNameToStub(templateName);
        if (this.layerName == null) {
            path = "/themes/" + stub;
        } else {
            path = "/themes/" + this.layerName + stub;
        }
        if (ext == null || ext.length() <= 0) {
            return path;
        }
        return path + '.' + ext;
    }

    public String getDefaultExtension() {
        return this.defaultExtension;
    }

    public boolean provides(String itemName) {
        if (_get(itemName, this.defaultExtension, false) == null) {
            return false;
        }
        return true;
    }

    public void setJarContext(Class<?> classInSameJar) {
        this.classInJar = classInSameJar;
    }

    public void setJarContext(Object ctx) {
        this.resourceContext = ctx;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
        if (layerName != null && !layerName.endsWith("/")) {
            this.layerName += "/";
        }
    }

    public void setEncoding(String encoding) {
        this.expectedEncoding = encoding;
    }

    public Map<String, ChunkFilter> getFilters() {
        return null;
    }
}
