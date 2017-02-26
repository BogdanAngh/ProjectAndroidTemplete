package org.apache.commons.math4.geometry.euclidean.threed;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.fraction.BigFraction;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.enclosing.EnclosingBall;
import org.apache.commons.math4.geometry.enclosing.SupportBallGenerator;
import org.apache.commons.math4.geometry.euclidean.twod.DiskGenerator;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.util.FastMath;

public class SphereGenerator implements SupportBallGenerator<Euclidean3D, Vector3D> {
    public EnclosingBall<Euclidean3D, Vector3D> ballOnSupport(List<Vector3D> support) {
        if (support.size() < 1) {
            return new EnclosingBall(Vector3D.ZERO, Double.NEGATIVE_INFINITY, new Vector3D[0]);
        }
        Vector vA = (Vector3D) support.get(0);
        if (support.size() < 2) {
            return new EnclosingBall(vA, 0.0d, vA);
        }
        Vector vB = (Vector3D) support.get(1);
        if (support.size() < 3) {
            return new EnclosingBall(new Vector3D(0.5d, vA, 0.5d, vB), 0.5d * vA.distance(vB), vA, vB);
        }
        Vector vC = (Vector3D) support.get(2);
        if (support.size() < 4) {
            Plane p = new Plane(vA, vB, vC, ((vA.getNorm1() + vB.getNorm1()) + vC.getNorm1()) * CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD);
            EnclosingBall<Euclidean2D, Vector2D> disk = new DiskGenerator().ballOnSupport(Arrays.asList(new Vector2D[]{p.toSubSpace(vA), p.toSubSpace(vB), p.toSubSpace(vC)}));
            return new EnclosingBall(p.toSpace((Vector) disk.getCenter()), disk.getRadius(), vA, vB, vC);
        }
        Vector3D vD = (Vector3D) support.get(3);
        BigFraction[] c2 = new BigFraction[]{new BigFraction(vA.getX()), new BigFraction(vB.getX()), new BigFraction(vC.getX()), new BigFraction(vD.getX())};
        BigFraction[] c3 = new BigFraction[]{new BigFraction(vA.getY()), new BigFraction(vB.getY()), new BigFraction(vC.getY()), new BigFraction(vD.getY())};
        BigFraction[] c4 = new BigFraction[]{new BigFraction(vA.getZ()), new BigFraction(vB.getZ()), new BigFraction(vC.getZ()), new BigFraction(vD.getZ())};
        BigFraction[] c1 = new BigFraction[]{c2[0].multiply(c2[0]).add(c3[0].multiply(c3[0])).add(c4[0].multiply(c4[0])), c2[1].multiply(c2[1]).add(c3[1].multiply(c3[1])).add(c4[1].multiply(c4[1])), c2[2].multiply(c2[2]).add(c3[2].multiply(c3[2])).add(c4[2].multiply(c4[2])), c2[3].multiply(c2[3]).add(c3[3].multiply(c3[3])).add(c4[3].multiply(c4[3]))};
        BigFraction twoM11 = minor(c2, c3, c4).multiply(2);
        BigFraction m12 = minor(c1, c3, c4);
        BigFraction m13 = minor(c1, c2, c4);
        BigFraction m14 = minor(c1, c2, c3);
        BigFraction centerX = m12.divide(twoM11);
        BigFraction centerY = m13.divide(twoM11).negate();
        BigFraction centerZ = m14.divide(twoM11);
        BigFraction dx = c2[0].subtract(centerX);
        BigFraction dy = c3[0].subtract(centerY);
        BigFraction dz = c4[0].subtract(centerZ);
        BigFraction r2 = dx.multiply(dx).add(dy.multiply(dy)).add(dz.multiply(dz));
        return new EnclosingBall(new Vector3D(centerX.doubleValue(), centerY.doubleValue(), centerZ.doubleValue()), FastMath.sqrt(r2.doubleValue()), vA, vB, vC, vD);
    }

    private BigFraction minor(BigFraction[] c1, BigFraction[] c2, BigFraction[] c3) {
        return c2[0].multiply(c3[1]).multiply(c1[2].subtract(c1[3])).add(c2[0].multiply(c3[2]).multiply(c1[3].subtract(c1[1]))).add(c2[0].multiply(c3[3]).multiply(c1[1].subtract(c1[2]))).add(c2[1].multiply(c3[0]).multiply(c1[3].subtract(c1[2]))).add(c2[1].multiply(c3[2]).multiply(c1[0].subtract(c1[3]))).add(c2[1].multiply(c3[3]).multiply(c1[2].subtract(c1[0]))).add(c2[2].multiply(c3[0]).multiply(c1[1].subtract(c1[3]))).add(c2[2].multiply(c3[1]).multiply(c1[3].subtract(c1[0]))).add(c2[2].multiply(c3[3]).multiply(c1[0].subtract(c1[1]))).add(c2[3].multiply(c3[0]).multiply(c1[2].subtract(c1[1]))).add(c2[3].multiply(c3[1]).multiply(c1[0].subtract(c1[2]))).add(c2[3].multiply(c3[2]).multiply(c1[1].subtract(c1[0])));
    }
}
