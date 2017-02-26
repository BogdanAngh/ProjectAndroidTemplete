package com.badlogic.gdx.backends.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.example.games.basegameutils.GameHelper;
import java.util.ArrayList;

public class AndroidInput implements Input, OnKeyListener, OnTouchListener {
    final float[] f93R;
    public boolean accelerometerAvailable;
    private SensorEventListener accelerometerListener;
    private final float[] accelerometerValues;
    final Application app;
    private float azimuth;
    private boolean catchBack;
    private boolean catchMenu;
    private boolean compassAvailable;
    private SensorEventListener compassListener;
    private final AndroidApplicationConfiguration config;
    final Context context;
    private long currentEventTimeStamp;
    int[] deltaX;
    int[] deltaY;
    private Handler handle;
    final boolean hasMultitouch;
    private float inclination;
    private boolean justTouched;
    ArrayList<KeyEvent> keyEvents;
    ArrayList<OnKeyListener> keyListeners;
    boolean keyboardAvailable;
    private IntMap<Object> keys;
    private final float[] magneticFieldValues;
    private SensorManager manager;
    private final Orientation nativeOrientation;
    private final AndroidOnscreenKeyboard onscreenKeyboard;
    final float[] orientation;
    private float pitch;
    private InputProcessor processor;
    int[] realId;
    boolean requestFocus;
    private float roll;
    private int sleepTime;
    private String text;
    private TextInputListener textListener;
    ArrayList<TouchEvent> touchEvents;
    private final AndroidTouchHandler touchHandler;
    int[] touchX;
    int[] touchY;
    boolean[] touched;
    Pool<KeyEvent> usedKeyEvents;
    Pool<TouchEvent> usedTouchEvents;
    protected final Vibrator vibrator;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3 */
    class C00373 implements Runnable {
        final /* synthetic */ TextInputListener val$listener;
        final /* synthetic */ String val$text;
        final /* synthetic */ String val$title;

        /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.1 */
        class C00321 implements OnClickListener {
            final /* synthetic */ EditText val$input;

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.1.1 */
            class C00311 implements Runnable {
                C00311() {
                }

                public void run() {
                    C00373.this.val$listener.input(C00321.this.val$input.getText().toString());
                }
            }

            C00321(EditText editText) {
                this.val$input = editText;
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                Gdx.app.postRunnable(new C00311());
            }
        }

        /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.2 */
        class C00342 implements OnClickListener {

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.2.1 */
            class C00331 implements Runnable {
                C00331() {
                }

                public void run() {
                    C00373.this.val$listener.canceled();
                }
            }

            C00342() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                Gdx.app.postRunnable(new C00331());
            }
        }

        /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.3 */
        class C00363 implements OnCancelListener {

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.3.3.1 */
            class C00351 implements Runnable {
                C00351() {
                }

                public void run() {
                    C00373.this.val$listener.canceled();
                }
            }

            C00363() {
            }

            public void onCancel(DialogInterface arg0) {
                Gdx.app.postRunnable(new C00351());
            }
        }

        C00373(String str, String str2, TextInputListener textInputListener) {
            this.val$title = str;
            this.val$text = str2;
            this.val$listener = textInputListener;
        }

