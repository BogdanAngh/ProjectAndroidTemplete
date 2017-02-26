package com.x5.template.filters;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.Chunk;
import java.util.Collection;

public class LengthFilter implements ChunkFilter {
    public Object applyFilter(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return Constants.ZERO;
        }
        return Integer.toString(text.length());
    }

    public Object applyFilter(Chunk chunk, Object obj, FilterArgs args) {
        if (obj == null) {
            return Constants.ZERO;
        }
        int len;
        if (obj instanceof Collection) {
            len = ((Collection) obj).size();
        } else if (obj instanceof Object[]) {
            len = ((Object[]) obj).length;
        } else {
            len = obj.toString().length();
        }
        return Integer.toString(len);
    }

    public String getFilterName() {
        return "length";
    }

    public String[] getFilterAliases() {
        return new String[]{"len"};
    }
}
