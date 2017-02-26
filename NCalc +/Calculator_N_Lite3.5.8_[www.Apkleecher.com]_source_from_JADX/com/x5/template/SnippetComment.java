package com.x5.template;

import java.io.IOException;
import java.io.Writer;

public class SnippetComment extends SnippetPart {
    public SnippetComment(String text) {
        super(text);
    }

    public void render(Writer out, Chunk context, String origin, int depth) throws IOException {
    }
}
