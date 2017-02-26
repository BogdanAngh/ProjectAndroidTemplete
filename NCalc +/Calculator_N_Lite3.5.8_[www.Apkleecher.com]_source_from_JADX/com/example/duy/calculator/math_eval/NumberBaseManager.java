package com.example.duy.calculator.math_eval;

import com.example.duy.calculator.R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NumberBaseManager {
    private Base mBase;
    private Set<Integer> mBasicViewIds;
    private Map<Base, Set<Integer>> mDisabledViewIds;
    private Set<Integer> mHexViewIds;
    private Set<Integer> mOctViewIds;

    public NumberBaseManager(Base base) {
        this.mBase = base;
        List<Integer> hexList = Arrays.asList(new Integer[]{Integer.valueOf(R.id.A), Integer.valueOf(R.id.B), Integer.valueOf(R.id.C), Integer.valueOf(R.id.D), Integer.valueOf(R.id.E), Integer.valueOf(R.id.F)});
        List<Integer> binaryList = Arrays.asList(new Integer[]{Integer.valueOf(R.id.digit2), Integer.valueOf(R.id.digit3), Integer.valueOf(R.id.digit4), Integer.valueOf(R.id.digit5), Integer.valueOf(R.id.digit6), Integer.valueOf(R.id.digit7), Integer.valueOf(R.id.digit8), Integer.valueOf(R.id.digit9)});
        List<Integer> octalList = Arrays.asList(new Integer[]{Integer.valueOf(R.id.digit8), Integer.valueOf(R.id.digit9)});
        Set<Integer> disabledForBinary = new HashSet(binaryList);
        disabledForBinary.addAll(hexList);
        Set<Integer> disableForOctal = new HashSet(octalList);
        disableForOctal.addAll(hexList);
        this.mDisabledViewIds = new HashMap();
        this.mDisabledViewIds.put(Base.DECIMAL, new HashSet(hexList));
        this.mDisabledViewIds.put(Base.BINARY, disabledForBinary);
        this.mDisabledViewIds.put(Base.HEXADECIMAL, new HashSet());
        this.mDisabledViewIds.put(Base.OCTAL, disableForOctal);
        this.mBasicViewIds = new HashSet();
        this.mBasicViewIds.addAll(binaryList);
        this.mHexViewIds = new HashSet();
        this.mHexViewIds.addAll(hexList);
        this.mOctViewIds = new HashSet();
        this.mOctViewIds.addAll(octalList);
        setNumberBase(this.mBase);
    }

    public void setNumberBase(Base base) {
        this.mBase = base;
    }

    public Set<Integer> getViewIds() {
        HashSet<Integer> set = new HashSet();
        set.addAll(this.mBasicViewIds);
        set.addAll(this.mHexViewIds);
        set.addAll(this.mOctViewIds);
        return set;
    }

    public boolean isViewDisabled(int viewResId) {
        return ((Set) this.mDisabledViewIds.get(this.mBase)).contains(Integer.valueOf(viewResId));
    }
}
