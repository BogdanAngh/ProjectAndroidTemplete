package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.template.ChunkLocale;
import io.github.kexanie.library.BuildConfig;
import java.util.IllegalFormatException;
import java.util.Locale;

public class FormatFilter extends BasicFilter {
    public String transformText(Chunk chunk, String text, FilterArgs arg) {
        if (text == null) {
            return null;
        }
        String fmtString = arg.getUnparsedArgs();
        if (fmtString == null) {
            return BuildConfig.FLAVOR;
        }
        ChunkLocale locale = null;
        if (chunk != null) {
            locale = chunk.getLocale();
        }
        return applyFormatString(text, fmtString, locale);
    }

    public String getFilterName() {
        return "sprintf";
    }

    private static String applyFormatString(String text, String formatString, ChunkLocale locale) {
        if (formatString.startsWith("sprintf(")) {
            formatString = formatString.substring(8);
            if (formatString.endsWith(")")) {
                formatString = formatString.substring(0, formatString.length() - 1);
            }
        }
        char first = formatString.charAt(0);
        if (first == formatString.charAt(formatString.length() - 1) && (first == '\'' || first == Letters.QUOTE)) {
            formatString = formatString.substring(1, formatString.length() - 1);
        }
        return formatNumberFromString(formatString, text, locale);
    }

    public static String formatNumberFromString(String formatString, String value) {
        return formatNumberFromString(formatString, value, null);
    }

    public static Locale getJavaLocale(ChunkLocale locale) {
        if (locale == null) {
            return null;
        }
        return locale.getJavaLocale();
    }

    public static String formatNumberFromString(String formatString, String value, ChunkLocale chunkLocale) {
        char expecting = formatString.charAt(formatString.length() - 1);
        try {
            Locale locale = getJavaLocale(chunkLocale);
            if ("sS".indexOf(expecting) > -1) {
                return String.format(locale, formatString, new Object[]{value});
            } else if ("eEfgGaA".indexOf(expecting) > -1) {
                f = Float.valueOf(value).floatValue();
                return String.format(locale, formatString, new Object[]{Float.valueOf(f)});
            } else if ("doxX".indexOf(expecting) > -1) {
                long l;
                if (value.trim().startsWith("#")) {
                    l = Long.parseLong(value.trim().substring(1), 16);
                    return String.format(locale, formatString, new Object[]{Long.valueOf(l)});
                } else if (value.trim().startsWith("0X") || value.trim().startsWith("0x")) {
                    l = Long.parseLong(value.trim().substring(2), 16);
                    return String.format(locale, formatString, new Object[]{Long.valueOf(l)});
                } else {
                    f = Float.valueOf(value).floatValue();
                    return String.format(locale, formatString, new Object[]{Long.valueOf((long) f)});
                }
            } else if ("cC".indexOf(expecting) <= -1) {
                return "[Unknown format " + expecting + ": \"" + formatString + "\"," + value + "]";
            } else {
                if (value.trim().startsWith("0X") || value.trim().startsWith("0x")) {
                    int i = Integer.parseInt(value.trim().substring(2), 16);
                    return String.format(locale, formatString, new Object[]{Character.valueOf((char) i)});
                }
                f = Float.valueOf(value).floatValue();
                return String.format(locale, formatString, new Object[]{Character.valueOf((char) ((int) f))});
            }
        } catch (NumberFormatException e) {
            return value;
        } catch (IllegalFormatException e2) {
            return "[" + e2.getClass().getName() + ": " + e2.getMessage() + " \"" + formatString + "\"," + value + "]";
        }
    }
}
