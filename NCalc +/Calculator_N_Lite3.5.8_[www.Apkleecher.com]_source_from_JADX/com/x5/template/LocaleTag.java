package com.x5.template;

import com.x5.template.filters.FilterArgs;
import com.x5.template.filters.RegexFilter;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math4.geometry.VectorFormat;

public class LocaleTag extends BlockTag {
    public static String LOCALE_SIMPLE_CLOSE;
    public static String LOCALE_SIMPLE_OPEN;
    public static String LOCALE_TAG_CLOSE;
    public static String LOCALE_TAG_OPEN;
    private static final Pattern OPEN_TAG_PATTERN;
    private String[] args;
    private String body;
    private Chunk context;

    static {
        LOCALE_TAG_OPEN = "{_[";
        LOCALE_TAG_CLOSE = VectorFormat.DEFAULT_SUFFIX;
        LOCALE_SIMPLE_OPEN = "_[";
        LOCALE_SIMPLE_CLOSE = "]";
        OPEN_TAG_PATTERN = Pattern.compile(RegexFilter.escapeRegex(LOCALE_TAG_OPEN) + "|" + RegexFilter.escapeRegex(LOCALE_SIMPLE_OPEN));
    }

    public LocaleTag(String params, Chunk context) {
        this.body = null;
        this.context = context;
        parseParams(params);
    }

    public LocaleTag() {
        this.body = null;
    }

    public LocaleTag(String params, Snippet body) {
        this.body = null;
        this.body = body.toString();
    }

    private void parseParams(String params) {
        if (params != null) {
            int spacePos = params.indexOf(" ");
            if (spacePos >= 0) {
                String cleanParams = params.substring(spacePos + 1).trim();
                if (cleanParams.startsWith(",")) {
                    cleanParams = cleanParams.substring(1).trim();
                }
                if (cleanParams.length() != 0) {
                    this.args = cleanParams.split(" *(?<!\\\\), *");
                }
            }
        }
    }

    private String _translate() {
        ChunkLocale locale = this.context.getLocale();
        if (locale == null) {
            return ChunkLocale.processFormatString(this.body, this.args, this.context);
        }
        return locale.translate(this.body, this.args, this.context);
    }

    public String getBlockStartMarker() {
        return "loc";
    }

    public String getBlockEndMarker() {
        return "/loc";
    }

    private static String convertToChunkTag(String ezSyntax, Chunk ctx) {
        int bodyB;
        String body;
        String blockStart;
        if (ezSyntax.startsWith(LOCALE_SIMPLE_OPEN)) {
            bodyB = ezSyntax.length();
            if (ezSyntax.endsWith(LOCALE_SIMPLE_CLOSE)) {
                bodyB--;
            }
            body = ezSyntax.substring(2, bodyB);
            blockStart = ctx.makeTag(".loc");
            return blockStart + body + ctx.makeTag("./loc");
        } else if (!ezSyntax.startsWith(LOCALE_TAG_OPEN)) {
            return ezSyntax;
        } else {
            bodyB = ezSyntax.lastIndexOf(LOCALE_SIMPLE_CLOSE);
            if (bodyB < 0) {
                return convertToChunkTag(ezSyntax.substring(1), ctx);
            }
            body = ezSyntax.substring(3, bodyB);
            int argsA = bodyB + 1;
            int argsB = ezSyntax.length();
            if (ezSyntax.endsWith(LOCALE_TAG_CLOSE)) {
                argsB--;
            }
            blockStart = ctx.makeTag(".loc " + ezSyntax.substring(argsA, argsB));
            return blockStart + body + ctx.makeTag("./loc");
        }
    }

    public static String expandLocaleTags(String template, Chunk ctx) {
        int[] markers = scanForMarkers(template);
        if (markers == null) {
            return template;
        }
        StringBuilder buf = new StringBuilder();
        int cursor = 0;
        for (int i = 0; i < markers.length; i += 3) {
            int a = markers[i];
            int b = markers[i + 1];
            int c = markers[i + 2];
            buf.append(template.substring(cursor, a));
            buf.append(convertToChunkTag(template.substring(a, b), ctx));
            cursor = c;
        }
        if (cursor < template.length()) {
            buf.append(template.substring(cursor));
        }
        return buf.toString();
    }

    private static int[] scanForMarkers(String template) {
        if (template.indexOf(LOCALE_SIMPLE_OPEN) < 0) {
            return null;
        }
        int tagPos;
        String markers = BuildConfig.FLAVOR;
        int len = template.length();
        Matcher m = OPEN_TAG_PATTERN.matcher(template);
        if (m.find()) {
            tagPos = m.start();
        } else {
            tagPos = -1;
        }
        while (tagPos > -1) {
            boolean isSimple = m.group().equals(LOCALE_SIMPLE_OPEN);
            int tagEndInside = nextUnescapedDelim(isSimple, template, tagPos);
            int tagEndOutside = tagEndInside + (isSimple ? LOCALE_SIMPLE_CLOSE.length() : LOCALE_TAG_CLOSE.length());
            markers = markers + tagPos + "," + tagEndInside + "," + tagEndOutside + ",";
            if (tagEndOutside >= len) {
                break;
            } else if (m.find(tagEndOutside)) {
                tagPos = m.start();
            } else {
                tagPos = -1;
            }
        }
        return makeIntArray(markers);
    }

    private static int nextUnescapedDelim(boolean isSimple, String template, int tagPos) {
        if (isSimple) {
            return FilterArgs.nextUnescapedDelim(LOCALE_SIMPLE_CLOSE, template, LOCALE_SIMPLE_OPEN.length() + tagPos);
        }
        return FilterArgs.nextUnescapedDelim(LOCALE_TAG_CLOSE, template, LOCALE_TAG_OPEN.length() + tagPos);
    }

    private static int[] makeIntArray(String markersStr) {
        StringTokenizer tokens = new StringTokenizer(markersStr, ",");
        int[] markers = new int[tokens.countTokens()];
        for (int i = 0; i < markers.length; i++) {
            markers[i] = Integer.parseInt(tokens.nextToken());
        }
        return markers;
    }

    public void renderBlock(Writer out, Chunk context, String origin, int depth) throws IOException {
        if (this.body != null) {
            this.context = context;
            out.append(_translate());
        }
    }
}
