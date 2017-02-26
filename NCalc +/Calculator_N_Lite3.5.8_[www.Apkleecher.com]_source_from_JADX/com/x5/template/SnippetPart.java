package com.x5.template;

import java.io.IOException;
import java.io.Writer;

public class SnippetPart {
    private boolean isLiteral;
    protected String snippetText;

    public SnippetPart(String text) {
        this.isLiteral = false;
        this.snippetText = text;
    }

    public String getText() {
        return this.snippetText;
    }

    public void setText(String text) {
        this.snippetText = text;
    }

    public void setLiteral(boolean isLiteral) {
        this.isLiteral = isLiteral;
    }

    public boolean isLiteral() {
        return this.isLiteral;
    }

    public boolean isTag() {
        return false;
    }

    public boolean depthCheckFails(int depth, Writer out) throws IOException {
        if (depth < 17) {
            return false;
        }
        out.append("[**ERR** max template recursions: 17]");
        return true;
    }

    public void render(Writer out, Chunk rules, String origin, int depth) throws IOException {
        if (this.isLiteral) {
            out.append(this.snippetText);
        }
    }

    public String toString() {
        return this.snippetText;
    }
}
