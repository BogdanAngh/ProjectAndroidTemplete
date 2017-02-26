package edu.jas.gbmod;

import edu.jas.gb.SolvableGroebnerBaseAbstract;
import edu.jas.gbufd.SGBFactory;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;

public class ModSolvableGroebnerBasePar<C extends GcdRingElem<C>> extends ModSolvableGroebnerBaseSeq<C> {
    public ModSolvableGroebnerBasePar(RingFactory<C> cf) {
        this(SGBFactory.getProxy((RingFactory) cf));
    }

    public ModSolvableGroebnerBasePar(SolvableGroebnerBaseAbstract<C> sbb) {
        super((SolvableGroebnerBaseAbstract) sbb);
    }

    public void terminate() {
        this.sbb.terminate();
    }

    public int cancel() {
        return this.sbb.cancel();
    }
}
