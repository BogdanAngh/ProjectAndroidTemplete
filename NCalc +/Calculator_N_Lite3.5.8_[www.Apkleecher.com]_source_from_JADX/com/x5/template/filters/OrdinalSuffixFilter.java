package com.x5.template.filters;

import com.x5.template.Chunk;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class OrdinalSuffixFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        return ordinalSuffix(text);
    }

    public String getFilterName() {
        return "th";
    }

    public String[] getFilterAliases() {
        return new String[]{"ord", "ordsuffix"};
    }

    private static String ordinalSuffix(String num) {
        if (num == null) {
            return null;
        }
        int x = Integer.parseInt(num);
        int mod10 = x % 10;
        if ((x % 100) - mod10 == 10) {
            return num + "th";
        }
        switch (mod10) {
            case ValueServer.REPLAY_MODE /*1*/:
                return num + "st";
            case IExpr.DOUBLEID /*2*/:
                return num + "nd";
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return num + "rd";
            default:
                return num + "th";
        }
    }
}
