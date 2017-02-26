package com.x5.template.providers;

import com.x5.template.ContentSource;
import com.x5.template.Snippet;
import com.x5.template.TemplateDoc;
import com.x5.template.TemplateDoc.Doclet;
import java.io.IOException;
import java.util.HashMap;

public abstract class TemplateProvider implements ContentSource {
    private static String DEFAULT_ENCODING;
    private static String DEFAULT_EXTENSION;
    private String encoding;
    private String extension;
    HashMap<String, Snippet> snippetCache;

    public abstract String getProtocol();

    public abstract String loadContainerDoc(String str) throws IOException;

    public TemplateProvider() {
        this.extension = DEFAULT_EXTENSION;
        this.encoding = DEFAULT_ENCODING;
        this.snippetCache = new HashMap();
    }

    static {
        DEFAULT_EXTENSION = "chtml";
        DEFAULT_ENCODING = "UTF-8";
    }

    public String fetch(String templateName) {
        Snippet s = getSnippet(templateName);
        if (s == null) {
            return null;
        }
        return s.toString();
    }

    public boolean provides(String itemName) {
        return getSnippet(itemName) != null;
    }

    public Snippet getSnippet(String templateName) {
        Snippet snippet = null;
        if (this.snippetCache.containsKey(templateName)) {
            return (Snippet) this.snippetCache.get(templateName);
        }
        String rawTemplate = null;
        try {
            rawTemplate = loadItemDoc(templateName);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        if (rawTemplate == null) {
            this.snippetCache.put(templateName, snippet);
            return snippet;
        }
        try {
            return parseSnippet(new TemplateDoc(templateName, rawTemplate), templateName);
        } catch (IOException e2) {
            return snippet;
        }
    }

    private Snippet parseSnippet(TemplateDoc doc, String snippetName) throws IOException {
        Snippet r = null;
        for (Doclet doclet : doc.parseTemplates(this.encoding)) {
            String templateKey = doclet.getName();
            Snippet s = doclet.getSnippet();
            if (templateKey.equals(snippetName)) {
                r = s;
            }
            this.snippetCache.put(templateKey, s);
        }
        return r;
    }

    public String loadItemDoc(String itemName) throws IOException {
        return loadContainerDoc(resourceName(itemName));
    }

    private String resourceName(String itemName) {
        String ext = this.extension;
        String embeddedExtension = parseEmbeddedExtension(itemName);
        if (embeddedExtension != null) {
            itemName = itemName.substring(embeddedExtension.length() + 2);
            ext = embeddedExtension;
        }
        int hashPos = itemName.indexOf(35);
        if (ext == null || ext.length() < 1) {
            if (hashPos < 0) {
                return itemName;
            }
            return itemName.substring(0, hashPos);
        } else if (hashPos < 0) {
            return itemName + '.' + ext;
        } else {
            return itemName.substring(0, hashPos) + '.' + ext;
        }
    }

    private String parseEmbeddedExtension(String itemName) {
        if (itemName.charAt(0) != ';') {
            return null;
        }
        int endColonPos = itemName.indexOf(59, 1);
        if (endColonPos >= 0) {
            return itemName.substring(1, endColonPos);
        }
        return null;
    }

    public void clearCache() {
        this.snippetCache.clear();
    }

    public void clearCache(String itemName) {
        this.snippetCache.remove(itemName);
    }

    public void setDefaultExtension(String ext) {
        this.extension = ext;
    }
}
