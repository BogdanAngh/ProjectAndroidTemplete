package edu.jas.structure;

import java.io.Reader;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public interface ElemFactory<C extends Element<C>> extends Serializable {
    C copy(C c);

    C fromInteger(long j);

    C fromInteger(BigInteger bigInteger);

    List<C> generators();

    boolean isFinite();

    C parse(Reader reader);

    C parse(String str);

    C random(int i);

    C random(int i, Random random);

    String toScript();
}
