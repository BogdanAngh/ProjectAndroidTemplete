package edu.jas.util;

import edu.jas.structure.Element;
import edu.jas.structure.UnaryFunctor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListUtil {
    public static <C extends Element<C>, D extends Element<D>> List<D> map(List<C> list, UnaryFunctor<C, D> f) {
        if (list == null) {
            return null;
        }
        List<D> nl;
        if (list instanceof ArrayList) {
            nl = new ArrayList(list.size());
        } else if (list instanceof LinkedList) {
            nl = new LinkedList();
        } else {
            throw new RuntimeException("list type not implemented");
        }
        for (C c : list) {
            nl.add(f.eval(c));
        }
        return nl;
    }

    public static <C> List<List<C>> tupleFromList(List<List<C>> A) {
        if (A == null) {
            return null;
        }
        List<List<C>> T = new ArrayList(A.size());
        if (A.size() == 0) {
            return T;
        }
        if (A.size() == 1) {
            for (C a : (List) A.get(0)) {
                List<C> Tp = new ArrayList(1);
                Tp.add(a);
                T.add(Tp);
            }
            return T;
        }
        List<List<C>> Ap = new ArrayList(A);
        List<C> f = (List) Ap.remove(0);
        List<List<C>> Tp2 = tupleFromList(Ap);
        for (C a2 : f) {
            for (List<C> tp : Tp2) {
                List<C> ts = new ArrayList();
                ts.add(a2);
                ts.addAll(tp);
                T.add(ts);
            }
        }
        return T;
    }

    public static <C> List<C> fill(int n, C e) {
        List<C> r = new ArrayList(n);
        for (int m = 0; m < n; m++) {
            r.add(e);
        }
        return r;
    }
}
