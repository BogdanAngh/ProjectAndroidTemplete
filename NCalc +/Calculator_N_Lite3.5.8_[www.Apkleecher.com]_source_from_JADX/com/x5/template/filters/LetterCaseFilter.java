package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.template.ChunkLocale;
import java.util.Locale;

public class LetterCaseFilter extends BasicFilter implements ChunkFilter {
    int OP_CAPITALIZE;
    int OP_LOWER;
    int OP_TITLE;
    int OP_UPPER;

    public LetterCaseFilter() {
        this.OP_UPPER = 0;
        this.OP_LOWER = 1;
        this.OP_CAPITALIZE = 2;
        this.OP_TITLE = 4;
    }

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        int op = this.OP_UPPER;
        if (args != null) {
            String filterName = args.getFilterName();
            if (filterName.equals("lower") || filterName.equals("lc")) {
                op = this.OP_LOWER;
            } else if (filterName.equals("capitalize") || filterName.equals("cap")) {
                op = this.OP_CAPITALIZE;
            } else if (filterName.equals("title")) {
                op = this.OP_TITLE;
            }
        }
        ChunkLocale locale = chunk == null ? null : chunk.getLocale();
        Locale javaLocale = null;
        if (locale != null) {
            javaLocale = locale.getJavaLocale();
        }
        if (javaLocale == null) {
            if (op == this.OP_UPPER) {
                return text.toUpperCase();
            }
            if (op == this.OP_LOWER) {
                return text.toLowerCase();
            }
            if (op == this.OP_CAPITALIZE) {
                return capitalize(text, null, false);
            }
            if (op == this.OP_TITLE) {
                return capitalize(text, null, true);
            }
            return null;
        } else if (op == this.OP_UPPER) {
            return text.toUpperCase(javaLocale);
        } else {
            if (op == this.OP_LOWER) {
                return text.toLowerCase(javaLocale);
            }
            if (op == this.OP_CAPITALIZE) {
                return capitalize(text, javaLocale, false);
            }
            if (op == this.OP_TITLE) {
                return capitalize(text, javaLocale, true);
            }
            return null;
        }
    }

    private String capitalize(String text, Locale javaLocale, boolean lcFirst) {
        if (lcFirst) {
            text = javaLocale == null ? text.toLowerCase() : text.toLowerCase(javaLocale);
        }
        char[] chars = text.toCharArray();
        boolean found = false;
        int i = 0;
        while (i < chars.length) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = javaLocale == null ? Character.toUpperCase(chars[i]) : Character.toString(chars[i]).toUpperCase(javaLocale).charAt(0);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false;
            }
            i++;
        }
        return String.valueOf(chars);
    }

    public String getFilterName() {
        return "upper";
    }

    public String[] getFilterAliases() {
        return new String[]{"uc", "lower", "lc", "capitalize", "cap", "title"};
    }
}
