package butterknife.internal;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.UiThread;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.View;
import java.lang.reflect.Array;
import java.util.List;

public final class Utils {
    private static final boolean HAS_SUPPORT_V4;
    private static final TypedValue VALUE;

    static class SupportV4 {
        private static final TypedValue OUT_VALUE;

        SupportV4() {
        }

        static {
            OUT_VALUE = new TypedValue();
        }

        static Drawable getTintedDrawable(Resources res, Theme theme, @DrawableRes int id, @AttrRes int tintAttributeId) {
            if (theme.resolveAttribute(tintAttributeId, OUT_VALUE, true)) {
                Drawable drawable = DrawableCompat.wrap(Utils.getDrawable(res, theme, id).mutate());
                DrawableCompat.setTint(drawable, Utils.getColor(res, theme, OUT_VALUE.resourceId));
                return drawable;
            }
            throw new NotFoundException("Required tint color attribute with name " + res.getResourceEntryName(tintAttributeId) + " and attribute ID " + tintAttributeId + " was not found.");
        }
    }

    static {
        HAS_SUPPORT_V4 = hasSupportV4();
        VALUE = new TypedValue();
    }

    private static boolean hasSupportV4() {
        try {
            Class.forName("android.support.v4.graphics.drawable.DrawableCompat");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (VerifyError e2) {
            return false;
        }
    }

    public static Drawable getTintedDrawable(Resources res, Theme theme, @DrawableRes int id, @AttrRes int tintAttrId) {
        if (HAS_SUPPORT_V4) {
            return SupportV4.getTintedDrawable(res, theme, id, tintAttrId);
        }
        throw new RuntimeException("Android support-v4 library is required for @BindDrawable with tint.");
    }

    public static int getColor(Resources res, Theme theme, @ColorRes int id) {
        if (VERSION.SDK_INT < 23) {
            return res.getColor(id);
        }
        return res.getColor(id, theme);
    }

    @UiThread
    public static float getFloat(Resources res, @DimenRes int id) {
        TypedValue value = VALUE;
        res.getValue(id, value, true);
        if (value.type == 4) {
            return value.getFloat();
        }
        throw new NotFoundException("Resource ID #0x" + Integer.toHexString(id) + " type #0x" + Integer.toHexString(value.type) + " is not valid");
    }

    public static ColorStateList getColorStateList(Resources res, Theme theme, @ColorRes int id) {
        if (VERSION.SDK_INT < 23) {
            return res.getColorStateList(id);
        }
        return res.getColorStateList(id, theme);
    }

    public static Drawable getDrawable(Resources res, Theme theme, @DrawableRes int id) {
        if (VERSION.SDK_INT < 21) {
            return res.getDrawable(id);
        }
        return res.getDrawable(id, theme);
    }

    @SafeVarargs
    public static <T> T[] arrayOf(T... views) {
        return filterNull(views);
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... views) {
        return new ImmutableList(filterNull(views));
    }

    private static <T> T[] filterNull(T[] views) {
        int length = views.length;
        int i = 0;
        int end = 0;
        while (i < length) {
            int end2;
            T view = views[i];
            if (view != null) {
                end2 = end + 1;
                views[end] = view;
            } else {
                end2 = end;
            }
            i++;
            end = end2;
        }
        if (end == length) {
            return views;
        }
        T[] newViews = (Object[]) ((Object[]) Array.newInstance(views.getClass().getComponentType(), end));
        System.arraycopy(views, 0, newViews, 0, end);
        return newViews;
    }

    public static <T> T findOptionalViewAsType(View source, @IdRes int id, String who, Class<T> cls) {
        return castView(source.findViewById(id), id, who, cls);
    }

    public static View findRequiredView(View source, @IdRes int id, String who) {
        View view = source.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("Required view '" + getResourceEntryName(source, id) + "' with ID " + id + " for " + who + " was not found. If this view is optional add '@Nullable' (fields) or '@Optional' (methods) annotation.");
    }

    public static <T> T findRequiredViewAsType(View source, @IdRes int id, String who, Class<T> cls) {
        return castView(findRequiredView(source, id, who), id, who, cls);
    }

    public static <T> T castView(View view, @IdRes int id, String who, Class<T> cls) {
        try {
            return cls.cast(view);
        } catch (ClassCastException e) {
            throw new IllegalStateException("View '" + getResourceEntryName(view, id) + "' with ID " + id + " for " + who + " was of the wrong type. See cause for more info.", e);
        }
    }

    public static <T> T castParam(Object value, String from, int fromPos, String to, int toPos) {
        return value;
    }

    private static String getResourceEntryName(View view, @IdRes int id) {
        if (view.isInEditMode()) {
            return "<unavailable while editing>";
        }
        return view.getContext().getResources().getResourceEntryName(id);
    }

    private Utils() {
        throw new AssertionError("No instances.");
    }
}
