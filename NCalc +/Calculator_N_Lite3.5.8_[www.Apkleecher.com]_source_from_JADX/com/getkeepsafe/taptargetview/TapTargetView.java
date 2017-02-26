package com.getkeepsafe.taptargetview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import edu.jas.vector.GenVectorModul;

@SuppressLint({"ViewConstructor"})
public class TapTargetView extends View {
    private static final int UNSET_COLOR = -1;
    final int CIRCLE_PADDING;
    final int GUTTER_DIM;
    final int SHADOW_DIM;
    final int TARGET_PADDING;
    final int TARGET_PULSE_RADIUS;
    final int TARGET_RADIUS;
    final int TEXT_PADDING;
    final int TEXT_SPACING;
    private ValueAnimator[] animators;
    int bottomBoundary;
    @Nullable
    final ViewGroup boundingParent;
    int calculatedOuterCircleRadius;
    boolean cancelable;
    boolean debug;
    final Paint debugPaint;
    @Nullable
    CharSequence description;
    @Nullable
    StaticLayout descriptionLayout;
    final TextPaint descriptionPaint;
    int dimColor;
    final ValueAnimator dismissAnimation;
    private final ValueAnimator dismissConfirmAnimation;
    Rect drawingBounds;
    final ValueAnimator expandAnimation;
    final UpdateListener expandContractUpdateListener;
    boolean isDark;
    private boolean isDismissed;
    private boolean isInteractable;
    float lastTouchX;
    float lastTouchY;
    Listener listener;
    int outerCircleAlpha;
    int[] outerCircleCenter;
    final Paint outerCirclePaint;
    Path outerCirclePath;
    float outerCircleRadius;
    final Paint outerCircleShadowPaint;
    @Nullable
    ViewOutlineProvider outlineProvider;
    final ViewManager parent;
    final ValueAnimator pulseAnimation;
    boolean shouldDrawShadow;
    boolean shouldTintTarget;
    final TapTarget target;
    final Rect targetBounds;
    int targetCircleAlpha;
    final Paint targetCirclePaint;
    int targetCirclePulseAlpha;
    final Paint targetCirclePulsePaint;
    float targetCirclePulseRadius;
    float targetCircleRadius;
    int textAlpha;
    Rect textBounds;
    Bitmap tintedTarget;
    CharSequence title;
    StaticLayout titleLayout;
    final TextPaint titlePaint;
    int topBoundary;
    boolean visible;

    public static class Listener {
        public void onTargetClick(TapTargetView view) {
            view.dismiss(true);
        }

        public void onTargetLongClick(TapTargetView view) {
            onTargetClick(view);
        }

        public void onTargetCancel(TapTargetView view) {
            view.dismiss(false);
        }

        public void onOuterCircleClick(TapTargetView view) {
        }

