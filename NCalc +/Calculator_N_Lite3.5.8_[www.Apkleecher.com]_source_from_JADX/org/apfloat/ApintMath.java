package org.apfloat;

public class ApintMath {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ApintMath.class.desiredAssertionStatus();
    }

    private ApintMath() {
    }

    public static Apint pow(Apint x, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n == 0) {
            if (x.signum() != 0) {
                return new Apint(1, x.radix());
            }
            throw new ArithmeticException("Zero to power zero");
        } else if (n < 0) {
            return Apint.ZERO;
        } else {
            int b2pow = 0;
            while ((n & 1) == 0) {
                b2pow++;
                n >>= 1;
            }
            Apint r = x;
            while (true) {
                n >>= 1;
                if (n <= 0) {
                    break;
                }
                x = x.multiply(x);
                if ((n & 1) != 0) {
                    r = r.multiply(x);
                }
            }
            int b2pow2 = b2pow;
            while (true) {
                b2pow = b2pow2 - 1;
                if (b2pow2 <= 0) {
                    return r;
                }
                r = r.multiply(r);
                b2pow2 = b2pow;
            }
        }
    }

    public static Apint[] sqrt(Apint x) throws ArithmeticException, ApfloatRuntimeException {
        return root(x, 2);
    }

    public static Apint[] cbrt(Apint x) throws ApfloatRuntimeException {
        return root(x, 3);
    }

    public static Apint[] root(Apint x, long n) throws ArithmeticException, ApfloatRuntimeException {
        if (n == 0) {
            if (x.signum() == 0) {
                throw new ArithmeticException("Zeroth root of zero");
            }
            Apint one = new Apint(1, x.radix());
            return new Apint[]{one, x.subtract(one)};
        } else if (x.signum() == 0) {
            return new Apint[]{x, x};
        } else {
            if (x.equals(Apint.ONE) || n == 1) {
                return new Apint[]{x, Apint.ZERO};
            } else if (n < 0) {
                return new Apint[]{Apint.ZERO, x};
            } else {
                Apint root = ApfloatMath.root(x.precision((x.scale() / n) + 20), n).truncate();
                Apint pow = pow(root, n);
                if (abs(pow).compareTo(abs(x)) > 0) {
                    pow = x.signum() >= 0 ? powXMinus1(pow, root, n) : powXPlus1(pow, root, n);
                    root = root.subtract(new Apint((long) x.signum(), x.radix()));
                } else {
                    Apint powPlus1;
                    if (x.signum() >= 0) {
                        powPlus1 = powXPlus1(pow, root, n);
                    } else {
                        powPlus1 = powXMinus1(pow, root, n);
                    }
                    if (abs(powPlus1).compareTo(abs(x)) <= 0) {
                        pow = powPlus1;
                        root = root.add(new Apint((long) x.signum(), x.radix()));
                    }
                }
                Apint remainder = x.subtract(pow);
                if ($assertionsDisabled || remainder.signum() * x.signum() >= 0) {
                    return new Apint[]{root, remainder};
                }
                throw new AssertionError();
            }
        }
    }

    private static Apint powXMinus1(Apint pow, Apint x, long n) throws ApfloatRuntimeException {
        Apint one = new Apint(1, x.radix());
        if (n == 2) {
            return pow.subtract(x).subtract(x).add(one);
        }
        if (n == 3) {
            return pow.subtract(new Apint(3, x.radix()).multiply(x).multiply(x.subtract(one))).subtract(one);
        }
        return pow(x.subtract(one), n);
    }

    private static Apint powXPlus1(Apint pow, Apint x, long n) throws ApfloatRuntimeException {
        Apint one = new Apint(1, x.radix());
        if (n == 2) {
            return pow.add(x).add(x).add(one);
        }
        if (n == 3) {
            return pow.add(new Apint(3, x.radix()).multiply(x).multiply(x.add(one))).add(one);
        }
        return pow(x.add(one), n);
    }

    @Deprecated
    public static Apint negate(Apint x) throws ApfloatRuntimeException {
        return x.negate();
    }

    public static Apint abs(Apint x) throws ApfloatRuntimeException {
        return x.signum() >= 0 ? x : x.negate();
    }

    public static Apint copySign(Apint x, Apint y) throws ApfloatRuntimeException {
        if (y.signum() == 0) {
            return y;
        }
        return x.signum() != y.signum() ? x.negate() : x;
    }

    public static Apint scale(Apint x, long scale) throws ApfloatRuntimeException {
        return ApfloatMath.scale(x, scale).truncate();
    }

    public static Apint[] div(Apint x, Apint y) throws ArithmeticException, ApfloatRuntimeException {
        if (y.signum() == 0) {
            throw new ArithmeticException("Division by zero");
        } else if (x.signum() == 0) {
            return new Apint[]{x, x};
        } else if (y.equals(Apint.ONE)) {
            return new Apint[]{x, Apint.ZERO};
        } else {
            Apint a = abs(x);
            Apint b = abs(y);
            if (a.compareTo(b) < 0) {
                return new Apint[]{Apint.ZERO, x};
            }
            long precision = (x.scale() - y.scale()) + 20;
            Apint q = x.precision(precision).divide(y.precision(precision)).truncate();
            a = a.subtract(abs(q.multiply(y)));
            if (a.compareTo(b) >= 0) {
                q = q.add(new Apint((long) (x.signum() * y.signum()), x.radix()));
                a = a.subtract(b);
            } else if (a.signum() < 0) {
                q = q.subtract(new Apint((long) (x.signum() * y.signum()), x.radix()));
                a = a.add(b);
            }
            Apint r = copySign(a, x);
            return new Apint[]{q, r};
        }
    }

    public static Apint gcd(Apint a, Apint b) throws ApfloatRuntimeException {
        return GCDHelper.gcd(a, b);
    }

    public static Apint lcm(Apint a, Apint b) throws ApfloatRuntimeException {
        if (a.signum() == 0 && b.signum() == 0) {
            return Apint.ZERO;
        }
        return abs(a.multiply(b)).divide(gcd(a, b));
    }

    public static Apint modMultiply(Apint a, Apint b, Apint m) throws ApfloatRuntimeException {
        return a.multiply(b).mod(m);
    }

    private static Apint modMultiply(Apint x1, Apint x2, Apint y, Apfloat inverseY) throws ApfloatRuntimeException {
        Apint x = x1.multiply(x2);
        if (x.signum() == 0) {
            return x;
        }
        long precision = (x.scale() - y.scale()) + 20;
        Apint a = abs(x);
        Apint b = abs(y);
        if (a.compareTo(b) < 0) {
            return x;
        }
        a = a.subtract(abs(x.multiply(inverseY.precision(precision)).truncate().multiply(y)));
        if (a.compareTo(b) >= 0) {
            a = a.subtract(b);
        } else if (a.signum() < 0) {
            a = a.add(b);
        }
        return copySign(a, x);
    }

    public static Apint modPow(Apint a, Apint b, Apint m) throws ArithmeticException, ApfloatRuntimeException {
        if (b.signum() == 0) {
            if (a.signum() != 0) {
                return new Apint(1, a.radix());
            }
            throw new ArithmeticException("Zero to power zero");
        } else if (m.signum() == 0) {
            return m;
        } else {
            Apint[] qr;
            m = abs(m);
            Apfloat inverseModulus = ApfloatMath.inverseRoot(m, 1, m.scale() + 20);
            a = a.mod(m);
            if (b.signum() < 0) {
                a = modInverse(a, m);
                b = b.negate();
            }
            Apint two = new Apint(2, b.radix());
            while (true) {
                qr = div(b, two);
                if (qr[1].signum() != 0) {
                    break;
                }
                a = modMultiply(a, a, m, inverseModulus);
                b = qr[0];
            }
            Apint r = a;
            qr = div(b, two);
            while (true) {
                b = qr[0];
                if (b.signum() <= 0) {
                    return r;
                }
                a = modMultiply(a, a, m, inverseModulus);
                qr = div(b, two);
                if (qr[1].signum() != 0) {
                    r = modMultiply(r, a, m, inverseModulus);
                }
            }
        }
    }

    private static Apint modInverse(Apint a, Apint m) throws ArithmeticException, ApfloatRuntimeException {
        Apint one = new Apint(1, m.radix());
        Apint x = Apint.ZERO;
        Apint y = one;
        Apint oldX = one;
        Apint oldY = Apint.ZERO;
        Apint oldA = a;
        Apint b = m;
        while (b.signum() != 0) {
            Apint q = a.divide(b);
            Apint tmp = b;
            b = a.mod(b);
            a = tmp;
            tmp = x;
            x = oldX.subtract(q.multiply(x));
            oldX = tmp;
            tmp = y;
            y = oldY.subtract(q.multiply(y));
            oldY = tmp;
        }
        if (!abs(a).equals(one)) {
            throw new ArithmeticException("Modular inverse does not exist");
        } else if (oldX.signum() != oldA.signum()) {
            return oldX.add(copySign(m, oldA));
        } else {
            return oldX;
        }
    }

    public static Apint factorial(long n) throws ArithmeticException, NumberFormatException, ApfloatRuntimeException {
        return new Apint(ApfloatMath.factorial(n, Long.MAX_VALUE));
    }

    public static Apint factorial(long n, int radix) throws ArithmeticException, NumberFormatException, ApfloatRuntimeException {
        return new Apint(ApfloatMath.factorial(n, Long.MAX_VALUE, radix));
    }

    public static Apint product(Apint... x) throws ApfloatRuntimeException {
        return new Apint(ApfloatMath.product(x));
    }

    public static Apint sum(Apint... x) throws ApfloatRuntimeException {
        return new Apint(ApfloatMath.sum(x));
    }
}
