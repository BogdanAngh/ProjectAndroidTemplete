package edu.jas.gb;

/* compiled from: GBTransportMess */
final class GBTransportMessPairIndex extends GBTransportMess {
    public final int i;
    public final int j;
    public final int s;

    public GBTransportMessPairIndex(Pair p) {
        this(p.i, p.j, p.s);
    }

    @Deprecated
    public GBTransportMessPairIndex(int i, int j) {
        this(i, j, 0);
    }

    public GBTransportMessPairIndex(int i, int j, int s) {
        this.i = i;
        this.j = j;
        this.s = Math.max(this.j, Math.max(this.i, s));
    }

    public GBTransportMessPairIndex(Integer i, Integer j, Integer s) {
        this(i.intValue(), j.intValue(), s.intValue());
    }

    public String toString() {
        return super.toString() + "( " + this.i + "," + this.j + "," + this.s + ")";
    }
}
