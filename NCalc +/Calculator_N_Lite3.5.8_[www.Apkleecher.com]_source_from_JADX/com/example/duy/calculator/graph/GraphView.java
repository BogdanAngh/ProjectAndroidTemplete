package com.example.duy.calculator.graph;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.example.duy.calculator.math_eval.Constants;
import com.getkeepsafe.taptargetview.R;
import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.ParseError;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.BuildConfig;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class GraphView extends View implements OnTouchListener {
    public static final int RECT = 0;
    public static final int THREED = 3;
    public static Method _getX;
    public static Method _getY;
    public static final int[][] colors;
    public static Parser constantParser;
    public static Method getPointerCount;
    private boolean allowMove;
    private boolean choose;
    public Activity context;
    private boolean deriv;
    public double endPolar;
    public double endT;
    public String[] functions;
    public Bitmap graphImage;
    public boolean[] graphable;
    public int height;
    public GraphHelper helper;
    public double initX;
    public double initY;
    public int interA;
    public int interB;
    public double maxX;
    public double maxY;
    public double minX;
    public double minY;
    public int mode;
    public String[] paramX;
    public String[] paramY;
    public float pinchDist;
    private boolean rect;
    public String rk;
    private boolean rkBool;
    public double scaleX;
    public double scaleY;
    public String slope;
    private boolean slopeBool;
    public double startPolar;
    public double startT;
    public float startX;
    public float startY;
    private boolean threeD;
    private boolean trace;
    private double traceDeriv;
    private int traceFun;
    private double tracexVal;
    private double traceyVal;
    public int width;

    private class GraphHelper {
        GraphView graphView;
        Expression[][] paramExp;
        Parser paramParser;
        Variable paramVar;
        Expression[] rectDeriv;
        Expression[] rectExp;
        Expression rk;
        Parser simpParser;
        Variable simpVar;
        Expression slope;
        Parser threeDParser;
        Variable threeDXVar;
        Variable threeDYVar;

        public GraphHelper(GraphView g) {
            this.simpParser = new Parser(1598);
            this.paramParser = new Parser(1598);
            this.threeDParser = new Parser(1598);
            this.rectExp = new Expression[6];
            this.rectDeriv = new Expression[6];
            this.paramExp = (Expression[][]) Array.newInstance(Expression.class, new int[]{2, 6});
            this.threeDXVar = new Variable(UnivPowerSeriesRing.DEFAULT_NAME);
            this.threeDYVar = new Variable(Constants.Y);
            this.simpVar = new Variable(UnivPowerSeriesRing.DEFAULT_NAME);
            this.paramVar = new Variable("t");
            this.graphView = g;
            this.simpParser.add(this.simpVar);
            this.paramParser.add(this.paramVar);
            MathGraph.setUpParser(this.simpParser);
            MathGraph.setUpParser(this.paramParser);
            MathGraph.setUpParser(this.threeDParser);
            this.threeDParser.add(this.threeDXVar);
            this.threeDParser.add(this.threeDYVar);
            for (int i = GraphView.RECT; i < 6; i++) {
                if (g.rect) {
                    try {
                        this.rectExp[i] = this.simpParser.parse(g.functions[i]);
                        g.graphable[i] = true;
                    } catch (ParseError e) {
                        g.graphable[i] = false;
                    }
                    try {
                        this.rectDeriv[i] = this.rectExp[i].derivative(this.simpVar);
                    } catch (NullPointerException e2) {
                    }
                } else {
                    try {
                        this.paramExp[GraphView.RECT][i] = this.paramParser.parse(g.paramX[i]);
                        this.paramExp[1][i] = this.paramParser.parse(g.paramY[i]);
                        g.graphable[i] = true;
                    } catch (ParseError e3) {
                        g.graphable[i] = false;
                    }
                }
            }
            if (g.rect) {
                try {
                    this.rk = this.threeDParser.parse(g.rk);
                } catch (ParseError e4) {
                }
                try {
                    this.slope = this.threeDParser.parse(g.slope);
                } catch (ParseError e5) {
                    this.slope = this.threeDParser.parse("1");
                }
            }
        }

        public double getVal(int i, double val) {
            if (this.graphView.getMode() != 0) {
                return 0.0d;
            }
            this.simpVar.setVal(val);
            return this.rectExp[i].getVal();
        }

        public double getXVal(int i, double val) {
            this.paramVar.setVal(val);
            return this.paramExp[GraphView.RECT][i].getVal();
        }

        public double getYVal(int i, double val) {
            this.paramVar.setVal(val);
            return this.paramExp[1][i].getVal();
        }

        public double getRKVal(double x, double y) {
            this.threeDXVar.setVal(x);
            this.threeDYVar.setVal(y);
            return this.rk.getVal();
        }

        public double getSlopeVal(double x, double y) {
            this.threeDXVar.setVal(x);
            this.threeDYVar.setVal(y);
            return this.slope.getVal();
        }

        public double getDerivative(int i, double val) {
            this.simpVar.setVal(val);
            return this.rectDeriv[i].getVal();
        }
    }

    static {
        colors = new int[][]{new int[]{204, RECT, RECT}, new int[]{R.styleable.AppCompatTheme_buttonStyle, 153, MotionEventCompat.ACTION_MASK}, new int[]{MotionEventCompat.ACTION_MASK, R.styleable.AppCompatTheme_buttonStyle, RECT}, new int[]{RECT, 204, RECT}, new int[]{MotionEventCompat.ACTION_MASK, 204, RECT}, new int[]{RECT, 51, 153}};
        constantParser = new Parser(1566);
        _getX = null;
        _getY = null;
        getPointerCount = null;
        initMultiTouch();
        MathGraph.setUpParser(constantParser);
    }

    public GraphView(int mode, Activity c) {
        super(c);
        this.graphable = new boolean[]{false, false, false, false, false, false};
        this.minX = -3.0d;
        this.maxX = 3.0d;
        this.minY = -5.0d;
        this.maxY = 5.0d;
        this.scaleX = 1.0d;
        this.scaleY = 1.0d;
        this.initX = 0.0d;
        this.initY = 0.0d;
        this.startPolar = -3.141592653589793d;
        this.endPolar = FastMath.PI;
        this.startT = -20.0d;
        this.endT = 20.0d;
        this.traceFun = -1;
        this.slopeBool = false;
        this.rkBool = false;
        this.trace = false;
        this.deriv = false;
        this.choose = false;
        this.rect = true;
        this.threeD = false;
        this.context = c;
        setDisplay();
        setMode(mode);
        importFunctions();
        this.helper = new GraphHelper(this);
    }

    private static void initMultiTouch() {
        try {
            _getX = MotionEvent.class.getMethod("getX", new Class[]{Integer.TYPE});
            _getY = MotionEvent.class.getMethod("getY", new Class[]{Integer.TYPE});
            getPointerCount = MotionEvent.class.getMethod("getPointerCount", new Class[RECT]);
        } catch (Throwable th) {
        }
    }

    private void setDisplay() {
        Display display = this.context.getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = (int) (((double) display.getHeight()) * 0.96d);
    }

    public void importFunctions() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        if (this.rect) {
            this.functions = new String[6];
            this.rk = sp.getString("rkFun", BuildConfig.FLAVOR);
            this.slope = sp.getString("slopeFun", BuildConfig.FLAVOR);
            this.rkBool = MathGraph.isValid(this.rk, new String[]{UnivPowerSeriesRing.DEFAULT_NAME, Constants.Y});
            this.slopeBool = MathGraph.isValid(this.slope, new String[]{UnivPowerSeriesRing.DEFAULT_NAME, Constants.Y});
            try {
                this.initX = constantParser.parse(sp.getString("rkX", BuildConfig.FLAVOR)).getVal();
                this.initY = constantParser.parse(sp.getString("rkY", BuildConfig.FLAVOR)).getVal();
            } catch (ParseError e) {
                this.initX = 0.0d;
                this.initY = 0.0d;
            }
        } else {
            this.paramX = new String[6];
            this.paramY = new String[6];
        }
        for (int i = RECT; i < 6; i++) {
            if (this.rect) {
                this.functions[i] = sp.getString("f" + (i + 1), BuildConfig.FLAVOR);
            } else {
                this.paramX[i] = sp.getString(UnivPowerSeriesRing.DEFAULT_NAME + (i + 1), BuildConfig.FLAVOR);
                this.paramY[i] = sp.getString(Constants.Y + (i + 1), BuildConfig.FLAVOR);
            }
        }
    }

    private void drawDiffy(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(191, 191, 191));
        for (float i = 0.0f; i < ((float) this.width); i += 24.0f) {
            for (float j = 0.0f; j < ((float) this.height); j += 24.0f) {
                float slope = (float) this.helper.getSlopeVal(getX(12.0f + i), getY(12.0f + j));
                Canvas canvas2;
                if (Math.abs(slope) <= 1.0f) {
                    float f = (12.0f + j) - (10.0f * slope);
                    canvas2 = canvas;
                    canvas2.drawLine(i + 2.0f, (10.0f * slope) + (12.0f + j), i + 22.0f, f, paint);
                } else {
                    canvas2 = canvas;
                    canvas2.drawLine((10.0f / slope) + (12.0f + i), j + 2.0f, (12.0f + i) - (10.0f / slope), j + 22.0f, paint);
                }
            }
        }
    }

    public void drawGraph() {
        invalidate();
    }

    private void drawParam(Canvas canvas, double start, double end) {
        Paint paint = new Paint();
        for (int i = RECT; i < 6; i++) {
            if (this.graphable[i]) {
                paint.setColor(Color.rgb(colors[i][RECT], colors[i][1], colors[i][2]));
                double x1 = this.helper.getXVal(i, start);
                double y1 = this.helper.getYVal(i, start);
                double t = start + ((end - start) / 1000.0d);
                while (t < end) {
                    double x0 = x1;
                    double y0 = y1;
                    x1 = this.helper.getXVal(i, t);
                    y1 = this.helper.getYVal(i, t);
                    if (!(y1 == Double.POSITIVE_INFINITY || y0 == Double.POSITIVE_INFINITY || ((y1 < 0.0d && y1 >= 0.0d) || ((y0 < 0.0d && y0 >= 0.0d) || x1 == Double.POSITIVE_INFINITY || x0 == Double.POSITIVE_INFINITY || ((x1 < 0.0d && x1 >= 0.0d) || (x0 < 0.0d && x0 >= 0.0d)))))) {
                        if (Math.abs(x1 - x0) < (this.maxX - this.minX) / 5.0d) {
                            if (Math.abs(x1 - x0) < (this.maxX - this.minX) / 5.0d) {
                                canvas.drawLine((float) getxPixel(x0), (float) getyPixel(y0), (float) getxPixel(x1), (float) getyPixel(y1), paint);
                            }
                        }
                    }
                    t += (end - start) / 1000.0d;
                }
            }
        }
    }

    private void drawPolar(Canvas canvas, double start, double end) {
        Paint paint = new Paint();
        for (int i = RECT; i < 6; i++) {
            if (this.graphable[i]) {
                paint.setColor(Color.rgb(colors[i][RECT], colors[i][1], colors[i][2]));
                double theta = start;
                while (theta < end - ((end - start) / 200.0d)) {
                    double r = this.helper.getVal(i, theta);
                    double newTheta = theta + ((end - start) / 200.0d);
                    double newR = this.helper.getVal(i, newTheta);
                    if (!(r == Double.POSITIVE_INFINITY || newR == Double.POSITIVE_INFINITY || ((r < 0.0d && r >= 0.0d) || (newR < 0.0d && newR >= 0.0d)))) {
                        canvas.drawLine(getPolarX(theta, r), getPolarY(theta, r), getPolarX(newTheta, newR), getPolarY(newTheta, newR), paint);
                    }
                    theta += (end - start) / 200.0d;
                }
            }
        }
    }

    private void drawRect(Canvas canvas) {
        Paint paint = new Paint();
        for (int i = RECT; i < 6; i++) {
            if (this.graphable[i]) {
                paint.setColor(Color.rgb(colors[i][RECT], colors[i][1], colors[i][2]));
                double y2 = this.helper.getVal(i, this.minX);
                for (int j = RECT; j < this.width; j++) {
                    double y1 = y2;
                    double d = (double) this.width;
                    y2 = this.helper.getVal(i, this.minX + (((1.0d + ((double) j)) * (this.maxX - this.minX)) / r0));
                    if (!(y1 == Double.POSITIVE_INFINITY || y2 == Double.POSITIVE_INFINITY || ((y1 < 0.0d && y1 >= 0.0d) || ((y2 < 0.0d && y2 >= 0.0d) || ((y1 > 20.0d && y2 < -20.0d) || (y1 < -20.0d && y2 > 20.0d)))))) {
                        canvas.drawLine((float) j, (float) getyPixel(y1), (float) (j + 1), (float) getyPixel(y2), paint);
                    }
                }
            }
        }
    }

    private void drawRK(Canvas canvas) {
        int i;
        Paint paint = new Paint();
        paint.setARGB(MotionEventCompat.ACTION_MASK, IExpr.SYMBOLID, RECT, IExpr.SYMBOLID);
        double[] k = new double[4];
        double y = this.initY;
        for (i = getxPixel(this.initX); i < this.width; i++) {
            k[RECT] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) i), y);
            k[1] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (((double) i) + 0.5d)), (0.5d * k[RECT]) + y);
            k[2] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (((double) i) + 0.5d)), (0.5d * k[1]) + y);
            k[THREED] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (i + 1)), k[2] + y);
            double tempY = y + ((((k[RECT] + (2.0d * k[1])) + (2.0d * k[2])) + k[THREED]) / 6.0d);
            canvas.drawLine((float) i, (float) getyPixel(y), (float) (i + 1), (float) getyPixel(tempY), paint);
            y = tempY;
        }
        y = this.initY;
        for (i = getxPixel(this.initX); i > 0; i--) {
            k[RECT] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) i), y);
            k[1] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (((double) i) - 0.5d)), (0.5d * k[RECT]) + y);
            k[2] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (((double) i) - 0.5d)), (0.5d * k[1]) + y);
            k[THREED] = ((this.maxX - this.minX) / ((double) this.width)) * this.helper.getRKVal(getX((float) (i - 1)), k[2] + y);
            tempY = y - ((((k[RECT] + (2.0d * k[1])) + (2.0d * k[2])) + k[THREED]) / 6.0d);
            canvas.drawLine((float) i, (float) getyPixel(y), (float) (i - 1), (float) getyPixel(tempY), paint);
            y = tempY;
        }
    }

    private void drawTrace(Canvas canvas) {
        if (this.traceFun != -1) {
            Paint paint = new Paint();
            paint.setColor(Color.rgb(colors[this.traceFun][RECT], colors[this.traceFun][1], colors[this.traceFun][2]));
            paint.setTextSize((float) ((this.width + this.height) / 100));
            double roundX = ((double) Math.round(this.tracexVal * 1000.0d)) / 1000.0d;
            double roundY = ((double) Math.round(this.traceyVal * 10000.0d)) / 10000.0d;
            double roundD = ((double) Math.round(this.traceDeriv * 10000.0d)) / 10000.0d;
            if (this.trace && !this.choose) {
                canvas.drawLine((float) (getxPixel(this.tracexVal) - 8), (float) (getyPixel(this.traceyVal) - 8), (float) (getxPixel(this.tracexVal) + 8), (float) (getyPixel(this.traceyVal) + 8), paint);
                canvas.drawLine((float) (getxPixel(this.tracexVal) + 8), (float) (getyPixel(this.traceyVal) - 8), (float) (getxPixel(this.tracexVal) - 8), (float) (getyPixel(this.traceyVal) + 8), paint);
                paint.setColor(Color.rgb(colors[this.traceFun][RECT], colors[this.traceFun][1], colors[this.traceFun][2]));
                canvas.drawText("f(" + roundX + ")=" + roundY, 50.0f, 50.0f, paint);
            } else if (this.deriv && !this.choose) {
                float f = (float) getxPixel(this.tracexVal - ((this.traceyVal - this.minY) / this.traceDeriv));
                float f2 = (float) this.height;
                double d = this.maxY;
                double d2 = this.traceyVal;
                canvas.drawLine(f, f2, (float) getxPixel(this.tracexVal + ((r0 - r0) / this.traceDeriv)), 0.0f, paint);
                paint.setColor(Color.rgb(colors[this.traceFun][RECT], colors[this.traceFun][1], colors[this.traceFun][2]));
                canvas.drawText("f'(" + roundX + ")=" + roundD, 20.0f, 20.0f, paint);
            }
        }
    }

    public int getMode() {
        return RECT;
    }

    private void setMode(int m) {
        if (m == 0) {
            this.rect = true;
            this.threeD = false;
            return;
        }
        this.rect = false;
        this.threeD = true;
    }

    public double getParamEnd() {
        return this.endT;
    }

    public double getParamStart() {
        return this.startT;
    }

    public double getPolarEnd() {
        return this.endPolar;
    }

    public double getPolarStart() {
        return this.startPolar;
    }

    public float getPolarX(double theta, double r) {
        while (theta > FastMath.PI) {
            theta -= FastMath.PI;
        }
        while (theta < -3.141592653589793d) {
            theta += FastMath.PI;
        }
        return (float) getxPixel(r * Math.cos(theta));
    }

    public float getPolarY(double theta, double r) {
        while (theta > FastMath.PI) {
            theta -= FastMath.PI;
        }
        while (theta < -3.141592653589793d) {
            theta += FastMath.PI;
        }
        return (float) getyPixel(r * Math.sin(theta));
    }

    public double[] getWindow() {
        return new double[]{this.minX, this.maxX, this.minY, this.maxY, this.scaleX, this.scaleY};
    }

    private double getX(float x) {
        return (((double) (x / ((float) this.width))) * (this.maxX - this.minX)) + this.minX;
    }

    private int getxPixel(double x) {
        return (int) ((((double) this.width) * (x - this.minX)) / (this.maxX - this.minX));
    }

    private double getY(float y) {
        return ((((double) (((float) this.height) - y)) * (this.maxY - this.minY)) / ((double) this.height)) + this.minY;
    }

    private int getyPixel(double y) {
        return (int) (((double) this.height) - ((((double) this.height) * (y - this.minY)) / (this.maxY - this.minY)));
    }

    public boolean isEmpty() {
        boolean[] zArr = this.graphable;
        int length = zArr.length;
        for (int i = RECT; i < length; i++) {
            if (zArr[i]) {
                return false;
            }
        }
        return true;
    }

    private void multiTouchMove(MotionEvent event) {
        try {
            double difX = ((this.maxX - this.minX) * ((double) (this.startX - event.getX()))) / ((double) this.width);
            double difY = ((this.maxY - this.minY) * ((double) (event.getY() - this.startY))) / ((double) this.height);
            this.minX += difX;
            this.maxX += difX;
            this.minY += difY;
            this.maxY += difY;
            this.startX = event.getX();
            this.startY = event.getY();
            invalidate();
        } catch (Throwable th) {
        }
    }

    protected void onDraw(Canvas original) {
        this.helper = new GraphHelper(this);
        this.graphImage = Bitmap.createBitmap(this.width, this.height, Config.RGB_565);
        Canvas canvas = new Canvas(this.graphImage);
        Paint paint = new Paint();
        paint.setARGB(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        paint.setTextSize((float) ((this.width + this.height) / 100));
        Paint paint2 = new Paint();
        paint2.setARGB(50, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        int j = RECT;
        if (this.maxY - this.minY > 1.0d) {
            while (Math.log10(this.maxX - this.minX) / Math.log10(5.0d) > ((double) j)) {
                j++;
            }
            j -= 2;
        } else {
            while (Math.log10(this.maxX - this.minX) / Math.log10(5.0d) < ((double) j)) {
                j--;
            }
            j--;
        }
        if (!this.threeD) {
            int i;
            for (i = 1; i < 10; i++) {
                double x1Real = ((double) i) * Math.pow(5.0d, (double) j);
                double x2Real = ((double) (-i)) * Math.pow(5.0d, (double) j);
                int x1 = getxPixel(x1Real);
                int x2 = getxPixel(x2Real);
                int y = getyPixel(0.0d);
                int yText = 18;
                if (y < 0) {
                    y = RECT;
                } else if (y > this.height - 20) {
                    y = this.height;
                    yText = -18;
                }
                Log.e(x1 + BuildConfig.FLAVOR, x2 + BuildConfig.FLAVOR);
                canvas.drawLine((float) x1, 0.0f, (float) x1, (float) this.height, paint2);
                canvas.drawLine((float) x2, 0.0f, (float) x2, (float) this.height, paint2);
                canvas.drawLine((float) x1, (float) (y - 10), (float) x1, (float) (y + 10), paint);
                canvas.drawLine((float) x2, (float) (y - 10), (float) x2, (float) (y + 10), paint);
                canvas.drawText(BuildConfig.FLAVOR + new BigDecimal(x1Real).round(new MathContext(4)).floatValue(), (float) x1, (float) (y + yText), paint);
                canvas.drawText(BuildConfig.FLAVOR + new BigDecimal(x2Real).round(new MathContext(4)).floatValue(), (float) x2, (float) (y + yText), paint);
            }
            j = RECT;
            if (this.maxY - this.minY > 1.0d) {
                while (Math.log10(this.maxY - this.minY) / Math.log10(5.0d) > ((double) j)) {
                    j++;
                }
                j -= 2;
            } else {
                while (Math.log10(this.maxY - this.minY) / Math.log10(5.0d) < ((double) j)) {
                    j--;
                }
                j--;
            }
            for (i = 1; i < 10; i++) {
                int x = getxPixel(0.0d);
                double y1Real = ((double) i) * Math.pow(5.0d, (double) j);
                double y2Real = ((double) (-i)) * Math.pow(5.0d, (double) j);
                int y1 = getyPixel(y1Real);
                int y2 = getyPixel(y2Real);
                int xText = 10;
                if (x < 0) {
                    x = RECT;
                } else if (x > this.width - 20) {
                    x = this.width;
                    xText = -20;
                }
                canvas.drawLine(0.0f, (float) y1, (float) this.width, (float) y1, paint2);
                canvas.drawLine(0.0f, (float) y2, (float) this.width, (float) y2, paint2);
                canvas.drawLine((float) (x - 10), (float) y1, (float) (x + 10), (float) y1, paint);
                canvas.drawLine((float) (x - 10), (float) y2, (float) (x + 10), (float) y2, paint);
                canvas.drawText(BuildConfig.FLAVOR + new BigDecimal(y1Real).round(new MathContext(4)).floatValue(), (float) (x + xText), (float) y1, paint);
                canvas.drawText(BuildConfig.FLAVOR + new BigDecimal(y2Real).round(new MathContext(4)).floatValue(), (float) (x + xText), (float) y2, paint);
            }
            int x0 = getxPixel(0.0d);
            int y0 = getyPixel(0.0d);
            canvas.drawLine((float) x0, 0.0f, (float) x0, (float) this.height, paint);
            canvas.drawLine(0.0f, (float) y0, (float) this.width, (float) y0, paint);
        }
        if (this.rect) {
            drawRect(canvas);
        }
        if (this.rkBool) {
            drawRK(canvas);
        }
        if (!this.choose && (this.trace || this.deriv)) {
            drawTrace(canvas);
        }
        original.drawBitmap(this.graphImage, 0.0f, 0.0f, paint);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return onTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            if (this.trace || this.deriv) {
                if (!this.choose) {
                    this.tracexVal = getX(event.getX());
                    this.traceyVal = this.helper.getVal(this.traceFun, this.tracexVal);
                    this.traceDeriv = this.helper.getDerivative(this.traceFun, this.tracexVal);
                    invalidate();
                }
            } else if (_getX != null) {
                try {
                    if (((Integer) getPointerCount.invoke(event, new Object[RECT])).intValue() == 2) {
                        this.pinchDist = spacing(event);
                    } else {
                        this.allowMove = true;
                        this.startX = ((Float) _getX.invoke(event, new Object[]{Integer.valueOf(RECT)})).floatValue();
                        this.startY = ((Float) _getY.invoke(event, new Object[]{Integer.valueOf(RECT)})).floatValue();
                    }
                } catch (Throwable th) {
                }
            } else {
                this.startX = event.getX();
                this.startY = event.getY();
                this.allowMove = true;
            }
            return true;
        } else if (event.getAction() == 2) {
            if (this.trace || this.deriv) {
                if (!this.choose) {
                    if (this.rect) {
                        this.tracexVal = getX(event.getX());
                        this.traceyVal = this.helper.getVal(this.traceFun, this.tracexVal);
                        this.traceDeriv = this.helper.getDerivative(this.traceFun, this.tracexVal);
                    }
                    invalidate();
                }
            } else if (_getX != null) {
                try {
                    if (((Integer) getPointerCount.invoke(event, new Object[RECT])).intValue() == 2) {
                        float tempDist = spacing(event);
                        if (tempDist > ((float) ((this.width + this.height) / 4)) && this.pinchDist > ((float) ((this.width + this.height) / 4))) {
                            zoom((this.pinchDist - tempDist) / this.pinchDist);
                        }
                        this.pinchDist = tempDist;
                    } else if (this.allowMove) {
                        multiTouchMove(event);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            } else {
                touchMove(event);
            }
            return true;
        } else {
            if (event.getAction() == 1) {
                this.pinchDist = -1.0f;
                this.allowMove = false;
                if (this.choose) {
                    int i;
                    double x = getX(event.getX());
                    double y = getY(event.getY());
                    double[] distance = new double[6];
                    distance[RECT] = Double.MAX_VALUE;
                    for (i = RECT; i < 6; i++) {
                        if (this.graphable[i]) {
                            distance[i] = Math.abs(this.helper.getVal(i, x) - y);
                        }
                    }
                    int smallest = RECT;
                    i = 1;
                    while (i < 6) {
                        if (this.graphable[i] && distance[smallest] > distance[i]) {
                            smallest = i;
                        }
                        i++;
                    }
                    this.traceFun = smallest;
                }
                this.choose = false;
            }
            return false;
        }
    }

    public boolean setDeriv(boolean dr) {
        if (isEmpty()) {
            this.deriv = false;
            return false;
        }
        this.deriv = dr;
        this.choose = true;
        invalidate();
        return true;
    }

    public void setParamBounds(double start, double end) {
        this.startT = start;
        this.endT = end;
    }

    public void setPolarBounds(double start, double end) {
        this.startPolar = start;
        this.endPolar = end;
    }

    public boolean setTrace(boolean tr) {
        if (isEmpty()) {
            this.trace = false;
            return false;
        }
        this.trace = tr;
        this.choose = true;
        invalidate();
        return true;
    }

    public void setWindow(double minx, double miny, double maxx, double maxy, double scalex, double scaley) {
        this.minX = minx;
        this.minY = miny;
        this.maxX = maxx;
        this.maxY = maxy;
        this.scaleX = scalex;
        this.scaleY = scaley;
    }

    public float spacing(MotionEvent event) {
        try {
            Integer arg0 = new Integer(RECT);
            Integer arg1 = new Integer(1);
            float x = ((Float) _getX.invoke(event, new Object[]{arg0})).floatValue() - ((Float) _getX.invoke(event, new Object[]{arg1})).floatValue();
            float y = ((Float) _getY.invoke(event, new Object[]{arg0})).floatValue() - ((Float) _getY.invoke(event, new Object[]{arg1})).floatValue();
            return (float) Math.sqrt((double) ((x * x) + (y * y)));
        } catch (Throwable th) {
            return Float.NaN;
        }
    }

    private void touchMove(MotionEvent event) {
        double difX = ((this.maxX - this.minX) * ((double) (this.startX - event.getX()))) / ((double) this.width);
        double difY = ((this.maxY - this.minY) * ((double) (event.getY() - this.startY))) / ((double) this.height);
        this.minX += difX;
        this.maxX += difX;
        this.minY += difY;
        this.maxY += difY;
        this.startX = event.getX();
        this.startY = event.getY();
        invalidate();
    }

    public void zoom(float perc) {
        double realWidth = this.maxX - this.minX;
        double realHeight = this.maxY - this.minY;
        this.maxX += (((double) perc) * realWidth) / 2.0d;
        this.minX -= (((double) perc) * realWidth) / 2.0d;
        this.minY -= (((double) perc) * realHeight) / 2.0d;
        this.maxY += (((double) perc) * realHeight) / 2.0d;
        this.scaleX += this.scaleX * ((double) perc);
        this.scaleY += this.scaleY * ((double) perc);
        invalidate();
    }

    public Bitmap getBitmap() {
        return this.graphImage;
    }
}
