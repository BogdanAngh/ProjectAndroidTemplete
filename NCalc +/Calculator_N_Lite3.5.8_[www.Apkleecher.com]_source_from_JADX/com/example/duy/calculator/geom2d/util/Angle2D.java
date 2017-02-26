package com.example.duy.calculator.geom2d.util;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.line.Line2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class Angle2D {
    public static final double M_2PI = 6.283185307179586d;
    public static final double M_3PI_2 = 4.71238898038469d;
    public static final double M_PI = 3.141592653589793d;
    public static final double M_PI_2 = 1.5707963267948966d;
    public static final double M_PI_4 = 0.7853981633974483d;

    public static double formatAngle(double angle) {
        return ((angle % M_2PI) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(Point2D point) {
        return (Math.atan2(point.y, point.x) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(double x, double y) {
        return (Math.atan2(y, x) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(Vector2D vect) {
        return (Math.atan2(vect.y, vect.x) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(LinearShape2D object) {
        Vector2D vect = object.supportingLine().direction();
        return (Math.atan2(vect.y, vect.x) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(Point2D p1, Point2D p2) {
        return (Math.atan2(p2.y - p1.y, p2.x - p1.x) + M_2PI) % M_2PI;
    }

    public static double horizontalAngle(double x1, double y1, double x2, double y2) {
        return (Math.atan2(y2 - y1, x2 - x1) + M_2PI) % M_2PI;
    }

    public static double pseudoAngle(Point2D p1, Point2D p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double s = Math.abs(dx) + Math.abs(dy);
        double t = s == 0.0d ? 0.0d : dy / s;
        if (dx < 0.0d) {
            t = 2.0d - t;
        } else if (dy < 0.0d) {
            t += 4.0d;
        }
        return 90.0d * t;
    }

    public static double angle(LinearShape2D obj1, LinearShape2D obj2) {
        return ((obj2.horizontalAngle() - obj1.horizontalAngle()) + M_2PI) % M_2PI;
    }

    public static double angle(Line2D obj1, Line2D obj2) {
        return ((obj2.horizontalAngle() - obj1.horizontalAngle()) + M_2PI) % M_2PI;
    }

    public static double angle(Vector2D vect1, Vector2D vect2) {
        return ((horizontalAngle(vect2) - horizontalAngle(vect1)) + M_2PI) % M_2PI;
    }

    public static double angle(Point2D p1, Point2D p2, Point2D p3) {
        return ((horizontalAngle(p2, p3) - horizontalAngle(p2, p1)) + M_2PI) % M_2PI;
    }

    public static double angle(double x1, double y1, double x2, double y2, double x3, double y3) {
        return ((horizontalAngle(x2, y2, x3, y3) - horizontalAngle(x2, y2, x1, y1)) + M_2PI) % M_2PI;
    }

    public static double absoluteAngle(Point2D p1, Point2D p2, Point2D p3) {
        double angle1 = ((horizontalAngle(new Vector2D(p2, p3)) - horizontalAngle(new Vector2D(p2, p1))) + M_2PI) % M_2PI;
        return angle1 < M_PI ? angle1 : M_2PI - angle1;
    }

    public static double absoluteAngle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double angle1 = ((horizontalAngle(x2, y2, x3, y3) - horizontalAngle(x2, y2, x1, y1)) + M_2PI) % M_2PI;
        return angle1 < M_PI ? angle1 : M_2PI - angle1;
    }

    public static boolean almostEquals(double angle1, double angle2, double eps) {
        double diff = formatAngle(formatAngle(angle1) - formatAngle(angle2));
        if (diff >= eps && Math.abs(diff - M_2PI) >= eps) {
            return false;
        }
        return true;
    }

    public static boolean equals(double angle1, double angle2) {
        double diff = formatAngle(formatAngle(angle1) - formatAngle(angle2));
        if (diff >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE && Math.abs(diff - M_2PI) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    public static boolean containsAngle(double startAngle, double endAngle, double angle) {
        boolean z = false;
        startAngle = formatAngle(startAngle);
        endAngle = formatAngle(endAngle);
        angle = formatAngle(angle);
        if (startAngle >= endAngle) {
            if (EqualUtils.isLE(angle, endAngle) || EqualUtils.isGE(angle, startAngle)) {
                z = true;
            }
            return z;
        } else if (EqualUtils.isGE(angle, startAngle) && EqualUtils.isLE(angle, endAngle)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean containsAngle(double startAngle, double endAngle, double angle, boolean direct) {
        boolean z = false;
        startAngle = formatAngle(startAngle);
        endAngle = formatAngle(endAngle);
        angle = formatAngle(angle);
        if (direct) {
            if (startAngle >= endAngle) {
                if (EqualUtils.isLE(angle, endAngle) || EqualUtils.isGE(angle, startAngle)) {
                    z = true;
                }
                return z;
            } else if (EqualUtils.isGE(angle, startAngle) && EqualUtils.isLE(angle, endAngle)) {
                return true;
            } else {
                return false;
            }
        } else if (startAngle < endAngle) {
            if (EqualUtils.isLE(angle, startAngle) || EqualUtils.isGE(angle, endAngle)) {
                z = true;
            }
            return z;
        } else if (EqualUtils.isGE(angle, endAngle) && EqualUtils.isLE(angle, startAngle)) {
            return true;
        } else {
            return false;
        }
    }
}
