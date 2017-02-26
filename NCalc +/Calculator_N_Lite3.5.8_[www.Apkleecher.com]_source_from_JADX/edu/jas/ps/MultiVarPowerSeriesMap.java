package edu.jas.ps;

import edu.jas.structure.RingElem;

public interface MultiVarPowerSeriesMap<C extends RingElem<C>> {
    MultiVarPowerSeries<C> map(MultiVarPowerSeries<C> multiVarPowerSeries);
}
