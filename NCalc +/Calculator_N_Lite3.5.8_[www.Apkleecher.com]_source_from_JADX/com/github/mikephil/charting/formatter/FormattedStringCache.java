package com.github.mikephil.charting.formatter;

import io.github.kexanie.library.BuildConfig;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;

public class FormattedStringCache {
    protected Format mFormat;

    public static class Generic<K, V> extends FormattedStringCache {
        private HashMap<K, String> mCachedStringsHashMap;
        private HashMap<K, V> mCachedValuesHashMap;

        public Generic(Format format) {
            super(format);
            this.mCachedStringsHashMap = new HashMap();
            this.mCachedValuesHashMap = new HashMap();
        }

        public String getFormattedValue(V value, K key) {
            if (!this.mCachedValuesHashMap.containsKey(key)) {
                this.mCachedStringsHashMap.put(key, this.mFormat.format(value));
                this.mCachedValuesHashMap.put(key, value);
            }
            if (!value.equals(this.mCachedValuesHashMap.get(key))) {
                this.mCachedStringsHashMap.put(key, this.mFormat.format(value));
                this.mCachedValuesHashMap.put(key, value);
            }
            return (String) this.mCachedStringsHashMap.get(key);
        }
    }

    public static class PrimDouble extends FormattedStringCache {
        private ArrayList<String> cachedStrings;
        private ArrayList<Double> cachedValues;

        public PrimDouble(Format format) {
            super(format);
            this.cachedValues = new ArrayList();
            this.cachedStrings = new ArrayList();
        }

        public String getFormattedValue(double value) {
            boolean alreadyHasValue = false;
            int vCount = this.cachedValues.size();
            int sIndex = -1;
            for (int i = 0; i < vCount; i++) {
                if (((Double) this.cachedValues.get(i)).doubleValue() == value) {
                    sIndex = i;
                    alreadyHasValue = true;
                    break;
                }
            }
            if (!alreadyHasValue) {
                this.cachedValues.add(Double.valueOf(value));
                this.cachedStrings.add(this.mFormat.format(Double.valueOf(value)));
                sIndex = this.cachedValues.size() - 1;
            }
            return (String) this.cachedStrings.get(sIndex);
        }
    }

    public static class PrimFloat extends FormattedStringCache {
        private ArrayList<String> cachedStrings;
        private ArrayList<Float> cachedValues;

        public PrimFloat(Format format) {
            super(format);
            this.cachedValues = new ArrayList();
            this.cachedStrings = new ArrayList();
        }

        public String getFormattedValue(float value) {
            boolean alreadyHasValue = false;
            int vCount = this.cachedValues.size();
            int sIndex = -1;
            for (int i = 0; i < vCount; i++) {
                if (((Float) this.cachedValues.get(i)).floatValue() == value) {
                    sIndex = i;
                    alreadyHasValue = true;
                    break;
                }
            }
            if (!alreadyHasValue) {
                this.cachedValues.add(Float.valueOf(value));
                this.cachedStrings.add(this.mFormat.format(Float.valueOf(value)));
                sIndex = this.cachedValues.size() - 1;
            }
            return (String) this.cachedStrings.get(sIndex);
        }
    }

    public static class PrimIntFloat extends FormattedStringCache {
        private ArrayList<String> cachedStrings;
        private ArrayList<Float> cachedValues;

        public PrimIntFloat(Format format) {
            super(format);
            this.cachedValues = new ArrayList();
            this.cachedStrings = new ArrayList();
        }

        public String getFormattedValue(float value, int key) {
            boolean hasValueAtdataSetIndex = true;
            if (this.cachedValues.size() <= key) {
                for (int p = key; p >= 0; p--) {
                    if (p == 0) {
                        this.cachedValues.add(Float.valueOf(value));
                        this.cachedStrings.add(BuildConfig.FLAVOR);
                    } else {
                        this.cachedValues.add(Float.valueOf(Float.NaN));
                        this.cachedStrings.add(BuildConfig.FLAVOR);
                    }
                }
                hasValueAtdataSetIndex = false;
            }
            if (hasValueAtdataSetIndex) {
                Float cachedValue = (Float) this.cachedValues.get(key);
                hasValueAtdataSetIndex = cachedValue != null && cachedValue.floatValue() == value;
            }
            if (!hasValueAtdataSetIndex) {
                this.cachedValues.set(key, Float.valueOf(value));
                this.cachedStrings.set(key, this.mFormat.format(Float.valueOf(value)));
            }
            return (String) this.cachedStrings.get(key);
        }
    }

    public Format getFormat() {
        return this.mFormat;
    }

    public FormattedStringCache(Format format) {
        this.mFormat = format;
    }
}