        public void run() {
            Builder alert = new Builder(AndroidInput.this.context);
            alert.setTitle(this.val$title);
            EditText input = new EditText(AndroidInput.this.context);
            input.setText(this.val$text);
            input.setSingleLine();
            alert.setView(input);
            alert.setPositiveButton("Ok", new C00321(input));
            alert.setNegativeButton("Cancel", new C00342());
            alert.setOnCancelListener(new C00363());
            alert.show();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.4 */
    class C00424 implements Runnable {
        final /* synthetic */ TextInputListener val$listener;
        final /* synthetic */ String val$placeholder;
        final /* synthetic */ String val$title;

        /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.4.1 */
        class C00391 implements OnClickListener {
            final /* synthetic */ EditText val$input;

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.4.1.1 */
            class C00381 implements Runnable {
                C00381() {
                }

                public void run() {
                    C00424.this.val$listener.input(C00391.this.val$input.getText().toString());
                }
            }

            C00391(EditText editText) {
                this.val$input = editText;
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                Gdx.app.postRunnable(new C00381());
            }
        }

        /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.4.2 */
        class C00412 implements OnCancelListener {

            /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.4.2.1 */
            class C00401 implements Runnable {
                C00401() {
                }

                public void run() {
                    C00424.this.val$listener.canceled();
                }
            }

            C00412() {
            }

            public void onCancel(DialogInterface arg0) {
                Gdx.app.postRunnable(new C00401());
            }
        }

        C00424(String str, String str2, TextInputListener textInputListener) {
            this.val$title = str;
            this.val$placeholder = str2;
            this.val$listener = textInputListener;
        }

        public void run() {
            Builder alert = new Builder(AndroidInput.this.context);
            alert.setTitle(this.val$title);
            EditText input = new EditText(AndroidInput.this.context);
            input.setHint(this.val$placeholder);
            input.setSingleLine();
            alert.setView(input);
            alert.setPositiveButton("Ok", new C00391(input));
            alert.setOnCancelListener(new C00412());
            alert.show();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.5 */
    class C00435 implements Runnable {
        final /* synthetic */ boolean val$visible;

        C00435(boolean z) {
            this.val$visible = z;
        }

        public void run() {
            InputMethodManager manager = (InputMethodManager) AndroidInput.this.context.getSystemService("input_method");
            if (this.val$visible) {
                View view = ((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView();
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                manager.showSoftInput(((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView(), 0);
                return;
            }
            manager.hideSoftInputFromWindow(((AndroidGraphics) AndroidInput.this.app.getGraphics()).getView().getWindowToken(), 0);
        }
    }

    static class KeyEvent {
        static final int KEY_DOWN = 0;
        static final int KEY_TYPED = 2;
        static final int KEY_UP = 1;
        char keyChar;
        int keyCode;
        long timeStamp;
        int type;

        KeyEvent() {
        }
    }

    private class SensorListener implements SensorEventListener {
        final float[] accelerometerValues;
        final float[] magneticFieldValues;
        final Orientation nativeOrientation;

        SensorListener(Orientation nativeOrientation, float[] accelerometerValues, float[] magneticFieldValues) {
            this.accelerometerValues = accelerometerValues;
            this.magneticFieldValues = magneticFieldValues;
            this.nativeOrientation = nativeOrientation;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == 1) {
                if (this.nativeOrientation == Orientation.Portrait) {
                    System.arraycopy(event.values, 0, this.accelerometerValues, 0, this.accelerometerValues.length);
                } else {
                    this.accelerometerValues[0] = event.values[1];
                    this.accelerometerValues[1] = -event.values[0];
                    this.accelerometerValues[2] = event.values[2];
                }
            }
            if (event.sensor.getType() == 2) {
                System.arraycopy(event.values, 0, this.magneticFieldValues, 0, this.magneticFieldValues.length);
            }
        }
    }

    static class TouchEvent {
        static final int TOUCH_DOWN = 0;
        static final int TOUCH_DRAGGED = 2;
        static final int TOUCH_UP = 1;
        int pointer;
        long timeStamp;
        int type;
        int f35x;
        int f36y;

        TouchEvent() {
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.1 */
    class C03461 extends Pool<KeyEvent> {
        C03461(int x0, int x1) {
            super(x0, x1);
        }

        protected KeyEvent newObject() {
            return new KeyEvent();
        }
    }

    /* renamed from: com.badlogic.gdx.backends.android.AndroidInput.2 */
    class C03472 extends Pool<TouchEvent> {
        C03472(int x0, int x1) {
            super(x0, x1);
        }

        protected TouchEvent newObject() {
            return new TouchEvent();
        }
    }

    public AndroidInput(Application activity, Context context, Object view, AndroidApplicationConfiguration config) {
        this.usedKeyEvents = new C03461(16, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
        this.usedTouchEvents = new C03472(16, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
        this.keyListeners = new ArrayList();
        this.keyEvents = new ArrayList();
        this.touchEvents = new ArrayList();
        this.touchX = new int[20];
        this.touchY = new int[20];
        this.deltaX = new int[20];
        this.deltaY = new int[20];
        this.touched = new boolean[20];
        this.realId = new int[10];
        this.keys = new IntMap();
        this.accelerometerAvailable = false;
        this.accelerometerValues = new float[3];
        this.text = null;
        this.textListener = null;
        this.sleepTime = 0;
        this.catchBack = false;
        this.catchMenu = false;
        this.compassAvailable = false;
        this.magneticFieldValues = new float[3];
        this.azimuth = 0.0f;
        this.pitch = 0.0f;
        this.roll = 0.0f;
        this.inclination = 0.0f;
        this.justTouched = false;
        this.currentEventTimeStamp = System.nanoTime();
        this.requestFocus = true;
        this.f93R = new float[9];
        this.orientation = new float[3];
        if (view instanceof View) {
            View v = (View) view;
            v.setOnKeyListener(this);
            v.setOnTouchListener(this);
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            v.requestFocus();
            v.requestFocusFromTouch();
        }
        this.config = config;
        this.onscreenKeyboard = new AndroidOnscreenKeyboard(context, new Handler(), this);
        for (int i = 0; i < this.realId.length; i++) {
            this.realId[i] = -1;
        }
        this.handle = new Handler();
        this.app = activity;
        this.context = context;
        this.sleepTime = config.touchSleepTime;
        if (Integer.parseInt(VERSION.SDK) >= 5) {
            this.touchHandler = new AndroidMultiTouchHandler();
        } else {
            this.touchHandler = new AndroidSingleTouchHandler();
        }
        this.hasMultitouch = this.touchHandler.supportsMultitouch(context);
        this.vibrator = (Vibrator) context.getSystemService("vibrator");
        int rotation = getRotation();
        DisplayMode mode = this.app.getGraphics().getDesktopDisplayMode();
        if (((rotation == 0 || rotation == 180) && mode.width >= mode.height) || ((rotation == 90 || rotation == 270) && mode.width <= mode.height)) {
            this.nativeOrientation = Orientation.Landscape;
        } else {
            this.nativeOrientation = Orientation.Portrait;
        }
    }

    public float getAccelerometerX() {
        return this.accelerometerValues[0];
    }

    public float getAccelerometerY() {
        return this.accelerometerValues[1];
    }

    public float getAccelerometerZ() {
        return this.accelerometerValues[2];
    }

    public void getTextInput(TextInputListener listener, String title, String text) {
        this.handle.post(new C00373(title, text, listener));
    }

    public void getPlaceholderTextInput(TextInputListener listener, String title, String placeholder) {
        this.handle.post(new C00424(title, placeholder, listener));
    }

    public int getX() {
        int i;
        synchronized (this) {
            i = this.touchX[0];
        }
        return i;
    }

    public int getY() {
        int i;
        synchronized (this) {
            i = this.touchY[0];
        }
        return i;
    }

    public int getX(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchX[pointer];
        }
        return i;
    }

    public int getY(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchY[pointer];
        }
        return i;
    }

    public boolean isTouched(int pointer) {
        boolean z;
        synchronized (this) {
            z = this.touched[pointer];
        }
        return z;
    }

    public boolean isKeyPressed(int key) {
        boolean z;
        synchronized (this) {
            if (key == -1) {
                z = this.keys.size > 0;
            } else {
                z = this.keys.containsKey(key);
            }
        }
        return z;
    }

    public boolean isTouched() {
        boolean z;
        synchronized (this) {
            z = this.touched[0];
        }
        return z;
    }

    public void setInputProcessor(InputProcessor processor) {
        synchronized (this) {
            this.processor = processor;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void processEvents() {
        /*
        r8 = this;
        monitor-enter(r8);
        r4 = 0;
        r8.justTouched = r4;	 Catch:{ all -> 0x0032 }
        r4 = r8.processor;	 Catch:{ all -> 0x0032 }
        if (r4 == 0) goto L_0x0086;
    L_0x0008:
        r3 = r8.processor;	 Catch:{ all -> 0x0032 }
        r4 = r8.keyEvents;	 Catch:{ all -> 0x0032 }
        r2 = r4.size();	 Catch:{ all -> 0x0032 }
        r1 = 0;
    L_0x0011:
        if (r1 >= r2) goto L_0x0041;
    L_0x0013:
        r4 = r8.keyEvents;	 Catch:{ all -> 0x0032 }
        r0 = r4.get(r1);	 Catch:{ all -> 0x0032 }
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r0;	 Catch:{ all -> 0x0032 }
        r4 = r0.timeStamp;	 Catch:{ all -> 0x0032 }
        r8.currentEventTimeStamp = r4;	 Catch:{ all -> 0x0032 }
        r4 = r0.type;	 Catch:{ all -> 0x0032 }
        switch(r4) {
            case 0: goto L_0x002c;
            case 1: goto L_0x0035;
            case 2: goto L_0x003b;
            default: goto L_0x0024;
        };	 Catch:{ all -> 0x0032 }
    L_0x0024:
        r4 = r8.usedKeyEvents;	 Catch:{ all -> 0x0032 }
        r4.free(r0);	 Catch:{ all -> 0x0032 }
        r1 = r1 + 1;
        goto L_0x0011;
    L_0x002c:
        r4 = r0.keyCode;	 Catch:{ all -> 0x0032 }
        r3.keyDown(r4);	 Catch:{ all -> 0x0032 }
        goto L_0x0024;
    L_0x0032:
        r4 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0032 }
        throw r4;
    L_0x0035:
        r4 = r0.keyCode;	 Catch:{ all -> 0x0032 }
        r3.keyUp(r4);	 Catch:{ all -> 0x0032 }
        goto L_0x0024;
    L_0x003b:
        r4 = r0.keyChar;	 Catch:{ all -> 0x0032 }
        r3.keyTyped(r4);	 Catch:{ all -> 0x0032 }
        goto L_0x0024;
    L_0x0041:
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r2 = r4.size();	 Catch:{ all -> 0x0032 }
        r1 = 0;
    L_0x0048:
        if (r1 >= r2) goto L_0x00bd;
    L_0x004a:
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r0 = r4.get(r1);	 Catch:{ all -> 0x0032 }
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.TouchEvent) r0;	 Catch:{ all -> 0x0032 }
        r4 = r0.timeStamp;	 Catch:{ all -> 0x0032 }
        r8.currentEventTimeStamp = r4;	 Catch:{ all -> 0x0032 }
        r4 = r0.type;	 Catch:{ all -> 0x0032 }
        switch(r4) {
            case 0: goto L_0x0063;
            case 1: goto L_0x0071;
            case 2: goto L_0x007c;
            default: goto L_0x005b;
        };	 Catch:{ all -> 0x0032 }
    L_0x005b:
        r4 = r8.usedTouchEvents;	 Catch:{ all -> 0x0032 }
        r4.free(r0);	 Catch:{ all -> 0x0032 }
        r1 = r1 + 1;
        goto L_0x0048;
    L_0x0063:
        r4 = r0.f35x;	 Catch:{ all -> 0x0032 }
        r5 = r0.f36y;	 Catch:{ all -> 0x0032 }
        r6 = r0.pointer;	 Catch:{ all -> 0x0032 }
        r7 = 0;
        r3.touchDown(r4, r5, r6, r7);	 Catch:{ all -> 0x0032 }
        r4 = 1;
        r8.justTouched = r4;	 Catch:{ all -> 0x0032 }
        goto L_0x005b;
    L_0x0071:
        r4 = r0.f35x;	 Catch:{ all -> 0x0032 }
        r5 = r0.f36y;	 Catch:{ all -> 0x0032 }
        r6 = r0.pointer;	 Catch:{ all -> 0x0032 }
        r7 = 0;
        r3.touchUp(r4, r5, r6, r7);	 Catch:{ all -> 0x0032 }
        goto L_0x005b;
    L_0x007c:
        r4 = r0.f35x;	 Catch:{ all -> 0x0032 }
        r5 = r0.f36y;	 Catch:{ all -> 0x0032 }
        r6 = r0.pointer;	 Catch:{ all -> 0x0032 }
        r3.touchDragged(r4, r5, r6);	 Catch:{ all -> 0x0032 }
        goto L_0x005b;
    L_0x0086:
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r2 = r4.size();	 Catch:{ all -> 0x0032 }
        r1 = 0;
    L_0x008d:
        if (r1 >= r2) goto L_0x00a6;
    L_0x008f:
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r0 = r4.get(r1);	 Catch:{ all -> 0x0032 }
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.TouchEvent) r0;	 Catch:{ all -> 0x0032 }
        r4 = r0.type;	 Catch:{ all -> 0x0032 }
        if (r4 != 0) goto L_0x009e;
    L_0x009b:
        r4 = 1;
        r8.justTouched = r4;	 Catch:{ all -> 0x0032 }
    L_0x009e:
        r4 = r8.usedTouchEvents;	 Catch:{ all -> 0x0032 }
        r4.free(r0);	 Catch:{ all -> 0x0032 }
        r1 = r1 + 1;
        goto L_0x008d;
    L_0x00a6:
        r4 = r8.keyEvents;	 Catch:{ all -> 0x0032 }
        r2 = r4.size();	 Catch:{ all -> 0x0032 }
        r1 = 0;
    L_0x00ad:
        if (r1 >= r2) goto L_0x00bd;
    L_0x00af:
        r4 = r8.usedKeyEvents;	 Catch:{ all -> 0x0032 }
        r5 = r8.keyEvents;	 Catch:{ all -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ all -> 0x0032 }
        r4.free(r5);	 Catch:{ all -> 0x0032 }
        r1 = r1 + 1;
        goto L_0x00ad;
    L_0x00bd:
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r4 = r4.size();	 Catch:{ all -> 0x0032 }
        if (r4 != 0) goto L_0x00da;
    L_0x00c5:
        r1 = 0;
    L_0x00c6:
        r4 = r8.deltaX;	 Catch:{ all -> 0x0032 }
        r4 = r4.length;	 Catch:{ all -> 0x0032 }
        if (r1 >= r4) goto L_0x00da;
    L_0x00cb:
        r4 = r8.deltaX;	 Catch:{ all -> 0x0032 }
        r5 = 0;
        r6 = 0;
        r4[r5] = r6;	 Catch:{ all -> 0x0032 }
        r4 = r8.deltaY;	 Catch:{ all -> 0x0032 }
        r5 = 0;
        r6 = 0;
        r4[r5] = r6;	 Catch:{ all -> 0x0032 }
        r1 = r1 + 1;
        goto L_0x00c6;
    L_0x00da:
        r4 = r8.keyEvents;	 Catch:{ all -> 0x0032 }
        r4.clear();	 Catch:{ all -> 0x0032 }
        r4 = r8.touchEvents;	 Catch:{ all -> 0x0032 }
        r4.clear();	 Catch:{ all -> 0x0032 }
        monitor-exit(r8);	 Catch:{ all -> 0x0032 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidInput.processEvents():void");
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (this.requestFocus && view != null) {
            view.requestFocus();
            view.requestFocusFromTouch();
            this.requestFocus = false;
        }
        this.touchHandler.onTouch(event, this);
        if (this.sleepTime != 0) {
            try {
                Thread.sleep((long) this.sleepTime);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    public void onTap(int x, int y) {
        postTap(x, y);
    }

    public void onDrop(int x, int y) {
        postTap(x, y);
    }

    protected void postTap(int x, int y) {
        synchronized (this) {
            TouchEvent event = (TouchEvent) this.usedTouchEvents.obtain();
            event.timeStamp = System.nanoTime();
            event.pointer = 0;
            event.f35x = x;
            event.f36y = y;
            event.type = 0;
            this.touchEvents.add(event);
            event = (TouchEvent) this.usedTouchEvents.obtain();
            event.timeStamp = System.nanoTime();
            event.pointer = 0;
            event.f35x = x;
            event.f36y = y;
            event.type = 1;
            this.touchEvents.add(event);
        }
        Gdx.app.getGraphics().requestRendering();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onKey(android.view.View r14, int r15, android.view.KeyEvent r16) {
        /*
        r13 = this;
        r12 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r11 = 4;
        r9 = 2;
        r7 = 1;
        r8 = 0;
        r4 = 0;
        r6 = r13.keyListeners;
        r5 = r6.size();
    L_0x000d:
        if (r4 >= r5) goto L_0x0024;
    L_0x000f:
        r6 = r13.keyListeners;
        r6 = r6.get(r4);
        r6 = (android.view.View.OnKeyListener) r6;
        r0 = r16;
        r6 = r6.onKey(r14, r15, r0);
        if (r6 == 0) goto L_0x0021;
    L_0x001f:
        r6 = r7;
    L_0x0020:
        return r6;
    L_0x0021:
        r4 = r4 + 1;
        goto L_0x000d;
    L_0x0024:
        monitor-enter(r13);
        r3 = 0;
        r6 = r16.getKeyCode();	 Catch:{ all -> 0x00ae }
        if (r6 != 0) goto L_0x005e;
    L_0x002c:
        r6 = r16.getAction();	 Catch:{ all -> 0x00ae }
        if (r6 != r9) goto L_0x005e;
    L_0x0032:
        r2 = r16.getCharacters();	 Catch:{ all -> 0x00ae }
        r4 = 0;
    L_0x0037:
        r6 = r2.length();	 Catch:{ all -> 0x00ae }
        if (r4 >= r6) goto L_0x005b;
    L_0x003d:
        r6 = r13.usedKeyEvents;	 Catch:{ all -> 0x00ae }
        r6 = r6.obtain();	 Catch:{ all -> 0x00ae }
        r0 = r6;
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r0;	 Catch:{ all -> 0x00ae }
        r3 = r0;
        r6 = 0;
        r3.keyCode = r6;	 Catch:{ all -> 0x00ae }
        r6 = r2.charAt(r4);	 Catch:{ all -> 0x00ae }
        r3.keyChar = r6;	 Catch:{ all -> 0x00ae }
        r6 = 2;
        r3.type = r6;	 Catch:{ all -> 0x00ae }
        r6 = r13.keyEvents;	 Catch:{ all -> 0x00ae }
        r6.add(r3);	 Catch:{ all -> 0x00ae }
        r4 = r4 + 1;
        goto L_0x0037;
    L_0x005b:
        monitor-exit(r13);	 Catch:{ all -> 0x00ae }
        r6 = r8;
        goto L_0x0020;
    L_0x005e:
        r6 = r16.getUnicodeChar();	 Catch:{ all -> 0x00ae }
        r1 = (char) r6;	 Catch:{ all -> 0x00ae }
        r6 = 67;
        if (r15 != r6) goto L_0x0069;
    L_0x0067:
        r1 = 8;
    L_0x0069:
        r6 = r16.getAction();	 Catch:{ all -> 0x00ae }
        switch(r6) {
            case 0: goto L_0x007e;
            case 1: goto L_0x00b1;
            default: goto L_0x0070;
        };	 Catch:{ all -> 0x00ae }
    L_0x0070:
        r6 = r13.app;	 Catch:{ all -> 0x00ae }
        r6 = r6.getGraphics();	 Catch:{ all -> 0x00ae }
        r6.requestRendering();	 Catch:{ all -> 0x00ae }
        monitor-exit(r13);	 Catch:{ all -> 0x00ae }
        if (r15 != r12) goto L_0x0105;
    L_0x007c:
        r6 = r7;
        goto L_0x0020;
    L_0x007e:
        r6 = r13.usedKeyEvents;	 Catch:{ all -> 0x00ae }
        r6 = r6.obtain();	 Catch:{ all -> 0x00ae }
        r0 = r6;
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r0;	 Catch:{ all -> 0x00ae }
        r3 = r0;
        r6 = 0;
        r3.keyChar = r6;	 Catch:{ all -> 0x00ae }
        r6 = r16.getKeyCode();	 Catch:{ all -> 0x00ae }
        r3.keyCode = r6;	 Catch:{ all -> 0x00ae }
        r6 = 0;
        r3.type = r6;	 Catch:{ all -> 0x00ae }
        if (r15 != r11) goto L_0x00a0;
    L_0x0096:
        r6 = r16.isAltPressed();	 Catch:{ all -> 0x00ae }
        if (r6 == 0) goto L_0x00a0;
    L_0x009c:
        r15 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r3.keyCode = r15;	 Catch:{ all -> 0x00ae }
    L_0x00a0:
        r6 = r13.keyEvents;	 Catch:{ all -> 0x00ae }
        r6.add(r3);	 Catch:{ all -> 0x00ae }
        r6 = r13.keys;	 Catch:{ all -> 0x00ae }
        r9 = r3.keyCode;	 Catch:{ all -> 0x00ae }
        r10 = 0;
        r6.put(r9, r10);	 Catch:{ all -> 0x00ae }
        goto L_0x0070;
    L_0x00ae:
        r6 = move-exception;
        monitor-exit(r13);	 Catch:{ all -> 0x00ae }
        throw r6;
    L_0x00b1:
        r6 = r13.usedKeyEvents;	 Catch:{ all -> 0x00ae }
        r6 = r6.obtain();	 Catch:{ all -> 0x00ae }
        r0 = r6;
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r0;	 Catch:{ all -> 0x00ae }
        r3 = r0;
        r6 = 0;
        r3.keyChar = r6;	 Catch:{ all -> 0x00ae }
        r6 = r16.getKeyCode();	 Catch:{ all -> 0x00ae }
        r3.keyCode = r6;	 Catch:{ all -> 0x00ae }
        r6 = 1;
        r3.type = r6;	 Catch:{ all -> 0x00ae }
        if (r15 != r11) goto L_0x00d3;
    L_0x00c9:
        r6 = r16.isAltPressed();	 Catch:{ all -> 0x00ae }
        if (r6 == 0) goto L_0x00d3;
    L_0x00cf:
        r15 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r3.keyCode = r15;	 Catch:{ all -> 0x00ae }
    L_0x00d3:
        r6 = r13.keyEvents;	 Catch:{ all -> 0x00ae }
        r6.add(r3);	 Catch:{ all -> 0x00ae }
        r6 = r13.usedKeyEvents;	 Catch:{ all -> 0x00ae }
        r6 = r6.obtain();	 Catch:{ all -> 0x00ae }
        r0 = r6;
        r0 = (com.badlogic.gdx.backends.android.AndroidInput.KeyEvent) r0;	 Catch:{ all -> 0x00ae }
        r3 = r0;
        r3.keyChar = r1;	 Catch:{ all -> 0x00ae }
        r6 = 0;
        r3.keyCode = r6;	 Catch:{ all -> 0x00ae }
        r6 = 2;
        r3.type = r6;	 Catch:{ all -> 0x00ae }
        r6 = r13.keyEvents;	 Catch:{ all -> 0x00ae }
        r6.add(r3);	 Catch:{ all -> 0x00ae }
        if (r15 != r12) goto L_0x00fa;
    L_0x00f1:
        r6 = r13.keys;	 Catch:{ all -> 0x00ae }
        r9 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r6.remove(r9);	 Catch:{ all -> 0x00ae }
        goto L_0x0070;
    L_0x00fa:
        r6 = r13.keys;	 Catch:{ all -> 0x00ae }
        r9 = r16.getKeyCode();	 Catch:{ all -> 0x00ae }
        r6.remove(r9);	 Catch:{ all -> 0x00ae }
        goto L_0x0070;
    L_0x0105:
        r6 = r13.catchBack;
        if (r6 == 0) goto L_0x010e;
    L_0x0109:
        if (r15 != r11) goto L_0x010e;
    L_0x010b:
        r6 = r7;
        goto L_0x0020;
    L_0x010e:
        r6 = r13.catchMenu;
        if (r6 == 0) goto L_0x0119;
    L_0x0112:
        r6 = 82;
        if (r15 != r6) goto L_0x0119;
    L_0x0116:
        r6 = r7;
        goto L_0x0020;
    L_0x0119:
        r6 = r8;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidInput.onKey(android.view.View, int, android.view.KeyEvent):boolean");
    }

    public void setOnscreenKeyboardVisible(boolean visible) {
        this.handle.post(new C00435(visible));
    }

    public void setCatchBackKey(boolean catchBack) {
        this.catchBack = catchBack;
    }

    public void setCatchMenuKey(boolean catchMenu) {
        this.catchMenu = catchMenu;
    }

    public void vibrate(int milliseconds) {
        this.vibrator.vibrate((long) milliseconds);
    }

    public void vibrate(long[] pattern, int repeat) {
        this.vibrator.vibrate(pattern, repeat);
    }

    public void cancelVibrate() {
        this.vibrator.cancel();
    }

    public boolean justTouched() {
        return this.justTouched;
    }

    public boolean isButtonPressed(int button) {
        if (button == 0) {
            return isTouched();
        }
        return false;
    }

    private void updateOrientation() {
        if (SensorManager.getRotationMatrix(this.f93R, null, this.accelerometerValues, this.magneticFieldValues)) {
            SensorManager.getOrientation(this.f93R, this.orientation);
            this.azimuth = (float) Math.toDegrees((double) this.orientation[0]);
            this.pitch = (float) Math.toDegrees((double) this.orientation[1]);
            this.roll = (float) Math.toDegrees((double) this.orientation[2]);
        }
    }

    public void getRotationMatrix(float[] matrix) {
        SensorManager.getRotationMatrix(matrix, null, this.accelerometerValues, this.magneticFieldValues);
    }

    public float getAzimuth() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.azimuth;
    }

    public float getPitch() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.pitch;
    }

    public float getRoll() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.roll;
    }

    void registerSensorListeners() {
        if (this.config.useAccelerometer) {
            this.manager = (SensorManager) this.context.getSystemService("sensor");
            if (this.manager.getSensorList(1).size() == 0) {
                this.accelerometerAvailable = false;
            } else {
                Sensor accelerometer = (Sensor) this.manager.getSensorList(1).get(0);
                this.accelerometerListener = new SensorListener(this.nativeOrientation, this.accelerometerValues, this.magneticFieldValues);
                this.accelerometerAvailable = this.manager.registerListener(this.accelerometerListener, accelerometer, 1);
            }
        } else {
            this.accelerometerAvailable = false;
        }
        if (this.config.useCompass) {
            if (this.manager == null) {
                this.manager = (SensorManager) this.context.getSystemService("sensor");
            }
            Sensor sensor = this.manager.getDefaultSensor(2);
            if (sensor != null) {
                this.compassAvailable = this.accelerometerAvailable;
                if (this.compassAvailable) {
                    this.compassListener = new SensorListener(this.nativeOrientation, this.accelerometerValues, this.magneticFieldValues);
                    this.compassAvailable = this.manager.registerListener(this.compassListener, sensor, 1);
                }
            } else {
                this.compassAvailable = false;
            }
        } else {
            this.compassAvailable = false;
        }
        Gdx.app.log("AndroidInput", "sensor listener setup");
    }

    void unregisterSensorListeners() {
        if (this.manager != null) {
            if (this.accelerometerListener != null) {
                this.manager.unregisterListener(this.accelerometerListener);
                this.accelerometerListener = null;
            }
            if (this.compassListener != null) {
                this.manager.unregisterListener(this.compassListener);
                this.compassListener = null;
            }
            this.manager = null;
        }
        Gdx.app.log("AndroidInput", "sensor listener tear down");
    }

    public InputProcessor getInputProcessor() {
        return this.processor;
    }

    public boolean isPeripheralAvailable(Peripheral peripheral) {
        if (peripheral == Peripheral.Accelerometer) {
            return this.accelerometerAvailable;
        }
        if (peripheral == Peripheral.Compass) {
            return this.compassAvailable;
        }
        if (peripheral == Peripheral.HardwareKeyboard) {
            return this.keyboardAvailable;
        }
        if (peripheral == Peripheral.OnscreenKeyboard) {
            return true;
        }
        if (peripheral != Peripheral.Vibrator) {
            return peripheral == Peripheral.MultitouchScreen ? this.hasMultitouch : false;
        } else {
            if (this.vibrator == null) {
                return false;
            }
            return true;
        }
    }

    public int getFreePointerIndex() {
        int len = this.realId.length;
        for (int i = 0; i < len; i++) {
            if (this.realId[i] == -1) {
                return i;
            }
        }
        int[] tmp = new int[(this.realId.length + 1)];
        System.arraycopy(this.realId, 0, tmp, 0, this.realId.length);
        this.realId = tmp;
        return tmp.length - 1;
    }

    public int lookUpPointerIndex(int pointerId) {
        int i;
        int len = this.realId.length;
        for (i = 0; i < len; i++) {
            if (this.realId[i] == pointerId) {
                return i;
            }
        }
        StringBuffer buf = new StringBuffer();
        for (i = 0; i < len; i++) {
            buf.append(i + ":" + this.realId[i] + " ");
        }
        Gdx.app.log("AndroidInput", "Pointer ID lookup failed: " + pointerId + ", " + buf.toString());
        return -1;
    }

    public int getRotation() {
        int orientation;
        if (this.context instanceof Activity) {
            orientation = ((Activity) this.context).getWindowManager().getDefaultDisplay().getOrientation();
        } else {
            orientation = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getOrientation();
        }
        switch (orientation) {
            case GameHelper.CLIENT_NONE /*0*/:
                return 0;
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return 90;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return 180;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return 270;
            default:
                return 0;
        }
    }

    public Orientation getNativeOrientation() {
        return this.nativeOrientation;
    }

    public void setCursorCatched(boolean catched) {
    }

    public boolean isCursorCatched() {
        return false;
    }

    public int getDeltaX() {
        return this.deltaX[0];
    }

    public int getDeltaX(int pointer) {
        return this.deltaX[pointer];
    }

    public int getDeltaY() {
        return this.deltaY[0];
    }

    public int getDeltaY(int pointer) {
        return this.deltaY[pointer];
    }

    public void setCursorPosition(int x, int y) {
    }

    public void setCursorImage(Pixmap pixmap, int xHotspot, int yHotspot) {
    }

    public long getCurrentEventTime() {
        return this.currentEventTimeStamp;
    }

    public void addKeyListener(OnKeyListener listener) {
        this.keyListeners.add(listener);
    }
}
