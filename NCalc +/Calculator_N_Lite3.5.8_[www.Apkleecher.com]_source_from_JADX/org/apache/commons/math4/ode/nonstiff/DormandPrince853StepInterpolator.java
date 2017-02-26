package org.apache.commons.math4.ode.nonstiff;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.ode.AbstractIntegrator;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.sampling.StepInterpolator;

class DormandPrince853StepInterpolator extends RungeKuttaStepInterpolator {
    private static final double B_01 = 0.054293734116568765d;
    private static final double B_06 = 4.450312892752409d;
    private static final double B_07 = 1.8915178993145003d;
    private static final double B_08 = -5.801203960010585d;
    private static final double B_09 = 0.3111643669578199d;
    private static final double B_10 = -0.1521609496625161d;
    private static final double B_11 = 0.20136540080403034d;
    private static final double B_12 = 0.04471061572777259d;
    private static final double C14 = 0.1d;
    private static final double C15 = 0.2d;
    private static final double C16 = 0.7777777777777778d;
    private static final double[][] D;
    private static final double K14_01 = 0.0018737681664791894d;
    private static final double K14_06 = -4.450312892752409d;
    private static final double K14_07 = -1.6380176890978755d;
    private static final double K14_08 = 5.554964922539782d;
    private static final double K14_09 = -0.4353557902216363d;
    private static final double K14_10 = 0.30545274794128174d;
    private static final double K14_11 = -0.19316434850839564d;
    private static final double K14_12 = -0.03714271806722689d;
    private static final double K14_13 = -0.008298d;
    private static final double K15_01 = -0.022459085953066622d;
    private static final double K15_06 = -4.422011983080043d;
    private static final double K15_07 = -1.8379759110070617d;
    private static final double K15_08 = 5.746280211439194d;
    private static final double K15_09 = -0.3111643669578199d;
    private static final double K15_10 = 0.1521609496625161d;
    private static final double K15_11 = -0.2014737481327276d;
    private static final double K15_12 = -0.04432804463693693d;
    private static final double K15_13 = -3.4046500868740456E-4d;
    private static final double K15_14 = 0.1413124436746325d;
    private static final double K16_01 = -0.4831900357003607d;
    private static final double K16_06 = -9.147934308113573d;
    private static final double K16_07 = 5.791903296748099d;
    private static final double K16_08 = 9.870193778407696d;
    private static final double K16_09 = 0.04556282049746119d;
    private static final double K16_10 = 0.1521609496625161d;
    private static final double K16_11 = -0.20136540080403034d;
    private static final double K16_12 = -0.04471061572777259d;
    private static final double K16_13 = -0.0013990241651590145d;
    private static final double K16_14 = 2.9475147891527724d;
    private static final double K16_15 = -9.15095847217987d;
    private static final long serialVersionUID = 20111120;
    private double[][] v;
    private boolean vectorsInitialized;
    private double[][] yDotKLast;

    static {
        D = new double[][]{new double[]{-8.428938276109013d, 0.5667149535193777d, -3.0689499459498917d, 2.38466765651207d, 2.1170345824450285d, -0.871391583777973d, 2.2404374302607883d, 0.6315787787694688d, -0.08899033645133331d, 18.148505520854727d, -9.194632392478356d, -4.436036387594894d}, new double[]{10.427508642579134d, 242.28349177525817d, 165.20045171727028d, -374.5467547226902d, -22.113666853125302d, 7.733432668472264d, -30.674084731089398d, -9.332130526430229d, 15.697238121770845d, -31.139403219565178d, -9.35292435884448d, 35.81684148639408d}, new double[]{19.985053242002433d, -387.0373087493518d, -189.17813819516758d, 527.8081592054236d, -11.573902539959631d, 6.8812326946963d, -1.0006050966910838d, 0.7777137798053443d, -2.778205752353508d, -60.19669523126412d, 84.32040550667716d, 11.99229113618279d}, new double[]{-25.69393346270375d, -154.18974869023643d, -231.5293791760455d, 357.6391179106141d, 93.4053241836243d, -37.45832313645163d, 104.0996495089623d, 29.8402934266605d, -43.53345659001114d, 96.32455395918828d, -39.17726167561544d, -149.72683625798564d}};
    }

    public DormandPrince853StepInterpolator() {
        this.yDotKLast = null;
        this.v = null;
        this.vectorsInitialized = false;
    }

