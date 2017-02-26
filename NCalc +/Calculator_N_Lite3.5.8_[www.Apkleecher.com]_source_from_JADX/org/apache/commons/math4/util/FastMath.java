package org.apache.commons.math4.util;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.widget.AutoScrollHelper;
import com.example.duy.calculator.geom2d.util.Angle2D;
import edu.jas.vector.GenVectorModul;
import java.io.PrintStream;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class FastMath {
    private static final double[] CBRTTWO;
    private static final double[] COSINE_TABLE_A;
    private static final double[] COSINE_TABLE_B;
    public static final double E = 2.718281828459045d;
    private static final double[] EIGHTHS;
    static final int EXP_FRAC_TABLE_LEN = 1025;
    static final int EXP_INT_TABLE_LEN = 1500;
    static final int EXP_INT_TABLE_MAX_INDEX = 750;
    private static final double F_11_12 = 0.9166666666666666d;
    private static final double F_13_14 = 0.9285714285714286d;
    private static final double F_15_16 = 0.9375d;
    private static final double F_1_11 = 0.09090909090909091d;
    private static final double F_1_13 = 0.07692307692307693d;
    private static final double F_1_15 = 0.06666666666666667d;
    private static final double F_1_17 = 0.058823529411764705d;
    private static final double F_1_2 = 0.5d;
    private static final double F_1_3 = 0.3333333333333333d;
    private static final double F_1_4 = 0.25d;
    private static final double F_1_5 = 0.2d;
    private static final double F_1_7 = 0.14285714285714285d;
    private static final double F_1_9 = 0.1111111111111111d;
    private static final double F_3_4 = 0.75d;
    private static final double F_5_6 = 0.8333333333333334d;
    private static final double F_7_8 = 0.875d;
    private static final double F_9_10 = 0.9d;
    private static final long HEX_40000000 = 1073741824;
    private static final double LN_2_A = 0.6931470632553101d;
    private static final double LN_2_B = 1.1730463525082348E-7d;
    private static final double[][] LN_HI_PREC_COEF;
    static final int LN_MANT_LEN = 1024;
    private static final double[][] LN_QUICK_COEF;
    private static final double LOG_MAX_VALUE;
    private static final long MASK_30BITS = -1073741824;
    private static final int MASK_NON_SIGN_INT = Integer.MAX_VALUE;
    private static final long MASK_NON_SIGN_LONG = Long.MAX_VALUE;
    public static final double PI = 3.141592653589793d;
    private static final long[] PI_O_4_BITS;
    private static final long[] RECIP_2PI;
    private static final boolean RECOMPUTE_TABLES_AT_RUNTIME = false;
    private static final double[] SINE_TABLE_A;
    private static final double[] SINE_TABLE_B;
    private static final int SINE_TABLE_LEN = 14;
    private static final double[] TANGENT_TABLE_A;
    private static final double[] TANGENT_TABLE_B;
    private static final double TWO_POWER_52 = 4.503599627370496E15d;
    private static final double TWO_POWER_53 = 9.007199254740992E15d;

    private static class CodyWaite {
        private final int finalK;
        private final double finalRemA;
        private final double finalRemB;

        CodyWaite(double xa) {
            int k = (int) (0.6366197723675814d * xa);
            while (true) {
                double a = ((double) (-k)) * 1.570796251296997d;
                double remA = xa + a;
                double remB = -((remA - xa) - a);
                a = ((double) (-k)) * 7.549789948768648E-8d;
                double b = remA;
                remA = a + b;
                remB += -((remA - b) - a);
                a = ((double) (-k)) * 6.123233995736766E-17d;
                b = remA;
                remA = a + b;
                remB += -((remA - b) - a);
                if (remA > FastMath.LOG_MAX_VALUE) {
                    this.finalK = k;
                    this.finalRemA = remA;
                    this.finalRemB = remB;
                    return;
                }
                k--;
            }
        }

        int getK() {
            return this.finalK;
        }

        double getRemA() {
            return this.finalRemA;
        }

        double getRemB() {
            return this.finalRemB;
        }
    }

    private static class ExpFracTable {
        private static final double[] EXP_FRAC_TABLE_A;
        private static final double[] EXP_FRAC_TABLE_B;

        private ExpFracTable() {
        }

        static {
            EXP_FRAC_TABLE_A = FastMathLiteralArrays.loadExpFracA();
            EXP_FRAC_TABLE_B = FastMathLiteralArrays.loadExpFracB();
        }
    }

    private static class ExpIntTable {
        private static final double[] EXP_INT_TABLE_A;
        private static final double[] EXP_INT_TABLE_B;

        private ExpIntTable() {
        }

        static {
            EXP_INT_TABLE_A = FastMathLiteralArrays.loadExpIntA();
            EXP_INT_TABLE_B = FastMathLiteralArrays.loadExpIntB();
        }
    }

    private static class lnMant {
        private static final double[][] LN_MANT;

        private lnMant() {
        }

        static {
            LN_MANT = FastMathLiteralArrays.loadLnMant();
        }
    }

    static {
        LOG_MAX_VALUE = StrictMath.log(Double.MAX_VALUE);
        LN_QUICK_COEF = new double[][]{new double[]{1.0d, 5.669184079525E-24d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.986821492305628E-8d}, new double[]{-0.25d, -6.663542893624021E-14d}, new double[]{0.19999998807907104d, 1.1921056801463227E-8d}, new double[]{-0.1666666567325592d, -7.800414592973399E-9d}, new double[]{0.1428571343421936d, 5.650007086920087E-9d}, new double[]{-0.12502530217170715d, -7.44321345601866E-11d}, new double[]{0.11113807559013367d, 9.219544613762692E-9d}};
        LN_HI_PREC_COEF = new double[][]{new double[]{1.0d, -6.032174644509064E-23d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.9868161777724352E-8d}, new double[]{-0.2499999701976776d, -2.957007209750105E-8d}, new double[]{0.19999954104423523d, 1.5830993332061267E-10d}, new double[]{-0.16624879837036133d, -2.6033824355191673E-8d}};
        SINE_TABLE_A = new double[]{LOG_MAX_VALUE, 0.1246747374534607d, 0.24740394949913025d, 0.366272509098053d, 0.4794255495071411d, 0.5850973129272461d, 0.6816387176513672d, 0.7675435543060303d, 0.8414709568023682d, 0.902267575263977d, 0.9489846229553223d, 0.9808930158615112d, 0.9974949359893799d, 0.9985313415527344d};
        SINE_TABLE_B = new double[]{LOG_MAX_VALUE, -4.068233003401932E-9d, 9.755392680573412E-9d, 1.9987994582857286E-8d, -1.0902938113007961E-8d, -3.9986783938944604E-8d, 4.23719669792332E-8d, -5.207000323380292E-8d, 2.800552834259E-8d, 1.883511811213715E-8d, -3.5997360512765566E-9d, 4.116164446561962E-8d, 5.0614674548127384E-8d, -1.0129027912496858E-9d};
        COSINE_TABLE_A = new double[]{1.0d, 0.9921976327896118d, 0.9689123630523682d, 0.9305076599121094d, 0.8775825500488281d, 0.8109631538391113d, 0.7316888570785522d, 0.6409968137741089d, 0.5403022766113281d, 0.4311765432357788d, 0.3153223395347595d, 0.19454771280288696d, 0.07073719799518585d, -0.05417713522911072d};
        COSINE_TABLE_B = new double[]{LOG_MAX_VALUE, 3.4439717236742845E-8d, 5.865827662008209E-8d, -3.7999795083850525E-8d, 1.184154459111628E-8d, -3.43338934259355E-8d, 1.1795268640216787E-8d, 4.438921624363781E-8d, 2.925681159240093E-8d, -2.6437112632041807E-8d, 2.2860509143963117E-8d, -4.813899778443457E-9d, 3.6725170580355583E-9d, 2.0217439756338078E-10d};
        TANGENT_TABLE_A = new double[]{LOG_MAX_VALUE, 0.1256551444530487d, 0.25534194707870483d, 0.3936265707015991d, 0.5463024377822876d, 0.7214844226837158d, 0.9315965175628662d, 1.1974215507507324d, 1.5574076175689697d, 2.092571258544922d, 3.0095696449279785d, 5.041914939880371d, 14.101419448852539d, -18.430862426757812d};
        TANGENT_TABLE_B = new double[]{LOG_MAX_VALUE, -7.877917738262007E-9d, -2.5857668567479893E-8d, 5.2240336371356666E-9d, 5.206150291559893E-8d, 1.8307188599677033E-8d, -5.7618793749770706E-8d, 7.848361555046424E-8d, 1.0708593250394448E-7d, 1.7827257129423813E-8d, 2.893485277253286E-8d, 3.1660099222737955E-7d, 4.983191803254889E-7d, -3.356118100840571E-7d};
        RECIP_2PI = new long[]{2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L};
        PI_O_4_BITS = new long[]{-3958705157555305932L, -4267615245585081135L};
        EIGHTHS = new double[]{LOG_MAX_VALUE, 0.125d, F_1_4, 0.375d, F_1_2, 0.625d, F_3_4, F_7_8, 1.0d, 1.125d, 1.25d, 1.375d, 1.5d, 1.625d};
        CBRTTWO = new double[]{0.6299605249474366d, 0.7937005259840998d, 1.0d, 1.2599210498948732d, 1.5874010519681994d};
    }

    private FastMath() {
    }

    private static double doubleHighPart(double d) {
        return (d <= (-Precision.SAFE_MIN) || d >= Precision.SAFE_MIN) ? Double.longBitsToDouble(Double.doubleToRawLongBits(d) & MASK_30BITS) : d;
    }

    public static double sqrt(double a) {
        return Math.sqrt(a);
    }

    public static double cosh(double x) {
        if (x != x) {
            return x;
        }
        double t;
        if (x > 20.0d) {
            if (x < LOG_MAX_VALUE) {
                return F_1_2 * exp(x);
            }
            t = exp(F_1_2 * x);
            return (F_1_2 * t) * t;
        } else if (x < -20.0d) {
            if (x <= (-LOG_MAX_VALUE)) {
                t = exp(-0.5d * x);
                return (F_1_2 * t) * t;
            }
            return F_1_2 * exp(-x);
        } else {
            double[] hiPrec = new double[2];
            if (x < LOG_MAX_VALUE) {
                x = -x;
            }
            exp(x, LOG_MAX_VALUE, hiPrec);
            double ya = hiPrec[0] + hiPrec[1];
            double yb = -((ya - hiPrec[0]) - hiPrec[1]);
            double temp = ya * 1.073741824E9d;
            double yaa = (ya + temp) - temp;
            double yab = ya - yaa;
            double recip = 1.0d / ya;
            temp = recip * 1.073741824E9d;
            double recipa = (recip + temp) - temp;
            double recipb = recip - recipa;
            recipb = (recipb + (((((1.0d - (yaa * recipa)) - (yaa * recipb)) - (yab * recipa)) - (yab * recipb)) * recip)) + (((-yb) * recip) * recip);
            temp = ya + recipa;
            double d = -((temp - ya) - recipa);
            ya = temp;
            temp = ya + recipb;
            d = -((temp - ya) - recipb);
            return (temp + ((yb + r0) + r0)) * F_1_2;
        }
    }

    public static double sinh(double x) {
        boolean negate = RECOMPUTE_TABLES_AT_RUNTIME;
        if (x != x) {
            return x;
        }
        double t;
        if (x > 20.0d) {
            if (x < LOG_MAX_VALUE) {
                return F_1_2 * exp(x);
            }
            t = exp(F_1_2 * x);
            return (F_1_2 * t) * t;
        } else if (x < -20.0d) {
            if (x <= (-LOG_MAX_VALUE)) {
                t = exp(-0.5d * x);
                return (-0.5d * t) * t;
            }
            return -0.5d * exp(-x);
        } else if (x == LOG_MAX_VALUE) {
            return x;
        } else {
            double result;
            if (x < LOG_MAX_VALUE) {
                x = -x;
                negate = true;
            }
            double[] hiPrec;
            double ya;
            double yb;
            double temp;
            double d;
            if (x > F_1_4) {
                hiPrec = new double[2];
                exp(x, LOG_MAX_VALUE, hiPrec);
                ya = hiPrec[0] + hiPrec[1];
                yb = -((ya - hiPrec[0]) - hiPrec[1]);
                temp = ya * 1.073741824E9d;
                double yaa = (ya + temp) - temp;
                double yab = ya - yaa;
                double recip = 1.0d / ya;
                temp = recip * 1.073741824E9d;
                double recipa = (recip + temp) - temp;
                double recipb = recip - recipa;
                recipa = -recipa;
                recipb = -((recipb + (((((1.0d - (yaa * recipa)) - (yaa * recipb)) - (yab * recipa)) - (yab * recipb)) * recip)) + (((-yb) * recip) * recip));
                temp = ya + recipa;
                d = -((temp - ya) - recipa);
                ya = temp;
                temp = ya + recipb;
                ya = temp;
                result = (ya + ((yb + r0) + (-((temp - ya) - recipb)))) * F_1_2;
            } else {
                hiPrec = new double[2];
                expm1(x, hiPrec);
                ya = hiPrec[0] + hiPrec[1];
                yb = -((ya - hiPrec[0]) - hiPrec[1]);
                double denom = 1.0d + ya;
                double denomr = 1.0d / denom;
                double ratio = ya * denomr;
                temp = ratio * 1.073741824E9d;
                double ra = (ratio + temp) - temp;
                double rb = ratio - ra;
                temp = denom * 1.073741824E9d;
                double za = (denom + temp) - temp;
                double zb = denom - za;
                d = -ya;
                rb = ((rb + (((((ya - (za * ra)) - (za * rb)) - (zb * ra)) - (zb * rb)) * denomr)) + (yb * denomr)) + (((r0 * ((-((denom - 1.0d) - ya)) + yb)) * denomr) * denomr);
                temp = ya + ra;
                d = -((temp - ya) - ra);
                ya = temp;
                temp = ya + rb;
                ya = temp;
                result = (ya + ((yb + r0) + (-((temp - ya) - rb)))) * F_1_2;
            }
            if (negate) {
                result = -result;
            }
            return result;
        }
    }

    public static double tanh(double x) {
        boolean negate = RECOMPUTE_TABLES_AT_RUNTIME;
        if (x != x) {
            return x;
        }
        if (x > 20.0d) {
            return 1.0d;
        }
        if (x < -20.0d) {
            return -1.0d;
        }
        if (x == LOG_MAX_VALUE) {
            return x;
        }
        double result;
        if (x < LOG_MAX_VALUE) {
            x = -x;
            negate = true;
        }
        double[] hiPrec;
        double ya;
        double yb;
        double na;
        double temp;
        double da;
        double d;
        double daa;
        double dab;
        double ratio;
        double ratioa;
        double ratiob;
        if (x >= F_1_2) {
            hiPrec = new double[2];
            exp(2.0d * x, LOG_MAX_VALUE, hiPrec);
            ya = hiPrec[0] + hiPrec[1];
            yb = -((ya - hiPrec[0]) - hiPrec[1]);
            na = -1.0d + ya;
            temp = na + yb;
            double nb = (-((1.0d + na) - ya)) + (-((temp - na) - yb));
            na = temp;
            da = 1.0d + ya;
            temp = da + yb;
            d = -((temp - da) - yb);
            da = temp;
            temp = da * 1.073741824E9d;
            daa = (da + temp) - temp;
            dab = da - daa;
            ratio = na / da;
            temp = ratio * 1.073741824E9d;
            ratioa = (ratio + temp) - temp;
            ratiob = ratio - ratioa;
            result = ratioa + (((ratiob + (((((na - (daa * ratioa)) - (daa * ratiob)) - (dab * ratioa)) - (dab * ratiob)) / da)) + (nb / da)) + ((((-((-((da - 1.0d) - ya)) + r0)) * na) / da) / da));
        } else {
            hiPrec = new double[2];
            expm1(2.0d * x, hiPrec);
            ya = hiPrec[0] + hiPrec[1];
            yb = -((ya - hiPrec[0]) - hiPrec[1]);
            na = ya;
            da = 2.0d + ya;
            temp = da + yb;
            d = -((temp - da) - yb);
            da = temp;
            temp = da * 1.073741824E9d;
            daa = (da + temp) - temp;
            dab = da - daa;
            ratio = na / da;
            temp = ratio * 1.073741824E9d;
            ratioa = (ratio + temp) - temp;
            ratiob = ratio - ratioa;
            result = ratioa + (((ratiob + (((((na - (daa * ratioa)) - (daa * ratiob)) - (dab * ratioa)) - (dab * ratiob)) / da)) + (yb / da)) + ((((-((-((da - 2.0d) - ya)) + r0)) * na) / da) / da));
        }
        if (negate) {
            result = -result;
        }
        return result;
    }

    public static double acosh(double a) {
        return log(sqrt((a * a) - 1.0d) + a);
    }

    public static double asinh(double a) {
        double absAsinh;
        boolean negative = RECOMPUTE_TABLES_AT_RUNTIME;
        if (a < LOG_MAX_VALUE) {
            negative = true;
            a = -a;
        }
        if (a > 0.167d) {
            absAsinh = log(sqrt((a * a) + 1.0d) + a);
        } else {
            double a2 = a * a;
            if (a > 0.097d) {
                absAsinh = a * (1.0d - (((F_1_3 - (((F_1_5 - (((F_1_7 - (((F_1_9 - (((F_1_11 - (((F_1_13 - (((F_1_15 - ((F_1_17 * a2) * F_15_16)) * a2) * F_13_14)) * a2) * F_11_12)) * a2) * F_9_10)) * a2) * F_7_8)) * a2) * F_5_6)) * a2) * F_3_4)) * a2) * F_1_2));
            } else if (a > 0.036d) {
                absAsinh = a * (1.0d - (((F_1_3 - (((F_1_5 - (((F_1_7 - (((F_1_9 - (((F_1_11 - ((F_1_13 * a2) * F_11_12)) * a2) * F_9_10)) * a2) * F_7_8)) * a2) * F_5_6)) * a2) * F_3_4)) * a2) * F_1_2));
            } else if (a > 0.0036d) {
                absAsinh = a * (1.0d - (((F_1_3 - (((F_1_5 - (((F_1_7 - ((F_1_9 * a2) * F_7_8)) * a2) * F_5_6)) * a2) * F_3_4)) * a2) * F_1_2));
            } else {
                absAsinh = a * (1.0d - (((F_1_3 - ((F_1_5 * a2) * F_3_4)) * a2) * F_1_2));
            }
        }
        return negative ? -absAsinh : absAsinh;
    }

    public static double atanh(double a) {
        double absAtanh;
        boolean negative = RECOMPUTE_TABLES_AT_RUNTIME;
        if (a < LOG_MAX_VALUE) {
            negative = true;
            a = -a;
        }
        if (a > 0.15d) {
            absAtanh = F_1_2 * log((1.0d + a) / (1.0d - a));
        } else {
            double a2 = a * a;
            if (a > 0.087d) {
                absAtanh = a * (1.0d + ((F_1_3 + ((F_1_5 + ((F_1_7 + ((F_1_9 + ((F_1_11 + ((F_1_13 + ((F_1_15 + (F_1_17 * a2)) * a2)) * a2)) * a2)) * a2)) * a2)) * a2)) * a2));
            } else if (a > 0.031d) {
                absAtanh = a * (1.0d + ((F_1_3 + ((F_1_5 + ((F_1_7 + ((F_1_9 + ((F_1_11 + (F_1_13 * a2)) * a2)) * a2)) * a2)) * a2)) * a2));
            } else if (a > 0.003d) {
                absAtanh = a * (1.0d + ((F_1_3 + ((F_1_5 + ((F_1_7 + (F_1_9 * a2)) * a2)) * a2)) * a2));
            } else {
                absAtanh = a * (1.0d + ((F_1_3 + (F_1_5 * a2)) * a2));
            }
        }
        return negative ? -absAtanh : absAtanh;
    }

    public static double signum(double a) {
        if (a < LOG_MAX_VALUE) {
            return -1.0d;
        }
        return a > LOG_MAX_VALUE ? 1.0d : a;
    }

    public static float signum(float a) {
        if (a < 0.0f) {
            return -1.0f;
        }
        return a > 0.0f ? 1.0f : a;
    }

    public static double nextUp(double a) {
        return nextAfter(a, Double.POSITIVE_INFINITY);
    }

    public static float nextUp(float a) {
        return nextAfter(a, Double.POSITIVE_INFINITY);
    }

    public static double nextDown(double a) {
        return nextAfter(a, Double.NEGATIVE_INFINITY);
    }

    public static float nextDown(float a) {
        return nextAfter(a, Double.NEGATIVE_INFINITY);
    }

    public static double random() {
        return Math.random();
    }

    public static double exp(double x) {
        return exp(x, LOG_MAX_VALUE, null);
    }

    private static double exp(double x, double extra, double[] hiPrec) {
        double result;
        int intVal = (int) x;
        if (x < LOG_MAX_VALUE) {
            if (x < -746.0d) {
                if (hiPrec != null) {
                    hiPrec[0] = LOG_MAX_VALUE;
                    hiPrec[1] = LOG_MAX_VALUE;
                }
                return LOG_MAX_VALUE;
            } else if (intVal < -709) {
                result = exp(40.19140625d + x, extra, hiPrec) / 2.85040095144011776E17d;
                if (hiPrec == null) {
                    return result;
                }
                hiPrec[0] = hiPrec[0] / 2.85040095144011776E17d;
                hiPrec[1] = hiPrec[1] / 2.85040095144011776E17d;
                return result;
            } else if (intVal == -709) {
                result = exp(1.494140625d + x, extra, hiPrec) / 4.455505956692757d;
                if (hiPrec == null) {
                    return result;
                }
                hiPrec[0] = hiPrec[0] / 4.455505956692757d;
                hiPrec[1] = hiPrec[1] / 4.455505956692757d;
                return result;
            } else {
                intVal--;
            }
        } else if (intVal > 709) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
                hiPrec[1] = LOG_MAX_VALUE;
            }
            return Double.POSITIVE_INFINITY;
        }
        double intPartA = ExpIntTable.EXP_INT_TABLE_A[intVal + EXP_INT_TABLE_MAX_INDEX];
        double intPartB = ExpIntTable.EXP_INT_TABLE_B[intVal + EXP_INT_TABLE_MAX_INDEX];
        int intFrac = (int) ((x - ((double) intVal)) * 1024.0d);
        double fracPartA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac];
        double fracPartB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
        double epsilon = x - (((double) intVal) + (((double) intFrac) / 1024.0d));
        double z = (((((((0.04168701738764507d * epsilon) + 0.1666666505023083d) * epsilon) + 0.5000000000042687d) * epsilon) + 1.0d) * epsilon) - 1.1409003175371524E20d;
        double tempA = intPartA * fracPartA;
        double tempB = ((intPartA * fracPartB) + (intPartB * fracPartA)) + (intPartB * fracPartB);
        double tempC = tempB + tempA;
        if (extra != LOG_MAX_VALUE) {
            result = (((((tempC * extra) * z) + (tempC * extra)) + (tempC * z)) + tempB) + tempA;
        } else {
            result = ((tempC * z) + tempB) + tempA;
        }
        if (hiPrec == null) {
            return result;
        }
        hiPrec[0] = tempA;
        hiPrec[1] = ((((tempC * extra) * z) + (tempC * extra)) + (tempC * z)) + tempB;
        return result;
    }

    public static double expm1(double x) {
        return expm1(x, null);
    }

    private static double expm1(double x, double[] hiPrecOut) {
        if (x != x || x == LOG_MAX_VALUE) {
            return x;
        }
        if (x <= -1.0d || x >= 1.0d) {
            double[] hiPrec = new double[2];
            exp(x, LOG_MAX_VALUE, hiPrec);
            if (x > LOG_MAX_VALUE) {
                return (-1.0d + hiPrec[0]) + hiPrec[1];
            }
            double ra = -1.0d + hiPrec[0];
            return ra + ((-((1.0d + ra) - hiPrec[0])) + hiPrec[1]);
        }
        boolean negative = RECOMPUTE_TABLES_AT_RUNTIME;
        if (x < LOG_MAX_VALUE) {
            x = -x;
            negative = true;
        }
        int intFrac = (int) (1024.0d * x);
        double tempA = ExpFracTable.EXP_FRAC_TABLE_A[intFrac] - 1.0d;
        double tempB = ExpFracTable.EXP_FRAC_TABLE_B[intFrac];
        double temp = tempA + tempB;
        tempA = temp;
        temp = tempA * 1.073741824E9d;
        double baseA = (tempA + temp) - temp;
        double baseB = (-((temp - tempA) - tempB)) + (tempA - baseA);
        double epsilon = x - (((double) intFrac) / 1024.0d);
        double zb = (((((((0.008336750013465571d * epsilon) + 0.041666663879186654d) * epsilon) + 0.16666666666745392d) * epsilon) + 0.49999999999999994d) * epsilon) * epsilon;
        double za = epsilon;
        temp = za + zb;
        za = temp;
        temp = za * 1.073741824E9d;
        temp = (za + temp) - temp;
        zb = (-((temp - za) - zb)) + (za - temp);
        za = temp;
        double ya = za * baseA;
        temp = ya + (za * baseB);
        ya = temp;
        temp = ya + (zb * baseA);
        double yb = (-((temp - ya) - (za * baseB))) + (-((temp - ya) - (zb * baseA)));
        ya = temp;
        temp = ya + (zb * baseB);
        double d = -((temp - ya) - (zb * baseB));
        ya = temp;
        temp = ya + baseA;
        d = -((temp - baseA) - ya);
        ya = temp;
        temp = ya + za;
        d = -((temp - ya) - za);
        ya = temp;
        temp = ya + baseB;
        d = -((temp - ya) - baseB);
        ya = temp;
        temp = ya + zb;
        yb = ((((yb + r0) + r0) + r0) + r0) + (-((temp - ya) - zb));
        ya = temp;
        if (negative) {
            double denom = 1.0d + ya;
            double denomr = 1.0d / denom;
            double ratio = ya * denomr;
            temp = ratio * 1.073741824E9d;
            ra = (ratio + temp) - temp;
            double rb = ratio - ra;
            temp = denom * 1.073741824E9d;
            za = (denom + temp) - temp;
            zb = denom - za;
            d = -ya;
            ya = -ra;
            yb = -(((rb + (((((ya - (za * ra)) - (za * rb)) - (zb * ra)) - (zb * rb)) * denomr)) + (yb * denomr)) + (((r0 * ((-((denom - 1.0d) - ya)) + yb)) * denomr) * denomr));
        }
        if (hiPrecOut != null) {
            hiPrecOut[0] = ya;
            hiPrecOut[1] = yb;
        }
        return ya + yb;
    }

    public static double log(double x) {
        return log(x, null);
    }

    private static double log(double x, double[] hiPrec) {
        if (x == LOG_MAX_VALUE) {
            return Double.NEGATIVE_INFINITY;
        }
        long bits = Double.doubleToRawLongBits(x);
        if (((Long.MIN_VALUE & bits) != 0 || x != x) && x != LOG_MAX_VALUE) {
            if (hiPrec != null) {
                hiPrec[0] = Double.NaN;
            }
            return Double.NaN;
        } else if (x == Double.POSITIVE_INFINITY) {
            if (hiPrec != null) {
                hiPrec[0] = Double.POSITIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        } else {
            int exp = ((int) (bits >> 52)) - 1023;
            if ((9218868437227405312L & bits) == 0) {
                if (x == LOG_MAX_VALUE) {
                    if (hiPrec != null) {
                        hiPrec[0] = Double.NEGATIVE_INFINITY;
                    }
                    return Double.NEGATIVE_INFINITY;
                }
                bits <<= 1;
                while ((4503599627370496L & bits) == 0) {
                    exp--;
                    bits <<= 1;
                }
            }
            double xa;
            double xb;
            double tmp;
            double aa;
            double ab;
            double[] lnCoef_last;
            double ya;
            double yb;
            int i;
            double[] lnCoef_i;
            if ((exp == -1 || exp == 0) && x < 1.01d && x > 0.99d && hiPrec == null) {
                xa = x - 1.0d;
                xb = (xa - x) + 1.0d;
                tmp = xa * 1.073741824E9d;
                aa = (xa + tmp) - tmp;
                ab = xa - aa;
                xa = aa;
                xb = ab;
                lnCoef_last = LN_QUICK_COEF[LN_QUICK_COEF.length - 1];
                ya = lnCoef_last[0];
                yb = lnCoef_last[1];
                for (i = LN_QUICK_COEF.length - 2; i >= 0; i--) {
                    aa = ya * xa;
                    ab = ((ya * xb) + (yb * xa)) + (yb * xb);
                    tmp = aa * 1.073741824E9d;
                    ya = (aa + tmp) - tmp;
                    yb = (aa - ya) + ab;
                    lnCoef_i = LN_QUICK_COEF[i];
                    aa = ya + lnCoef_i[0];
                    tmp = aa * 1.073741824E9d;
                    ya = (aa + tmp) - tmp;
                    yb = (aa - ya) + (yb + lnCoef_i[1]);
                }
                aa = ya * xa;
                ab = ((ya * xb) + (yb * xa)) + (yb * xb);
                tmp = aa * 1.073741824E9d;
                ya = (aa + tmp) - tmp;
                return ya + ((aa - ya) + ab);
            }
            double lnza;
            double[] lnm = lnMant.LN_MANT[(int) ((4499201580859392L & bits) >> 42)];
            double epsilon = ((double) (4398046511103L & bits)) / (TWO_POWER_52 + ((double) (4499201580859392L & bits)));
            double lnzb = LOG_MAX_VALUE;
            if (hiPrec != null) {
                tmp = epsilon * 1.073741824E9d;
                aa = (epsilon + tmp) - tmp;
                xa = aa;
                xb = epsilon - aa;
                double denom = TWO_POWER_52 + ((double) (4499201580859392L & bits));
                xb += ((((double) (4398046511103L & bits)) - (xa * denom)) - (xb * denom)) / denom;
                lnCoef_last = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1];
                ya = lnCoef_last[0];
                yb = lnCoef_last[1];
                for (i = LN_HI_PREC_COEF.length - 2; i >= 0; i--) {
                    aa = ya * xa;
                    ab = ((ya * xb) + (yb * xa)) + (yb * xb);
                    tmp = aa * 1.073741824E9d;
                    ya = (aa + tmp) - tmp;
                    yb = (aa - ya) + ab;
                    lnCoef_i = LN_HI_PREC_COEF[i];
                    aa = ya + lnCoef_i[0];
                    tmp = aa * 1.073741824E9d;
                    ya = (aa + tmp) - tmp;
                    yb = (aa - ya) + (yb + lnCoef_i[1]);
                }
                aa = ya * xa;
                ab = ((ya * xb) + (yb * xa)) + (yb * xb);
                lnza = aa + ab;
                lnzb = -((lnza - aa) - ab);
            } else {
                lnza = ((((((((((-0.16624882440418567d * epsilon) + 0.19999954120254515d) * epsilon) - 16.00000002972804d) * epsilon) + 0.3333333333332802d) * epsilon) - 8.0d) * epsilon) + 1.0d) * epsilon;
            }
            double a = LN_2_A * ((double) exp);
            double c = a + lnm[0];
            double d = (c - a) - lnm[0];
            a = c;
            c = a + lnza;
            d = (c - a) - lnza;
            a = c;
            c = a + (LN_2_B * ((double) exp));
            d = (c - a) - (LN_2_B * ((double) exp));
            a = c;
            c = a + lnm[1];
            d = (c - a) - lnm[1];
            a = c;
            c = a + lnzb;
            d = (c - a) - lnzb;
            a = c;
            double b = ((((LOG_MAX_VALUE + (-r42)) + (-r42)) + (-r42)) + (-r42)) + (-r42);
            if (hiPrec != null) {
                hiPrec[0] = a;
                hiPrec[1] = b;
            }
            return a + b;
        }
    }

    public static double log1p(double x) {
        if (x == -1.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        if (x <= AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY && x >= -1.0E-6d) {
            return ((((F_1_3 * x) - F_1_2) * x) + 1.0d) * x;
        }
        double xpa = 1.0d + x;
        double xpb = -((xpa - 1.0d) - x);
        double[] hiPrec = new double[2];
        double lores = log(xpa, hiPrec);
        if (Double.isInfinite(lores)) {
            return lores;
        }
        double fx1 = xpb / xpa;
        return ((((F_1_2 * fx1) + 1.0d) * fx1) + hiPrec[1]) + hiPrec[0];
    }

    public static double log10(double x) {
        double[] hiPrec = new double[2];
        double lores = log(x, hiPrec);
        if (Double.isInfinite(lores)) {
            return lores;
        }
        double tmp = hiPrec[0] * 1.073741824E9d;
        double lna = (hiPrec[0] + tmp) - tmp;
        double lnb = (hiPrec[0] - lna) + hiPrec[1];
        return (((1.9699272335463627E-8d * lnb) + (1.9699272335463627E-8d * lna)) + (0.4342944622039795d * lnb)) + (0.4342944622039795d * lna);
    }

    public static double log(double base, double x) {
        return log(x) / log(base);
    }

    public static double pow(double x, double y) {
        double[] lns = new double[2];
        if (y == LOG_MAX_VALUE) {
            return 1.0d;
        }
        if (x != x) {
            return x;
        }
        long yi;
        if (x == LOG_MAX_VALUE) {
            if ((Long.MIN_VALUE & Double.doubleToRawLongBits(x)) != 0) {
                yi = (long) y;
                if (y < LOG_MAX_VALUE) {
                    if (y == ((double) yi) && (1 & yi) == 1) {
                        return Double.NEGATIVE_INFINITY;
                    }
                }
                if (y > LOG_MAX_VALUE) {
                    if (y == ((double) yi) && (1 & yi) == 1) {
                        return -0.0d;
                    }
                }
            }
            if (y < LOG_MAX_VALUE) {
                return Double.POSITIVE_INFINITY;
            }
            if (y > LOG_MAX_VALUE) {
                return LOG_MAX_VALUE;
            }
            return Double.NaN;
        } else if (x == Double.POSITIVE_INFINITY) {
            if (y != y) {
                return y;
            }
            if (y < LOG_MAX_VALUE) {
                return LOG_MAX_VALUE;
            }
            return Double.POSITIVE_INFINITY;
        } else if (y != Double.POSITIVE_INFINITY) {
            if (x == Double.NEGATIVE_INFINITY) {
                if (y != y) {
                    return y;
                }
                if (y < LOG_MAX_VALUE) {
                    yi = (long) y;
                    if (y == ((double) yi) && (1 & yi) == 1) {
                        return -0.0d;
                    }
                    return LOG_MAX_VALUE;
                } else if (y > LOG_MAX_VALUE) {
                    yi = (long) y;
                    if (y == ((double) yi) && (1 & yi) == 1) {
                        return Double.NEGATIVE_INFINITY;
                    }
                    return Double.POSITIVE_INFINITY;
                }
            }
            if (y == Double.NEGATIVE_INFINITY) {
                if (x * x == 1.0d) {
                    return Double.NaN;
                }
                if (x * x < 1.0d) {
                    return Double.POSITIVE_INFINITY;
                }
                return LOG_MAX_VALUE;
            } else if (x >= LOG_MAX_VALUE) {
                double tmp1;
                double ya;
                double yb;
                if (y >= 8.0E298d || y <= -8.0E298d) {
                    tmp1 = y * 9.313225746154785E-10d;
                    ya = (((tmp1 + (tmp1 * 9.313225746154785E-10d)) - tmp1) * 1.073741824E9d) * 1.073741824E9d;
                    yb = y - ya;
                } else {
                    tmp1 = y * 1.073741824E9d;
                    ya = (y + tmp1) - tmp1;
                    yb = y - ya;
                }
                double lores = log(x, lns);
                if (Double.isInfinite(lores)) {
                    return lores;
                }
                double lna = lns[0];
                tmp1 = lna * 1.073741824E9d;
                double tmp2 = (lna + tmp1) - tmp1;
                double lnb = lns[1] + (lna - tmp2);
                lna = tmp2;
                double aa = lna * ya;
                double ab = ((lna * yb) + (lnb * ya)) + (lnb * yb);
                lna = aa + ab;
                lnb = -((lna - aa) - ab);
                return exp(lna, ((((((((0.008333333333333333d * lnb) + 0.041666666666666664d) * lnb) + 0.16666666666666666d) * lnb) + F_1_2) * lnb) + 1.0d) * lnb, null);
            } else if (y >= TWO_POWER_53 || y <= -9.007199254740992E15d) {
                return pow(-x, y);
            } else {
                if (y != ((double) ((long) y))) {
                    return Double.NaN;
                }
                double pow;
                if ((((long) y) & 1) == 0) {
                    pow = pow(-x, y);
                } else {
                    pow = -pow(-x, y);
                }
                return pow;
            }
        } else if (x * x == 1.0d) {
            return Double.NaN;
        } else {
            if (x * x > 1.0d) {
                return Double.POSITIVE_INFINITY;
            }
            return LOG_MAX_VALUE;
        }
    }

    public static double pow(double d, int e) {
        if (e == 0) {
            return 1.0d;
        }
        if (e < 0) {
            e = -e;
            d = 1.0d / d;
        }
        double cd = 1.34217729E8d * d;
        double d1High = cd - (cd - d);
        double d1Low = d - d1High;
        double resultHigh = 1.0d;
        double resultLow = LOG_MAX_VALUE;
        double d2p = d;
        double d2pHigh = d1High;
        double d2pLow = d1Low;
        for (e = 
        /* Method generation error in method: org.apache.commons.math4.util.FastMath.pow(double, int):double
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r46_2 'e' int) = (r46_0 'e' int), (r46_1 'e' int) binds: {(r46_1 'e' int)=B:3:0x0007, (r46_0 'e' int)=B:2:0x0005} in method: org.apache.commons.math4.util.FastMath.pow(double, int):double
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:225)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:177)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:324)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:263)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:116)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:81)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: jadx.core.utils.exceptions.CodegenException: Unknown instruction: PHI in method: org.apache.commons.math4.util.FastMath.pow(double, int):double
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:512)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:219)
	... 19 more
 */

        private static double polySine(double x) {
            double x2 = x * x;
            return (((((((2.7553817452272217E-6d * x2) - 22521.49865654966d) * x2) + 0.008333333333329196d) * x2) - 26.666666666666668d) * x2) * x;
        }

        private static double polyCosine(double x) {
            double x2 = x * x;
            return ((((((2.479773539153719E-5d * x2) - 3231.288930800263d) * x2) + 0.041666666666621166d) * x2) - 8.000000000000002d) * x2;
        }

        private static double sinQ(double xa, double xb) {
            int idx = (int) ((8.0d * xa) + F_1_2);
            double epsilon = xa - EIGHTHS[idx];
            double sintA = SINE_TABLE_A[idx];
            double sintB = SINE_TABLE_B[idx];
            double costA = COSINE_TABLE_A[idx];
            double costB = COSINE_TABLE_B[idx];
            double sinEpsA = epsilon;
            double sinEpsB = polySine(epsilon);
            double cosEpsB = polyCosine(epsilon);
            double temp = sinEpsA * 1.073741824E9d;
            double temp2 = (sinEpsA + temp) - temp;
            sinEpsB += sinEpsA - temp2;
            sinEpsA = temp2;
            double t = sintA;
            double c = LOG_MAX_VALUE + t;
            double d = -((c - LOG_MAX_VALUE) - t);
            double a = c;
            t = costA * sinEpsA;
            c = a + t;
            d = -((c - a) - t);
            a = c;
            double b = ((((((sintA * cosEpsB) + ((LOG_MAX_VALUE + r0) + r0)) + (costA * sinEpsB)) + sintB) + (costB * sinEpsA)) + (sintB * cosEpsB)) + (costB * sinEpsB);
            if (xb != LOG_MAX_VALUE) {
                t = (((costA + costB) * (1.0d + cosEpsB)) - ((sintA + sintB) * (sinEpsA + sinEpsB))) * xb;
                c = a + t;
                a = c;
                b += -((c - a) - t);
            }
            return a + b;
        }

        private static double cosQ(double xa, double xb) {
            double a = Angle2D.M_PI_2 - xa;
            return sinQ(a, (-((a - Angle2D.M_PI_2) + xa)) + (6.123233995736766E-17d - xb));
        }

        private static double tanQ(double xa, double xb, boolean cotanFlag) {
            int idx = (int) ((8.0d * xa) + F_1_2);
            double epsilon = xa - EIGHTHS[idx];
            double sintA = SINE_TABLE_A[idx];
            double sintB = SINE_TABLE_B[idx];
            double costA = COSINE_TABLE_A[idx];
            double costB = COSINE_TABLE_B[idx];
            double sinEpsA = epsilon;
            double sinEpsB = polySine(epsilon);
            double cosEpsB = polyCosine(epsilon);
            double temp = sinEpsA * 1.073741824E9d;
            double temp2 = (sinEpsA + temp) - temp;
            sinEpsB += sinEpsA - temp2;
            sinEpsA = temp2;
            double t = sintA;
            double c = LOG_MAX_VALUE + t;
            double a = c;
            t = costA * sinEpsA;
            c = a + t;
            double d = -((c - a) - t);
            a = c;
            double b = (((LOG_MAX_VALUE + (-((c - LOG_MAX_VALUE) - t))) + r0) + ((sintA * cosEpsB) + (costA * sinEpsB))) + ((((costB * sinEpsA) + sintB) + (sintB * cosEpsB)) + (costB * sinEpsB));
            double sina = a + b;
            double sinb = -((sina - a) - b);
            c = LOG_MAX_VALUE;
            a = LOG_MAX_VALUE;
            t = costA * 1.0d;
            c = a + t;
            d = -((c - a) - t);
            a = c;
            t = (-sintA) * sinEpsA;
            c = a + t;
            a = c;
            b = (((LOG_MAX_VALUE + r0) + (-((c - a) - t))) + (((1.0d * costB) + (costA * cosEpsB)) + (costB * cosEpsB))) - (((sintB * sinEpsA) + (sintA * sinEpsB)) + (sintB * sinEpsB));
            double cosa = a + b;
            double cosb = -((cosa - a) - b);
            if (cotanFlag) {
                double tmp = cosa;
                cosa = sina;
                sina = tmp;
                tmp = cosb;
                cosb = sinb;
                sinb = tmp;
            }
            double est = sina / cosa;
            temp = est * 1.073741824E9d;
            double esta = (est + temp) - temp;
            double estb = est - esta;
            temp = cosa * 1.073741824E9d;
            double cosaa = (cosa + temp) - temp;
            double cosab = cosa - cosaa;
            double err = ((((((sina - (esta * cosaa)) - (esta * cosab)) - (estb * cosaa)) - (estb * cosab)) / cosa) + (sinb / cosa)) + ((((-sina) * cosb) / cosa) / cosa);
            if (xb != LOG_MAX_VALUE) {
                double xbadj = xb + ((est * est) * xb);
                if (cotanFlag) {
                    xbadj = -xbadj;
                }
                err += xbadj;
            }
            return est + err;
        }

        private static void reducePayneHanek(double x, double[] result) {
            long shpi0;
            long shpiA;
            long shpiB;
            long inbits = Double.doubleToRawLongBits(x);
            int exponent = (((int) ((inbits >> 52) & 2047)) - 1023) + 1;
            inbits = ((inbits & 4503599627370495L) | 4503599627370496L) << 11;
            int idx = exponent >> 6;
            int shift = exponent - (idx << 6);
            if (shift != 0) {
                shpi0 = (idx == 0 ? 0 : RECIP_2PI[idx - 1] << shift) | (RECIP_2PI[idx] >>> (64 - shift));
                shpiA = (RECIP_2PI[idx] << shift) | (RECIP_2PI[idx + 1] >>> (64 - shift));
                shpiB = (RECIP_2PI[idx + 1] << shift) | (RECIP_2PI[idx + 2] >>> (64 - shift));
            } else {
                if (idx == 0) {
                    shpi0 = 0;
                } else {
                    shpi0 = RECIP_2PI[idx - 1];
                }
                shpiA = RECIP_2PI[idx];
                shpiB = RECIP_2PI[idx + 1];
            }
            long a = inbits >>> 32;
            long b = inbits & 4294967295L;
            long c = shpiA >>> 32;
            long d = shpiA & 4294967295L;
            long bd = b * d;
            long bc = b * c;
            long ad = a * d;
            long prodB = bd + (ad << 32);
            long prodA = (a * c) + (ad >>> 32);
            boolean bita = (Long.MIN_VALUE & bd) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            boolean bitb = (2147483648L & ad) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            boolean bitsum = (Long.MIN_VALUE & prodB) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prodA++;
            }
            bita = (Long.MIN_VALUE & prodB) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (2147483648L & bc) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            prodB += bc << 32;
            prodA += bc >>> 32;
            bitsum = (Long.MIN_VALUE & prodB) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prodA++;
            }
            c = shpiB >>> 32;
            long ac = (a * c) + (((b * c) + (a * (shpiB & 4294967295L))) >>> 32);
            bita = (Long.MIN_VALUE & prodB) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (Long.MIN_VALUE & ac) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            prodB += ac;
            bitsum = (Long.MIN_VALUE & prodB) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prodA++;
            }
            d = shpi0 & 4294967295L;
            prodA += (((b * (shpi0 >>> 32)) + (a * d)) << 32) + (b * d);
            int intPart = (int) (prodA >>> 62);
            prodA = (prodA << 2) | (prodB >>> 62);
            prodB <<= 2;
            a = prodA >>> 32;
            b = prodA & 4294967295L;
            c = PI_O_4_BITS[0] >>> 32;
            d = PI_O_4_BITS[0] & 4294967295L;
            bd = b * d;
            bc = b * c;
            ad = a * d;
            long prod2B = bd + (ad << 32);
            long prod2A = (a * c) + (ad >>> 32);
            bita = (Long.MIN_VALUE & bd) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (2147483648L & ad) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitsum = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prod2A++;
            }
            bita = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (2147483648L & bc) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            prod2B += bc << 32;
            prod2A += bc >>> 32;
            bitsum = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prod2A++;
            }
            c = PI_O_4_BITS[1] >>> 32;
            ac = (a * c) + (((b * c) + (a * (PI_O_4_BITS[1] & 4294967295L))) >>> 32);
            bita = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (Long.MIN_VALUE & ac) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            prod2B += ac;
            bitsum = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prod2A++;
            }
            a = prodB >>> 32;
            c = PI_O_4_BITS[0] >>> 32;
            ac = (a * c) + ((((prodB & 4294967295L) * c) + (a * (PI_O_4_BITS[0] & 4294967295L))) >>> 32);
            bita = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            bitb = (Long.MIN_VALUE & ac) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            prod2B += ac;
            bitsum = (Long.MIN_VALUE & prod2B) != 0 ? true : RECOMPUTE_TABLES_AT_RUNTIME;
            if ((bita && bitb) || ((bita || bitb) && !bitsum)) {
                prod2A++;
            }
            double tmpA = ((double) (prod2A >>> 12)) / TWO_POWER_52;
            double tmpB = (((double) (((4095 & prod2A) << 40) + (prod2B >>> 24))) / TWO_POWER_52) / TWO_POWER_52;
            double sumA = tmpA + tmpB;
            double sumB = -((sumA - tmpA) - tmpB);
            result[0] = (double) intPart;
            result[1] = 2.0d * sumA;
            result[2] = 2.0d * sumB;
        }

        public static double sin(double x) {
            boolean negative = RECOMPUTE_TABLES_AT_RUNTIME;
            int quadrant = 0;
            double xb = LOG_MAX_VALUE;
            double xa = x;
            if (x < LOG_MAX_VALUE) {
                negative = true;
                xa = -xa;
            }
            if (xa == LOG_MAX_VALUE) {
                if (Double.doubleToRawLongBits(x) < 0) {
                    return -0.0d;
                }
                return LOG_MAX_VALUE;
            } else if (xa != xa || xa == Double.POSITIVE_INFINITY) {
                return Double.NaN;
            } else {
                if (xa > 3294198.0d) {
                    double[] reduceResults = new double[3];
                    reducePayneHanek(xa, reduceResults);
                    quadrant = ((int) reduceResults[0]) & 3;
                    xa = reduceResults[1];
                    xb = reduceResults[2];
                } else if (xa > Angle2D.M_PI_2) {
                    CodyWaite cw = new CodyWaite(xa);
                    quadrant = cw.getK() & 3;
                    xa = cw.getRemA();
                    xb = cw.getRemB();
                }
                if (negative) {
                    quadrant ^= 2;
                }
                switch (quadrant) {
                    case ValueServer.DIGEST_MODE /*0*/:
                        return sinQ(xa, xb);
                    case ValueServer.REPLAY_MODE /*1*/:
                        return cosQ(xa, xb);
                    case IExpr.DOUBLEID /*2*/:
                        return -sinQ(xa, xb);
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        return -cosQ(xa, xb);
                    default:
                        return Double.NaN;
                }
            }
        }

        public static double cos(double x) {
            int quadrant = 0;
            double xa = x;
            if (x < LOG_MAX_VALUE) {
                xa = -xa;
            }
            if (xa != xa || xa == Double.POSITIVE_INFINITY) {
                return Double.NaN;
            }
            double xb = LOG_MAX_VALUE;
            if (xa > 3294198.0d) {
                double[] reduceResults = new double[3];
                reducePayneHanek(xa, reduceResults);
                quadrant = ((int) reduceResults[0]) & 3;
                xa = reduceResults[1];
                xb = reduceResults[2];
            } else if (xa > Angle2D.M_PI_2) {
                CodyWaite cw = new CodyWaite(xa);
                quadrant = cw.getK() & 3;
                xa = cw.getRemA();
                xb = cw.getRemB();
            }
            switch (quadrant) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return cosQ(xa, xb);
                case ValueServer.REPLAY_MODE /*1*/:
                    return -sinQ(xa, xb);
                case IExpr.DOUBLEID /*2*/:
                    return -cosQ(xa, xb);
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    return sinQ(xa, xb);
                default:
                    return Double.NaN;
            }
        }

        public static double tan(double x) {
            boolean negative = RECOMPUTE_TABLES_AT_RUNTIME;
            int quadrant = 0;
            double xa = x;
            if (x < LOG_MAX_VALUE) {
                negative = true;
                xa = -xa;
            }
            if (xa == LOG_MAX_VALUE) {
                if (Double.doubleToRawLongBits(x) < 0) {
                    return -0.0d;
                }
                return LOG_MAX_VALUE;
            } else if (xa != xa || xa == Double.POSITIVE_INFINITY) {
                return Double.NaN;
            } else {
                double result;
                double xb = LOG_MAX_VALUE;
                if (xa > 3294198.0d) {
                    double[] reduceResults = new double[3];
                    reducePayneHanek(xa, reduceResults);
                    quadrant = ((int) reduceResults[0]) & 3;
                    xa = reduceResults[1];
                    xb = reduceResults[2];
                } else if (xa > Angle2D.M_PI_2) {
                    CodyWaite cw = new CodyWaite(xa);
                    quadrant = cw.getK() & 3;
                    xa = cw.getRemA();
                    xb = cw.getRemB();
                }
                if (xa > 1.5d) {
                    double a = Angle2D.M_PI_2 - xa;
                    double b = (-((a - Angle2D.M_PI_2) + xa)) + (6.123233995736766E-17d - xb);
                    xa = a + b;
                    xb = -((xa - a) - b);
                    quadrant ^= 1;
                    negative ^= 1;
                }
                if ((quadrant & 1) == 0) {
                    result = tanQ(xa, xb, RECOMPUTE_TABLES_AT_RUNTIME);
                } else {
                    result = -tanQ(xa, xb, true);
                }
                if (negative) {
                    return -result;
                }
                return result;
            }
        }

        public static double atan(double x) {
            return atan(x, LOG_MAX_VALUE, RECOMPUTE_TABLES_AT_RUNTIME);
        }

        private static double atan(double xa, double xb, boolean leftPlane) {
            if (xa == LOG_MAX_VALUE) {
                return leftPlane ? copySign((double) PI, xa) : xa;
            } else {
                boolean negate;
                if (xa < LOG_MAX_VALUE) {
                    xa = -xa;
                    xb = -xb;
                    negate = true;
                } else {
                    negate = RECOMPUTE_TABLES_AT_RUNTIME;
                }
                if (xa > 1.633123935319537E16d) {
                    return (negate ^ leftPlane) != 0 ? -1.5707963267948966d : Angle2D.M_PI_2;
                }
                int idx;
                double yb;
                double za;
                if (xa < 1.0d) {
                    idx = (int) (((((-1.7168146928204135d * xa) * xa) + 8.0d) * xa) + F_1_2);
                } else {
                    double oneOverXa = 1.0d / xa;
                    idx = (int) ((-((((-1.7168146928204135d * oneOverXa) * oneOverXa) + 8.0d) * oneOverXa)) + 13.07d);
                }
                double ttA = TANGENT_TABLE_A[idx];
                double ttB = TANGENT_TABLE_B[idx];
                double epsA = xa - ttA;
                double epsB = (-((epsA - xa) + ttA)) + (xb - ttB);
                double temp = epsA + epsB;
                epsB = -((temp - epsA) - epsB);
                epsA = temp;
                temp = xa * 1.073741824E9d;
                double ya = (xa + temp) - temp;
                xa = ya;
                xb += (xb + xa) - ya;
                if (idx == 0) {
                    double denom = 1.0d / (1.0d + ((xa + xb) * (ttA + ttB)));
                    ya = epsA * denom;
                    yb = epsB * denom;
                } else {
                    double temp2 = xa * ttA;
                    za = 1.0d + temp2;
                    temp2 = (xb * ttA) + (xa * ttB);
                    temp = za + temp2;
                    za = temp;
                    ya = epsA / za;
                    temp = ya * 1.073741824E9d;
                    double yaa = (ya + temp) - temp;
                    double yab = ya - yaa;
                    temp = za * 1.073741824E9d;
                    double zaa = (za + temp) - temp;
                    double zab = za - zaa;
                    yb = ((((((epsA - (yaa * zaa)) - (yaa * zab)) - (yab * zaa)) - (yab * zab)) / za) + ((((-epsA) * (((-((za - 1.0d) - temp2)) + (-((temp - za) - temp2))) + (xb * ttB))) / za) / za)) + (epsB / za);
                }
                epsA = ya;
                epsB = yb;
                double epsA2 = epsA * epsA;
                yb = (((((((((((0.07490822288864472d * epsA2) - 0.09088450866185192d) * epsA2) + 0.11111095942313305d) * epsA2) - 0.1428571423679182d) * epsA2) + 0.19999999999923582d) * epsA2) - 0.33333333333333287d) * epsA2) * epsA;
                ya = epsA;
                temp = ya + yb;
                ya = temp;
                yb = (-((temp - ya) - yb)) + (epsB / (1.0d + (epsA * epsA)));
                double eighths = EIGHTHS[idx];
                za = eighths + ya;
                temp = za + yb;
                double zb = (-((za - eighths) - ya)) + (-((temp - za) - yb));
                za = temp;
                double result = za + zb;
                if (leftPlane) {
                    za = PI - result;
                    result = za + ((-((za - PI) + result)) + (1.2246467991473532E-16d - (-((result - za) - zb))));
                }
                if ((negate ^ leftPlane) != 0) {
                    result = -result;
                }
                return result;
            }
        }

        public static double atan2(double y, double x) {
            if (x != x || y != y) {
                return Double.NaN;
            }
            if (y == LOG_MAX_VALUE) {
                double result = x * y;
                double invx = 1.0d / x;
                double invy = 1.0d / y;
                if (invx == LOG_MAX_VALUE) {
                    if (x > LOG_MAX_VALUE) {
                        return y;
                    }
                    return copySign((double) PI, y);
                } else if (x >= LOG_MAX_VALUE && invx >= LOG_MAX_VALUE) {
                    return result;
                } else {
                    if (y < LOG_MAX_VALUE || invy < LOG_MAX_VALUE) {
                        return -3.141592653589793d;
                    }
                    return PI;
                }
            } else if (y == Double.POSITIVE_INFINITY) {
                if (x == Double.POSITIVE_INFINITY) {
                    return Angle2D.M_PI_4;
                }
                if (x == Double.NEGATIVE_INFINITY) {
                    return 2.356194490192345d;
                }
                return Angle2D.M_PI_2;
            } else if (y != Double.NEGATIVE_INFINITY) {
                if (x == Double.POSITIVE_INFINITY) {
                    if (y > LOG_MAX_VALUE || 1.0d / y > LOG_MAX_VALUE) {
                        return LOG_MAX_VALUE;
                    }
                    if (y < LOG_MAX_VALUE || 1.0d / y < LOG_MAX_VALUE) {
                        return -0.0d;
                    }
                }
                if (x == Double.NEGATIVE_INFINITY) {
                    if (y > LOG_MAX_VALUE || 1.0d / y > LOG_MAX_VALUE) {
                        return PI;
                    }
                    if (y < LOG_MAX_VALUE || 1.0d / y < LOG_MAX_VALUE) {
                        return -3.141592653589793d;
                    }
                }
                if (x == LOG_MAX_VALUE) {
                    if (y > LOG_MAX_VALUE || 1.0d / y > LOG_MAX_VALUE) {
                        return Angle2D.M_PI_2;
                    }
                    if (y < LOG_MAX_VALUE || 1.0d / y < LOG_MAX_VALUE) {
                        return -1.5707963267948966d;
                    }
                }
                double r = y / x;
                if (Double.isInfinite(r)) {
                    return atan(r, LOG_MAX_VALUE, x < LOG_MAX_VALUE ? true : RECOMPUTE_TABLES_AT_RUNTIME);
                }
                double ra = doubleHighPart(r);
                double rb = r - ra;
                double xa = doubleHighPart(x);
                double xb = x - xa;
                rb += ((((y - (ra * xa)) - (ra * xb)) - (rb * xa)) - (rb * xb)) / x;
                double temp = ra + rb;
                rb = -((temp - ra) - rb);
                ra = temp;
                if (ra == LOG_MAX_VALUE) {
                    ra = copySign((double) LOG_MAX_VALUE, y);
                }
                return atan(ra, rb, x < LOG_MAX_VALUE ? true : RECOMPUTE_TABLES_AT_RUNTIME);
            } else if (x == Double.POSITIVE_INFINITY) {
                return -0.7853981633974483d;
            } else {
                if (x == Double.NEGATIVE_INFINITY) {
                    return -2.356194490192345d;
                }
                return -1.5707963267948966d;
            }
        }

        public static double asin(double x) {
            if (x != x) {
                return Double.NaN;
            }
            if (x > 1.0d || x < -1.0d) {
                return Double.NaN;
            }
            if (x == 1.0d) {
                return Angle2D.M_PI_2;
            }
            if (x == -1.0d) {
                return -1.5707963267948966d;
            }
            if (x == LOG_MAX_VALUE) {
                return x;
            }
            double temp = x * 1.073741824E9d;
            double xa = (x + temp) - temp;
            double xb = x - xa;
            double ya = -(xa * xa);
            double yb = -(((xa * xb) * 2.0d) + (xb * xb));
            double za = 1.0d + ya;
            temp = za + yb;
            double zb = (-((za - 1.0d) - ya)) + (-((temp - za) - yb));
            za = temp;
            double y = sqrt(za);
            temp = y * 1.073741824E9d;
            ya = (y + temp) - temp;
            yb = y - ya;
            yb += (((za - (ya * ya)) - ((2.0d * ya) * yb)) - (yb * yb)) / (2.0d * y);
            double r = x / y;
            temp = r * 1.073741824E9d;
            double ra = (r + temp) - temp;
            double rb = r - ra;
            double d = -x;
            rb = (rb + (((((x - (ra * ya)) - (ra * yb)) - (rb * ya)) - (rb * yb)) / y)) + (((r0 * (zb / (2.0d * y))) / y) / y);
            temp = ra + rb;
            double d2 = (temp - ra) - rb;
            return atan(temp, -d, RECOMPUTE_TABLES_AT_RUNTIME);
        }

        public static double acos(double x) {
            if (x != x) {
                return Double.NaN;
            }
            if (x > 1.0d || x < -1.0d) {
                return Double.NaN;
            }
            if (x == -1.0d) {
                return PI;
            }
            if (x == 1.0d) {
                return LOG_MAX_VALUE;
            }
            if (x == LOG_MAX_VALUE) {
                return Angle2D.M_PI_2;
            }
            double temp = x * 1.073741824E9d;
            double xa = (x + temp) - temp;
            double xb = x - xa;
            double ya = -(xa * xa);
            double yb = -(((xa * xb) * 2.0d) + (xb * xb));
            double za = 1.0d + ya;
            temp = za + yb;
            double zb = (-((za - 1.0d) - ya)) + (-((temp - za) - yb));
            za = temp;
            double y = sqrt(za);
            temp = y * 1.073741824E9d;
            ya = (y + temp) - temp;
            yb = y - ya;
            yb = (yb + ((((za - (ya * ya)) - ((2.0d * ya) * yb)) - (yb * yb)) / (2.0d * y))) + (zb / (2.0d * y));
            y = ya + yb;
            yb = -((y - ya) - yb);
            double r = y / x;
            if (Double.isInfinite(r)) {
                return Angle2D.M_PI_2;
            }
            double ra = doubleHighPart(r);
            double rb = r - ra;
            rb = (rb + (((((y - (ra * xa)) - (ra * xb)) - (rb * xa)) - (rb * xb)) / x)) + (yb / x);
            temp = ra + rb;
            return atan(temp, -((temp - ra) - rb), x < LOG_MAX_VALUE ? true : RECOMPUTE_TABLES_AT_RUNTIME);
        }

        public static double cbrt(double x) {
            long inbits = Double.doubleToRawLongBits(x);
            int exponent = ((int) ((inbits >> 52) & 2047)) - 1023;
            boolean subnormal = RECOMPUTE_TABLES_AT_RUNTIME;
            if (exponent == -1023) {
                if (x == LOG_MAX_VALUE) {
                    return x;
                }
                subnormal = true;
                x *= 1.8014398509481984E16d;
                inbits = Double.doubleToRawLongBits(x);
                exponent = ((int) ((inbits >> 52) & 2047)) - 1023;
            }
            if (exponent == LN_MANT_LEN) {
                return x;
            }
            double p2 = Double.longBitsToDouble((Long.MIN_VALUE & inbits) | (((long) (((exponent / 3) + 1023) & 2047)) << 52));
            double mant = Double.longBitsToDouble((4503599627370495L & inbits) | 4607182418800017408L);
            double est = ((((((((-0.010714690733195933d * mant) + 0.0875862700108075d) * mant) - 14.214349574856733d) * mant) + 0.7249995199969751d) * mant) + 0.5039018405998233d) * CBRTTWO[(exponent % 3) + 2];
            double xs = x / ((p2 * p2) * p2);
            est += (xs - ((est * est) * est)) / ((3.0d * est) * est);
            est += (xs - ((est * est) * est)) / ((3.0d * est) * est);
            double temp = est * 1.073741824E9d;
            double ya = (est + temp) - temp;
            double yb = est - ya;
            double za = ya * ya;
            temp = za * 1.073741824E9d;
            double temp2 = (za + temp) - temp;
            double zb = (((ya * yb) * 2.0d) + (yb * yb)) + (za - temp2);
            za = temp2;
            zb = ((za * yb) + (ya * zb)) + (zb * yb);
            za *= ya;
            double na = xs - za;
            est = (est + ((na + ((-((na - xs) + za)) - zb)) / ((3.0d * est) * est))) * p2;
            if (subnormal) {
                est *= 3.814697265625E-6d;
            }
            return est;
        }

        public static double toRadians(double x) {
            if (Double.isInfinite(x) || x == LOG_MAX_VALUE) {
                return x;
            }
            double xa = doubleHighPart(x);
            double xb = x - xa;
            double result = (((1.997844754509471E-9d * xb) + (0.01745329052209854d * xb)) + (1.997844754509471E-9d * xa)) + (0.01745329052209854d * xa);
            if (result == LOG_MAX_VALUE) {
                return result * x;
            }
            return result;
        }

        public static double toDegrees(double x) {
            if (Double.isInfinite(x) || x == LOG_MAX_VALUE) {
                return x;
            }
            double xa = doubleHighPart(x);
            double xb = x - xa;
            return (((3.145894820876798E-6d * xb) + (57.2957763671875d * xb)) + (3.145894820876798E-6d * xa)) + (57.2957763671875d * xa);
        }

        public static int abs(int x) {
            int i = x >>> 31;
            return (((i ^ -1) + 1) ^ x) + i;
        }

        public static long abs(long x) {
            long l = x >>> 63;
            return (((-1 ^ l) + 1) ^ x) + l;
        }

        public static float abs(float x) {
            return Float.intBitsToFloat(MASK_NON_SIGN_INT & Float.floatToRawIntBits(x));
        }

        public static double abs(double x) {
            return Double.longBitsToDouble(MASK_NON_SIGN_LONG & Double.doubleToRawLongBits(x));
        }

        public static double ulp(double x) {
            if (Double.isInfinite(x)) {
                return Double.POSITIVE_INFINITY;
            }
            return abs(x - Double.longBitsToDouble(Double.doubleToRawLongBits(x) ^ 1));
        }

        public static float ulp(float x) {
            if (Float.isInfinite(x)) {
                return Float.POSITIVE_INFINITY;
            }
            return abs(x - Float.intBitsToFloat(Float.floatToIntBits(x) ^ 1));
        }

        public static double scalb(double d, int n) {
            if (n > -1023 && n < LN_MANT_LEN) {
                return Double.longBitsToDouble(((long) (n + 1023)) << 52) * d;
            }
            if (Double.isNaN(d) || Double.isInfinite(d) || d == LOG_MAX_VALUE) {
                return d;
            }
            if (n < -2098) {
                return d > LOG_MAX_VALUE ? LOG_MAX_VALUE : -0.0d;
            } else {
                if (n > 2097) {
                    return d > LOG_MAX_VALUE ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                } else {
                    long bits = Double.doubleToRawLongBits(d);
                    long sign = bits & Long.MIN_VALUE;
                    int exponent = ((int) (bits >>> 52)) & 2047;
                    long mantissa = bits & 4503599627370495L;
                    int scaledExponent = exponent + n;
                    if (n < 0) {
                        if (scaledExponent > 0) {
                            return Double.longBitsToDouble(((((long) scaledExponent) << 52) | sign) | mantissa);
                        }
                        if (scaledExponent <= -53) {
                            return sign == 0 ? LOG_MAX_VALUE : -0.0d;
                        } else {
                            mantissa |= 4503599627370496L;
                            long mostSignificantLostBit = mantissa & (1 << (-scaledExponent));
                            mantissa >>>= 1 - scaledExponent;
                            if (mostSignificantLostBit != 0) {
                                mantissa++;
                            }
                            return Double.longBitsToDouble(sign | mantissa);
                        }
                    } else if (exponent == 0) {
                        while ((mantissa >>> 52) != 1) {
                            mantissa <<= 1;
                            scaledExponent--;
                        }
                        scaledExponent++;
                        mantissa &= 4503599627370495L;
                        if (scaledExponent < 2047) {
                            return Double.longBitsToDouble(((((long) scaledExponent) << 52) | sign) | mantissa);
                        }
                        return sign == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                    } else if (scaledExponent < 2047) {
                        return Double.longBitsToDouble(((((long) scaledExponent) << 52) | sign) | mantissa);
                    } else {
                        return sign == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                    }
                }
            }
        }

        public static float scalb(float f, int n) {
            float f2 = Float.POSITIVE_INFINITY;
            if (n > -127 && n < IExpr.SYMBOLID) {
                return Float.intBitsToFloat((n + TransportMediator.KEYCODE_MEDIA_PAUSE) << 23) * f;
            }
            if (Float.isNaN(f) || Float.isInfinite(f) || f == 0.0f) {
                return f;
            }
            if (n < -277) {
                if (f <= 0.0f) {
                    return -0.0f;
                }
                return 0.0f;
            } else if (n > 276) {
                return f > 0.0f ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
            } else {
                int bits = Float.floatToIntBits(f);
                int sign = bits & RtlSpacingHelper.UNDEFINED;
                int exponent = (bits >>> 23) & MotionEventCompat.ACTION_MASK;
                int mantissa = bits & 8388607;
                int scaledExponent = exponent + n;
                if (n < 0) {
                    if (scaledExponent > 0) {
                        return Float.intBitsToFloat(((scaledExponent << 23) | sign) | mantissa);
                    }
                    if (scaledExponent > -24) {
                        mantissa |= AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED;
                        int mostSignificantLostBit = mantissa & (1 << (-scaledExponent));
                        mantissa >>>= 1 - scaledExponent;
                        if (mostSignificantLostBit != 0) {
                            mantissa++;
                        }
                        return Float.intBitsToFloat(sign | mantissa);
                    } else if (sign != 0) {
                        return -0.0f;
                    } else {
                        return 0.0f;
                    }
                } else if (exponent == 0) {
                    while ((mantissa >>> 23) != 1) {
                        mantissa <<= 1;
                        scaledExponent--;
                    }
                    scaledExponent++;
                    mantissa &= 8388607;
                    if (scaledExponent < MotionEventCompat.ACTION_MASK) {
                        return Float.intBitsToFloat(((scaledExponent << 23) | sign) | mantissa);
                    }
                    if (sign != 0) {
                        f2 = Float.NEGATIVE_INFINITY;
                    }
                    return f2;
                } else if (scaledExponent < MotionEventCompat.ACTION_MASK) {
                    return Float.intBitsToFloat(((scaledExponent << 23) | sign) | mantissa);
                } else {
                    if (sign != 0) {
                        f2 = Float.NEGATIVE_INFINITY;
                    }
                    return f2;
                }
            }
        }

        public static double nextAfter(double d, double direction) {
            if (Double.isNaN(d) || Double.isNaN(direction)) {
                return Double.NaN;
            }
            if (d == direction) {
                return direction;
            }
            if (Double.isInfinite(d)) {
                return d < LOG_MAX_VALUE ? -1.7976931348623157E308d : Double.MAX_VALUE;
            } else {
                if (d == LOG_MAX_VALUE) {
                    return direction < LOG_MAX_VALUE ? -4.9E-324d : Double.MIN_VALUE;
                } else {
                    long bits = Double.doubleToRawLongBits(d);
                    long sign = bits & Long.MIN_VALUE;
                    if (((sign == 0 ? 1 : 0) ^ (direction < d ? 1 : 0)) != 0) {
                        return Double.longBitsToDouble(((MASK_NON_SIGN_LONG & bits) + 1) | sign);
                    }
                    return Double.longBitsToDouble(((MASK_NON_SIGN_LONG & bits) - 1) | sign);
                }
            }
        }

        public static float nextAfter(float f, double direction) {
            int i = 1;
            if (Double.isNaN((double) f) || Double.isNaN(direction)) {
                return Float.NaN;
            }
            if (((double) f) == direction) {
                return (float) direction;
            }
            if (Float.isInfinite(f)) {
                return f < 0.0f ? -3.4028235E38f : AutoScrollHelper.NO_MAX;
            } else {
                if (f == 0.0f) {
                    return direction < LOG_MAX_VALUE ? -1.4E-45f : Float.MIN_VALUE;
                } else {
                    int i2;
                    int bits = Float.floatToIntBits(f);
                    int sign = bits & RtlSpacingHelper.UNDEFINED;
                    if (direction < ((double) f)) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    if (sign != 0) {
                        i = 0;
                    }
                    if ((i ^ i2) != 0) {
                        return Float.intBitsToFloat(((bits & MASK_NON_SIGN_INT) + 1) | sign);
                    }
                    return Float.intBitsToFloat(((bits & MASK_NON_SIGN_INT) - 1) | sign);
                }
            }
        }

        public static double floor(double x) {
            if (x != x || x >= TWO_POWER_52 || x <= -4.503599627370496E15d) {
                return x;
            }
            long y = (long) x;
            if (x < LOG_MAX_VALUE && ((double) y) != x) {
                y--;
            }
            if (y == 0) {
                return x * ((double) y);
            }
            return (double) y;
        }

        public static double ceil(double x) {
            if (x != x) {
                return x;
            }
            double y = floor(x);
            if (y == x) {
                return y;
            }
            y += 1.0d;
            if (y == LOG_MAX_VALUE) {
                return y * x;
            }
            return y;
        }

        public static double rint(double x) {
            double y = floor(x);
            double d = x - y;
            if (d <= F_1_2) {
                return (d < F_1_2 || (1 & ((long) y)) == 0) ? y : y + 1.0d;
            } else {
                if (y == -1.0d) {
                    return -0.0d;
                }
                return y + 1.0d;
            }
        }

        public static long round(double x) {
            return (long) floor(F_1_2 + x);
        }

        public static int round(float x) {
            return (int) floor((double) (GenVectorModul.DEFAULT_DENSITY + x));
        }

        public static int min(int a, int b) {
            return a <= b ? a : b;
        }

        public static long min(long a, long b) {
            return a <= b ? a : b;
        }

        public static float min(float a, float b) {
            if (a > b) {
                return b;
            }
            if (a < b) {
                return a;
            }
            if (a != b) {
                return Float.NaN;
            }
            if (Float.floatToRawIntBits(a) == RtlSpacingHelper.UNDEFINED) {
                return a;
            }
            return b;
        }

        public static double min(double a, double b) {
            if (a > b) {
                return b;
            }
            if (a < b) {
                return a;
            }
            if (a != b) {
                return Double.NaN;
            }
            if (Double.doubleToRawLongBits(a) == Long.MIN_VALUE) {
                return a;
            }
            return b;
        }

        public static int max(int a, int b) {
            return a <= b ? b : a;
        }

        public static long max(long a, long b) {
            return a <= b ? b : a;
        }

        public static float max(float a, float b) {
            if (a > b) {
                return a;
            }
            if (a < b) {
                return b;
            }
            if (a != b) {
                return Float.NaN;
            }
            if (Float.floatToRawIntBits(a) == RtlSpacingHelper.UNDEFINED) {
                return b;
            }
            return a;
        }

        public static double max(double a, double b) {
            if (a > b) {
                return a;
            }
            if (a < b) {
                return b;
            }
            if (a != b) {
                return Double.NaN;
            }
            if (Double.doubleToRawLongBits(a) == Long.MIN_VALUE) {
                return b;
            }
            return a;
        }

        public static double hypot(double x, double y) {
            if (Double.isInfinite(x) || Double.isInfinite(y)) {
                return Double.POSITIVE_INFINITY;
            }
            if (Double.isNaN(x) || Double.isNaN(y)) {
                return Double.NaN;
            }
            int expX = getExponent(x);
            int expY = getExponent(y);
            if (expX > expY + 27) {
                return abs(x);
            }
            if (expY > expX + 27) {
                return abs(y);
            }
            int middleExp = (expX + expY) / 2;
            double scaledX = scalb(x, -middleExp);
            double scaledY = scalb(y, -middleExp);
            return scalb(sqrt((scaledX * scaledX) + (scaledY * scaledY)), middleExp);
        }

        public static double IEEEremainder(double dividend, double divisor) {
            return StrictMath.IEEEremainder(dividend, divisor);
        }

        public static int toIntExact(long n) throws MathArithmeticException {
            if (n >= -2147483648L && n <= 2147483647L) {
                return (int) n;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
        }

        public static int incrementExact(int n) throws MathArithmeticException {
            if (n != MASK_NON_SIGN_INT) {
                return n + 1;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Integer.valueOf(n), Integer.valueOf(1));
        }

        public static long incrementExact(long n) throws MathArithmeticException {
            if (n != MASK_NON_SIGN_LONG) {
                return 1 + n;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Long.valueOf(n), Integer.valueOf(1));
        }

        public static int decrementExact(int n) throws MathArithmeticException {
            if (n != RtlSpacingHelper.UNDEFINED) {
                return n - 1;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, Integer.valueOf(n), Integer.valueOf(1));
        }

        public static long decrementExact(long n) throws MathArithmeticException {
            if (n != Long.MIN_VALUE) {
                return n - 1;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, Long.valueOf(n), Integer.valueOf(1));
        }

        public static int addExact(int a, int b) throws MathArithmeticException {
            int sum = a + b;
            if ((a ^ b) < 0 || (sum ^ b) >= 0) {
                return sum;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Integer.valueOf(a), Integer.valueOf(b));
        }

        public static long addExact(long a, long b) throws MathArithmeticException {
            long sum = a + b;
            if ((a ^ b) < 0 || (sum ^ b) >= 0) {
                return sum;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, Long.valueOf(a), Long.valueOf(b));
        }

        public static int subtractExact(int a, int b) {
            int sub = a - b;
            if ((a ^ b) >= 0 || (sub ^ b) < 0) {
                return sub;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, Integer.valueOf(a), Integer.valueOf(b));
        }

        public static long subtractExact(long a, long b) {
            long sub = a - b;
            if ((a ^ b) >= 0 || (sub ^ b) < 0) {
                return sub;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, Long.valueOf(a), Long.valueOf(b));
        }

        public static int multiplyExact(int a, int b) {
            if ((b <= 0 || (a <= MASK_NON_SIGN_INT / b && a >= RtlSpacingHelper.UNDEFINED / b)) && ((b >= -1 || (a <= RtlSpacingHelper.UNDEFINED / b && a >= MASK_NON_SIGN_INT / b)) && (b != -1 || a != RtlSpacingHelper.UNDEFINED))) {
                return a * b;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_MULTIPLICATION, Integer.valueOf(a), Integer.valueOf(b));
        }

        public static long multiplyExact(long a, long b) {
            if ((b <= 0 || (a <= MASK_NON_SIGN_LONG / b && a >= Long.MIN_VALUE / b)) && ((b >= -1 || (a <= Long.MIN_VALUE / b && a >= MASK_NON_SIGN_LONG / b)) && (b != -1 || a != Long.MIN_VALUE))) {
                return a * b;
            }
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_MULTIPLICATION, Long.valueOf(a), Long.valueOf(b));
        }

        public static int floorDiv(int a, int b) throws MathArithmeticException {
            if (b == 0) {
                throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
            }
            int m = a % b;
            if ((a ^ b) >= 0 || m == 0) {
                return a / b;
            }
            return (a / b) - 1;
        }

        public static long floorDiv(long a, long b) throws MathArithmeticException {
            if (b == 0) {
                throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
            }
            long m = a % b;
            if ((a ^ b) >= 0 || m == 0) {
                return a / b;
            }
            return (a / b) - 1;
        }

        public static int floorMod(int a, int b) throws MathArithmeticException {
            if (b == 0) {
                throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
            }
            int m = a % b;
            return ((a ^ b) >= 0 || m == 0) ? m : m + b;
        }

        public static long floorMod(long a, long b) {
            if (b == 0) {
                throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
            }
            long m = a % b;
            return ((a ^ b) >= 0 || m == 0) ? m : m + b;
        }

        public static double copySign(double magnitude, double sign) {
            return (Double.doubleToRawLongBits(magnitude) ^ Double.doubleToRawLongBits(sign)) >= 0 ? magnitude : -magnitude;
        }

        public static float copySign(float magnitude, float sign) {
            return (Float.floatToRawIntBits(magnitude) ^ Float.floatToRawIntBits(sign)) >= 0 ? magnitude : -magnitude;
        }

        public static int getExponent(double d) {
            return ((int) ((Double.doubleToRawLongBits(d) >>> 52) & 2047)) - 1023;
        }

        public static int getExponent(float f) {
            return ((Float.floatToRawIntBits(f) >>> 23) & MotionEventCompat.ACTION_MASK) - 127;
        }

        public static void main(String[] a) {
            PrintStream out = System.out;
            FastMathCalc.printarray(out, "EXP_INT_TABLE_A", (int) EXP_INT_TABLE_LEN, ExpIntTable.EXP_INT_TABLE_A);
            FastMathCalc.printarray(out, "EXP_INT_TABLE_B", (int) EXP_INT_TABLE_LEN, ExpIntTable.EXP_INT_TABLE_B);
            FastMathCalc.printarray(out, "EXP_FRAC_TABLE_A", (int) EXP_FRAC_TABLE_LEN, ExpFracTable.EXP_FRAC_TABLE_A);
            FastMathCalc.printarray(out, "EXP_FRAC_TABLE_B", (int) EXP_FRAC_TABLE_LEN, ExpFracTable.EXP_FRAC_TABLE_B);
            FastMathCalc.printarray(out, "LN_MANT", (int) LN_MANT_LEN, lnMant.LN_MANT);
            FastMathCalc.printarray(out, "SINE_TABLE_A", (int) SINE_TABLE_LEN, SINE_TABLE_A);
            FastMathCalc.printarray(out, "SINE_TABLE_B", (int) SINE_TABLE_LEN, SINE_TABLE_B);
            FastMathCalc.printarray(out, "COSINE_TABLE_A", (int) SINE_TABLE_LEN, COSINE_TABLE_A);
            FastMathCalc.printarray(out, "COSINE_TABLE_B", (int) SINE_TABLE_LEN, COSINE_TABLE_B);
            FastMathCalc.printarray(out, "TANGENT_TABLE_A", (int) SINE_TABLE_LEN, TANGENT_TABLE_A);
            FastMathCalc.printarray(out, "TANGENT_TABLE_B", (int) SINE_TABLE_LEN, TANGENT_TABLE_B);
        }
    }
