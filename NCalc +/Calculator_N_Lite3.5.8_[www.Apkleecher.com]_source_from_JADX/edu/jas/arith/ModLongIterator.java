package edu.jas.arith;

import java.util.Iterator;

/* compiled from: ModLongRing */
class ModLongIterator implements Iterator<ModLong> {
    long curr;
    final ModLongRing ring;

    public ModLongIterator(ModLongRing fac) {
        this.curr = 0;
        this.ring = fac;
    }

    public synchronized boolean hasNext() {
        return this.curr < this.ring.modul;
    }

    public synchronized ModLong next() {
        ModLong i;
        i = new ModLong(this.ring, this.curr);
        this.curr++;
        return i;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
