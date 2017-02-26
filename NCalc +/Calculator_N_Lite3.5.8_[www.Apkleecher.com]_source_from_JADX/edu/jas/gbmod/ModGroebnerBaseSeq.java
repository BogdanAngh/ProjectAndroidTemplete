package edu.jas.gbmod;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gbufd.GBFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import java.util.List;

public class ModGroebnerBaseSeq<C extends GcdRingElem<C>> extends ModGroebnerBaseAbstract<C> {
    protected final GroebnerBaseAbstract<C> bb;

    public ModGroebnerBaseSeq(RingFactory<C> cf) {
        this(GBFactory.getImplementation((RingFactory) cf));
    }

    public ModGroebnerBaseSeq(GroebnerBaseAbstract<C> bb) {
        this.bb = bb;
    }

    public boolean isGB(int modv, List<GenPolynomial<C>> F) {
        return this.bb.isGB(modv, (List) F);
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        return this.bb.GB(modv, F);
    }
}
