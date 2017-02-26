package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFilter extends BasicFilter implements ChunkFilter {
    private static final Pattern INNOCUOUS_CHARS;

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return text;
        }
        String regex = args.getUnparsedArgs();
        return regex != null ? applyRegex(text, regex) : text;
    }

    public String getFilterName() {
        return "s";
    }

    public static int nextRegexDelim(String regex, int searchFrom) {
        return FilterArgs.nextUnescapedDelim("/", regex, searchFrom);
    }

    public static String applyRegex(String text, String regex) {
        int patternStart = 1;
        if (regex.charAt(0) == 's') {
            patternStart = 2;
        }
        int patternEnd = nextRegexDelim(regex, patternStart);
        if (patternEnd < 0) {
            return text;
        }
        int replaceEnd = nextRegexDelim(regex, patternEnd + 1);
        if (replaceEnd < 0) {
            return text;
        }
        boolean greedy = false;
        boolean ignoreCase = false;
        boolean multiLine = false;
        boolean dotAll = false;
        for (int i = regex.length() - 1; i > replaceEnd; i--) {
            char option = regex.charAt(i);
            if (option == 'g') {
                greedy = true;
            }
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
        String pattern = regex.substring(patternStart, patternEnd);
        String str = "\\\\";
        String replaceWith = Chunk.findAndReplace(parseRegexEscapes(regex.substring(patternEnd + 1, replaceEnd)), "\\", r16);
        if (multiLine) {
            pattern = "(?m)" + pattern;
        }
        if (ignoreCase) {
            pattern = "(?i)" + pattern;
        }
        if (dotAll) {
            pattern = "(?s)" + pattern;
        }
        boolean caseConversions = false;
        if (replaceWith.matches(".*\\\\[UL][\\$\\\\]\\d.*")) {
            caseConversions = true;
            replaceWith = replaceWith.replaceAll("\\\\([UL])[\\$\\\\](\\d)", "!$1@\\$$2@$1!");
        }
        if (greedy) {
            try {
                String result = text.replaceAll(pattern, replaceWith);
            } catch (IndexOutOfBoundsException e) {
                return text + "[REGEX " + regex + " Error: " + e.getMessage() + "]";
            }
        }
        result = text.replaceFirst(pattern, replaceWith);
        if (caseConversions) {
            return applyCaseConversions(result);
        }
        return result;
    }

    private static String applyCaseConversions(String result) {
        StringBuilder x = new StringBuilder();
        Matcher m = Pattern.compile("!U@(.*?)@U!").matcher(result);
        int last = 0;
        while (m.find()) {
            x.append(result.substring(last, m.start()));
            x.append(m.group(1).toUpperCase());
            last = m.end();
        }
        if (last > 0) {
            x.append(result.substring(last));
            result = x.toString();
            x = new StringBuilder();
            last = 0;
        }
        m = Pattern.compile("!L@(.*?)@L!").matcher(result);
        while (m.find()) {
            x.append(result.substring(last, m.start()));
            x.append(m.group(1).toLowerCase());
            last = m.end();
        }
        if (last <= 0) {
            return result;
        }
        x.append(result.substring(last));
        return x.toString();
    }

    public static String parseRegexEscapes(String str) {
        if (str == null) {
            return str;
        }
        char[] strArr = str.toCharArray();
        boolean escape = false;
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while (i < strArr.length) {
            if (escape) {
                if (strArr[i] == 'b') {
                    buf.append('\b');
                } else if (strArr[i] == 't') {
                    buf.append('\t');
                } else if (strArr[i] == 'n') {
                    buf.append('\n');
                } else if (strArr[i] == 'r') {
                    buf.append(Letters.CR);
                } else if (strArr[i] == 'f') {
                    buf.append(Letters.FORM_FEED);
                } else if (strArr[i] == 'U') {
                    buf.append("\\U");
                } else if (strArr[i] == 'L') {
                    buf.append("\\L");
                } else if (strArr[i] == 'u') {
                    if (i + 4 < strArr.length) {
                        buf.append((char) Integer.parseInt(str.substring(i + 1, i + 5), 16));
                        i += 4;
                    } else {
                        buf.append(Letters.BACKSLASH);
                        buf.append(strArr[i]);
                    }
                } else if (Character.isDigit(strArr[i])) {
                    int j = 1;
                    while (j < 2 && i + j < strArr.length && Character.isDigit(strArr[i + j])) {
                        j++;
                    }
                    buf.append((char) Integer.parseInt(str.substring(i, i + j), 8));
                    i += j - 1;
                } else {
                    buf.append(strArr[i]);
                }
                escape = false;
            } else if (strArr[i] == Letters.BACKSLASH) {
                escape = true;
            } else {
                buf.append(strArr[i]);
            }
            i++;
        }
        return buf.toString();
    }

    static {
        INNOCUOUS_CHARS = Pattern.compile("^[-A-Za-z0-9_ <>\"']*$");
    }

    public static String escapeRegex(String x) {
        if (INNOCUOUS_CHARS.matcher(x).find()) {
            return x;
        }
        StringBuilder noSpecials = new StringBuilder();
        for (int i = 0; i < x.length(); i++) {
            char c = x.charAt(i);
            if (c == Letters.SPACE || ((c >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')))) {
                noSpecials.append(c);
            } else {
                noSpecials.append("\\");
                noSpecials.append(c);
            }
        }
        return noSpecials.toString();
    }
}
