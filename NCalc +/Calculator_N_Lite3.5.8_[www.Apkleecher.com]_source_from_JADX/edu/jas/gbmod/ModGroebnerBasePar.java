package edu.jas.gbmod;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gbufd.GBFactory;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;

public class ModGroebnerBasePar<C extends GcdRingElem<C>> extends ModGroebnerBaseSeq<C> {
    public ModGroebnerBasePar(RingFactory<C> cf) {
        this(GBFactory.getProxy((RingFactory) cf));
    }

    public ModGroebnerBasePar(GroebnerBaseAbstract<C> bb) {
        super((GroebnerBaseAbstract) bb);
    }

    public void terminate() {
        this.bb.terminate();
    }

    public int cancel() {
        return this.bb.cancel();
    }
}
