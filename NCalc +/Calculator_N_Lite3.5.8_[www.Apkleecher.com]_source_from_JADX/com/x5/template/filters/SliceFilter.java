package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.ArrayList;
import java.util.List;

public class SliceFilter extends ListFilter {
    public String getFilterName() {
        return "slice";
    }

    private static int parseSliceArg(String arg, int defaultVal, int len) {
        int x = defaultVal;
        if (arg == null || arg.trim().length() <= 0) {
            return x;
        }
        try {
            x = Integer.parseInt(arg.trim());
            if (len < 0 || x >= 0) {
                return x;
            }
            if (len + x < 0) {
                return 0;
            }
            return x + len;
        } catch (NumberFormatException e) {
            return x;
        }
    }

    public Object transformList(Chunk chunk, List list, FilterArgs arg) {
        if (list == null) {
            return list;
        }
        int step;
        int from;
        int to;
        int len = list.size();
        String endArg = null;
        String stepArg = null;
        String[] args = arg.getFilterArgs();
        String firstArg = args[0];
        if (args.length > 0) {
            int i;
            String[] sliceArgs = SplitFilter.splitNonRegex(firstArg, ":");
            boolean colonDelim = sliceArgs.length > 1;
            String fromArg = sliceArgs[0];
            if (colonDelim) {
                endArg = sliceArgs[1];
                if (sliceArgs.length > 2) {
                    stepArg = sliceArgs[2];
                }
            } else if (args.length > 1) {
                endArg = args[1];
                if (args.length > 2) {
                    stepArg = args[2];
                }
            }
            step = parseSliceArg(stepArg, 1, -1);
            from = parseSliceArg(fromArg, step < 0 ? len - 1 : 0, len);
            if (step < 0) {
                i = -1;
            } else {
                i = len;
            }
            to = parseSliceArg(endArg, i, len);
            if (from > len) {
                from = len;
            }
            if (step == 0) {
                step = 1;
                to = from;
            }
            if ((step > 0 && to < from) || (step < 0 && to > from)) {
                to = from;
            }
        } else {
            from = 0;
            to = len;
            step = 1;
        }
        if (step == 1) {
            return list.subList(from, to);
        }
        ArrayList<Object> stepped = new ArrayList();
        int i2 = from;
        while (true) {
            if (step > 0) {
                if (i2 >= to) {
                    break;
                }
            } else if (i2 <= to) {
                break;
            }
            stepped.add(list.get(i2));
            i2 += step;
        }
        return stepped;
    }
}
