package com.example.duy.calculator.graph;

import android.app.Activity;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView.Renderer;
import android.preference.PreferenceManager;
import com.example.duy.calculator.math_eval.Constants;
import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;
import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.vector.GenVectorModul;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.apache.commons.math4.util.FastMath;

public class Graph3DRenderer implements Renderer {
    private float alpha;
    FloatBuffer axisBuffer;
    private float beta;
    private Activity context;
    boolean dirty;
    private Expression f;
    private ArrayList<String> functions;
    private float gamma;
    long milliseconds;
    public float newScale;
    private Parser p;
    public float scale;
    private SharedPreferences sp;
    boolean userRotate;
    float[][][][] vertexArray;
    FloatBuffer[][][] vertexBuffer;
    private Variable x;
    private Variable y;

    public Graph3DRenderer(Activity c) {
        this.userRotate = false;
        this.p = new Parser(1598);
        this.x = new Variable(UnivPowerSeriesRing.DEFAULT_NAME);
        this.y = new Variable(Constants.Y);
        this.alpha = 0.0f;
        this.beta = 0.0f;
        this.gamma = 0.0f;
        this.context = c;
        this.scale = 3.0f;
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(16640);
        gl.glMatrixMode(5888);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        gl.glRotatef(this.gamma, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(this.alpha, 1.0f, 1.0f, 0.0f);
        gl.glRotatef(this.beta, 0.0f, 0.0f, 1.0f);
        gl.glEnableClientState(32884);
        if (this.dirty) {
            setUpArray();
            this.dirty = false;
        }
        for (int k = 0; k < this.functions.size(); k++) {
            int[] colors = GraphView.colors[k];
            gl.glColor4f(((float) colors[0]) / 255.0f, ((float) colors[1]) / 255.0f, (float) colors[2], 1.0f);
            for (int i = 0; i <= 30; i++) {
                this.vertexBuffer[k][i][1].position(0);
                gl.glVertexPointer(3, 5126, 0, this.vertexBuffer[k][i][0]);
                gl.glDrawArrays(3, 0, 31);
                this.vertexBuffer[k][i][1].position(0);
                gl.glVertexPointer(3, 5126, 0, this.vertexBuffer[k][i][1]);
                gl.glDrawArrays(3, 0, 31);
            }
        }
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.axisBuffer.position(0);
        gl.glVertexPointer(3, 5126, 0, this.axisBuffer);
        gl.glDrawArrays(3, 0, 31);
        gl.glRotatef(this.gamma * 2.0f, 0.0f, 0.0f, 1.0f);
        gl.glTranslatef(GenVectorModul.DEFAULT_DENSITY, GenVectorModul.DEFAULT_DENSITY, GenVectorModul.DEFAULT_DENSITY);
        gl.glEnableClientState(32884);
        long newSeconds = System.currentTimeMillis();
        if (!this.userRotate) {
            this.gamma += 0.04f * ((float) (newSeconds - this.milliseconds));
        }
        this.milliseconds = newSeconds;
    }

    public void move(float xMove, float yMove) {
        float tempAlpha = this.alpha + yMove;
        float tempBeta = this.beta + yMove;
        this.gamma += (((float) Math.abs(Math.cos(((double) (this.gamma / 180.0f)) * FastMath.PI) / 2.0d)) * yMove) + xMove;
        this.beta = tempBeta;
        this.alpha = tempAlpha;
    }

    public void setUpArray() {
        ByteBuffer bb;
        this.vertexBuffer = (FloatBuffer[][][]) Array.newInstance(FloatBuffer.class, new int[]{this.functions.size(), 31, 2});
        for (int k = 0; k < this.functions.size(); k++) {
            this.f = this.p.parse((String) this.functions.get(k));
            for (int i = 0; i <= 30; i++) {
                int j;
                float[] temp = getDataPoints(true, i);
                bb = ByteBuffer.allocateDirect(temp.length * 4);
                bb.order(ByteOrder.nativeOrder());
                FloatBuffer fbVertices = bb.asFloatBuffer();
                for (j = 0; j < temp.length; j++) {
                    fbVertices.put(j, temp[j]);
                }
                this.vertexBuffer[k][i][0] = fbVertices;
                temp = getDataPoints(false, i);
                bb = ByteBuffer.allocateDirect(temp.length * 4);
                bb.order(ByteOrder.nativeOrder());
                fbVertices = bb.asFloatBuffer();
                for (j = 0; j < temp.length; j++) {
                    fbVertices.put(j, temp[j]);
                }
                this.vertexBuffer[k][i][1] = fbVertices;
            }
        }
        bb = ByteBuffer.allocateDirect(72);
        bb.order(ByteOrder.nativeOrder());
        this.axisBuffer = bb.asFloatBuffer();
        this.axisBuffer.put(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f});
    }

    public void setScale(float s) {
        this.newScale = s;
    }

    public void stopRotate() {
        this.userRotate = true;
    }

    public void spin(float a) {
        this.gamma += a;
    }

    public float[] getDataPoints(boolean isX, int num) {
        float[] vertices = new float[93];
        int j;
        if (isX) {
            float xVal = (float) (-1.25d + (((double) num) * 0.08333333333d));
            this.x.setVal((double) (this.scale * xVal));
            for (j = 0; j <= 30; j++) {
                vertices[j * 3] = xVal;
                vertices[(j * 3) + 2] = (float) (-1.25d + (((double) j) * 0.083333333d));
                this.y.setVal((double) (vertices[(j * 3) + 2] * this.scale));
                vertices[(j * 3) + 1] = ((float) this.f.getVal()) / this.scale;
            }
        } else {
            float yVal = (float) (-1.25d + (((double) num) * 0.0833333333d));
            this.y.setVal((double) (this.scale * yVal));
            for (j = 0; j <= 30; j++) {
                vertices[(j * 3) + 2] = yVal;
                vertices[j * 3] = (float) (-1.25d + (((double) j) * 0.083333333d));
                this.x.setVal((double) (vertices[j * 3] * this.scale));
                vertices[(j * 3) + 1] = ((float) this.f.getVal()) / this.scale;
            }
        }
        return vertices;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = ((float) width) / ((float) height);
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 1.0f, 10.0f);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.p = new Parser(1566);
        this.x = new Variable(UnivPowerSeriesRing.DEFAULT_NAME);
        this.y = new Variable(Constants.Y);
        this.p.add(this.x);
        this.p.add(this.y);
        MathGraph.setUpParser(this.p);
        this.milliseconds = System.currentTimeMillis();
        this.sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.functions = new ArrayList();
        for (int i = 0; i < 6; i++) {
            String s = this.sp.getString("f" + (i + 1), "thisshouldntparse");
            if (MathGraph.isValid(s, new String[]{UnivPowerSeriesRing.DEFAULT_NAME, Constants.Y})) {
                this.functions.add(s);
            }
        }
        setUpArray();
        gl.glDisable(3024);
        gl.glHint(3152, 4353);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(2884);
        gl.glShadeModel(7425);
        gl.glEnable(2929);
    }
}
