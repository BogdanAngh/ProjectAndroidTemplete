package org.apache.commons.math4.special;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class BesselJ implements UnivariateFunction {
    private static final double ENMTEN = 8.9E-308d;
    private static final double ENSIG = 1.0E16d;
    private static final double ENTEN = 1.0E308d;
    private static final double[] FACT;
    private static final double PI2 = 0.6366197723675814d;
    private static final double RTNSIG = 1.0E-4d;
    private static final double TOWPI1 = 6.28125d;
    private static final double TWOPI = 6.283185307179586d;
    private static final double TWOPI2 = 0.001935307179586477d;
    private static final double X_MAX = 10000.0d;
    private static final double X_MIN = 0.0d;
    private final double order;

    public static class BesselJResult {
        private final int nVals;
        private final double[] vals;

        public BesselJResult(double[] b, int n) {
            this.vals = MathArrays.copyOf(b, b.length);
            this.nVals = n;
        }

        public double[] getVals() {
            return MathArrays.copyOf(this.vals, this.vals.length);
        }

        public int getnVals() {
            return this.nVals;
        }
    }

    static {
        FACT = new double[]{1.0d, 1.0d, 2.0d, 6.0d, 24.0d, 120.0d, 720.0d, 5040.0d, 40320.0d, 362880.0d, 3628800.0d, 3.99168E7d, 4.790016E8d, 6.2270208E9d, 8.71782912E10d, 1.307674368E12d, 2.0922789888E13d, 3.55687428096E14d, 6.402373705728E15d, 1.21645100408832E17d, 2.43290200817664E18d, 5.109094217170944E19d, 1.1240007277776077E21d, 2.585201673888498E22d, 6.204484017332394E23d};
    }

    public BesselJ(double order) {
        this.order = order;
    }

    public double value(double x) throws MathIllegalArgumentException, ConvergenceException {
        return value(this.order, x);
    }

    public static double value(double order, double x) throws MathIllegalArgumentException, ConvergenceException {
        int n = (int) order;
        int nb = n + 1;
        BesselJResult res = rjBesl(x, order - ((double) n), nb);
        if (res.nVals >= nb) {
            return res.vals[n];
        }
        if (res.nVals < 0) {
            throw new MathIllegalArgumentException(LocalizedFormats.BESSEL_FUNCTION_BAD_ARGUMENT, Double.valueOf(order), Double.valueOf(x));
        } else if (FastMath.abs(res.vals[res.nVals - 1]) < 1.0E-100d) {
            return res.vals[n];
        } else {
            throw new ConvergenceException(LocalizedFormats.BESSEL_FUNCTION_FAILED_CONVERGENCE, Double.valueOf(order), Double.valueOf(x));
        }
    }

    public static BesselJResult rjBesl(double x, double alpha, int nb) {
        int ncalc;
        double[] b = new double[nb];
        int magx = (int) x;
        if (nb <= 0 || x < 0.0d || x > X_MAX || alpha < 0.0d || alpha >= 1.0d) {
            if (b.length > 0) {
                b[0] = 0.0d;
            }
            ncalc = FastMath.min(nb, 0) - 1;
        } else {
            int i;
            ncalc = nb;
            for (i = 0; i < nb; i++) {
                b[i] = 0.0d;
            }
            double tempa;
            double alpem;
            double tempb;
            int n;
            double tempc;
            if (x < RTNSIG) {
                tempa = 1.0d;
                alpem = 1.0d + alpha;
                double halfx = 0.0d;
                if (x > ENMTEN) {
                    halfx = 0.5d * x;
                }
                if (alpha != 0.0d) {
                    tempa = FastMath.pow(halfx, alpha) / (Gamma.gamma(alpha) * alpha);
                }
                tempb = 0.0d;
                if (1.0d + x > 1.0d) {
                    tempb = (-halfx) * halfx;
                }
                b[0] = ((tempa * tempb) / alpem) + tempa;
                if (x != 0.0d && b[0] == 0.0d) {
                    ncalc = 0;
                }
                if (nb != 1) {
                    if (x <= 0.0d) {
                        for (n = 1; n < nb; n++) {
                            b[n] = 0.0d;
                        }
                    } else {
                        double tover;
                        tempc = halfx;
                        if (tempb != 0.0d) {
                            tover = ENMTEN / tempb;
                        } else {
                            tover = 1.78E-307d / x;
                        }
                        n = 1;
                        while (n < nb) {
                            tempa /= alpem;
                            alpem += 1.0d;
                            tempa *= tempc;
                            if (tempa <= tover * alpem) {
                                tempa = 0.0d;
                            }
                            b[n] = ((tempa * tempb) / alpem) + tempa;
                            if (b[n] == 0.0d && ncalc > n) {
                                ncalc = n;
                            }
                            n++;
                        }
                    }
                }
            } else if (x <= 25.0d || nb > magx + 1) {
                int nend;
                double pold;
                int l;
                n = magx + 1;
                double en = 2.0d * (((double) n) + alpha);
                double plast = 1.0d;
                double p = en / x;
                double test = 2.0E16d;
                boolean readyToInitialize = false;
                if (nb - magx >= 3) {
                    int nstart = magx + 2;
                    nend = nb - 1;
                    en = 2.0d * (((double) (nstart - 1)) + alpha);
                    k = nstart;
                    while (k <= nend) {
                        n = k;
                        en += 2.0d;
                        pold = plast;
                        plast = p;
                        p = ((en * plast) / x) - pold;
                        if (p > 1.0E292d) {
                            p /= ENTEN;
                            plast /= ENTEN;
                            double psave = p;
                            double psavel = plast;
                            nstart = n + 1;
                            do {
                                n++;
                                en += 2.0d;
                                pold = plast;
                                plast = p;
                                p = ((en * plast) / x) - pold;
                            } while (p <= 1.0d);
                            tempb = en / x;
                            test = ((pold * plast) * (0.5d - (0.5d / (tempb * tempb)))) / ENSIG;
                            p = plast * ENTEN;
                            n--;
                            en -= 2.0d;
                            nend = FastMath.min(nb, n);
                            for (l = nstart; l <= nend; l++) {
                                pold = psavel;
                                psavel = psave;
                                psave = ((en * psavel) / x) - pold;
                                if (psave * psavel > test) {
                                    ncalc = l - 1;
                                    break;
                                }
                            }
                            ncalc = nend;
                            readyToInitialize = true;
                            if (!readyToInitialize) {
                                n = nend;
                                en = 2.0d * (((double) n) + alpha);
                                test = FastMath.max(test, FastMath.sqrt(ENSIG * plast) * FastMath.sqrt(2.0d * p));
                            }
                        } else {
                            k++;
                        }
                    }
                    if (readyToInitialize) {
                        n = nend;
                        en = 2.0d * (((double) n) + alpha);
                        test = FastMath.max(test, FastMath.sqrt(ENSIG * plast) * FastMath.sqrt(2.0d * p));
                    }
                }
                if (!readyToInitialize) {
                    do {
                        n++;
                        en += 2.0d;
                        pold = plast;
                        plast = p;
                        p = ((en * plast) / x) - pold;
                    } while (p < test);
                }
                n++;
                en += 2.0d;
                tempb = 0.0d;
                tempa = 1.0d / p;
                m = (n * 2) - ((n / 2) * 4);
                double sum = 0.0d;
                double em = (double) (n / 2);
                alpem = (em - 1.0d) + alpha;
                double alp2em = (2.0d * em) + alpha;
                if (m != 0) {
                    sum = ((tempa * alpem) * alp2em) / em;
                }
                nend = n - nb;
                boolean readyToNormalize = false;
                boolean calculatedB0 = false;
                for (l = 1; l <= nend; l++) {
                    n--;
                    en -= 2.0d;
                    tempc = tempb;
                    tempb = tempa;
                    tempa = ((en * tempb) / x) - tempc;
                    m = 2 - m;
                    if (m != 0) {
                        em -= 1.0d;
                        alp2em = (2.0d * em) + alpha;
                        if (n == 1) {
                            break;
                        }
                        alpem = (em - 1.0d) + alpha;
                        if (alpem == 0.0d) {
                            alpem = 1.0d;
                        }
                        sum = (((tempa * alp2em) + sum) * alpem) / em;
                    }
                }
                b[n - 1] = tempa;
                if (nend >= 0) {
                    if (nb <= 1) {
                        alp2em = alpha;
                        if (1.0d + alpha == 1.0d) {
                            alp2em = 1.0d;
                        }
                        sum += b[0] * alp2em;
                        readyToNormalize = true;
                    } else {
                        n--;
                        en -= 2.0d;
                        b[n - 1] = ((en * tempa) / x) - tempb;
                        if (n == 1) {
                            calculatedB0 = true;
                        } else {
                            m = 2 - m;
                            if (m != 0) {
                                em -= 1.0d;
                                alp2em = (2.0d * em) + alpha;
                                alpem = (em - 1.0d) + alpha;
                                if (alpem == 0.0d) {
                                    alpem = 1.0d;
                                }
                                sum = (((b[n - 1] * alp2em) + sum) * alpem) / em;
                            }
                        }
                    }
                }
                if (!(readyToNormalize || calculatedB0)) {
                    nend = n - 2;
                    if (nend != 0) {
                        for (l = 1; l <= nend; l++) {
                            n--;
                            en -= 2.0d;
                            b[n - 1] = ((b[n] * en) / x) - b[n + 1];
                            m = 2 - m;
                            if (m != 0) {
                                em -= 1.0d;
                                alp2em = (2.0d * em) + alpha;
                                alpem = (em - 1.0d) + alpha;
                                if (alpem == 0.0d) {
                                    alpem = 1.0d;
                                }
                                sum = (((b[n - 1] * alp2em) + sum) * alpem) / em;
                            }
                        }
                    }
                }
                if (!readyToNormalize) {
                    if (!calculatedB0) {
                        b[0] = (((2.0d * (1.0d + alpha)) * b[1]) / x) - b[2];
                    }
                    alp2em = (2.0d * (em - 1.0d)) + alpha;
                    if (alp2em == 0.0d) {
                        alp2em = 1.0d;
                    }
                    sum += b[0] * alp2em;
                }
                if (FastMath.abs(alpha) > 1.0E-16d) {
                    sum *= Gamma.gamma(alpha) * FastMath.pow(0.5d * x, -alpha);
                }
                tempa = ENMTEN;
                if (sum > 1.0d) {
                    tempa = ENMTEN * sum;
                }
                for (n = 0; n < nb; n++) {
                    if (FastMath.abs(b[n]) < tempa) {
                        b[n] = 0.0d;
                    }
                    b[n] = b[n] / sum;
                }
            } else {
                int j;
                double xc = FastMath.sqrt(PI2 / x);
                double mul = 0.125d / x;
                double xin = mul * mul;
                if (x >= 130.0d) {
                    m = 4;
                } else if (x >= 35.0d) {
                    m = 8;
                } else {
                    m = 11;
                }
                double xm = 4.0d * ((double) m);
                double t = (double) ((int) ((x / TWOPI) + 0.5d));
                double z = ((x - (TOWPI1 * t)) - (TWOPI2 * t)) - ((0.5d + alpha) / PI2);
                double vsin = FastMath.sin(z);
                double vcos = FastMath.cos(z);
                double gnu = 2.0d * alpha;
                for (i = 1; i <= 2; i++) {
                    double s = ((((xm - 1.0d) - gnu) * ((xm - 1.0d) + gnu)) * xin) * 0.5d;
                    t = (gnu - (xm - 3.0d)) * ((xm - 3.0d) + gnu);
                    double capp = (s * t) / FACT[m * 2];
                    double capq = (s * ((gnu - (1.0d + xm)) * ((1.0d + xm) + gnu))) / FACT[(m * 2) + 1];
                    double xk = xm;
                    k = m * 2;
                    double t1 = t;
                    for (j = 2; j <= m; j++) {
                        xk -= 4.0d;
                        s = ((xk - 1.0d) - gnu) * ((xk - 1.0d) + gnu);
                        t = (gnu - (xk - 3.0d)) * ((xk - 3.0d) + gnu);
                        capp = ((((1.0d / FACT[k - 2]) + capp) * s) * t) * xin;
                        capq = ((((1.0d / FACT[k - 1]) + capq) * s) * t1) * xin;
                        k -= 2;
                        t1 = t;
                    }
                    b[i - 1] = (((capp + 1.0d) * vcos) - ((((1.0d + capq) * ((gnu * gnu) - 1.0d)) * (0.125d / x)) * vsin)) * xc;
                    if (nb == 1) {
                        return new BesselJResult(MathArrays.copyOf(b, b.length), ncalc);
                    }
                    t = vsin;
                    vsin = -vcos;
                    vcos = t;
                    gnu += 2.0d;
                }
                if (nb > 2) {
                    gnu = (2.0d * alpha) + 2.0d;
                    for (j = 2; j < nb; j++) {
                        b[j] = ((b[j - 1] * gnu) / x) - b[j - 2];
                        gnu += 2.0d;
                    }
                }
            }
        }
        return new BesselJResult(MathArrays.copyOf(b, b.length), ncalc);
    }
}
