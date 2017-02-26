package com.example.duy.calculator.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.example.duy.calculator.R;
import com.example.duy.calculator.view.NineOldViewPager.OnPageChangeListener;
import com.example.duy.calculator.view.NineOldViewPager.PageTransformer;
import com.example.duy.calculator.view.NineOldViewPager.SimpleOnPageChangeListener;
import com.nineoldandroids.view.ViewHelper;

public class CalculatorPadViewPager extends NineOldViewPager {
    private final OnPageChangeListener mOnPageChangeListener;
    private final PageTransformer mPageTransformer;
    private final PagerAdapter mStaticPagerAdapter;

    class 1 extends PagerAdapter {
        1() {
        }

        public int getCount() {
            return CalculatorPadViewPager.this.getChildCount();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            return CalculatorPadViewPager.this.getChildAt(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            CalculatorPadViewPager.this.removeViewAt(position);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public float getPageWidth(int position) {
            return position == 1 ? 0.8888889f : 1.0f;
        }
    }

    class 2 extends SimpleOnPageChangeListener {
        2() {
        }

        private void recursivelySetEnabled(View view, boolean enabled) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int childIndex = 0; childIndex < viewGroup.getChildCount(); childIndex++) {
                    recursivelySetEnabled(viewGroup.getChildAt(childIndex), enabled);
                }
                return;
            }
            view.setEnabled(enabled);
        }

        public void onPageSelected(int position) {
            if (CalculatorPadViewPager.this.getAdapter() == CalculatorPadViewPager.this.mStaticPagerAdapter) {
                int childIndex = 0;
                while (childIndex < CalculatorPadViewPager.this.getChildCount()) {
                    recursivelySetEnabled(CalculatorPadViewPager.this.getChildAt(childIndex), childIndex == position);
                    childIndex++;
                }
            }
        }
    }

    class 3 implements PageTransformer {
        3() {
        }

        public void transformPage(View view, float position) {
            if (position < 0.0f) {
                ViewHelper.setTranslationX(view, ((float) CalculatorPadViewPager.this.getWidth()) * (-position));
                ViewHelper.setAlpha(view, Math.max(1.0f + position, 0.0f));
                return;
            }
            ViewHelper.setTranslationX(view, 0.0f);
            ViewHelper.setAlpha(view, 1.0f);
        }
    }

    public CalculatorPadViewPager(Context context) {
        this(context, null);
    }

    public CalculatorPadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mStaticPagerAdapter = new 1();
        this.mOnPageChangeListener = new 2();
        this.mPageTransformer = new 3();
        setAdapter(this.mStaticPagerAdapter);
        setBackgroundColor(getResources().getColor(17170444));
        setOnPageChangeListener(this.mOnPageChangeListener);
        setPageMargin(getResources().getDimensionPixelSize(R.dimen.pad_page_margin));
        setPageTransformer(false, this.mPageTransformer);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getAdapter() == this.mStaticPagerAdapter) {
            this.mStaticPagerAdapter.notifyDataSetChanged();
        }
    }
}
