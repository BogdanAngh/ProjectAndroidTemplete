package edu.jas.gbmod;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.List;

public interface SolvableSyzygy<C extends RingElem<C>> extends Serializable {
    boolean isLeftOreCond(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2, GenSolvablePolynomial<C>[] genSolvablePolynomialArr);

    boolean isLeftZeroRelation(ModuleList<C> moduleList, ModuleList<C> moduleList2);

    boolean isLeftZeroRelation(List<List<GenSolvablePolynomial<C>>> list, List<GenSolvablePolynomial<C>> list2);

    boolean isRightOreCond(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2, GenSolvablePolynomial<C>[] genSolvablePolynomialArr);

    boolean isRightZeroRelation(ModuleList<C> moduleList, ModuleList<C> moduleList2);

    boolean isRightZeroRelation(List<List<GenSolvablePolynomial<C>>> list, List<GenSolvablePolynomial<C>> list2);

    GenSolvablePolynomial<C>[] leftOreCond(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    ModuleList<C> leftZeroRelations(ModuleList<C> moduleList);

    List<List<GenSolvablePolynomial<C>>> leftZeroRelations(int i, List<GenSolvablePolynomial<C>> list);

    List<List<GenSolvablePolynomial<C>>> leftZeroRelations(List<GenSolvablePolynomial<C>> list);

    ModuleList<C> leftZeroRelationsArbitrary(ModuleList<C> moduleList);

    List<List<GenSolvablePolynomial<C>>> leftZeroRelationsArbitrary(int i, List<GenSolvablePolynomial<C>> list);

    List<List<GenSolvablePolynomial<C>>> leftZeroRelationsArbitrary(List<GenSolvablePolynomial<C>> list);

    List<SolvResPart<C>> resolution(ModuleList<C> moduleList);

    List resolution(PolynomialList<C> polynomialList);

    List<SolvResPart<C>> resolutionArbitrary(ModuleList<C> moduleList);

    List resolutionArbitrary(PolynomialList<C> polynomialList);

    GenSolvablePolynomial<C>[] rightOreCond(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    List<List<GenSolvablePolynomial<C>>> rightZeroRelationsArbitrary(int i, List<GenSolvablePolynomial<C>> list);

    List<List<GenSolvablePolynomial<C>>> rightZeroRelationsArbitrary(List<GenSolvablePolynomial<C>> list);
}
