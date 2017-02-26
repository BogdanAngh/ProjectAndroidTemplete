package com.example.duy.calculator.graph;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import java.lang.reflect.Method;

public class Graph3DView extends GLSurfaceView {
    public static Method _getX;
    public static Method _getY;
    public static Method getPointerCount;
    Activity context;
    int height;
    float pinchDist;
    private Graph3DRenderer renderer;
    float startX;
    float startX2;
    float startY;
    float startY2;
    int width;

    class 1 implements Runnable {
        final /* synthetic */ float val$zoom;

        1(float f) {
            this.val$zoom = f;
        }

        public void run() {
            Graph3DRenderer access$000 = Graph3DView.this.renderer;
            access$000.scale *= 1.0f + this.val$zoom;
            Graph3DView.this.renderer.dirty = true;
        }
    }

    static {
        _getX = null;
        _getY = null;
        getPointerCount = null;
        initMultiTouch();
    }

    public Graph3DView(Context c, AttributeSet attrs) {
        super(c, attrs);
        this.startX = -1.0f;
        this.startY = -1.0f;
        this.startX2 = -1.0f;
        this.startY2 = -1.0f;
        this.pinchDist = 0.0f;
        this.context = (Activity) c;
        this.renderer = new Graph3DRenderer(this.context);
        setDisplay();
        setRenderer(this.renderer);
    }

    public Graph3DView(Context c) {
        super(c);
        this.startX = -1.0f;
        this.startY = -1.0f;
        this.startX2 = -1.0f;
        this.startY2 = -1.0f;
        this.pinchDist = 0.0f;
        this.context = (Activity) c;
        this.renderer = new Graph3DRenderer(this.context);
        setDisplay();
        setRenderer(this.renderer);
    }

    private static void initMultiTouch() {
        try {
            _getX = MotionEvent.class.getMethod("getX", new Class[]{Integer.TYPE});
            _getY = MotionEvent.class.getMethod("getY", new Class[]{Integer.TYPE});
            getPointerCount = MotionEvent.class.getMethod("getPointerCount", new Class[0]);
        } catch (Throwable th) {
        }
    }

    public float spacing(MotionEvent event) {
        try {
            Integer arg0 = Integer.valueOf(0);
            Integer arg1 = Integer.valueOf(1);
            float x = ((Float) _getX.invoke(event, new Object[]{arg0})).floatValue() - ((Float) _getX.invoke(event, new Object[]{arg1})).floatValue();
            float y = ((Float) _getY.invoke(event, new Object[]{arg0})).floatValue() - ((Float) _getY.invoke(event, new Object[]{arg1})).floatValue();
            return (float) Math.sqrt((double) ((x * x) + (y * y)));
        } catch (Throwable th) {
            return Float.NaN;
        }
    }

    private void setDisplay() {
        Display display = this.context.getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = (int) (((double) display.getHeight()) * 0.96d);
    }

    public void zoom(float z) {
        queueEvent(new 1(z));
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            if (_getX != null) {
                try {
                    if (((Integer) getPointerCount.invoke(event, new Object[0])).intValue() == 2) {
                        this.pinchDist = spacing(event);
                    } else {
                        this.renderer.stopRotate();
                        this.startX = ((Float) _getX.invoke(event, new Object[]{Integer.valueOf(0)})).floatValue();
                        this.startY = ((Float) _getY.invoke(event, new Object[]{Integer.valueOf(0)})).floatValue();
                    }
                } catch (Throwable th) {
                }
            } else {
                this.renderer.stopRotate();
                this.startX = event.getX();
                this.startY = event.getY();
            }
        } else if (event.getAction() == 2) {
            if (_getX != null) {
                try {
                    if (((Integer) getPointerCount.invoke(event, new Object[0])).intValue() == 2) {
                        float tempDist = spacing(event);
                        if (tempDist > ((float) ((this.width + this.height) / 4)) && this.pinchDist > ((float) ((this.width + this.height) / 4))) {
                            zoom((this.pinchDist - tempDist) / this.pinchDist);
                        }
                        this.pinchDist = tempDist;
                    } else {
                        this.renderer.move(((event.getX() - this.startX) / ((float) this.width)) * 360.0f, ((event.getY() - this.startY) / ((float) this.height)) * 360.0f);
                        this.startX = event.getX();
                        this.startY = event.getY();
                    }
                } catch (IllegalArgumentException e) {
                } catch (Throwable th2) {
                }
            } else {
                this.renderer.move(((event.getX() - this.startX) / ((float) this.width)) * 360.0f, ((event.getY() - this.startY) / ((float) this.height)) * 360.0f);
                this.startX = event.getX();
                this.startY = event.getY();
            }
        } else if (event.getAction() == 1) {
            this.pinchDist = -1.0f;
            this.startX = -1.0f;
            this.startY = -1.0f;
        }
        return true;
    }
}