    public DormandPrince853StepInterpolator(DormandPrince853StepInterpolator interpolator) {
        super(interpolator);
        if (interpolator.currentState == null) {
            this.yDotKLast = null;
            this.v = null;
            this.vectorsInitialized = false;
            return;
        }
        int k;
        int dimension = interpolator.currentState.length;
        this.yDotKLast = new double[3][];
        for (k = 0; k < this.yDotKLast.length; k++) {
            this.yDotKLast[k] = new double[dimension];
            System.arraycopy(interpolator.yDotKLast[k], 0, this.yDotKLast[k], 0, dimension);
        }
        this.v = new double[7][];
        for (k = 0; k < this.v.length; k++) {
            this.v[k] = new double[dimension];
            System.arraycopy(interpolator.v[k], 0, this.v[k], 0, dimension);
        }
        this.vectorsInitialized = interpolator.vectorsInitialized;
    }

    protected StepInterpolator doCopy() {
        return new DormandPrince853StepInterpolator(this);
    }

    public void reinitialize(AbstractIntegrator integrator, double[] y, double[][] yDotK, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
        int k;
        super.reinitialize(integrator, y, yDotK, forward, primaryMapper, secondaryMappers);
        int dimension = this.currentState.length;
        this.yDotKLast = new double[3][];
        for (k = 0; k < this.yDotKLast.length; k++) {
            this.yDotKLast[k] = new double[dimension];
        }
        this.v = new double[7][];
        for (k = 0; k < this.v.length; k++) {
            this.v[k] = new double[dimension];
        }
        this.vectorsInitialized = false;
    }

    public void storeTime(double t) {
        super.storeTime(t);
        this.vectorsInitialized = false;
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) throws MaxCountExceededException {
        int i;
        if (!this.vectorsInitialized) {
            int k;
            if (this.v == null) {
                this.v = new double[7][];
                for (k = 0; k < 7; k++) {
                    this.v[k] = new double[this.interpolatedState.length];
                }
            }
            finalizeStep();
            for (i = 0; i < this.interpolatedState.length; i++) {
                double yDot1 = this.yDotK[0][i];
                double yDot6 = this.yDotK[5][i];
                double yDot7 = this.yDotK[6][i];
                double yDot8 = this.yDotK[7][i];
                double yDot9 = this.yDotK[8][i];
                double yDot10 = this.yDotK[9][i];
                double yDot11 = this.yDotK[10][i];
                double yDot12 = this.yDotK[11][i];
                double yDot13 = this.yDotK[12][i];
                double yDot14 = this.yDotKLast[0][i];
                double yDot15 = this.yDotKLast[1][i];
                double yDot16 = this.yDotKLast[2][i];
                this.v[0][i] = (((((((B_01 * yDot1) + (B_06 * yDot6)) + (B_07 * yDot7)) + (B_08 * yDot8)) + (B_09 * yDot9)) + (B_10 * yDot10)) + (B_11 * yDot11)) + (B_12 * yDot12);
                this.v[1][i] = yDot1 - this.v[0][i];
                this.v[2][i] = (this.v[0][i] - this.v[1][i]) - this.yDotK[12][i];
                k = 0;
                while (true) {
                    if (k >= D.length) {
                        break;
                    }
                    this.v[k + 3][i] = (((((((((((D[k][0] * yDot1) + (D[k][1] * yDot6)) + (D[k][2] * yDot7)) + (D[k][3] * yDot8)) + (D[k][4] * yDot9)) + (D[k][5] * yDot10)) + (D[k][6] * yDot11)) + (D[k][7] * yDot12)) + (D[k][8] * yDot13)) + (D[k][9] * yDot14)) + (D[k][10] * yDot15)) + (D[k][11] * yDot16);
                    k++;
                }
            }
            this.vectorsInitialized = true;
        }
        double eta = 1.0d - theta;
        double twoTheta = 2.0d * theta;
        double theta2 = theta * theta;
        double dot1 = 1.0d - twoTheta;
        double dot2 = theta * (2.0d - (3.0d * theta));
        double dot3 = twoTheta * (1.0d + ((twoTheta - 3.0d) * theta));
        double dot4 = theta2 * (3.0d + (((5.0d * theta) - 8.0d) * theta));
        double dot5 = theta2 * (3.0d + ((-12.0d + ((15.0d - (6.0d * theta)) * theta)) * theta));
        double dot6 = (theta2 * theta) * (4.0d + ((-15.0d + ((18.0d - (7.0d * theta)) * theta)) * theta));
        if (this.previousState == null || theta > 0.5d) {
            for (i = 0; i < this.interpolatedState.length; i++) {
                this.interpolatedState[i] = this.currentState[i] - ((this.v[0][i] - ((this.v[1][i] + ((this.v[2][i] + ((this.v[3][i] + ((this.v[4][i] + ((this.v[5][i] + (this.v[6][i] * theta)) * eta)) * theta)) * eta)) * theta)) * theta)) * oneMinusThetaH);
                this.interpolatedDerivatives[i] = (((((this.v[0][i] + (this.v[1][i] * dot1)) + (this.v[2][i] * dot2)) + (this.v[3][i] * dot3)) + (this.v[4][i] * dot4)) + (this.v[5][i] * dot5)) + (this.v[6][i] * dot6);
            }
            return;
        }
        for (i = 0; i < this.interpolatedState.length; i++) {
            this.interpolatedState[i] = this.previousState[i] + ((this.h * theta) * (this.v[0][i] + ((this.v[1][i] + ((this.v[2][i] + ((this.v[3][i] + ((this.v[4][i] + ((this.v[5][i] + (this.v[6][i] * theta)) * eta)) * theta)) * eta)) * theta)) * eta)));
            this.interpolatedDerivatives[i] = (((((this.v[0][i] + (this.v[1][i] * dot1)) + (this.v[2][i] * dot2)) + (this.v[3][i] * dot3)) + (this.v[4][i] * dot4)) + (this.v[5][i] * dot5)) + (this.v[6][i] * dot6);
        }
    }

