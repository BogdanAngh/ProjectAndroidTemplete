package com.x5.template;

import java.io.IOException;
import java.io.Writer;

public class SnippetError extends SnippetPart {
    public SnippetError(String errMsg) {
        super(errMsg);
        super.setLiteral(true);
    }

    public void render(Writer out, Chunk rules, String origin, int depth) throws IOException {
        if (rules == null || rules.renderErrorsToOutput()) {
            out.append(this.snippetText);
        }
    }
}
