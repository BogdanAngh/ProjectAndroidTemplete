package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFilter extends ListFilter {
    public String getFilterName() {
        return "sort";
    }

    public Object transformList(Chunk chunk, List list, FilterArgs args) {
        if (list == null || list.size() < 2) {
            return list;
        }
        try {
            if (isInOrder(list)) {
                return list;
            }
            List sorted = new ArrayList(list);
            Collections.sort(sorted);
            return sorted;
        } catch (ClassCastException e) {
            return list;
        } catch (NullPointerException e2) {
            return list;
        }
    }

    private boolean isInOrder(List list) throws ClassCastException {
        boolean inOrder = true;
        for (int i = 1; i < list.size(); i++) {
            if (((Comparable) list.get(i - 1)).compareTo((Comparable) list.get(i)) > 0) {
                inOrder = false;
            }
        }
        return inOrder;
    }
}
