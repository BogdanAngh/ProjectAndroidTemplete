package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;

public interface CharacteristicSet<C extends GcdRingElem<C>> extends Serializable {
    List<GenPolynomial<C>> characteristicSet(List<GenPolynomial<C>> list);

    GenPolynomial<C> characteristicSetReduction(List<GenPolynomial<C>> list, GenPolynomial<C> genPolynomial);

    boolean isCharacteristicSet(List<GenPolynomial<C>> list);
}
