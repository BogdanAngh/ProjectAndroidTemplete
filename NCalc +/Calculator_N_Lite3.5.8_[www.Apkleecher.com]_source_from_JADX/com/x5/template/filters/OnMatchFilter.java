package com.x5.template.filters;

import com.x5.template.Chunk;
import io.github.kexanie.library.BuildConfig;
import java.util.regex.Pattern;

public class OnMatchFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return applyMatchTransform(chunk, text, args);
    }

    public String getFilterName() {
        return "onmatch";
    }

    private static String applyMatchTransform(Chunk context, String text, FilterArgs arg) {
        String[] args = arg.getFilterArgs();
        if (args == null) {
            return text;
        }
        if (args.length == 1 && args[0] != null && args[0].length() == 0) {
            return text;
        }
        for (int i = 0; i < args.length; i += 2) {
            int i2 = i + 1;
            int length = args.length;
            if (i2 >= r0) {
                return text;
            }
            String test = args[i];
            String value = args[i + 1];
            if (test.equals("|nomatch|")) {
                return FilterArgs.magicBraces(context, value);
            }
            if (text != null) {
                int patternStart = test.indexOf(47) + 1;
                int patternEnd = test.lastIndexOf(47);
                if (patternStart < 0 || patternStart == patternEnd) {
                    return text;
                }
                boolean ignoreCase = false;
                boolean multiLine = false;
                boolean dotAll = false;
                String pattern = test.substring(patternStart, patternEnd);
                for (int c = test.length() - 1; c > patternEnd; c--) {
                    char option = test.charAt(c);
                    if (option == 'i') {
                        ignoreCase = true;
                    }
                    if (option == 'm') {
                        multiLine = true;
                    }
                    if (option == 's') {
                        dotAll = true;
                    }
                }
                if (multiLine) {
                    pattern = "(?m)" + pattern;
                }
                if (ignoreCase) {
                    pattern = "(?i)" + pattern;
                }
                if (dotAll) {
                    pattern = "(?s)" + pattern;
                }
                if (Pattern.compile(pattern).matcher(text).find()) {
                    return FilterArgs.magicBraces(context, value);
                }
            }
        }
        return BuildConfig.FLAVOR;
    }
}
