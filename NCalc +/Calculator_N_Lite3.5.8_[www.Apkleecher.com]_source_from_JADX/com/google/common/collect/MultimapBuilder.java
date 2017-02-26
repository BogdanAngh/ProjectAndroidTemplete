package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@GwtCompatible
@Beta
public abstract class MultimapBuilder<K0, V0> {
    private static final int DEFAULT_EXPECTED_KEYS = 8;

    public static abstract class MultimapBuilderWithKeys<K0> {
        private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;

        class 1 extends ListMultimapBuilder<K0, Object> {
            final /* synthetic */ int val$expectedValuesPerKey;

            1(int i) {
                this.val$expectedValuesPerKey = i;
            }

            public <K extends K0, V> ListMultimap<K, V> build() {
                return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), new ArrayListSupplier(this.val$expectedValuesPerKey));
            }
        }

        class 2 extends ListMultimapBuilder<K0, Object> {
            2() {
            }

            public <K extends K0, V> ListMultimap<K, V> build() {
                return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), LinkedListSupplier.instance());
            }
        }

        class 3 extends SetMultimapBuilder<K0, Object> {
            final /* synthetic */ int val$expectedValuesPerKey;

            3(int i) {
                this.val$expectedValuesPerKey = i;
            }

            public <K extends K0, V> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new HashSetSupplier(this.val$expectedValuesPerKey));
            }
        }

        class 4 extends SetMultimapBuilder<K0, Object> {
            final /* synthetic */ int val$expectedValuesPerKey;

            4(int i) {
                this.val$expectedValuesPerKey = i;
            }

            public <K extends K0, V> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new LinkedHashSetSupplier(this.val$expectedValuesPerKey));
            }
        }

        class 5 extends SortedSetMultimapBuilder<K0, V0> {
            final /* synthetic */ Comparator val$comparator;

            5(Comparator comparator) {
                this.val$comparator = comparator;
            }

            public <K extends K0, V extends V0> SortedSetMultimap<K, V> build() {
                return Multimaps.newSortedSetMultimap(MultimapBuilderWithKeys.this.createMap(), new TreeSetSupplier(this.val$comparator));
            }
        }

        class 6 extends SetMultimapBuilder<K0, V0> {
            final /* synthetic */ Class val$valueClass;

            6(Class cls) {
                this.val$valueClass = cls;
            }

            public <K extends K0, V extends V0> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new EnumSetSupplier(this.val$valueClass));
            }
        }

        abstract <K extends K0, V> Map<K, Collection<V>> createMap();

        MultimapBuilderWithKeys() {
        }

        public ListMultimapBuilder<K0, Object> arrayListValues() {
            return arrayListValues(DEFAULT_EXPECTED_VALUES_PER_KEY);
        }

        public ListMultimapBuilder<K0, Object> arrayListValues(int expectedValuesPerKey) {
            CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
            return new 1(expectedValuesPerKey);
        }

        public ListMultimapBuilder<K0, Object> linkedListValues() {
            return new 2();
        }

        public SetMultimapBuilder<K0, Object> hashSetValues() {
            return hashSetValues(DEFAULT_EXPECTED_VALUES_PER_KEY);
        }

        public SetMultimapBuilder<K0, Object> hashSetValues(int expectedValuesPerKey) {
            CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
            return new 3(expectedValuesPerKey);
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues() {
            return linkedHashSetValues(DEFAULT_EXPECTED_VALUES_PER_KEY);
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues(int expectedValuesPerKey) {
            CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
            return new 4(expectedValuesPerKey);
        }

        public SortedSetMultimapBuilder<K0, Comparable> treeSetValues() {
            return treeSetValues(Ordering.natural());
        }

        public <V0> SortedSetMultimapBuilder<K0, V0> treeSetValues(Comparator<V0> comparator) {
            Preconditions.checkNotNull(comparator, "comparator");
            return new 5(comparator);
        }

        public <V0 extends Enum<V0>> SetMultimapBuilder<K0, V0> enumSetValues(Class<V0> valueClass) {
            Preconditions.checkNotNull(valueClass, "valueClass");
            return new 6(valueClass);
        }
    }

    static class 1 extends MultimapBuilderWithKeys<Object> {
        final /* synthetic */ int val$expectedKeys;

        1(int i) {
            this.val$expectedKeys = i;
        }

        <K, V> Map<K, Collection<V>> createMap() {
            return new HashMap(this.val$expectedKeys);
        }
    }

    static class 2 extends MultimapBuilderWithKeys<Object> {
        final /* synthetic */ int val$expectedKeys;

        2(int i) {
            this.val$expectedKeys = i;
        }

        <K, V> Map<K, Collection<V>> createMap() {
            return new LinkedHashMap(this.val$expectedKeys);
        }
    }

    static class 3 extends MultimapBuilderWithKeys<K0> {
        final /* synthetic */ Comparator val$comparator;

        3(Comparator comparator) {
            this.val$comparator = comparator;
        }

        <K extends K0, V> Map<K, Collection<V>> createMap() {
            return new TreeMap(this.val$comparator);
        }
    }

    static class 4 extends MultimapBuilderWithKeys<K0> {
        final /* synthetic */ Class val$keyClass;

        4(Class cls) {
            this.val$keyClass = cls;
        }

        <K extends K0, V> Map<K, Collection<V>> createMap() {
            return new EnumMap(this.val$keyClass);
        }
    }

    private static final class ArrayListSupplier<V> implements Supplier<List<V>>, Serializable {
        private final int expectedValuesPerKey;

        ArrayListSupplier(int expectedValuesPerKey) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        }

        public List<V> get() {
            return new ArrayList(this.expectedValuesPerKey);
        }
    }

    private static final class EnumSetSupplier<V extends Enum<V>> implements Supplier<Set<V>>, Serializable {
        private final Class<V> clazz;

        EnumSetSupplier(Class<V> clazz) {
            this.clazz = (Class) Preconditions.checkNotNull(clazz);
        }

        public Set<V> get() {
            return EnumSet.noneOf(this.clazz);
        }
    }

    private static final class HashSetSupplier<V> implements Supplier<Set<V>>, Serializable {
        private final int expectedValuesPerKey;

        HashSetSupplier(int expectedValuesPerKey) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        }

        public Set<V> get() {
            return new HashSet(this.expectedValuesPerKey);
        }
    }

    private static final class LinkedHashSetSupplier<V> implements Supplier<Set<V>>, Serializable {
        private final int expectedValuesPerKey;

        LinkedHashSetSupplier(int expectedValuesPerKey) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        }

        public Set<V> get() {
            return new LinkedHashSet(this.expectedValuesPerKey);
        }
    }

    private enum LinkedListSupplier implements Supplier<List<Object>> {
        INSTANCE;

        public static <V> Supplier<List<V>> instance() {
            return INSTANCE;
        }

        public List<Object> get() {
            return new LinkedList();
        }
    }

    public static abstract class ListMultimapBuilder<K0, V0> extends MultimapBuilder<K0, V0> {
        public abstract <K extends K0, V extends V0> ListMultimap<K, V> build();

        ListMultimapBuilder() {
            super();
        }

        public <K extends K0, V extends V0> ListMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (ListMultimap) super.build(multimap);
        }
    }

    public static abstract class SetMultimapBuilder<K0, V0> extends MultimapBuilder<K0, V0> {
        public abstract <K extends K0, V extends V0> SetMultimap<K, V> build();

        SetMultimapBuilder() {
            super();
        }

        public <K extends K0, V extends V0> SetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SetMultimap) super.build(multimap);
        }
    }

    public static abstract class SortedSetMultimapBuilder<K0, V0> extends SetMultimapBuilder<K0, V0> {
        public abstract <K extends K0, V extends V0> SortedSetMultimap<K, V> build();

        SortedSetMultimapBuilder() {
        }

        public <K extends K0, V extends V0> SortedSetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SortedSetMultimap) super.build((Multimap) multimap);
        }
    }

    private static final class TreeSetSupplier<V> implements Supplier<SortedSet<V>>, Serializable {
        private final Comparator<? super V> comparator;

        TreeSetSupplier(Comparator<? super V> comparator) {
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
        }

        public SortedSet<V> get() {
            return new TreeSet(this.comparator);
        }
    }

    public abstract <K extends K0, V extends V0> Multimap<K, V> build();

    private MultimapBuilder() {
    }

    public static MultimapBuilderWithKeys<Object> hashKeys() {
        return hashKeys(DEFAULT_EXPECTED_KEYS);
    }

    public static MultimapBuilderWithKeys<Object> hashKeys(int expectedKeys) {
        CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
        return new 1(expectedKeys);
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys() {
        return linkedHashKeys(DEFAULT_EXPECTED_KEYS);
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys(int expectedKeys) {
        CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
        return new 2(expectedKeys);
    }

    public static MultimapBuilderWithKeys<Comparable> treeKeys() {
        return treeKeys(Ordering.natural());
    }

    public static <K0> MultimapBuilderWithKeys<K0> treeKeys(Comparator<K0> comparator) {
        Preconditions.checkNotNull(comparator);
        return new 3(comparator);
    }

    public static <K0 extends Enum<K0>> MultimapBuilderWithKeys<K0> enumKeys(Class<K0> keyClass) {
        Preconditions.checkNotNull(keyClass);
        return new 4(keyClass);
    }

    public <K extends K0, V extends V0> Multimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        Multimap<K, V> result = build();
        result.putAll(multimap);
        return result;
    }
}
