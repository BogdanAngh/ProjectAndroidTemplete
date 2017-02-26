package edu.jas.gb;

import edu.jas.structure.RingElem;

public class EGroebnerBaseSeq<C extends RingElem<C>> extends DGroebnerBaseSeq<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected EReduction<C> ered;

    static {
        $assertionsDisabled = !EGroebnerBaseSeq.class.desiredAssertionStatus();
    }

    public EGroebnerBaseSeq() {
        this(new EReductionSeq());
    }

    public EGroebnerBaseSeq(EReductionSeq<C> ered) {
        super(ered);
        this.ered = ered;
        if (!$assertionsDisabled && this.ered != this.red) {
            throw new AssertionError();
        }
    }
}
