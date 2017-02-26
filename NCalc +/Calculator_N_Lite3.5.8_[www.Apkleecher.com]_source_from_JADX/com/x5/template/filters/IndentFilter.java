package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndentFilter extends BasicFilter implements ChunkFilter {
    private static final Pattern EOL;

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return text == null ? null : applyIndent(text, args);
    }

    public String getFilterName() {
        return "indent";
    }

    static {
        EOL = Pattern.compile("(\\r\\n|\\r\\r|\\n)");
    }

    public static String applyIndent(String text, FilterArgs arg) {
        String[] args = arg.getFilterArgs();
        if (args != null) {
            String indent = args[0];
            String padChip = " ";
            if (args.length > 1) {
                padChip = args[1];
            }
            try {
                int pad = Integer.parseInt(indent);
                int textLen = text.length();
                String linePrefix = padChip;
                for (int i = 1; i < pad; i++) {
                    linePrefix = linePrefix + padChip;
                }
                StringBuilder indented = new StringBuilder();
                indented.append(linePrefix);
                Matcher m = EOL.matcher(text);
                int marker = 0;
                while (m.find()) {
                    indented.append(text.substring(marker, m.end()));
                    marker = m.end();
                    if (marker < textLen) {
                        indented.append(linePrefix);
                    }
                }
                if (marker < textLen) {
                    indented.append(text.substring(marker));
                }
                text = indented.toString();
            } catch (NumberFormatException e) {
            }
        }
        return text;
    }
}
