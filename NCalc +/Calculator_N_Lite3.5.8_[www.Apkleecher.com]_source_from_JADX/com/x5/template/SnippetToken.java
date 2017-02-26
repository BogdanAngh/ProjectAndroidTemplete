package com.x5.template;

import java.io.IOException;
import java.io.Writer;

public class SnippetToken extends SnippetPart {
    private String[] args;
    protected String token;

    public SnippetToken(String text, String token) {
        super(text);
        this.token = token;
    }

    public void render(Writer out, Chunk context, String origin, int depth) throws IOException {
        String translated;
        ChunkLocale locale = context.getLocale();
        if (locale != null) {
            translated = locale.translate(this.token, this.args, context);
        } else if (this.args == null) {
            out.append(this.token);
            return;
        } else {
            translated = ChunkLocale.processFormatString(this.token, this.args, context);
        }
        Snippet.getSnippet(translated).render(out, context, depth);
    }

    public static SnippetToken parseTokenWithArgs(String wholeTag) {
        int bodyA = 3;
        int bodyB = wholeTag.lastIndexOf(LocaleTag.LOCALE_SIMPLE_CLOSE);
        if (bodyB < 0) {
            return new SnippetToken(wholeTag, wholeTag.substring(3));
        }
        int argsA = bodyB + 1;
        int argsB = wholeTag.length();
        if (wholeTag.endsWith(LocaleTag.LOCALE_TAG_CLOSE)) {
            argsB--;
        }
        if (wholeTag.startsWith("{%")) {
            bodyA = 3 + 1;
            while (Character.isWhitespace(wholeTag.charAt(bodyA - 2))) {
                bodyA++;
            }
            if (wholeTag.charAt(argsB - 1) == '%') {
                argsB--;
            }
            while (Character.isWhitespace(wholeTag.charAt(argsB - 1))) {
                argsB--;
            }
        }
        if (wholeTag.charAt(argsA) == Letters.COMMA) {
            argsA++;
        }
        String params = wholeTag.substring(argsA, argsB);
        SnippetToken tokenWithArgs = new SnippetToken(wholeTag, wholeTag.substring(bodyA, bodyB));
        if (params == null || params.trim().length() <= 0) {
            return tokenWithArgs;
        }
        tokenWithArgs.args = params.split(" *, *");
        return tokenWithArgs;
    }
}
