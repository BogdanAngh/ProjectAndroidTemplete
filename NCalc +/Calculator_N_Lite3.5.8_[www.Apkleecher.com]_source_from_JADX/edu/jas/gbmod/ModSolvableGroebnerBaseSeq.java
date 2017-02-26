package edu.jas.gbmod;

import edu.jas.gb.SolvableGroebnerBaseAbstract;
import edu.jas.gbufd.SGBFactory;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import java.util.List;

public class ModSolvableGroebnerBaseSeq<C extends GcdRingElem<C>> extends ModSolvableGroebnerBaseAbstract<C> {
    protected final SolvableGroebnerBaseAbstract<C> sbb;

    public ModSolvableGroebnerBaseSeq(RingFactory<C> cf) {
        this(SGBFactory.getImplementation((RingFactory) cf));
    }

    public ModSolvableGroebnerBaseSeq(SolvableGroebnerBaseAbstract<C> sbb) {
        this.sbb = sbb;
    }

    public boolean isLeftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return this.sbb.isLeftGB(modv, (List) F);
    }

    public List<GenSolvablePolynomial<C>> leftGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return this.sbb.leftGB(modv, F);
    }

    public boolean isTwosidedGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return this.sbb.isTwosidedGB(modv, F);
    }

    public List<GenSolvablePolynomial<C>> twosidedGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return this.sbb.twosidedGB(modv, F);
    }

    public boolean isRightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        return this.sbb.isRightGB(modv, F);
    }

    public List<GenSolvablePolynomial<C>> rightGB(int modv, List<GenSolvablePolynomial<C>> F) {
        if (modv == 0) {
            return this.sbb.rightGB(modv, F);
        }
        throw new UnsupportedOperationException("modv != 0 not jet implemented");
    }
}
