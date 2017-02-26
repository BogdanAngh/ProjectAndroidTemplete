package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.filters.ChunkFilter;
import io.github.kexanie.library.BuildConfig;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Theme implements ContentSource, ChunkFactory {
    private static final String DEFAULT_THEMES_FOLDER = "themes";
    private HashSet<ContentSource> altSources;
    private int cacheMins;
    private Map<String, ChunkFilter> customFilters;
    private PrintStream errLog;
    private String fileExtension;
    private String localeCode;
    private boolean renderErrs;
    private String themeLayerNames;
    private ArrayList<ContentSource> themeLayers;
    private String themesFolder;

    public Theme() {
        this(null, null, null);
    }

    public Theme(ThemeConfig config) {
        this(config.getThemeFolder(), config.getLayerNames(), config.getDefaultExtension());
        setDirtyInterval(config.getCacheMinutes());
        this.localeCode = config.getLocaleCode();
        if (config.hideErrors()) {
            setErrorHandling(false, config.getErrorLog());
        }
        ChunkFilter[] filters = config.getFilters();
        if (filters != null) {
            for (ChunkFilter filter : filters) {
                registerFilter(filter);
            }
        }
        String encoding = config.getEncoding();
        if (encoding != null) {
            setEncoding(encoding);
        }
    }

    public Theme(ContentSource templates) {
        this.themeLayers = new ArrayList();
        this.cacheMins = 0;
        this.localeCode = null;
        this.renderErrs = true;
        this.errLog = null;
        this.altSources = null;
        this.themeLayers.add(templates);
    }

    public Theme(String themeLayerNames) {
        this(null, themeLayerNames, null);
    }

    public Theme(String themesFolder, String themeLayerNames) {
        this(themesFolder, themeLayerNames, null);
    }

    public Theme(String themesFolder, String themeLayerNames, String ext) {
        this.themeLayers = new ArrayList();
        this.cacheMins = 0;
        this.localeCode = null;
        this.renderErrs = true;
        this.errLog = null;
        this.altSources = null;
        this.themesFolder = themesFolder;
        this.themeLayerNames = themeLayerNames;
        this.fileExtension = ext;
    }

    public void setDefaultFileExtension(String ext) {
        if (this.themeLayers.size() > 0) {
            throw new IllegalStateException("Must specify extension before lazy init.");
        }
        this.fileExtension = ext;
    }

    public void setLocale(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getLocale() {
        return this.localeCode;
    }

    public void setEncoding(String encoding) {
        ArrayList<TemplateSet> templateSets = getTemplateSets();
        if (templateSets != null) {
            Iterator i$ = templateSets.iterator();
            while (i$.hasNext()) {
                ((TemplateSet) i$.next()).setEncoding(encoding);
            }
        }
    }

    private void init() {
        if (this.themesFolder == null) {
            this.themesFolder = DEFAULT_THEMES_FOLDER;
        }
        char lastChar = this.themesFolder.charAt(this.themesFolder.length() - 1);
        char fs = System.getProperty("file.separator").charAt(0);
        if (!(lastChar == Letters.BACKSLASH || lastChar == Constants.DIV_UNICODE || lastChar == fs)) {
            this.themesFolder += fs;
        }
        String[] layerNames = parseLayerNames(this.themeLayerNames);
        if (layerNames == null) {
            TemplateSet simple = new TemplateSet(this.themesFolder, this.fileExtension, this.cacheMins);
            if (!this.renderErrs) {
                simple.signalFailureWithNull();
            }
            this.themeLayers.add(simple);
            return;
        }
        for (int i = 0; i < layerNames.length; i++) {
            TemplateSet x = new TemplateSet(this.themesFolder + layerNames[i], this.fileExtension, this.cacheMins);
            x.setLayerName(layerNames[i]);
            x.signalFailureWithNull();
            this.themeLayers.add(x);
        }
    }

    public void addLayer(ContentSource templates) {
        this.themeLayers.add(templates);
    }

    private ArrayList<ContentSource> getThemeLayers() {
        if (this.themeLayers.size() < 1) {
            init();
        }
        return this.themeLayers;
    }

    private String[] parseLayerNames(String themeLayerNames) {
        if (themeLayerNames == null || themeLayerNames.trim().length() == 0) {
            return null;
        }
        return themeLayerNames.split(" *, *");
    }

    public void setDirtyInterval(int minutes) {
        if (this.themeLayers.size() == 0) {
            this.cacheMins = minutes;
            return;
        }
        ArrayList<TemplateSet> templateSets = getTemplateSets();
        if (templateSets != null) {
            Iterator i$ = templateSets.iterator();
            while (i$.hasNext()) {
                ((TemplateSet) i$.next()).setDirtyInterval(minutes);
            }
        }
    }

    public Snippet getSnippet(String templateName, String ext) {
        ArrayList<ContentSource> layers = getThemeLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            Snippet template = ((ContentSource) layers.get(i)).getSnippet(";" + ext + ";" + templateName);
            if (template != null) {
                return template;
            }
        }
        return prettyFail(templateName, ext);
    }

    public Snippet getSnippet(String itemName) {
        ArrayList<ContentSource> layers = getThemeLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            ContentSource x = (ContentSource) layers.get(i);
            if (x.provides(itemName)) {
                return x.getSnippet(itemName);
            }
        }
        return prettyFail(itemName, null);
    }

    public boolean provides(String itemName) {
        for (int i = this.themeLayers.size() - 1; i >= 0; i--) {
            if (((ContentSource) this.themeLayers.get(i)).provides(itemName)) {
                return true;
            }
        }
        return false;
    }

    private Snippet prettyFail(String templateName, String ext) {
        if (!this.renderErrs && this.errLog == null) {
            return null;
        }
        String prettyExt = ext;
        if (prettyExt == null) {
            ContentSource baseLayer = (ContentSource) this.themeLayers.get(0);
            if (baseLayer instanceof TemplateSet) {
                prettyExt = ((TemplateSet) baseLayer).getDefaultExtension();
            }
        }
        StringBuilder err = new StringBuilder();
        err.append("[");
        if (prettyExt != null) {
            err.append(prettyExt);
            err.append(" ");
        }
        err.append("template '");
        err.append(templateName);
        err.append("' not found]");
        if (prettyExt != null) {
            String places = BuildConfig.FLAVOR;
            ArrayList<TemplateSet> templateSets = getTemplateSets();
            if (templateSets != null) {
                for (int i = templateSets.size() - 1; i >= 0; i--) {
                    TemplateSet ts = (TemplateSet) templateSets.get(i);
                    if (places.length() > 0) {
                        places = places + ",";
                    }
                    places = places + ts.getTemplatePath(templateName, prettyExt);
                }
            }
            if (places.length() > 0) {
                err.append("<!-- looked in [");
                err.append(places);
                err.append("] -->");
            }
        }
        if (this.errLog != null) {
            Chunk.logChunkError(this.errLog, err.toString());
        }
        if (this.renderErrs) {
            return Snippet.getSnippet(err.toString());
        }
        return null;
    }

    public String fetch(String itemName) {
        ArrayList<ContentSource> layers = getThemeLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            ContentSource x = (ContentSource) layers.get(i);
            if (x.provides(itemName)) {
                return x.fetch(itemName);
            }
        }
        return null;
    }

    public String getProtocol() {
        return "include";
    }

    public Chunk makeChunk() {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        shareContentSources(c);
        c.setLocale(this.localeCode);
        c.setErrorHandling(this.renderErrs, this.errLog);
        return c;
    }

    public Chunk makeChunk(String templateName) {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        c.append(getSnippet(templateName));
        shareContentSources(c);
        c.setLocale(this.localeCode);
        c.setErrorHandling(this.renderErrs, this.errLog);
        return c;
    }

    public Chunk makeChunk(String templateName, String extension) {
        Chunk c = new Chunk();
        c.setMacroLibrary(this, this);
        c.append(getSnippet(templateName, extension));
        shareContentSources(c);
        c.setLocale(this.localeCode);
        c.setErrorHandling(this.renderErrs, this.errLog);
        return c;
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

    public void setJarContext(Class<?> classInSameJar) {
        ArrayList<TemplateSet> templateSets = getTemplateSets();
        if (templateSets != null) {
            Iterator i$ = templateSets.iterator();
            while (i$.hasNext()) {
                ((TemplateSet) i$.next()).setJarContext((Class) classInSameJar);
            }
        }
    }

    public void setJarContext(Object ctx) {
        ArrayList<TemplateSet> templateSets = getTemplateSets();
        if (templateSets != null) {
            Iterator i$ = templateSets.iterator();
            while (i$.hasNext()) {
                ((TemplateSet) i$.next()).setJarContext(ctx);
            }
        }
    }

    private ArrayList<TemplateSet> getTemplateSets() {
        ArrayList<TemplateSet> sets = null;
        Iterator i$ = getThemeLayers().iterator();
        while (i$.hasNext()) {
            ContentSource x = (ContentSource) i$.next();
            if (x instanceof TemplateSet) {
                if (sets == null) {
                    sets = new ArrayList();
                }
                sets.add((TemplateSet) x);
            }
        }
        return sets;
    }

    public Map<String, ChunkFilter> getFilters() {
        return this.customFilters;
    }

    public void registerFilter(ChunkFilter filter) {
        if (this.customFilters == null) {
            this.customFilters = new HashMap();
        }
        this.customFilters.put(filter.getFilterName(), filter);
        String[] aliases = filter.getFilterAliases();
        if (aliases != null) {
            for (String alias : aliases) {
                this.customFilters.put(alias, filter);
            }
        }
    }

    public void setErrorHandling(boolean renderErrs, PrintStream errLog) {
        this.renderErrs = renderErrs;
        this.errLog = errLog;
    }
}
