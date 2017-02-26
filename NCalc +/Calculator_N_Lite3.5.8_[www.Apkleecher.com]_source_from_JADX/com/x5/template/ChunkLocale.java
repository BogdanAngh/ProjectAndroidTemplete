package com.x5.template;

import com.csvreader.CsvReader;
import com.x5.util.JarResource;
import io.github.kexanie.library.BuildConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.MissingResourceException;

public class ChunkLocale {
    private static HashMap<String, ChunkLocale> locales;
    private String localeCode;
    private HashMap<String, String> translations;

    static {
        locales = new HashMap();
    }

    public static ChunkLocale getInstance(String localeCode, Chunk context) {
        ChunkLocale instance = (ChunkLocale) locales.get(localeCode);
        if (instance != null) {
            return instance;
        }
        instance = new ChunkLocale(localeCode, context);
        locales.put(localeCode, instance);
        return instance;
    }

    public static void registerLocale(String localeCode, String[] translations) {
        locales.put(localeCode, new ChunkLocale(localeCode, translations));
    }

    private ChunkLocale(String localeCode, Chunk context) {
        this.localeCode = localeCode;
        loadTranslations(context);
    }

    private ChunkLocale(String localeCode, String[] strings) {
        this.localeCode = localeCode;
        if (strings != null && strings.length > 1) {
            this.translations = new HashMap();
            for (int i = 0; i + 1 < strings.length; i++) {
                this.translations.put(strings[i], strings[i + 1]);
            }
        }
    }

    private void loadTranslations(Chunk context) {
        try {
            InputStream in = locateLocaleDB(context);
            if (in != null) {
                CsvReader reader = new CsvReader(in, grokLocaleDBCharset());
                reader.setUseComments(true);
                this.translations = new HashMap();
                while (reader.readRecord()) {
                    String[] entry = reader.getValues();
                    if (!(entry == null || entry.length <= 1 || entry[0] == null || entry[1] == null)) {
                        this.translations.put(entry[0], entry[1]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR loading locale DB: " + this.localeCode);
            e.printStackTrace(System.err);
        }
    }

    private Charset grokLocaleDBCharset() {
        String override = System.getProperty("chunk.localedb.charset");
        if (override != null) {
            Charset charset = null;
            try {
                charset = Charset.forName(override);
            } catch (IllegalCharsetNameException e) {
            } catch (UnsupportedCharsetException e2) {
            }
            if (charset != null) {
                return charset;
            }
        }
        try {
            return Charset.forName("UTF-8");
        } catch (Exception e3) {
            return Charset.defaultCharset();
        }
    }

    private InputStream locateLocaleDB(Chunk context) throws IOException {
        String sysLocalePath = System.getProperty("chunk.localedb.path");
        if (sysLocalePath != null) {
            File folder = new File(sysLocalePath);
            if (folder.exists()) {
                File file = new File(folder, this.localeCode + "/translate.csv");
                if (file.exists()) {
                    return new FileInputStream(file);
                }
            }
        }
        String path = "/locale/" + this.localeCode + "/translate.csv";
        InputStream in = getClass().getResourceAsStream(path);
        if (in != null) {
            return in;
        }
        Class classInApp = TemplateSet.grokCallerClass();
        if (classInApp != null) {
            in = classInApp.getResourceAsStream(path);
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
                in = JarResource.peekInsideJar("jar:file:" + jar, path);
                if (in != null) {
                    return in;
                }
            }
        }
        return null;
    }

    public String translate(String string, String[] args, Chunk context) {
        return processFormatString(string, args, context, this.translations);
    }

    public static String processFormatString(String string, String[] args, Chunk context) {
        return processFormatString(string, args, context, null);
    }

    public static String processFormatString(String string, String[] args, Chunk context, HashMap<String, String> translations) {
        if (string == null) {
            return null;
        }
        String xlated = string;
        if (translations != null && translations.containsKey(string)) {
            xlated = (String) translations.get(string);
        }
        if (args == null || context == null || !xlated.contains("%s")) {
            return xlated;
        }
        Object[] values = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            String tagName = args[i];
            if (tagName.startsWith("~") || tagName.startsWith("$")) {
                Object val = context.get(tagName.substring(1));
                values[i] = val == null ? BuildConfig.FLAVOR : val.toString();
            } else {
                values[i] = tagName;
            }
        }
        try {
            return String.format(xlated, values);
        } catch (IllegalFormatException e) {
            return xlated;
        }
    }

    public Locale getJavaLocale() {
        if (this.localeCode != null && this.localeCode.contains("_")) {
            String[] langAndCountry = this.localeCode.split("_");
            if (langAndCountry.length > 1) {
                String lang = langAndCountry[0];
                String country = langAndCountry[1];
                if (lang != null && lang.trim().length() > 0 && country != null && country.trim().length() > 0) {
                    Locale locale = new Locale(lang, country);
                    try {
                        if (!(locale.getISO3Country() == null || locale.getISO3Language() == null)) {
                            return locale;
                        }
                    } catch (MissingResourceException e) {
                    }
                }
            }
        }
        return null;
    }

    public String toString() {
        return this.localeCode;
    }
}
