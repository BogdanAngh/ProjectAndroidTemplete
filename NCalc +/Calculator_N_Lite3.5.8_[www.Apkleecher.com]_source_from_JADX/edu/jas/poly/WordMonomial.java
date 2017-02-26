package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Map.Entry;

public final class WordMonomial<C extends RingElem<C>> {
    public final C c;
    public final Word e;

    public WordMonomial(Entry<Word, C> me) {
        this((Word) me.getKey(), (RingElem) me.getValue());
    }

    public WordMonomial(Word e, C c) {
        this.e = e;
        this.c = c;
    }

    public Word word() {
        return this.e;
    }

    public C coefficient() {
        return this.c;
    }

    public String toString() {
        return this.c.toString() + " " + this.e.toString();
    }
}
