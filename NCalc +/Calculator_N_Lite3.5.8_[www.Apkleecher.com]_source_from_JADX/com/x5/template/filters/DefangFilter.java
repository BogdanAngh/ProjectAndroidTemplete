package com.x5.template.filters;

import com.x5.template.Chunk;
import io.github.kexanie.library.BuildConfig;
import java.util.regex.Pattern;

public class DefangFilter extends BasicFilter implements ChunkFilter {
    private static final Pattern NOT_HARMLESS_CHAR;

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return text == null ? null : defang(text);
    }

    public String getFilterName() {
        return "defang";
    }

    public String[] getFilterAliases() {
        return new String[]{"noxss", "neuter"};
    }

    static {
        NOT_HARMLESS_CHAR = Pattern.compile("[^A-Za-z0-9@\\!\\?\\*\\#\\$\\(\\)\\+\\=\\:\\;\\,\\~\\/\\._-]");
    }

    private static String defang(String text) {
        if (text == null) {
            return null;
        }
        return NOT_HARMLESS_CHAR.matcher(text).replaceAll(BuildConfig.FLAVOR);
    }
}
