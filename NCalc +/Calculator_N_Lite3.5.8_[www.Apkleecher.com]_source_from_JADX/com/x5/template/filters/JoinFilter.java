package com.x5.template.filters;

import com.x5.template.Chunk;
import io.github.kexanie.library.BuildConfig;
import java.util.List;

public class JoinFilter extends ListFilter {
    public String getFilterName() {
        return "join";
    }

    public Object transformList(Chunk chunk, List list, FilterArgs args) {
        if (list == null) {
            return BuildConfig.FLAVOR;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        String divider = args.getUnparsedArgs();
        StringBuilder x = new StringBuilder();
        int i = 0;
        for (Object s : list) {
            if (i > 0 && divider != null) {
                x.append(divider);
            }
            if (s != null) {
                x.append(s.toString());
            }
            i++;
        }
        return x.toString();
    }
}
