package com.x5.template;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class SnippetBlockTag extends SnippetTag {
    private Snippet body;
    private BlockTag renderer;
    private SnippetTag tagClose;
    private SnippetTag tagOpen;

    public SnippetBlockTag(SnippetTag tagOpen, List<SnippetPart> bodyParts, SnippetTag tagClose, String origin) {
        super(tagOpen.snippetText, tagOpen.tag);
        this.tagOpen = tagOpen;
        this.tagClose = tagClose;
        this.body = new Snippet((List) bodyParts);
        this.body.setOrigin(origin);
        initBlockTag();
    }

    private void initBlockTag() {
        String tagName = this.tagOpen.tag;
        if (tagName.startsWith(".loop")) {
            this.renderer = new LoopTag(tagName, this.body);
        } else if (tagName.startsWith(".if")) {
            this.renderer = new IfTag(tagName, this.body);
        } else if (tagName.startsWith(".loc")) {
            this.renderer = new LocaleTag(tagName, this.body);
        } else if (tagName.startsWith(".exec")) {
            this.renderer = new MacroTag(tagName, this.body);
        }
    }

    public void render(Writer out, Chunk context, String origin, int depth) throws IOException {
        if (!depthCheckFails(depth, out) && this.renderer != null) {
            this.renderer.renderBlock(out, context, origin, depth);
        }
    }

    public String toString() {
        return this.snippetText + this.body.toString() + this.tagClose.toString();
    }

    public SnippetTag getOpenTag() {
        return this.tagOpen;
    }

    public Snippet getBody() {
        return this.body;
    }

    public SnippetTag getCloseTag() {
        return this.tagClose;
    }

    public boolean doSmartTrimAroundBlock() {
        if (this.renderer == null || !this.renderer.doSmartTrimAroundBlock()) {
            return false;
        }
        return true;
    }
}
