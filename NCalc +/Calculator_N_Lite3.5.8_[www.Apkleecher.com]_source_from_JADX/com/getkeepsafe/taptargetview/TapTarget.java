package com.getkeepsafe.taptargetview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TapTarget {
    Rect bounds;
    boolean cancelable;
    @Nullable
    final CharSequence description;
    private int descriptionTextColor;
    @ColorRes
    private int descriptionTextColorRes;
    @DimenRes
    private int descriptionTextDimen;
    private int descriptionTextSize;
    private int dimColor;
    @ColorRes
    private int dimColorRes;
    boolean drawShadow;
    Drawable icon;
    int id;
    private int outerCircleColor;
    @ColorRes
    private int outerCircleColorRes;
    private int targetCircleColor;
    @ColorRes
    private int targetCircleColorRes;
    int targetRadius;
    boolean tintTarget;
    final CharSequence title;
    private int titleTextColor;
    @ColorRes
    private int titleTextColorRes;
    @DimenRes
    private int titleTextDimen;
    private int titleTextSize;
    boolean transparentTarget;
    Typeface typeface;

    public static ToolbarTapTarget forToolbarOverflow(Toolbar toolbar, CharSequence title) {
        return forToolbarOverflow(toolbar, title, null);
    }

    public static ToolbarTapTarget forToolbarOverflow(Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, false, title, description);
    }

    public static ToolbarTapTarget forToolbarOverflow(android.widget.Toolbar toolbar, CharSequence title) {
        return forToolbarOverflow(toolbar, title, null);
    }

    public static ToolbarTapTarget forToolbarOverflow(android.widget.Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, false, title, description);
    }

    public static ToolbarTapTarget forToolbarNavigationIcon(Toolbar toolbar, CharSequence title) {
        return forToolbarNavigationIcon(toolbar, title, null);
    }

    public static ToolbarTapTarget forToolbarNavigationIcon(Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, true, title, description);
    }

    public static ToolbarTapTarget forToolbarNavigationIcon(android.widget.Toolbar toolbar, CharSequence title) {
        return forToolbarNavigationIcon(toolbar, title, null);
    }

    public static ToolbarTapTarget forToolbarNavigationIcon(android.widget.Toolbar toolbar, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, true, title, description);
    }

    public static ToolbarTapTarget forToolbarMenuItem(Toolbar toolbar, @IdRes int menuItemId, CharSequence title) {
        return forToolbarMenuItem(toolbar, menuItemId, title, null);
    }

    public static ToolbarTapTarget forToolbarMenuItem(Toolbar toolbar, @IdRes int menuItemId, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, menuItemId, title, description);
    }

    public static ToolbarTapTarget forToolbarMenuItem(android.widget.Toolbar toolbar, @IdRes int menuItemId, CharSequence title) {
        return forToolbarMenuItem(toolbar, menuItemId, title, null);
    }

    public static ToolbarTapTarget forToolbarMenuItem(android.widget.Toolbar toolbar, @IdRes int menuItemId, CharSequence title, @Nullable CharSequence description) {
        return new ToolbarTapTarget(toolbar, menuItemId, title, description);
    }

    public static ViewTapTarget forView(View view, CharSequence title) {
        return forView(view, title, null);
    }

    public static ViewTapTarget forView(View view, CharSequence title, @Nullable CharSequence description) {
        return new ViewTapTarget(view, title, description);
    }

    public static TapTarget forBounds(Rect bounds, CharSequence title) {
        return forBounds(bounds, title, null);
    }

    public static TapTarget forBounds(Rect bounds, CharSequence title, @Nullable CharSequence description) {
        return new TapTarget(bounds, title, description);
    }

    protected TapTarget(Rect bounds, CharSequence title, @Nullable CharSequence description) {
        this(title, description);
        if (bounds == null) {
            throw new IllegalArgumentException("Cannot pass null bounds or title");
        }
        this.bounds = bounds;
    }

    protected TapTarget(CharSequence title, @Nullable CharSequence description) {
        this.targetRadius = 44;
        this.outerCircleColorRes = -1;
        this.targetCircleColorRes = -1;
        this.dimColorRes = -1;
        this.titleTextColorRes = -1;
        this.descriptionTextColorRes = -1;
        this.outerCircleColor = -1;
        this.targetCircleColor = -1;
        this.dimColor = -1;
        this.titleTextColor = -1;
        this.descriptionTextColor = -1;
        this.titleTextDimen = -1;
        this.descriptionTextDimen = -1;
        this.titleTextSize = 20;
        this.descriptionTextSize = 18;
        this.id = -1;
        this.drawShadow = false;
        this.cancelable = true;
        this.tintTarget = true;
        this.transparentTarget = false;
        if (title == null) {
            throw new IllegalArgumentException("Cannot pass null title");
        }
        this.title = title;
        this.description = description;
    }

    public TapTarget transparentTarget(boolean transparent) {
        this.transparentTarget = transparent;
        return this;
    }

    public TapTarget outerCircleColor(@ColorRes int color) {
        this.outerCircleColorRes = color;
        return this;
    }

    public TapTarget outerCircleColorInt(@ColorInt int color) {
        this.outerCircleColor = color;
        return this;
    }

    public TapTarget targetCircleColor(@ColorRes int color) {
        this.targetCircleColorRes = color;
        return this;
    }

    public TapTarget targetCircleColorInt(@ColorInt int color) {
        this.targetCircleColor = color;
        return this;
    }

    public TapTarget textColor(@ColorRes int color) {
        this.titleTextColorRes = color;
        this.descriptionTextColorRes = color;
        return this;
    }

    public TapTarget textColorInt(@ColorInt int color) {
        this.titleTextColor = color;
        this.descriptionTextColor = color;
        return this;
    }

    public TapTarget titleTextColor(@ColorRes int color) {
        this.titleTextColorRes = color;
        return this;
    }

    public TapTarget titleTextColorInt(@ColorInt int color) {
        this.titleTextColor = color;
        return this;
    }

    public TapTarget descriptionTextColor(@ColorRes int color) {
        this.descriptionTextColorRes = color;
        return this;
    }

    public TapTarget descriptionTextColorInt(@ColorInt int color) {
        this.descriptionTextColor = color;
        return this;
    }

    public TapTarget textTypeface(Typeface typeface) {
        if (typeface == null) {
            throw new IllegalArgumentException("Cannot use a null typeface");
        }
        this.typeface = typeface;
        return this;
    }

    public TapTarget titleTextSize(int sp) {
        if (sp < 0) {
            throw new IllegalArgumentException("Given negative text size");
        }
        this.titleTextSize = sp;
        return this;
    }

    public TapTarget descriptionTextSize(int sp) {
        if (sp < 0) {
            throw new IllegalArgumentException("Given negative text size");
        }
        this.descriptionTextSize = sp;
        return this;
    }

    public TapTarget titleTextDimen(@DimenRes int dimen) {
        this.titleTextDimen = dimen;
        return this;
    }

    public TapTarget descriptionTextDimen(@DimenRes int dimen) {
        this.descriptionTextDimen = dimen;
        return this;
    }

    public TapTarget dimColor(@ColorRes int color) {
        this.dimColorRes = color;
        return this;
    }

    public TapTarget dimColorInt(@ColorInt int color) {
        this.dimColor = color;
        return this;
    }

    public TapTarget drawShadow(boolean draw) {
        this.drawShadow = draw;
        return this;
    }

    public TapTarget cancelable(boolean status) {
        this.cancelable = status;
        return this;
    }

    public TapTarget tintTarget(boolean tint) {
        this.tintTarget = tint;
        return this;
    }

    public TapTarget icon(Drawable icon) {
        return icon(icon, false);
    }

    public TapTarget icon(Drawable icon, boolean hasSetBounds) {
        if (icon == null) {
            throw new IllegalArgumentException("Cannot use null drawable");
        }
        this.icon = icon;
        if (!hasSetBounds) {
            this.icon.setBounds(new Rect(0, 0, this.icon.getIntrinsicWidth(), this.icon.getIntrinsicHeight()));
        }
        return this;
    }

    public TapTarget id(int id) {
        this.id = id;
        return this;
    }

    public TapTarget targetRadius(int targetRadius) {
        this.targetRadius = targetRadius;
        return this;
    }

    public int id() {
        return this.id;
    }

    public void onReady(Runnable runnable) {
        runnable.run();
    }

    public Rect bounds() {
        if (this.bounds != null) {
            return this.bounds;
        }
        throw new IllegalStateException("Requesting bounds that are not set! Make sure your target is ready");
    }

    int outerCircleColorInt(Context context) {
        return colorResOrInt(context, this.outerCircleColor, this.outerCircleColorRes);
    }

    int targetCircleColorInt(Context context) {
        return colorResOrInt(context, this.targetCircleColor, this.targetCircleColorRes);
    }

    int dimColorInt(Context context) {
        return colorResOrInt(context, this.dimColor, this.dimColorRes);
    }

    int titleTextColorInt(Context context) {
        return colorResOrInt(context, this.titleTextColor, this.titleTextColorRes);
    }

    int descriptionTextColorInt(Context context) {
        return colorResOrInt(context, this.descriptionTextColor, this.descriptionTextColorRes);
    }

    int titleTextSizePx(Context context) {
        return dimenOrSize(context, this.titleTextSize, this.titleTextDimen);
    }

    int descriptionTextSizePx(Context context) {
        return dimenOrSize(context, this.descriptionTextSize, this.descriptionTextDimen);
    }

    private int colorResOrInt(Context context, int value, @ColorRes int resource) {
        if (resource != -1) {
            return UiUtil.color(context, resource);
        }
        return value;
    }

    private int dimenOrSize(Context context, int size, @DimenRes int dimen) {
        if (dimen != -1) {
            return UiUtil.dimen(context, dimen);
        }
        return UiUtil.sp(context, size);
    }
}
