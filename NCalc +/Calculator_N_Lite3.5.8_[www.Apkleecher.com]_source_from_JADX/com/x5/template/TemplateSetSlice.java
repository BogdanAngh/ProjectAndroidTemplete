package com.x5.template;

public class TemplateSetSlice extends TemplateSet {
    private String context;
    private String extension;
    private TemplateSet parent;

    public TemplateSetSlice(TemplateSet parent, String templateContext) {
        this.extension = null;
        this.parent = parent;
        this.context = templateContext;
    }

    public TemplateSetSlice(TemplateSet parent, String templateContext, String ext) {
        this.extension = null;
        this.parent = parent;
        this.context = templateContext;
        this.extension = ext;
    }

    public Snippet getSnippet(String templateName) {
        String fullTemplateName = putInContext(templateName);
        if (this.extension == null) {
            return this.parent.getSnippet(fullTemplateName);
        }
        return this.parent.getSnippet(fullTemplateName, this.extension);
    }

    private String putInContext(String templateName) {
        if (templateName == null) {
            return null;
        }
        if (templateName.startsWith("#")) {
            return this.context + "." + templateName.substring(1);
        }
        return this.context + "." + templateName;
    }

    public Chunk makeChunk() {
        return this.parent.makeChunk();
    }

    public Chunk makeChunk(String templateName) {
        if (this.extension == null) {
            return this.parent.makeChunk(putInContext(templateName));
        }
        return this.parent.makeChunk(putInContext(templateName), this.extension);
    }
}
