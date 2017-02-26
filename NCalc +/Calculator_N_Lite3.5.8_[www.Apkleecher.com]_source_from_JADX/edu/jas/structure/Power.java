package edu.jas.structure;

import java.math.BigInteger;
import java.util.List;
import org.apache.log4j.Logger;

public class Power<C extends RingElem<C>> {
    private static boolean debug;
    private static final Logger logger;
    private final RingFactory<C> fac;

    static {
        logger = Logger.getLogger(Power.class);
        debug = logger.isDebugEnabled();
    }

    public Power() {
        this(null);
    }

    public Power(RingFactory<C> fac) {
        this.fac = fac;
    }

    public static <C extends RingElem<C>> C positivePower(C a, long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("only positive n allowed");
        } else if (a.isZERO() || a.isONE()) {
            return a;
        } else {
            MonoidElem b = a;
            long i = n - 1;
            RingElem p = b;
            do {
                if (i % 2 == 1) {
                    p = (RingElem) p.multiply(b);
                }
                i /= 2;
                if (i > 0) {
                    b = (RingElem) b.multiply(b);
                }
            } while (i > 0);
            return p;
        }
    }

    public static <C extends RingElem<C>> C positivePower(C a, BigInteger n) {
        if (n.signum() <= 0) {
            throw new IllegalArgumentException("only positive n allowed");
        } else if (a.isZERO() || a.isONE()) {
            return a;
        } else {
            MonoidElem b = a;
            if (n.compareTo(BigInteger.ONE) == 0) {
                return b;
            }
            RingElem p = a;
            BigInteger i = n.subtract(BigInteger.ONE);
            do {
                if (i.testBit(0)) {
                    p = (RingElem) p.multiply(b);
                }
                i = i.shiftRight(1);
                if (i.signum() > 0) {
                    b = (RingElem) b.multiply(b);
                }
            } while (i.signum() > 0);
            return p;
        }
    }

    public static <C extends RingElem<C>> C modPositivePower(C a, long n, C m) {
        if (n <= 0) {
            throw new IllegalArgumentException("only positive n allowed");
        } else if (a.isZERO() || a.isONE()) {
            return a;
        } else {
            MonoidElem b = (RingElem) a.remainder(m);
            long i = n - 1;
            RingElem p = b;
            do {
                if (i % 2 == 1) {
                    p = (RingElem) ((RingElem) p.multiply(b)).remainder(m);
                }
                i /= 2;
                if (i > 0) {
                    b = (RingElem) ((RingElem) b.multiply(b)).remainder(m);
                }
            } while (i > 0);
            return p;
        }
    }

    public static <C extends RingElem<C>> C power(RingFactory<C> fac, C a, long n) {
        if (a == null || a.isZERO()) {
            return a;
        }
        return (RingElem) power((MonoidFactory) fac, (MonoidElem) a, n);
    }

    public static <C extends MonoidElem<C>> C power(MonoidFactory<C> fac, C a, long n) {
        if (n == 0) {
            if (fac != null) {
                return fac.getONE();
            }
            throw new IllegalArgumentException("fac may not be null for a^0");
        } else if (a.isONE()) {
            return a;
        } else {
            C b = a;
            if (n < 0) {
                b = a.inverse();
                n = -n;
            }
            if (n == 1) {
                return b;
            }
            C p = fac.getONE();
            long i = n;
            do {
                if (i % 2 == 1) {
                    p = p.multiply(b);
                }
                i /= 2;
                if (i > 0) {
                    b = b.multiply(b);
                }
            } while (i > 0);
            if (n > 11 && debug) {
                logger.info("n  = " + n + ", p  = " + p);
            }
            return p;
        }
    }

    public static <C extends MonoidElem<C>> C modPower(MonoidFactory<C> fac, C a, long n, C m) {
        if (n == 0) {
            if (fac != null) {
                return fac.getONE();
            }
            throw new IllegalArgumentException("fac may not be null for a^0");
        } else if (a.isONE()) {
            return a;
        } else {
            C b = a.remainder(m);
            if (n < 0) {
                b = a.inverse().remainder(m);
                n = -n;
            }
            if (n == 1) {
                return b;
            }
            C p = fac.getONE();
            long i = n;
            do {
                if (i % 2 == 1) {
                    p = p.multiply(b).remainder(m);
                }
                i /= 2;
                if (i > 0) {
                    b = b.multiply(b).remainder(m);
                }
            } while (i > 0);
            if (n > 11 && debug) {
                logger.info("n  = " + n + ", p  = " + p);
            }
            return p;
        }
    }

    public static <C extends MonoidElem<C>> C modPower(MonoidFactory<C> fac, C a, BigInteger n, C m) {
        if (n.signum() == 0) {
            if (fac != null) {
                return fac.getONE();
            }
            throw new IllegalArgumentException("fac may not be null for a^0");
        } else if (a.isONE()) {
            return a;
        } else {
            C b = a.remainder(m);
            if (n.signum() < 0) {
                b = a.inverse().remainder(m);
                n = n.negate();
            }
            if (n.compareTo(BigInteger.ONE) == 0) {
                return b;
            }
            C p = fac.getONE();
            BigInteger i = n;
            do {
                if (i.testBit(0)) {
                    p = p.multiply(b).remainder(m);
                }
                i = i.shiftRight(1);
                if (i.signum() > 0) {
                    b = b.multiply(b).remainder(m);
                }
            } while (i.signum() > 0);
            if (debug) {
                logger.info("n  = " + n + ", p  = " + p);
            }
            return p;
        }
    }

    public C power(C a, long n) {
        return power(this.fac, (RingElem) a, n);
    }

    public C modPower(C a, long n, C m) {
        return (RingElem) modPower(this.fac, (MonoidElem) a, n, (MonoidElem) m);
    }

    public C modPower(C a, BigInteger n, C m) {
        return (RingElem) modPower(this.fac, (MonoidElem) a, n, (MonoidElem) m);
    }

    public static <C extends RingElem<C>> long logarithm(C p, C a) {
        long k = 1;
        C m = p;
        while (m.compareTo(a) < 0) {
            RingElem m2 = (RingElem) m.multiply(p);
            k++;
        }
        return k;
    }

    public static <C extends RingElem<C>> C multiply(RingFactory<C> fac, List<C> A) {
        return (RingElem) multiply((MonoidFactory) fac, (List) A);
    }

    public static <C extends MonoidElem<C>> C multiply(MonoidFactory<C> fac, List<C> A) {
        if (fac == null) {
            throw new IllegalArgumentException("fac may not be null for empty list");
        }
        C res = fac.getONE();
        if (A == null || A.isEmpty()) {
            return res;
        }
        for (C a : A) {
            res = res.multiply(a);
        }
        return res;
    }

    public static <C extends RingElem<C>> C sum(RingFactory<C> fac, List<C> A) {
        return (RingElem) sum((AbelianGroupFactory) fac, (List) A);
    }

    public static <C extends AbelianGroupElem<C>> C sum(AbelianGroupFactory<C> fac, List<C> A) {
        if (fac == null) {
            throw new IllegalArgumentException("fac may not be null for empty list");
        }
        C res = fac.getZERO();
        if (A == null || A.isEmpty()) {
            return res;
        }
        for (C a : A) {
            res = res.sum(a);
        }
        return res;
    }
}
