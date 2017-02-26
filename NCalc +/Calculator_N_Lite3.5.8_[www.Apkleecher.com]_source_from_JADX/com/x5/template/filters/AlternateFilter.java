package com.x5.template.filters;

import com.x5.template.Chunk;

public class AlternateFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs arg) {
        if (text == null) {
            return null;
        }
        String[] args = arg.getFilterArgs();
        if (args == null) {
            return text;
        }
        try {
            String output = null;
            if (Integer.parseInt(text) % 2 == 0) {
                output = args[0];
            } else if (args.length >= 2) {
                output = args[1];
            }
            return FilterArgs.magicBraces(chunk, output);
        } catch (NumberFormatException e) {
            return text;
        }
    }

    public String getFilterName() {
        return "alternate";
    }

    public String[] getFilterAliases() {
        return new String[]{"evenodd"};
    }
}
