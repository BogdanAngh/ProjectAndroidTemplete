package com.getkeepsafe.taptargetview;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;
import java.util.ArrayList;
import java.util.Stack;

public class ToolbarTapTarget extends ViewTapTarget {

    private interface ToolbarProxy {
        void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int i);

        View getChildAt(int i);

        int getChildCount();

        CharSequence getNavigationContentDescription();

        Drawable getNavigationIcon();

        @Nullable
        Drawable getOverflowIcon();

        Object internalToolbar();

        void setNavigationContentDescription(CharSequence charSequence);
    }

    @TargetApi(21)
    static class StandardToolbarProxy implements ToolbarProxy {
        private final Toolbar toolbar;

        StandardToolbarProxy(Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        public CharSequence getNavigationContentDescription() {
            return this.toolbar.getNavigationContentDescription();
        }

        public void setNavigationContentDescription(CharSequence description) {
            this.toolbar.setNavigationContentDescription(description);
        }

        public void findViewsWithText(ArrayList<View> out, CharSequence toFind, int flags) {
            this.toolbar.findViewsWithText(out, toFind, flags);
        }

        public Drawable getNavigationIcon() {
            return this.toolbar.getNavigationIcon();
        }

        @Nullable
        public Drawable getOverflowIcon() {
            if (VERSION.SDK_INT >= 23) {
                return this.toolbar.getOverflowIcon();
            }
            return null;
        }

        public int getChildCount() {
            return this.toolbar.getChildCount();
        }

        public View getChildAt(int position) {
            return this.toolbar.getChildAt(position);
        }

        public Object internalToolbar() {
            return this.toolbar;
        }
    }

    static class SupportToolbarProxy implements ToolbarProxy {
        private final android.support.v7.widget.Toolbar toolbar;

        SupportToolbarProxy(android.support.v7.widget.Toolbar toolbar) {
            this.toolbar = toolbar;
        }

        public CharSequence getNavigationContentDescription() {
            return this.toolbar.getNavigationContentDescription();
        }

        public void setNavigationContentDescription(CharSequence description) {
            this.toolbar.setNavigationContentDescription(description);
        }

        public void findViewsWithText(ArrayList<View> out, CharSequence toFind, int flags) {
            this.toolbar.findViewsWithText(out, toFind, flags);
        }

        public Drawable getNavigationIcon() {
            return this.toolbar.getNavigationIcon();
        }

        public Drawable getOverflowIcon() {
            return this.toolbar.getOverflowIcon();
        }

        public int getChildCount() {
            return this.toolbar.getChildCount();
        }

        public View getChildAt(int position) {
            return this.toolbar.getChildAt(position);
        }

        public Object internalToolbar() {
            return this.toolbar;
        }
    }

    protected ToolbarTapTarget(android.support.v7.widget.Toolbar toolbar, @IdRes int menuItemId, CharSequence title, @Nullable CharSequence description) {
        super(toolbar.findViewById(menuItemId), title, description);
    }

    protected ToolbarTapTarget(Toolbar toolbar, @IdRes int menuItemId, CharSequence title, @Nullable CharSequence description) {
        super(toolbar.findViewById(menuItemId), title, description);
    }

    protected ToolbarTapTarget(android.support.v7.widget.Toolbar toolbar, boolean findNavView, CharSequence title, @Nullable CharSequence description) {
        super(findNavView ? findNavView(toolbar) : findOverflowView(toolbar), title, description);
    }

    protected ToolbarTapTarget(Toolbar toolbar, boolean findNavView, CharSequence title, @Nullable CharSequence description) {
        super(findNavView ? findNavView(toolbar) : findOverflowView(toolbar), title, description);
    }

    private static ToolbarProxy proxyOf(Object instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Given null instance");
        } else if (instance instanceof android.support.v7.widget.Toolbar) {
            return new SupportToolbarProxy((android.support.v7.widget.Toolbar) instance);
        } else {
            if (instance instanceof Toolbar) {
                return new StandardToolbarProxy((Toolbar) instance);
            }
            throw new IllegalStateException("Couldn't provide proper toolbar proxy instance");
        }
    }

    private static View findNavView(Object instance) {
        boolean hadContentDescription;
        ToolbarProxy toolbar = proxyOf(instance);
        CharSequence currentDescription = toolbar.getNavigationContentDescription();
        if (TextUtils.isEmpty(currentDescription)) {
            hadContentDescription = false;
        } else {
            hadContentDescription = true;
        }
        CharSequence sentinel = hadContentDescription ? currentDescription : "taptarget-findme";
        toolbar.setNavigationContentDescription(sentinel);
        ArrayList<View> possibleViews = new ArrayList(1);
        toolbar.findViewsWithText(possibleViews, sentinel, 2);
        if (!hadContentDescription) {
            toolbar.setNavigationContentDescription(null);
        }
        if (possibleViews.size() > 0) {
            return (View) possibleViews.get(0);
        }
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            int size = toolbar.getChildCount();
            for (int i = 0; i < size; i++) {
                View child = toolbar.getChildAt(i);
                if ((child instanceof ImageButton) && ((ImageButton) child).getDrawable() == navigationIcon) {
                    return child;
                }
            }
        }
        try {
            return (View) ReflectUtil.getPrivateField(toolbar.internalToolbar(), "mNavButtonView");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Could not find navigation view for Toolbar!", e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to access navigation view for Toolbar!", e2);
        }
    }

    private static View findOverflowView(Object instance) {
        ToolbarProxy toolbar = proxyOf(instance);
        Drawable overflowDrawable = toolbar.getOverflowIcon();
        if (overflowDrawable != null) {
            Stack<ViewGroup> parents = new Stack();
            parents.push((ViewGroup) toolbar.internalToolbar());
            while (!parents.empty()) {
                ViewGroup parent = (ViewGroup) parents.pop();
                int size = parent.getChildCount();
                for (int i = 0; i < size; i++) {
                    View child = parent.getChildAt(i);
                    if (child instanceof ViewGroup) {
                        parents.push((ViewGroup) child);
                    } else if ((child instanceof ImageView) && ((ImageView) child).getDrawable() == overflowDrawable) {
                        return child;
                    }
                }
            }
        }
        try {
            return (View) ReflectUtil.getPrivateField(ReflectUtil.getPrivateField(ReflectUtil.getPrivateField(toolbar.internalToolbar(), "mMenuView"), "mPresenter"), "mOverflowButton");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Could not find overflow view for Toolbar!", e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to access overflow view for Toolbar!", e2);
        }
    }
}
