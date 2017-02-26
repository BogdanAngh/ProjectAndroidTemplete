package com.x5.template;

import com.x5.template.filters.ChunkFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class ThemeConfig {
    public static final String CACHE_MINUTES = "cache_minutes";
    public static final String DEFAULT_EXT = "default_extension";
    public static final String ENCODING = "encoding";
    public static final String ERROR_LOG = "error_log";
    public static final String FILTERS = "filters";
    public static final String HIDE_ERRORS = "hide_errors";
    public static final String LAYER_NAMES = "layers";
    public static final String LOCALE = "locale";
    public static final String STD_ERR = "stderr";
    public static final String THEME_PATH = "theme_path";
    private int cacheMinutes;
    private String defaultExtension;
    private String encoding;
    private PrintStream errorLog;
    private ChunkFilter[] filters;
    private boolean hideErrors;
    private String layerNames;
    private String locale;
    private String themePath;

    public ThemeConfig(Map<String, String> params) {
        this.themePath = null;
        this.layerNames = null;
        this.defaultExtension = null;
        this.cacheMinutes = 0;
        this.locale = null;
        this.encoding = null;
        this.hideErrors = false;
        this.errorLog = null;
        this.filters = null;
        if (params != null) {
            this.themePath = getParam(params, THEME_PATH);
            this.layerNames = getParam(params, LAYER_NAMES);
            this.defaultExtension = getParam(params, DEFAULT_EXT);
            if (params.containsKey(CACHE_MINUTES)) {
                try {
                    this.cacheMinutes = Integer.parseInt((String) params.get(CACHE_MINUTES));
                } catch (NumberFormatException e) {
                    System.err.println("Chunk Theme config error: cache_minutes must be a number.");
                }
            }
            this.locale = getParam(params, LOCALE);
            this.encoding = getParam(params, ENCODING);
            String hideErrorsParam = getParam(params, HIDE_ERRORS);
            if (!(hideErrorsParam == null || hideErrorsParam.equalsIgnoreCase("FALSE"))) {
                this.hideErrors = true;
                PrintStream errLog = null;
                if (params.containsKey(ERROR_LOG)) {
                    String errorLogName = getParam(params, ERROR_LOG);
                    if (!errorLogName.equalsIgnoreCase(STD_ERR)) {
                        errLog = openForAppend(errorLogName);
                    }
                }
                if (errLog == null) {
                    errLog = System.err;
                }
                this.errorLog = errLog;
            }
            String filterList = getParam(params, FILTERS);
            if (filterList != null) {
                this.filters = parseFilters(filterList);
            }
        }
    }

    private String getParam(Map<String, String> params, String key) {
        if (!params.containsKey(key)) {
            return null;
        }
        String value = (String) params.get(key);
        if (value == null) {
            return value;
        }
        value = value.trim();
        if (value.length() == 0) {
            return null;
        }
        return value;
    }

    private PrintStream openForAppend(String logPath) {
        try {
            return new PrintStream(new FileOutputStream(new File(logPath), true));
        } catch (FileNotFoundException e) {
            System.err.println("Can not open error log file '" + logPath + "' for append.");
            return null;
        }
    }

    private ChunkFilter[] parseFilters(String filterList) {
        ArrayList<ChunkFilter> filters = new ArrayList();
        for (String filterClassName : filterList.split("[\\s,]+")) {
            ChunkFilter filter = createFilterFromClassName(filterClassName);
            if (filter != null) {
                filters.add(filter);
            }
        }
        if (filters.size() == 0) {
            return null;
        }
        return (ChunkFilter[]) filters.toArray(new ChunkFilter[filters.size()]);
    }

    private ChunkFilter createFilterFromClassName(String filterClassName) {
        Object newInstance;
        Object filter = null;
        Class filterClass = null;
        try {
            filterClass = Class.forName(filterClassName);
            newInstance = filterClass.newInstance();
        } catch (InstantiationException e) {
            if (filterClassName.contains("$")) {
                try {
                    Object outerInstance = Class.forName(filterClassName.substring(0, filterClassName.indexOf(36))).newInstance();
                    filter = filterClass.getDeclaredConstructor(new Class[]{outerClass}).newInstance(new Object[]{outerInstance});
                } catch (ClassNotFoundException e2) {
                } catch (InstantiationException e3) {
                } catch (IllegalAccessException e4) {
                } catch (NoSuchMethodException e5) {
                } catch (InvocationTargetException e6) {
                }
            }
            if (filter == null) {
                System.err.println("Could not call constructor for filter: " + filterClassName);
                e.printStackTrace(System.err);
            }
            newInstance = filter;
        } catch (IllegalAccessException e7) {
            System.err.println("Permission denied adding user-contributed filter: " + filterClassName);
            e7.printStackTrace(System.err);
            newInstance = filter;
        } catch (ClassNotFoundException e8) {
            System.err.println("Filter class not found: " + filterClassName);
            e8.printStackTrace(System.err);
            newInstance = filter;
        }
        ChunkFilter chunkFilter = null;
        try {
            return (ChunkFilter) newInstance;
        } catch (InstantiationException e9) {
            System.err.println("User-contributed filter rejected; must implement ChunkFilter: " + filterClassName);
            e9.printStackTrace(System.err);
            return chunkFilter;
        }
    }

    public String getThemeFolder() {
        return this.themePath;
    }

    public String getLayerNames() {
        return this.layerNames;
    }

    public String getDefaultExtension() {
        return this.defaultExtension;
    }

    public int getCacheMinutes() {
        return this.cacheMinutes;
    }

    public String getLocaleCode() {
        return this.locale;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean hideErrors() {
        return this.hideErrors;
    }

    public PrintStream getErrorLog() {
        return this.errorLog;
    }

    public ChunkFilter[] getFilters() {
        return this.filters;
    }
}