        public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
        }
    }

    class 10 implements OnClickListener {
        10() {
        }

        public void onClick(View v) {
            boolean clickedInsideOfOuterCircle = true;
            if (TapTargetView.this.listener != null && TapTargetView.this.outerCircleCenter != null && TapTargetView.this.isInteractable) {
                boolean clickedInTarget = TapTargetView.this.targetBounds.contains((int) TapTargetView.this.lastTouchX, (int) TapTargetView.this.lastTouchY);
                if (TapTargetView.this.distance(TapTargetView.this.outerCircleCenter[0], TapTargetView.this.outerCircleCenter[1], (int) TapTargetView.this.lastTouchX, (int) TapTargetView.this.lastTouchY) > ((double) TapTargetView.this.outerCircleRadius)) {
                    clickedInsideOfOuterCircle = false;
                }
                if (clickedInTarget) {
                    TapTargetView.this.isInteractable = false;
                    TapTargetView.this.listener.onTargetClick(TapTargetView.this);
                } else if (clickedInsideOfOuterCircle) {
                    TapTargetView.this.listener.onOuterCircleClick(TapTargetView.this);
                } else if (TapTargetView.this.cancelable) {
                    TapTargetView.this.isInteractable = false;
                    TapTargetView.this.listener.onTargetCancel(TapTargetView.this);
                }
            }
        }
    }

    class 11 implements OnLongClickListener {
        11() {
        }

        public boolean onLongClick(View v) {
            if (TapTargetView.this.listener == null || !TapTargetView.this.targetBounds.contains((int) TapTargetView.this.lastTouchX, (int) TapTargetView.this.lastTouchY)) {
                return false;
            }
            TapTargetView.this.listener.onTargetLongClick(TapTargetView.this);
            return true;
        }
    }

    class 12 extends ViewOutlineProvider {
        12() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            if (TapTargetView.this.outerCircleCenter != null) {
                outline.setOval((int) (((float) TapTargetView.this.outerCircleCenter[0]) - TapTargetView.this.outerCircleRadius), (int) (((float) TapTargetView.this.outerCircleCenter[1]) - TapTargetView.this.outerCircleRadius), (int) (((float) TapTargetView.this.outerCircleCenter[0]) + TapTargetView.this.outerCircleRadius), (int) (((float) TapTargetView.this.outerCircleCenter[1]) + TapTargetView.this.outerCircleRadius));
                outline.setAlpha(((float) TapTargetView.this.outerCircleAlpha) / 255.0f);
                if (VERSION.SDK_INT >= 22) {
                    outline.offset(0, TapTargetView.this.SHADOW_DIM);
                }
            }
        }
    }

    class 1 implements UpdateListener {
        1() {
        }

        public void onUpdate(float lerpTime) {
            boolean expanding;
            float newOuterCircleRadius = ((float) TapTargetView.this.calculatedOuterCircleRadius) * lerpTime;
            if (newOuterCircleRadius > TapTargetView.this.outerCircleRadius) {
                expanding = true;
            } else {
                expanding = false;
            }
            if (!expanding) {
                TapTargetView.this.calculateDrawingBounds();
            }
            TapTargetView.this.outerCircleRadius = newOuterCircleRadius;
            TapTargetView.this.outerCircleAlpha = (int) Math.min(244.79999f, (lerpTime * 1.5f) * 244.79999f);
            TapTargetView.this.outerCirclePath.reset();
            TapTargetView.this.outerCirclePath.addCircle((float) TapTargetView.this.outerCircleCenter[0], (float) TapTargetView.this.outerCircleCenter[1], TapTargetView.this.outerCircleRadius, Direction.CW);
            TapTargetView.this.targetCircleAlpha = (int) Math.min(255.0f, (lerpTime * 1.5f) * 255.0f);
            if (expanding) {
                TapTargetView.this.targetCircleRadius = ((float) TapTargetView.this.TARGET_RADIUS) * Math.min(1.0f, lerpTime * 1.5f);
            } else {
                TapTargetView.this.targetCircleRadius = ((float) TapTargetView.this.TARGET_RADIUS) * lerpTime;
                TapTargetView tapTargetView = TapTargetView.this;
                tapTargetView.targetCirclePulseRadius *= lerpTime;
            }
            TapTargetView.this.textAlpha = (int) (TapTargetView.this.delayedLerp(lerpTime, 0.7f) * 255.0f);
            if (expanding) {
                TapTargetView.this.calculateDrawingBounds();
            }
            TapTargetView.this.invalidateViewAndOutline(TapTargetView.this.drawingBounds);
        }
    }

    class 2 implements EndListener {
        2() {
        }

        public void onEnd() {
            TapTargetView.this.pulseAnimation.start();
        }
    }

    class 3 implements UpdateListener {
        3() {
        }

        public void onUpdate(float lerpTime) {
            TapTargetView.this.expandContractUpdateListener.onUpdate(lerpTime);
        }
    }

    class 4 implements UpdateListener {
        4() {
        }

        public void onUpdate(float lerpTime) {
            float pulseLerp = TapTargetView.this.delayedLerp(lerpTime, GenVectorModul.DEFAULT_DENSITY);
            TapTargetView.this.targetCirclePulseRadius = (1.0f + pulseLerp) * ((float) TapTargetView.this.TARGET_RADIUS);
            TapTargetView.this.targetCirclePulseAlpha = (int) ((1.0f - pulseLerp) * 255.0f);
            TapTargetView.this.targetCircleRadius = ((float) TapTargetView.this.TARGET_RADIUS) + (TapTargetView.this.halfwayLerp(lerpTime) * ((float) TapTargetView.this.TARGET_PULSE_RADIUS));
            TapTargetView.this.calculateDrawingBounds();
            TapTargetView.this.invalidateViewAndOutline(TapTargetView.this.drawingBounds);
        }
    }

    class 5 implements EndListener {
        5() {
        }

        public void onEnd() {
            TapTargetView.this.parent.removeView(TapTargetView.this);
            TapTargetView.this.onDismiss();
        }
    }

    class 6 implements UpdateListener {
        6() {
        }

        public void onUpdate(float lerpTime) {
            TapTargetView.this.expandContractUpdateListener.onUpdate(lerpTime);
        }
    }

    class 7 implements EndListener {
        7() {
        }

        public void onEnd() {
            TapTargetView.this.parent.removeView(TapTargetView.this);
            TapTargetView.this.onDismiss();
        }
    }

    class 8 implements UpdateListener {
        8() {
        }

        public void onUpdate(float lerpTime) {
            float spedUpLerp = Math.min(1.0f, 2.0f * lerpTime);
            TapTargetView.this.outerCircleRadius = ((float) TapTargetView.this.calculatedOuterCircleRadius) * ((0.2f * spedUpLerp) + 1.0f);
            TapTargetView.this.outerCircleAlpha = (int) ((1.0f - spedUpLerp) * 255.0f);
            TapTargetView.this.outerCirclePath.reset();
            TapTargetView.this.outerCirclePath.addCircle((float) TapTargetView.this.outerCircleCenter[0], (float) TapTargetView.this.outerCircleCenter[1], TapTargetView.this.outerCircleRadius, Direction.CW);
            TapTargetView.this.targetCircleRadius = (1.0f - lerpTime) * ((float) TapTargetView.this.TARGET_RADIUS);
            TapTargetView.this.targetCircleAlpha = (int) ((1.0f - lerpTime) * 255.0f);
            TapTargetView.this.targetCirclePulseRadius = (1.0f + lerpTime) * ((float) TapTargetView.this.TARGET_RADIUS);
            TapTargetView.this.targetCirclePulseAlpha = (int) ((1.0f - lerpTime) * ((float) TapTargetView.this.targetCirclePulseAlpha));
            TapTargetView.this.textAlpha = (int) ((1.0f - spedUpLerp) * 255.0f);
            TapTargetView.this.calculateDrawingBounds();
            TapTargetView.this.invalidateViewAndOutline(TapTargetView.this.drawingBounds);
        }
    }

    class 9 implements Runnable {
        final /* synthetic */ ViewGroup val$boundingParent;
        final /* synthetic */ Context val$context;
        final /* synthetic */ TapTarget val$target;

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                int[] offset = new int[2];
                TapTargetView.this.targetBounds.set(9.this.val$target.bounds());
                TapTargetView.this.getLocationOnScreen(offset);
                TapTargetView.this.targetBounds.offset(-offset[0], -offset[1]);
                if (9.this.val$boundingParent != null) {
                    WindowManager windowManager = (WindowManager) 9.this.val$context.getSystemService("window");
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                    Rect rect = new Rect();
                    9.this.val$boundingParent.getWindowVisibleDisplayFrame(rect);
                    TapTargetView.this.topBoundary = Math.max(0, rect.top);
                    TapTargetView.this.bottomBoundary = Math.min(rect.bottom, displayMetrics.heightPixels);
                }
                TapTargetView.this.drawTintedTarget();
                TapTargetView.this.calculateDimensions();
                TapTargetView.this.expandAnimation.start();
                TapTargetView.this.visible = true;
                TapTargetView.this.requestFocus();
            }
        }

        9(TapTarget tapTarget, ViewGroup viewGroup, Context context) {
            this.val$target = tapTarget;
            this.val$boundingParent = viewGroup;
            this.val$context = context;
        }

        public void run() {
            TapTargetView.this.updateTextLayouts();
            this.val$target.onReady(new 1());
        }
    }

    public static TapTargetView showFor(Activity activity, TapTarget target) {
        return showFor(activity, target, null);
    }

    public static TapTargetView showFor(Activity activity, TapTarget target, Listener listener) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity is null");
        }
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        LayoutParams layoutParams = new LayoutParams(UNSET_COLOR, UNSET_COLOR);
        TapTargetView tapTargetView = new TapTargetView(activity, decor, (ViewGroup) decor.findViewById(16908290), target, listener);
        decor.addView(tapTargetView, layoutParams);
        return tapTargetView;
    }

    public static TapTargetView showFor(Dialog dialog, TapTarget target) {
        return showFor(dialog, target, null);
    }

    public static TapTargetView showFor(Dialog dialog, TapTarget target, Listener listener) {
        if (dialog == null) {
            throw new IllegalArgumentException("Dialog is null");
        }
        Context context = dialog.getContext();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = 2;
        params.format = 1;
        params.flags = 0;
        params.gravity = 8388659;
        params.x = 0;
        params.y = 0;
        params.width = UNSET_COLOR;
        params.height = UNSET_COLOR;
        TapTargetView tapTargetView = new TapTargetView(context, windowManager, null, target, listener);
        windowManager.addView(tapTargetView, params);
        return tapTargetView;
    }

    public TapTargetView(Context context, ViewManager parent, @Nullable ViewGroup boundingParent, TapTarget target, @Nullable Listener userListener) {
        super(context);
        this.isDismissed = false;
        this.isInteractable = true;
        this.expandContractUpdateListener = new 1();
        this.expandAnimation = new FloatValueAnimatorBuilder().duration(250).delayBy(250).interpolator(new AccelerateDecelerateInterpolator()).onUpdate(new 3()).onEnd(new 2()).build();
        this.pulseAnimation = new FloatValueAnimatorBuilder().duration(1000).repeat(UNSET_COLOR).interpolator(new AccelerateDecelerateInterpolator()).onUpdate(new 4()).build();
        this.dismissAnimation = new FloatValueAnimatorBuilder(true).duration(250).interpolator(new AccelerateDecelerateInterpolator()).onUpdate(new 6()).onEnd(new 5()).build();
        this.dismissConfirmAnimation = new FloatValueAnimatorBuilder().duration(250).interpolator(new AccelerateDecelerateInterpolator()).onUpdate(new 8()).onEnd(new 7()).build();
        this.animators = new ValueAnimator[]{this.expandAnimation, this.pulseAnimation, this.dismissConfirmAnimation, this.dismissAnimation};
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null");
        }
        this.target = target;
        this.parent = parent;
        this.boundingParent = boundingParent;
        if (userListener == null) {
            userListener = new Listener();
        }
        this.listener = userListener;
        this.title = target.title;
        this.description = target.description;
        this.TARGET_PADDING = UiUtil.dp(context, 20);
        this.CIRCLE_PADDING = UiUtil.dp(context, 40);
        this.TARGET_RADIUS = UiUtil.dp(context, target.targetRadius);
        this.TEXT_PADDING = UiUtil.dp(context, 40);
        this.TEXT_SPACING = UiUtil.dp(context, 8);
        this.GUTTER_DIM = UiUtil.dp(context, 88);
        this.SHADOW_DIM = UiUtil.dp(context, 8);
        this.TARGET_PULSE_RADIUS = (int) (0.1f * ((float) this.TARGET_RADIUS));
        this.outerCirclePath = new Path();
        this.targetBounds = new Rect();
        this.drawingBounds = new Rect();
        this.titlePaint = new TextPaint();
        this.titlePaint.setTextSize((float) target.titleTextSizePx(context));
        this.titlePaint.setTypeface(Typeface.create("sans-serif-medium", 0));
        this.titlePaint.setAntiAlias(true);
        this.descriptionPaint = new TextPaint();
        this.descriptionPaint.setTextSize((float) target.descriptionTextSizePx(context));
        this.descriptionPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
        this.descriptionPaint.setAntiAlias(true);
        this.descriptionPaint.setAlpha(137);
        this.outerCirclePaint = new Paint();
        this.outerCirclePaint.setAntiAlias(true);
        this.outerCirclePaint.setAlpha(244);
        this.outerCircleShadowPaint = new Paint();
        this.outerCircleShadowPaint.setAntiAlias(true);
        this.outerCircleShadowPaint.setAlpha(50);
        this.outerCircleShadowPaint.setShadowLayer(10.0f, 0.0f, 25.0f, ViewCompat.MEASURED_STATE_MASK);
        this.targetCirclePaint = new Paint();
        this.targetCirclePaint.setAntiAlias(true);
        this.targetCirclePulsePaint = new Paint();
        this.targetCirclePulsePaint.setAntiAlias(true);
        this.debugPaint = new Paint();
        this.debugPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.debugPaint.setStyle(Style.STROKE);
        applyTargetOptions(context);
        ViewUtil.onLaidOut(this, new 9(target, boundingParent, context));
        setFocusableInTouchMode(true);
        setClickable(true);
        setOnClickListener(new 10());
        setOnLongClickListener(new 11());
    }

    protected void applyTargetOptions(Context context) {
        boolean z = true;
        int i = ViewCompat.MEASURED_STATE_MASK;
        this.shouldTintTarget = this.target.tintTarget;
        this.shouldDrawShadow = this.target.drawShadow;
        this.cancelable = this.target.cancelable;
        if (this.shouldDrawShadow && VERSION.SDK_INT >= 21) {
            this.outlineProvider = new 12();
            setOutlineProvider(this.outlineProvider);
            setElevation((float) this.SHADOW_DIM);
        }
        if (!(this.shouldDrawShadow && this.outlineProvider == null) && VERSION.SDK_INT >= 18) {
            setLayerType(2, null);
        } else {
            setLayerType(1, null);
        }
        Theme theme = context.getTheme();
        if (UiUtil.themeIntAttr(context, "isLightTheme") != 0) {
            z = false;
        }
        this.isDark = z;
        int outerCircleColor = this.target.outerCircleColorInt(context);
        if (outerCircleColor != UNSET_COLOR) {
            this.outerCirclePaint.setColor(outerCircleColor);
        } else if (theme != null) {
            this.outerCirclePaint.setColor(UiUtil.themeIntAttr(context, "colorPrimary"));
        } else {
            this.outerCirclePaint.setColor(UNSET_COLOR);
        }
        int targetCircleColor = this.target.targetCircleColorInt(context);
        if (targetCircleColor != UNSET_COLOR) {
            this.targetCirclePaint.setColor(targetCircleColor);
        } else {
            this.targetCirclePaint.setColor(this.isDark ? ViewCompat.MEASURED_STATE_MASK : UNSET_COLOR);
        }
        if (this.target.transparentTarget) {
            this.targetCirclePaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        }
        this.targetCirclePulsePaint.setColor(this.targetCirclePaint.getColor());
        int targetDimColor = this.target.dimColorInt(context);
        if (targetDimColor != UNSET_COLOR) {
            this.dimColor = UiUtil.setAlpha(targetDimColor, 0.3f);
        } else {
            this.dimColor = UNSET_COLOR;
        }
        int titleTextColor = this.target.titleTextColorInt(context);
        if (titleTextColor != UNSET_COLOR) {
            this.titlePaint.setColor(titleTextColor);
        } else {
            TextPaint textPaint = this.titlePaint;
            if (!this.isDark) {
                i = UNSET_COLOR;
            }
            textPaint.setColor(i);
        }
        int descriptionTextColor = this.target.descriptionTextColorInt(context);
        if (descriptionTextColor != UNSET_COLOR) {
            this.descriptionPaint.setColor(descriptionTextColor);
        } else {
            this.descriptionPaint.setColor(this.titlePaint.getColor());
        }
        if (this.target.typeface != null) {
            this.titlePaint.setTypeface(this.target.typeface);
            this.descriptionPaint.setTypeface(this.target.typeface);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDismiss(false);
    }

    void onDismiss() {
        onDismiss(true);
    }

    void onDismiss(boolean userInitiated) {
        if (!this.isDismissed) {
            this.isDismissed = true;
            for (ValueAnimator animator : this.animators) {
                animator.cancel();
                animator.removeAllUpdateListeners();
            }
            this.visible = false;
            if (this.listener != null) {
                this.listener.onTargetDismissed(this, userInitiated);
            }
        }
    }

    protected void onDraw(Canvas c) {
        if (!this.isDismissed && this.outerCircleCenter != null) {
            int saveCount;
            if (this.topBoundary > 0 && this.bottomBoundary > 0) {
                c.clipRect(0, this.topBoundary, getWidth(), this.bottomBoundary);
            }
            if (this.dimColor != UNSET_COLOR) {
                c.drawColor(this.dimColor);
            }
            this.outerCirclePaint.setAlpha(this.outerCircleAlpha);
            if (this.shouldDrawShadow && this.outlineProvider == null) {
                saveCount = c.save();
                c.clipPath(this.outerCirclePath, Op.DIFFERENCE);
                this.outerCircleShadowPaint.setAlpha((int) (0.2f * ((float) this.outerCircleAlpha)));
                c.drawCircle((float) this.outerCircleCenter[0], (float) this.outerCircleCenter[1], this.outerCircleRadius, this.outerCircleShadowPaint);
                c.restoreToCount(saveCount);
            }
            c.drawCircle((float) this.outerCircleCenter[0], (float) this.outerCircleCenter[1], this.outerCircleRadius, this.outerCirclePaint);
            this.targetCirclePaint.setAlpha(this.targetCircleAlpha);
            if (this.targetCirclePulseAlpha > 0) {
                this.targetCirclePulsePaint.setAlpha(this.targetCirclePulseAlpha);
                c.drawCircle((float) this.targetBounds.centerX(), (float) this.targetBounds.centerY(), this.targetCirclePulseRadius, this.targetCirclePulsePaint);
            }
            c.drawCircle((float) this.targetBounds.centerX(), (float) this.targetBounds.centerY(), this.targetCircleRadius, this.targetCirclePaint);
            saveCount = c.save();
            c.clipPath(this.outerCirclePath);
            c.translate((float) this.textBounds.left, (float) this.textBounds.top);
            this.titlePaint.setAlpha(this.textAlpha);
            this.titleLayout.draw(c);
            if (this.descriptionLayout != null) {
                c.translate(0.0f, (float) (this.titleLayout.getHeight() + this.TEXT_SPACING));
                this.descriptionPaint.setAlpha((int) (0.54f * ((float) this.textAlpha)));
                this.descriptionLayout.draw(c);
            }
            c.restoreToCount(saveCount);
            saveCount = c.save();
            if (this.tintedTarget != null) {
                c.translate((float) (this.targetBounds.centerX() - (this.tintedTarget.getWidth() / 2)), (float) (this.targetBounds.centerY() - (this.tintedTarget.getHeight() / 2)));
                c.drawBitmap(this.tintedTarget, 0.0f, 0.0f, this.targetCirclePaint);
            } else if (this.target.icon != null) {
                c.translate((float) (this.targetBounds.centerX() - (this.target.icon.getBounds().width() / 2)), (float) (this.targetBounds.centerY() - (this.target.icon.getBounds().height() / 2)));
                this.target.icon.setAlpha(this.targetCirclePaint.getAlpha());
                this.target.icon.draw(c);
            }
            c.restoreToCount(saveCount);
            if (this.debug) {
                c.drawRect(this.textBounds, this.debugPaint);
                c.drawRect(this.targetBounds, this.debugPaint);
                c.drawCircle((float) this.outerCircleCenter[0], (float) this.outerCircleCenter[1], 10.0f, this.debugPaint);
                c.drawCircle((float) this.outerCircleCenter[0], (float) this.outerCircleCenter[1], (float) (this.calculatedOuterCircleRadius - this.CIRCLE_PADDING), this.debugPaint);
                c.drawCircle((float) this.targetBounds.centerX(), (float) this.targetBounds.centerY(), (float) (this.TARGET_RADIUS + this.TARGET_PADDING), this.debugPaint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        this.lastTouchX = e.getX();
        this.lastTouchY = e.getY();
        return super.onTouchEvent(e);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isVisible() || !this.cancelable || keyCode != 4) {
            return false;
        }
        event.startTracking();
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!isVisible() || !this.isInteractable || !this.cancelable || keyCode != 4 || !event.isTracking() || event.isCanceled()) {
            return false;
        }
        this.isInteractable = false;
        if (this.listener != null) {
            this.listener.onTargetCancel(this);
        } else {
            new Listener().onTargetCancel(this);
        }
        return true;
    }

    public void dismiss(boolean tappedTarget) {
        this.pulseAnimation.cancel();
        this.expandAnimation.cancel();
        if (tappedTarget) {
            this.dismissConfirmAnimation.start();
        } else {
            this.dismissAnimation.start();
        }
    }

    public void setDrawDebug(boolean status) {
        if (this.debug != status) {
            this.debug = status;
            postInvalidate();
        }
    }

    public boolean isVisible() {
        return !this.isDismissed && this.visible;
    }

    void drawTintedTarget() {
        Drawable icon = this.target.icon;
        if (!this.shouldTintTarget || icon == null) {
            this.tintedTarget = null;
            return;
        }
        this.tintedTarget = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(this.tintedTarget);
        icon.setColorFilter(new PorterDuffColorFilter(this.outerCirclePaint.getColor(), Mode.SRC_ATOP));
        icon.draw(canvas);
        icon.setColorFilter(null);
    }

    void updateTextLayouts() {
        int textWidth = Math.max(getMeasuredWidth() - (this.TEXT_PADDING * 2), 0);
        this.titleLayout = new StaticLayout(this.title, this.titlePaint, textWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        if (this.description != null) {
            this.descriptionLayout = new StaticLayout(this.description, this.descriptionPaint, textWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        } else {
            this.descriptionLayout = null;
        }
    }

    float halfwayLerp(float lerp) {
        if (lerp < GenVectorModul.DEFAULT_DENSITY) {
            return lerp / GenVectorModul.DEFAULT_DENSITY;
        }
        return (1.0f - lerp) / GenVectorModul.DEFAULT_DENSITY;
    }

    float delayedLerp(float lerp, float threshold) {
        if (lerp < threshold) {
            return 0.0f;
        }
        return (lerp - threshold) / (1.0f - threshold);
    }

    void calculateDimensions() {
        this.textBounds = getTextBounds();
        this.outerCircleCenter = getOuterCircleCenterPoint();
        this.calculatedOuterCircleRadius = getOuterCircleRadius(this.outerCircleCenter[0], this.outerCircleCenter[1], this.textBounds, this.targetBounds);
    }

    void calculateDrawingBounds() {
        this.drawingBounds.left = (int) Math.max(0.0f, ((float) this.outerCircleCenter[0]) - this.outerCircleRadius);
        this.drawingBounds.top = (int) Math.min(0.0f, ((float) this.outerCircleCenter[1]) - this.outerCircleRadius);
        this.drawingBounds.right = (int) Math.min((float) getWidth(), (((float) this.outerCircleCenter[0]) + this.outerCircleRadius) + ((float) this.CIRCLE_PADDING));
        this.drawingBounds.bottom = (int) Math.min((float) getHeight(), (((float) this.outerCircleCenter[1]) + this.outerCircleRadius) + ((float) this.CIRCLE_PADDING));
    }

    int getOuterCircleRadius(int centerX, int centerY, Rect textBounds, Rect targetBounds) {
        Rect expandedBounds = new Rect(targetBounds);
        expandedBounds.inset((-this.TARGET_PADDING) / 2, (-this.TARGET_PADDING) / 2);
        return Math.max(maxDistanceToPoints(centerX, centerY, textBounds), maxDistanceToPoints(centerX, centerY, expandedBounds)) + this.CIRCLE_PADDING;
    }

    Rect getTextBounds() {
        int top;
        int totalTextHeight = getTotalTextHeight();
        int totalTextWidth = getTotalTextWidth();
        int possibleTop = ((this.targetBounds.centerY() - this.TARGET_RADIUS) - this.TARGET_PADDING) - totalTextHeight;
        if (possibleTop > this.topBoundary) {
            top = possibleTop;
        } else {
            top = (this.targetBounds.centerY() + this.TARGET_RADIUS) + this.TARGET_PADDING;
        }
        return new Rect(this.TEXT_PADDING, top, this.TEXT_PADDING + totalTextWidth, top + totalTextHeight);
    }

    int[] getOuterCircleCenterPoint() {
        if (inGutter(this.targetBounds.centerY())) {
            return new int[]{this.targetBounds.centerX(), this.targetBounds.centerY()};
        }
        boolean onTop;
        int centerY;
        int targetRadius = (Math.max(this.targetBounds.width(), this.targetBounds.height()) / 2) + this.TARGET_PADDING;
        int totalTextHeight = getTotalTextHeight();
        if (((this.targetBounds.centerY() - this.TARGET_RADIUS) - this.TARGET_PADDING) - totalTextHeight > 0) {
            onTop = true;
        } else {
            onTop = false;
        }
        int left = Math.min(this.TEXT_PADDING, this.targetBounds.left - targetRadius);
        int right = Math.max(getWidth() - this.TEXT_PADDING, this.targetBounds.right + targetRadius);
        if (onTop) {
            centerY = (((this.targetBounds.centerY() - this.TARGET_RADIUS) - this.TARGET_PADDING) - totalTextHeight) + this.titleLayout.getHeight();
        } else {
            centerY = ((this.targetBounds.centerY() + this.TARGET_RADIUS) + this.TARGET_PADDING) + this.titleLayout.getHeight();
        }
        return new int[]{(left + right) / 2, centerY};
    }

    int getTotalTextHeight() {
        if (this.descriptionLayout == null) {
            return this.titleLayout.getHeight() + this.TEXT_SPACING;
        }
        return (this.titleLayout.getHeight() + this.descriptionLayout.getHeight()) + this.TEXT_SPACING;
    }

    int getTotalTextWidth() {
        if (this.descriptionLayout == null) {
            return this.titleLayout.getWidth();
        }
        return Math.max(this.titleLayout.getWidth(), this.descriptionLayout.getWidth());
    }

    boolean inGutter(int y) {
        if (this.bottomBoundary > 0) {
            if (y < this.GUTTER_DIM || y > this.bottomBoundary - this.GUTTER_DIM) {
                return true;
            }
            return false;
        } else if (y < this.GUTTER_DIM || y > getHeight() - this.GUTTER_DIM) {
            return true;
        } else {
            return false;
        }
    }

    int maxDistanceToPoints(int x1, int y1, Rect bounds) {
        return (int) Math.max(distance(x1, y1, bounds.left, bounds.top), Math.max(distance(x1, y1, bounds.right, bounds.top), Math.max(distance(x1, y1, bounds.left, bounds.bottom), distance(x1, y1, bounds.right, bounds.bottom))));
    }

    double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((double) (x2 - x1), 2.0d) + Math.pow((double) (y2 - y1), 2.0d));
    }

    void invalidateViewAndOutline(Rect bounds) {
        invalidate(bounds);
        if (this.outlineProvider != null && VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }
}
