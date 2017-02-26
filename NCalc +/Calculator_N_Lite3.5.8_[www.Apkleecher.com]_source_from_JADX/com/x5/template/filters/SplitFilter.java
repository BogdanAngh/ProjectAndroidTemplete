package com.x5.template.filters;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.Chunk;

public class SplitFilter implements ChunkFilter {
    public static final String DEFAULT_DELIM = "/\\s+/";

    public Object applyFilter(Chunk chunk, String text, FilterArgs arg) {
        if (text == null) {
            return text;
        }
        String delim = null;
        int limit = -1;
        String[] args = arg.getFilterArgs();
        if (args == null || args.length < 1 || arg.getUnparsedArgs().length() < 1) {
            delim = DEFAULT_DELIM;
        } else if (args.length == 1) {
            delim = args[0];
        } else if (args.length > 1) {
            if (arg.getUnparsedArgs().equals(",")) {
                delim = ",";
            } else {
                delim = args[0];
                if (delim.length() == 0) {
                    delim = DEFAULT_DELIM;
                }
                try {
                    limit = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                }
            }
        }
        if (delim.length() <= 1 || delim.charAt(0) != Constants.DIV_UNICODE || delim.charAt(delim.length() - 1) != Constants.DIV_UNICODE) {
            return splitNonRegex(text, delim, limit);
        }
        String regexDelim = delim.substring(1, delim.length() - 1);
        if (limit <= 0) {
            return text.split(regexDelim);
        }
        String[] parts = text.split(regexDelim, limit + 1);
        if (parts.length <= limit) {
            return parts;
        }
        String[] limited = new String[limit];
        System.arraycopy(parts, 0, limited, 0, limit);
        return limited;
    }

    public Object applyFilter(Chunk chunk, Object obj, FilterArgs args) {
        if (obj == null) {
            return null;
        }
        return applyFilter(chunk, obj.toString(), args);
    }

    public String getFilterName() {
        return "split";
    }

    public String[] getFilterAliases() {
        return null;
    }

    public static String[] splitNonRegex(String input, String delim) {
        return splitNonRegex(input, delim, -1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] splitNonRegex(java.lang.String r6, java.lang.String r7, int r8) {
        /*
        r5 = -1;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r0 = 0;
        r1 = r7.length();
    L_0x000b:
        r2 = r6.indexOf(r7, r0);
        if (r2 != r5) goto L_0x002f;
    L_0x0011:
        r4 = r6.substring(r0);
        r3.add(r4);
    L_0x0018:
        if (r2 == r5) goto L_0x0022;
    L_0x001a:
        if (r8 <= 0) goto L_0x000b;
    L_0x001c:
        r4 = r3.size();
        if (r4 < r8) goto L_0x000b;
    L_0x0022:
        r4 = r3.size();
        r4 = new java.lang.String[r4];
        r4 = r3.toArray(r4);
        r4 = (java.lang.String[]) r4;
        return r4;
    L_0x002f:
        r4 = r6.substring(r0, r2);
        r3.add(r4);
        r0 = r2 + r1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.template.filters.SplitFilter.splitNonRegex(java.lang.String, java.lang.String, int):java.lang.String[]");
    }
}
