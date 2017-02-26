package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentMap;

@Beta
public final class Interners {

    static class 1 implements Interner<E> {
        final /* synthetic */ ConcurrentMap val$map;

        1(ConcurrentMap concurrentMap) {
            this.val$map = concurrentMap;
        }

        public E intern(E sample) {
            E canonical = this.val$map.putIfAbsent(Preconditions.checkNotNull(sample), sample);
            return canonical == null ? sample : canonical;
        }
    }

    private static class InternerFunction<E> implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner) {
            this.interner = interner;
        }

        public E apply(E input) {
            return this.interner.intern(input);
        }

        public int hashCode() {
            return this.interner.hashCode();
        }

        public boolean equals(Object other) {
            if (!(other instanceof InternerFunction)) {
                return false;
            }
            return this.interner.equals(((InternerFunction) other).interner);
        }
    }

    private static class WeakInterner<E> implements Interner<E> {
        private final MapMakerInternalMap<E, Dummy> map;

        private enum Dummy {
            VALUE
        }

        private WeakInterner() {
            this.map = new MapMaker().weakKeys().keyEquivalence(Equivalence.equals()).makeCustomMap();
        }

        public E intern(E sample) {
            do {
                ReferenceEntry<E, Dummy> entry = this.map.getEntry(sample);
                if (entry != null) {
                    E canonical = entry.getKey();
                    if (canonical != null) {
                        return canonical;
                    }
                }
            } while (((Dummy) this.map.putIfAbsent(sample, Dummy.VALUE)) != null);
            return sample;
        }
    }

    private Interners() {
    }

    public static <E> Interner<E> newStrongInterner() {
        return new 1(new MapMaker().makeMap());
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return new WeakInterner();
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }
}
