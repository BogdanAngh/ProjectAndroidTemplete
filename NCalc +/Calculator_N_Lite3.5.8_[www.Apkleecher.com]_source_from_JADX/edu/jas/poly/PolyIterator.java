package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;

public class PolyIterator<C extends RingElem<C>> implements Iterator<Monomial<C>> {
    protected final Iterator<Entry<ExpVector, C>> ms;

    public PolyIterator(SortedMap<ExpVector, C> m) {
        this.ms = m.entrySet().iterator();
    }

    public boolean hasNext() {
        return this.ms.hasNext();
    }

    public Monomial<C> next() {
        return new Monomial((Entry) this.ms.next());
    }

    public void remove() {
        this.ms.remove();
    }
}
