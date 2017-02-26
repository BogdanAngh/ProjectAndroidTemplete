package de.lab4inf.math.functions;

public class Sinc extends L4MFunction {
    public double f(double... x) {
        return sinc(x[0]);
    }

    public static double sinc(double x) {
        if (x == 0.0d) {
            return 1.0d;
        }
        return Math.sin(x) / x;
    }
}
