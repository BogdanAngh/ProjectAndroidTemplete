package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;

public class WordPolyIterator<C extends RingElem<C>> implements Iterator<WordMonomial<C>> {
    protected final Iterator<Entry<Word, C>> ms;

    public WordPolyIterator(SortedMap<Word, C> m) {
        this.ms = m.entrySet().iterator();
    }

    public boolean hasNext() {
        return this.ms.hasNext();
    }

    public WordMonomial<C> next() {
        return new WordMonomial((Entry) this.ms.next());
    }

    public void remove() {
        this.ms.remove();
    }
}
