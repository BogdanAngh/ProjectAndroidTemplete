package org.apache.commons.math4.random;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;

public class StableRandomGenerator implements NormalizedRandomGenerator {
    private final double alpha;
    private final double beta;
    private final RandomGenerator generator;
    private final double zeta;

    public StableRandomGenerator(RandomGenerator generator, double alpha, double beta) throws NullArgumentException, OutOfRangeException {
        if (generator == null) {
            throw new NullArgumentException();
        } else if (alpha <= 0.0d || alpha > 2.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_LEFT, Double.valueOf(alpha), Integer.valueOf(0), Integer.valueOf(2));
        } else if (beta < -1.0d || beta > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_SIMPLE, Double.valueOf(beta), Integer.valueOf(-1), Integer.valueOf(1));
        } else {
            this.generator = generator;
            this.alpha = alpha;
            this.beta = beta;
            if (alpha >= 2.0d || beta == 0.0d) {
                this.zeta = 0.0d;
            } else {
                this.zeta = FastMath.tan((FastMath.PI * alpha) / 2.0d) * beta;
            }
        }
    }

    public double nextNormalizedDouble() {
        double omega = -FastMath.log(this.generator.nextDouble());
        double phi = FastMath.PI * (this.generator.nextDouble() - 0.5d);
        if (this.alpha == 2.0d) {
            return FastMath.sqrt(2.0d * omega) * FastMath.sin(phi);
        }
        if (this.beta == 0.0d) {
            if (this.alpha == 1.0d) {
                return FastMath.tan(phi);
            }
            return (FastMath.pow(FastMath.cos((1.0d - this.alpha) * phi) * omega, (1.0d / this.alpha) - 1.0d) * FastMath.sin(this.alpha * phi)) / FastMath.pow(FastMath.cos(phi), 1.0d / this.alpha);
        }
        double cosPhi = FastMath.cos(phi);
        if (FastMath.abs(this.alpha - 1.0d) > BOBYQAOptimizer.DEFAULT_STOPPING_RADIUS) {
            double alphaPhi = this.alpha * phi;
            double invAlphaPhi = phi - alphaPhi;
            double d = this.zeta;
            double d2 = this.zeta;
            double d3 = this.alpha;
            return (((FastMath.sin(alphaPhi) + (r0 * FastMath.cos(alphaPhi))) / cosPhi) * (FastMath.cos(invAlphaPhi) + (r0 * FastMath.sin(invAlphaPhi)))) / FastMath.pow(omega * cosPhi, (1.0d - r0) / this.alpha);
        }
        double betaPhi = Angle2D.M_PI_2 + (this.beta * phi);
        double x = 0.6366197723675814d * ((FastMath.tan(phi) * betaPhi) - (this.beta * FastMath.log(((Angle2D.M_PI_2 * omega) * cosPhi) / betaPhi)));
        if (this.alpha == 1.0d) {
            return x;
        }
        return x + (this.beta * FastMath.tan((FastMath.PI * this.alpha) / 2.0d));
    }
}
