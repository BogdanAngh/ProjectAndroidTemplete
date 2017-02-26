package com.x5.template.filters;

import com.google.common.base.Ascii;
import com.x5.template.Chunk;

public class EscapeXMLFilter extends BasicFilter implements ChunkFilter {
    private static final String findMe = "&<>\"'";
    private static final String[] replaceWith;

    static {
        replaceWith = new String[]{"&amp;", "&lt;", "&gt;", "&quot;", "&apos;"};
    }

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        boolean escapedSomething = false;
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int whichOne = findMe.indexOf(c);
            if (whichOne > -1) {
                escaped.append(replaceWith[whichOne]);
                escapedSomething = true;
            } else if (c == '\t' || c == '\n' || c == Letters.CR || (c >= Letters.SPACE && c < '\u0100')) {
                escaped.append(c);
            } else if (c > '\u00ff') {
                if (c <= '\ud7ff' || ((c >= '\ue000' && c <= '\ufffd') || (c >= Ascii.MIN && c <= '\uffff'))) {
                    escaped.append("&#x");
                    escaped.append(Integer.toHexString(c));
                    escaped.append(';');
                }
                escapedSomething = true;
            } else {
                escapedSomething = true;
            }
        }
        if (escapedSomething) {
            return escaped.toString();
        }
        return text;
    }

    public String getFilterName() {
        return "xml";
    }

    public String[] getFilterAliases() {
        return new String[]{"html", "xmlescape", "htmlescape", "escapexml", "escapehtml", "xmlesc", "htmlesc"};
    }
}
