package edu.jas.ps;

import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;
import edu.jas.util.ListUtil;
import java.util.List;

public class PSUtil {

    static class 1 implements UnaryFunctor<MultiVarPowerSeries<C>, MultiVarPowerSeries<C>> {
        1() {
        }

        public MultiVarPowerSeries<C> eval(MultiVarPowerSeries<C> c) {
            if (c == null) {
                return null;
            }
            return c.monic();
        }
    }

    static class 2 implements UnaryFunctor<UnivPowerSeries<C>, UnivPowerSeries<C>> {
        2() {
        }

        public UnivPowerSeries<C> eval(UnivPowerSeries<C> c) {
            if (c == null) {
                return null;
            }
            return c.monic();
        }
    }

    public static <C extends RingElem<C>> List<MultiVarPowerSeries<C>> monic(List<MultiVarPowerSeries<C>> L) {
        return ListUtil.map(L, new 1());
    }

    public static <C extends RingElem<C>> List<UnivPowerSeries<C>> monicUniv(List<UnivPowerSeries<C>> L) {
        return ListUtil.map(L, new 2());
    }
}
