package edu.jas.ps;

import edu.jas.structure.RingElem;

public interface UnivPowerSeriesMap<C extends RingElem<C>> {
    UnivPowerSeries<C> map(UnivPowerSeries<C> univPowerSeries);
}
