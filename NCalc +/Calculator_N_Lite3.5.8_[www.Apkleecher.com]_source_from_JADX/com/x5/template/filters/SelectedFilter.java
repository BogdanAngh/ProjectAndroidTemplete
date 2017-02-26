package com.x5.template.filters;

import com.x5.template.Chunk;
import io.github.kexanie.library.BuildConfig;

public class SelectedFilter extends BasicFilter implements ChunkFilter {
    private static final String CHECKED_TOKEN = " checked=\"checked\" ";
    private static final String SELECTED_TOKEN = " selected=\"selected\" ";

    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return text == null ? null : selected(chunk, text, args);
    }

    public String getFilterName() {
        return "selected";
    }

    public String[] getFilterAliases() {
        return new String[]{"select", "sel"};
    }

    private static String selected(Chunk context, String text, FilterArgs args) {
        return selected(context, text, args, SELECTED_TOKEN);
    }

    protected static String checked(Chunk context, String text, FilterArgs args) {
        return selected(context, text, args, CHECKED_TOKEN);
    }

    private static String selected(Chunk context, String text, FilterArgs arg, String token) {
        String[] args = arg.getFilterArgs();
        if (args == null) {
            return token;
        }
        String testValue = args[0];
        if (args.length > 1) {
            token = args[1];
        }
        if (testValue.charAt(0) == '~' || testValue.charAt(0) == '$') {
            Object value = context.get(testValue.substring(1));
            if (value == null || !text.equals(value.toString())) {
                return BuildConfig.FLAVOR;
            }
            return token;
        } else if (text.equals(testValue)) {
            return token;
        } else {
            return BuildConfig.FLAVOR;
        }
    }
}
