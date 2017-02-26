package edu.jas.gbmod;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import edu.jas.vector.GenVector;
import java.io.Serializable;
import java.util.List;

public interface Syzygy<C extends RingElem<C>> extends Serializable {
    boolean isZeroRelation(ModuleList<C> moduleList, ModuleList<C> moduleList2);

    boolean isZeroRelation(List<List<GenPolynomial<C>>> list, List<GenPolynomial<C>> list2);

    List<ResPart<C>> resolution(ModuleList<C> moduleList);

    List resolution(PolynomialList<C> polynomialList);

    List<ResPart<C>> resolutionArbitrary(ModuleList<C> moduleList);

    List resolutionArbitrary(PolynomialList<C> polynomialList);

    ModuleList<C> zeroRelations(ModuleList<C> moduleList);

    List<List<GenPolynomial<C>>> zeroRelations(int i, GenVector<GenPolynomial<C>> genVector);

    List<List<GenPolynomial<C>>> zeroRelations(int i, List<GenPolynomial<C>> list);

    List<List<GenPolynomial<C>>> zeroRelations(List<GenPolynomial<C>> list);

    ModuleList<C> zeroRelationsArbitrary(ModuleList<C> moduleList);

    List<List<GenPolynomial<C>>> zeroRelationsArbitrary(int i, List<GenPolynomial<C>> list);

    List<List<GenPolynomial<C>>> zeroRelationsArbitrary(List<GenPolynomial<C>> list);
}
