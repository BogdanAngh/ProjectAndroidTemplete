package edu.jas.poly;

import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.log4j.Logger;

public final class TermOrder implements Serializable {
    public static final int DEFAULT_EVORD = 4;
    public static final int GRLEX = 3;
    public static final int IGRLEX = 4;
    public static final int INVLEX = 2;
    public static final int LEX = 1;
    public static final int REVILEX = 6;
    public static final int REVITDG = 8;
    public static final int REVLEX = 5;
    public static final int REVTDEG = 7;
    private static final Logger logger;
    private final boolean debug;
    private final int evbeg1;
    private final int evbeg2;
    private final int evend1;
    private final int evend2;
    private final int evord;
    private final int evord2;
    private final EVComparator horder;
    private final EVComparator lorder;
    private final EVComparator sugar;
    private final long[][] weight;

    public static abstract class EVComparator implements Comparator<ExpVector>, Serializable {
        public abstract int compare(ExpVector expVector, ExpVector expVector2);
    }

    class 10 extends EVComparator {
        10() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVIGLC(e1, e2);
        }
    }

    class 11 extends EVComparator {
        11() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -ExpVector.EVIWLC(TermOrder.this.weight, e1, e2);
        }
    }

    class 12 extends EVComparator {
        12() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVIWLC(TermOrder.this.weight, e1, e2);
        }
    }

    class 13 extends EVComparator {
        13() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 14 extends EVComparator {
        14() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 15 extends EVComparator {
        15() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 16 extends EVComparator {
        16() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 17 extends EVComparator {
        17() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 18 extends EVComparator {
        18() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 19 extends EVComparator {
        19() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 1 extends EVComparator {
        1() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVILCP(e1, e2);
        }
    }

    class 20 extends EVComparator {
        20() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 21 extends EVComparator {
        21() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 22 extends EVComparator {
        22() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 23 extends EVComparator {
        23() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 24 extends EVComparator {
        24() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 25 extends EVComparator {
        25() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 26 extends EVComparator {
        26() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 27 extends EVComparator {
        27() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 28 extends EVComparator {
        28() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 29 extends EVComparator {
        29() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 2 extends EVComparator {
        2() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -ExpVector.EVILCP(e1, e2);
        }
    }

    class 30 extends EVComparator {
        30() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 31 extends EVComparator {
        31() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 32 extends EVComparator {
        32() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 33 extends EVComparator {
        33() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 34 extends EVComparator {
        34() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 35 extends EVComparator {
        35() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 36 extends EVComparator {
        36() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 37 extends EVComparator {
        37() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 38 extends EVComparator {
        38() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 39 extends EVComparator {
        39() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 3 extends EVComparator {
        3() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVIGLC(e1, e2);
        }
    }

    class 40 extends EVComparator {
        40() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 41 extends EVComparator {
        41() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 42 extends EVComparator {
        42() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 43 extends EVComparator {
        43() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 44 extends EVComparator {
        44() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 45 extends EVComparator {
        45() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 46 extends EVComparator {
        46() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 47 extends EVComparator {
        47() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 48 extends EVComparator {
        48() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 49 extends EVComparator {
        49() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 4 extends EVComparator {
        4() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -ExpVector.EVIGLC(e1, e2);
        }
    }

    class 50 extends EVComparator {
        50() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 51 extends EVComparator {
        51() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 52 extends EVComparator {
        52() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 53 extends EVComparator {
        53() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 54 extends EVComparator {
        54() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 55 extends EVComparator {
        55() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 56 extends EVComparator {
        56() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 57 extends EVComparator {
        57() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 58 extends EVComparator {
        58() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 59 extends EVComparator {
        59() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 5 extends EVComparator {
        5() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVRILCP(e1, e2);
        }
    }

    class 60 extends EVComparator {
        60() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 61 extends EVComparator {
        61() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 62 extends EVComparator {
        62() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 63 extends EVComparator {
        63() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 64 extends EVComparator {
        64() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 65 extends EVComparator {
        65() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 66 extends EVComparator {
        66() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRILCP(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 67 extends EVComparator {
        67() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 68 extends EVComparator {
        68() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            int t = -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg1, TermOrder.this.evend1);
            return t != 0 ? t : -ExpVector.EVRIGLC(e1, e2, TermOrder.this.evbeg2, TermOrder.this.evend2);
        }
    }

    class 69 extends EVComparator {
        69() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -TermOrder.this.horder.compare(e1, e2);
        }
    }

    class 6 extends EVComparator {
        6() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -ExpVector.EVRILCP(e1, e2);
        }
    }

    class 70 extends EVComparator {
        70() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVIGLC(e1, e2);
        }
    }

    class 7 extends EVComparator {
        7() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return ExpVector.EVRIGLC(e1, e2);
        }
    }

    class 8 extends EVComparator {
        8() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -ExpVector.EVRIGLC(e1, e2);
        }
    }

    class 9 extends EVComparator {
        9() {
        }

        public int compare(ExpVector e1, ExpVector e2) {
            return -TermOrder.this.horder.compare(e1, e2);
        }
    }

    static {
        logger = Logger.getLogger(TermOrder.class);
    }

    public TermOrder() {
        this((int) IGRLEX);
    }

    public TermOrder(int evord) {
        this.debug = logger.isDebugEnabled();
        if (evord < LEX || REVITDG < evord) {
            throw new IllegalArgumentException("invalid term order: " + evord);
        }
        this.evord = evord;
        this.evord2 = 0;
        this.weight = (long[][]) null;
        this.evbeg1 = 0;
        this.evend1 = BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        this.evbeg2 = this.evend1;
        this.evend2 = this.evend1;
        switch (evord) {
            case LEX /*1*/:
                this.horder = new 1();
                break;
            case INVLEX /*2*/:
                this.horder = new 2();
                break;
            case GRLEX /*3*/:
                this.horder = new 3();
                break;
            case IGRLEX /*4*/:
                this.horder = new 4();
                break;
            case REVLEX /*5*/:
                this.horder = new 5();
                break;
            case REVILEX /*6*/:
                this.horder = new 6();
                break;
            case REVTDEG /*7*/:
                this.horder = new 7();
                break;
            case REVITDG /*8*/:
                this.horder = new 8();
                break;
            default:
                this.horder = null;
                break;
        }
        if (this.horder == null) {
            throw new IllegalArgumentException("invalid term order: " + evord);
        }
        this.lorder = new 9();
        this.sugar = new 10();
    }

    public TermOrder(long[] w) {
        long[][] jArr = new long[LEX][];
        jArr[0] = w;
        this(jArr);
    }

    public TermOrder(long[][] w) {
        this.debug = logger.isDebugEnabled();
        if (w == null || w.length == 0) {
            throw new IllegalArgumentException("invalid term order weight");
        }
        this.weight = (long[][]) Arrays.copyOf(w, w.length);
        this.evord = 0;
        this.evord2 = 0;
        this.evbeg1 = 0;
        this.evend1 = this.weight[0].length;
        this.evbeg2 = this.evend1;
        this.evend2 = this.evend1;
        this.horder = new 11();
        this.lorder = new 12();
        this.sugar = this.horder;
    }

    public TermOrder(int r, int split) {
        this(IGRLEX, IGRLEX, r, split);
    }

    public TermOrder(int ev1, int ev2, int r, int split) {
        this.debug = logger.isDebugEnabled();
        if (ev1 < LEX || REVITDG < ev1) {
            throw new IllegalArgumentException("invalid term order: " + ev1);
        } else if (ev2 < LEX || REVITDG < ev2) {
            throw new IllegalArgumentException("invalid term order: " + ev2);
        } else {
            this.evord = ev1;
            this.evord2 = ev2;
            this.weight = (long[][]) null;
            this.evbeg1 = 0;
            this.evend1 = split;
            this.evbeg2 = split;
            this.evend2 = r;
            if (this.evbeg2 > this.evend2) {
                throw new IllegalArgumentException("invalid term order split, r = " + r + ", split = " + split);
            }
            switch (this.evord) {
                case LEX /*1*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 13();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 14();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 15();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 16();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case INVLEX /*2*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 17();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 18();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 19();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 20();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 21();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 22();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 23();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 24();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case GRLEX /*3*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 25();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 26();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 27();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 28();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case IGRLEX /*4*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 29();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 30();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 31();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 32();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 33();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 34();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 35();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 36();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case REVLEX /*5*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 37();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 38();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 39();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 40();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 41();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 42();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 43();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 44();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case REVILEX /*6*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 45();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 46();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 47();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 48();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 49();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 50();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 51();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 52();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case REVTDEG /*7*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 53();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 54();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 55();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 56();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 57();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 58();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 59();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 60();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                case REVITDG /*8*/:
                    switch (this.evord2) {
                        case LEX /*1*/:
                            this.horder = new 61();
                            break;
                        case INVLEX /*2*/:
                            this.horder = new 62();
                            break;
                        case GRLEX /*3*/:
                            this.horder = new 63();
                            break;
                        case IGRLEX /*4*/:
                            this.horder = new 64();
                            break;
                        case REVLEX /*5*/:
                            this.horder = new 65();
                            break;
                        case REVILEX /*6*/:
                            this.horder = new 66();
                            break;
                        case REVTDEG /*7*/:
                            this.horder = new 67();
                            break;
                        case REVITDG /*8*/:
                            this.horder = new 68();
                            break;
                        default:
                            this.horder = null;
                            break;
                    }
                default:
                    this.horder = null;
                    break;
            }
            if (this.horder == null) {
                throw new IllegalArgumentException("invalid term order: " + this.evord + " 2 " + this.evord2);
            }
            this.lorder = new 69();
            this.sugar = new 70();
        }
    }

    public int getEvord() {
        return this.evord;
    }

    public int getEvord2() {
        return this.evord2;
    }

    public int getSplit() {
        return this.evend1;
    }

    public long[][] getWeight() {
        if (this.weight == null) {
            return (long[][]) null;
        }
        return (long[][]) Arrays.copyOf(this.weight, this.weight.length);
    }

    public EVComparator getDescendComparator() {
        return this.horder;
    }

    public EVComparator getAscendComparator() {
        return this.lorder;
    }

    public EVComparator getSugarComparator() {
        return this.sugar;
    }

    public boolean equals(Object B) {
        if (!(B instanceof TermOrder)) {
            return false;
        }
        boolean t;
        TermOrder b = (TermOrder) B;
        if (this.evord == b.getEvord() && this.evord2 == b.evord2 && this.evbeg1 == b.evbeg1 && this.evend1 == b.evend1 && this.evbeg2 == b.evbeg2 && this.evend2 == b.evend2) {
            t = true;
        } else {
            t = false;
        }
        if (!t) {
            return t;
        }
        if (Arrays.equals(this.weight, b.weight)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h = (((((((((this.evord << GRLEX) + this.evord2) << IGRLEX) + this.evbeg1) << IGRLEX) + this.evend1) << IGRLEX) + this.evbeg2) << IGRLEX) + this.evend2;
        if (this.weight == null) {
            return h;
        }
        return (h * REVTDEG) + Arrays.deepHashCode(this.weight);
    }

    public String weightToString() {
        StringBuffer erg = new StringBuffer();
        if (this.weight != null) {
            erg.append("weight(");
            for (int j = 0; j < this.weight.length; j += LEX) {
                long[] wj = this.weight[j];
                erg.append("(");
                for (int i = 0; i < wj.length; i += LEX) {
                    erg.append(BuildConfig.FLAVOR + wj[(wj.length - i) - 1]);
                    if (i < wj.length - 1) {
                        erg.append(",");
                    }
                }
                erg.append(")");
                if (j < this.weight.length - 1) {
                    erg.append(",");
                }
            }
            erg.append(")");
        }
        return erg.toString();
    }

    public String toString() {
        StringBuffer erg = new StringBuffer();
        if (this.weight != null) {
            erg.append("W(");
            for (int j = 0; j < this.weight.length; j += LEX) {
                long[] wj = this.weight[j];
                erg.append("(");
                for (int i = 0; i < wj.length; i += LEX) {
                    erg.append(BuildConfig.FLAVOR + wj[(wj.length - i) - 1]);
                    if (i < wj.length - 1) {
                        erg.append(",");
                    }
                }
                erg.append(")");
                if (j < this.weight.length - 1) {
                    erg.append(",");
                }
            }
            erg.append(")");
            if (this.evend1 == this.evend2) {
                return erg.toString();
            }
            erg.append("[" + this.evbeg1 + "," + this.evend1 + "]");
            erg.append("[" + this.evbeg2 + "," + this.evend2 + "]");
            return erg.toString();
        }
        switch (this.evord) {
            case LEX /*1*/:
                erg.append("LEX");
                break;
            case INVLEX /*2*/:
                erg.append("INVLEX");
                break;
            case GRLEX /*3*/:
                erg.append("GRLEX");
                break;
            case IGRLEX /*4*/:
                erg.append("IGRLEX");
                break;
            case REVLEX /*5*/:
                erg.append("REVLEX");
                break;
            case REVILEX /*6*/:
                erg.append("REVILEX");
                break;
            case REVTDEG /*7*/:
                erg.append("REVTDEG");
                break;
            case REVITDG /*8*/:
                erg.append("REVITDG");
                break;
            default:
                erg.append("invalid(" + this.evord + ")");
                break;
        }
        if (this.evord2 <= 0) {
            return erg.toString();
        }
        erg.append("[" + this.evbeg1 + "," + this.evend1 + "]");
        switch (this.evord2) {
            case LEX /*1*/:
                erg.append("LEX");
                break;
            case INVLEX /*2*/:
                erg.append("INVLEX");
                break;
            case GRLEX /*3*/:
                erg.append("GRLEX");
                break;
            case IGRLEX /*4*/:
                erg.append("IGRLEX");
                break;
            case REVLEX /*5*/:
                erg.append("REVLEX");
                break;
            case REVILEX /*6*/:
                erg.append("REVILEX");
                break;
            case REVTDEG /*7*/:
                erg.append("REVTDEG");
                break;
            case REVITDG /*8*/:
                erg.append("REVITDG");
                break;
            default:
                erg.append("invalid(" + this.evord2 + ")");
                break;
        }
        erg.append("[" + this.evbeg2 + "," + this.evend2 + "]");
        return erg.toString();
    }

    public TermOrder extend(int r, int k) {
        if (this.weight != null) {
            long[][] w = new long[this.weight.length][];
            for (int i = 0; i < this.weight.length; i += LEX) {
                int j;
                long[] wi = this.weight[i];
                long max = 0;
                for (j = 0; j < wi.length; j += LEX) {
                    if (wi[j] > max) {
                        max = wi[j];
                    }
                }
                max++;
                long[] wj = new long[(wi.length + k)];
                for (j = 0; j < i; j += LEX) {
                    wj[j] = max;
                }
                System.arraycopy(wi, 0, wj, i, wi.length);
                w[i] = wj;
            }
            return new TermOrder(w);
        } else if (this.evord2 == 0) {
            return new TermOrder(IGRLEX, this.evord, r + k, k);
        } else {
            logger.debug("warn: TermOrder is already extended");
            if (!this.debug) {
                return new TermOrder(this.evord, this.evord2, r + k, this.evend1 + k);
            }
            throw new IllegalArgumentException("TermOrder is already extended: " + this);
        }
    }

    public TermOrder extendLower(int r, int k) {
        if (this.weight != null) {
            long[][] w = new long[this.weight.length][];
            for (int i = 0; i < this.weight.length; i += LEX) {
                int j;
                long[] wi = this.weight[i];
                long min = Long.MAX_VALUE;
                for (j = 0; j < wi.length; j += LEX) {
                    if (wi[j] < min) {
                        min = wi[j];
                    }
                }
                long[] wj = new long[(wi.length + k)];
                for (j = 0; j < i; j += LEX) {
                    wj[wi.length + j] = min;
                }
                System.arraycopy(wi, 0, wj, 0, wi.length);
                w[i] = wj;
            }
            return new TermOrder(w);
        } else if (this.evord2 == 0) {
            return new TermOrder(this.evord);
        } else {
            if (this.debug) {
                logger.warn("TermOrder is already extended");
            }
            return new TermOrder(this.evord, this.evord2, r + k, this.evend1 + k);
        }
    }

    public TermOrder contract(int k, int len) {
        if (this.weight != null) {
            long[][] w = new long[this.weight.length][];
            for (int i = 0; i < this.weight.length; i += LEX) {
                long[] wj = new long[len];
                System.arraycopy(this.weight[i], k, wj, 0, len);
                w[i] = wj;
            }
            return new TermOrder(w);
        } else if (this.evord2 == 0) {
            if (this.debug) {
                logger.warn("TermOrder is already contracted");
            }
            return new TermOrder(this.evord);
        } else if (this.evend1 <= k) {
            return new TermOrder(this.evord2);
        } else {
            int el = this.evend1 - k;
            while (el > len) {
                el -= len;
            }
            if (((long) el) == 0) {
                return new TermOrder(this.evord);
            }
            if (el == len) {
                return new TermOrder(this.evord);
            }
            return new TermOrder(this.evord, this.evord2, len, el);
        }
    }

    public TermOrder reverse() {
        return reverse(false);
    }

    public TermOrder reverse(boolean partial) {
        TermOrder t;
        if (this.weight != null) {
            if (partial) {
                logger.error("partial reversed weight order not implemented");
            }
            long[][] w = new long[this.weight.length][];
            for (int i = 0; i < this.weight.length; i += LEX) {
                long[] wi = this.weight[i];
                long[] wj = new long[wi.length];
                for (int j = 0; j < wj.length; j += LEX) {
                    wj[j] = wi[(wj.length - 1) - j];
                }
                w[i] = wj;
            }
            t = new TermOrder(w);
            logger.info("reverse = " + t + ", from = " + this);
            return t;
        } else if (this.evord2 == 0) {
            return new TermOrder(revert(this.evord));
        } else {
            if (partial) {
                t = new TermOrder(revert(this.evord), revert(this.evord2), this.evend2, this.evend1);
            } else {
                t = new TermOrder(revert(this.evord2), revert(this.evord), this.evend2, this.evend2 - this.evbeg2);
            }
            logger.info("reverse = " + t + ", from = " + this);
            return t;
        }
    }

    public static int revert(int evord) {
        int i = evord;
        switch (evord) {
            case LEX /*1*/:
                return REVLEX;
            case INVLEX /*2*/:
                return REVILEX;
            case GRLEX /*3*/:
                return REVTDEG;
            case IGRLEX /*4*/:
                return REVITDG;
            case REVLEX /*5*/:
                return LEX;
            case REVILEX /*6*/:
                return INVLEX;
            case REVTDEG /*7*/:
                return GRLEX;
            case REVITDG /*8*/:
                return IGRLEX;
            default:
                logger.error("can not revert " + evord);
                return i;
        }
    }

    public static long[] longArrayPermutation(List<Integer> P, long[] a) {
        if (a == null || a.length <= LEX) {
            return a;
        }
        long[] b = new long[a.length];
        int j = 0;
        for (Integer i : P) {
            b[j] = a[i.intValue()];
            j += LEX;
        }
        return b;
    }

    public TermOrder permutation(List<Integer> P) {
        TermOrder tord = this;
        if (getEvord2() != 0) {
            tord = new TermOrder(getEvord2());
            logger.warn("split term order '" + this + "' not permutable, resetting to most base term order " + tord);
        }
        long[][] weight = getWeight();
        if (weight == null) {
            return tord;
        }
        long[][] w = new long[weight.length][];
        for (int i = 0; i < weight.length; i += LEX) {
            w[i] = longArrayPermutation(P, weight[i]);
        }
        return new TermOrder(w);
    }
}