    protected void doFinalize() throws MaxCountExceededException {
        if (this.currentState != null) {
            int j;
            double[] yTmp = new double[this.currentState.length];
            double pT = getGlobalPreviousTime();
            for (j = 0; j < this.currentState.length; j++) {
                yTmp[j] = this.currentState[j] + (this.h * (((((((((K14_01 * this.yDotK[0][j]) + (K14_06 * this.yDotK[5][j])) + (K14_07 * this.yDotK[6][j])) + (K14_08 * this.yDotK[7][j])) + (K14_09 * this.yDotK[8][j])) + (K14_10 * this.yDotK[9][j])) + (K14_11 * this.yDotK[10][j])) + (K14_12 * this.yDotK[11][j])) + (K14_13 * this.yDotK[12][j])));
            }
            this.integrator.computeDerivatives((C14 * this.h) + pT, yTmp, this.yDotKLast[0]);
            for (j = 0; j < this.currentState.length; j++) {
                yTmp[j] = this.currentState[j] + (this.h * ((((((((((K15_01 * this.yDotK[0][j]) + (K15_06 * this.yDotK[5][j])) + (K15_07 * this.yDotK[6][j])) + (K15_08 * this.yDotK[7][j])) + (K15_09 * this.yDotK[8][j])) + (K16_10 * this.yDotK[9][j])) + (K15_11 * this.yDotK[10][j])) + (K15_12 * this.yDotK[11][j])) + (K15_13 * this.yDotK[12][j])) + (K15_14 * this.yDotKLast[0][j])));
            }
            this.integrator.computeDerivatives((C15 * this.h) + pT, yTmp, this.yDotKLast[1]);
            for (j = 0; j < this.currentState.length; j++) {
                yTmp[j] = this.currentState[j] + (this.h * (((((((((((K16_01 * this.yDotK[0][j]) + (K16_06 * this.yDotK[5][j])) + (K16_07 * this.yDotK[6][j])) + (K16_08 * this.yDotK[7][j])) + (K16_09 * this.yDotK[8][j])) + (K16_10 * this.yDotK[9][j])) + (K16_11 * this.yDotK[10][j])) + (K16_12 * this.yDotK[11][j])) + (K16_13 * this.yDotK[12][j])) + (K16_14 * this.yDotKLast[0][j])) + (K16_15 * this.yDotKLast[1][j])));
            }
            this.integrator.computeDerivatives((C16 * this.h) + pT, yTmp, this.yDotKLast[2]);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        try {
            finalizeStep();
            int dimension = this.currentState == null ? -1 : this.currentState.length;
            out.writeInt(dimension);
            for (int i = 0; i < dimension; i++) {
                out.writeDouble(this.yDotKLast[0][i]);
                out.writeDouble(this.yDotKLast[1][i]);
                out.writeDouble(this.yDotKLast[2][i]);
            }
            super.writeExternal(out);
        } catch (MaxCountExceededException mcee) {
            IOException ioe = new IOException(mcee.getLocalizedMessage());
            ioe.initCause(mcee);
            throw ioe;
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        double[] dArr = null;
        this.yDotKLast = new double[3][];
        int dimension = in.readInt();
        this.yDotKLast[0] = dimension < 0 ? null : new double[dimension];
        this.yDotKLast[1] = dimension < 0 ? null : new double[dimension];
        double[][] dArr2 = this.yDotKLast;
        if (dimension >= 0) {
            dArr = new double[dimension];
        }
        dArr2[2] = dArr;
        for (int i = 0; i < dimension; i++) {
            this.yDotKLast[0][i] = in.readDouble();
            this.yDotKLast[1][i] = in.readDouble();
            this.yDotKLast[2][i] = in.readDouble();
        }
        super.readExternal(in);
    }
}
