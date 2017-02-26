package edu.jas.poly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OverlapList implements Serializable {
    public final List<Overlap> ols;

    public OverlapList() {
        this.ols = new ArrayList();
    }

    public void add(Overlap ol) {
        this.ols.add(ol);
    }

    public String toString() {
        return this.ols.toString();
    }

    public boolean isOverlap(Word u, Word v) {
        for (Overlap ol : this.ols) {
            if (!ol.isOverlap(u, v)) {
                return false;
            }
        }
        return true;
    }
}
