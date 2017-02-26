package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.SortedLists.KeyAbsentBehavior;
import com.google.common.collect.SortedLists.KeyPresentBehavior;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtIncompatible("NavigableMap")
@Beta
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V> {
    private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY;
    private final ImmutableList<Range<K>> ranges;
    private final ImmutableList<V> values;

    class 1 extends ImmutableList<Range<K>> {
        final /* synthetic */ int val$len;
        final /* synthetic */ int val$off;
        final /* synthetic */ Range val$range;

        1(int i, int i2, Range range) {
            this.val$len = i;
            this.val$off = i2;
            this.val$range = range;
        }

        public int size() {
            return this.val$len;
        }

        public Range<K> get(int index) {
            Preconditions.checkElementIndex(index, this.val$len);
            if (index == 0 || index == this.val$len - 1) {
                return ((Range) ImmutableRangeMap.this.ranges.get(this.val$off + index)).intersection(this.val$range);
            }
            return (Range) ImmutableRangeMap.this.ranges.get(this.val$off + index);
        }

        boolean isPartialView() {
            return true;
        }
    }

    class 2 extends ImmutableRangeMap<K, V> {
        final /* synthetic */ ImmutableRangeMap val$outer;
        final /* synthetic */ Range val$range;

        2(ImmutableList x0, ImmutableList x1, Range range, ImmutableRangeMap immutableRangeMap) {
            this.val$range = range;
            this.val$outer = immutableRangeMap;
            super(x0, x1);
        }

        public /* bridge */ /* synthetic */ Map asMapOfRanges() {
            return super.asMapOfRanges();
        }

        public ImmutableRangeMap<K, V> subRangeMap(Range<K> subRange) {
            if (this.val$range.isConnected(subRange)) {
                return this.val$outer.subRangeMap(subRange.intersection(this.val$range));
            }
            return ImmutableRangeMap.of();
        }
    }

    public static final class Builder<K extends Comparable<?>, V> {
        private final RangeSet<K> keyRanges;
        private final RangeMap<K, V> rangeMap;

        public Builder() {
            this.keyRanges = TreeRangeSet.create();
            this.rangeMap = TreeRangeMap.create();
        }

        public Builder<K, V> put(Range<K> range, V value) {
            boolean z;
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(value);
            if (range.isEmpty()) {
                z = false;
            } else {
                z = true;
            }
            Preconditions.checkArgument(z, "Range must not be empty, but was %s", range);
            if (!this.keyRanges.complement().encloses(range)) {
                for (Entry<Range<K>, V> entry : this.rangeMap.asMapOfRanges().entrySet()) {
                    Range<K> key = (Range) entry.getKey();
                    if (key.isConnected(range) && !key.intersection(range).isEmpty()) {
                        throw new IllegalArgumentException("Overlapping ranges: range " + range + " overlaps with entry " + entry);
                    }
                }
            }
            this.keyRanges.add(range);
            this.rangeMap.put(range, value);
            return this;
        }

        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Entry<Range<K>, ? extends V> entry : rangeMap.asMapOfRanges().entrySet()) {
                put((Range) entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableRangeMap<K, V> build() {
            Map<Range<K>, V> map = this.rangeMap.asMapOfRanges();
            com.google.common.collect.ImmutableList.Builder<Range<K>> rangesBuilder = new com.google.common.collect.ImmutableList.Builder(map.size());
            com.google.common.collect.ImmutableList.Builder<V> valuesBuilder = new com.google.common.collect.ImmutableList.Builder(map.size());
            for (Entry<Range<K>, V> entry : map.entrySet()) {
                rangesBuilder.add(entry.getKey());
                valuesBuilder.add(entry.getValue());
            }
            return new ImmutableRangeMap(rangesBuilder.build(), valuesBuilder.build());
        }
    }

    static {
        EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return EMPTY;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V value) {
        return new ImmutableRangeMap(ImmutableList.of(range), ImmutableList.of(value));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map<Range<K>, ? extends V> map = rangeMap.asMapOfRanges();
        com.google.common.collect.ImmutableList.Builder<Range<K>> rangesBuilder = new com.google.common.collect.ImmutableList.Builder(map.size());
        com.google.common.collect.ImmutableList.Builder<V> valuesBuilder = new com.google.common.collect.ImmutableList.Builder(map.size());
        for (Entry<Range<K>, ? extends V> entry : map.entrySet()) {
            rangesBuilder.add(entry.getKey());
            valuesBuilder.add(entry.getValue());
        }
        return new ImmutableRangeMap(rangesBuilder.build(), valuesBuilder.build());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder();
    }

    ImmutableRangeMap(ImmutableList<Range<K>> ranges, ImmutableList<V> values) {
        this.ranges = ranges;
        this.values = values;
    }

    @Nullable
    public V get(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        if (index != -1 && ((Range) this.ranges.get(index)).contains(key)) {
            return this.values.get(index);
        }
        return null;
    }

    @Nullable
    public Entry<Range<K>, V> getEntry(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_LOWER);
        if (index == -1) {
            return null;
        }
        Range<K> range = (Range) this.ranges.get(index);
        if (range.contains(key)) {
            return Maps.immutableEntry(range, this.values.get(index));
        }
        return null;
    }

    public Range<K> span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.create(((Range) this.ranges.get(0)).lowerBound, ((Range) this.ranges.get(this.ranges.size() - 1)).upperBound);
    }

    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.of();
        }
        return new RegularImmutableSortedMap(new RegularImmutableSortedSet(this.ranges, Range.RANGE_LEX_ORDERING), this.values);
    }

    public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        if (this.ranges.isEmpty() || range.encloses(span())) {
            return this;
        }
        int lowerIndex = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
        int upperIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
        if (lowerIndex >= upperIndex) {
            return of();
        }
        int len = upperIndex - lowerIndex;
        return new 2(new 1(len, lowerIndex, range), this.values.subList(lowerIndex, upperIndex), range, this);
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof RangeMap)) {
            return false;
        }
        return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
    }

    public String toString() {
        return asMapOfRanges().toString();
    }
}
