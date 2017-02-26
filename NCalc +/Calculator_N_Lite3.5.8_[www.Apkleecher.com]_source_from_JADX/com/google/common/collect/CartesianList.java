package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
final class CartesianList<E> extends AbstractList<List<E>> implements RandomAccess {
    private final transient ImmutableList<List<E>> axes;
    private final transient int[] axesSizeProduct;

    class 1 extends ImmutableList<E> {
        final /* synthetic */ int val$index;

        1(int i) {
            this.val$index = i;
        }

        public int size() {
            return CartesianList.this.axes.size();
        }

        public E get(int axis) {
            Preconditions.checkElementIndex(axis, size());
            return ((List) CartesianList.this.axes.get(axis)).get(CartesianList.this.getAxisIndexForProductIndex(this.val$index, axis));
        }

        boolean isPartialView() {
            return true;
        }
    }

    static <E> List<List<E>> create(List<? extends List<? extends E>> lists) {
        Builder<List<E>> axesBuilder = new Builder(lists.size());
        for (Collection list : lists) {
            Object copy = ImmutableList.copyOf(list);
            if (copy.isEmpty()) {
                return ImmutableList.of();
            }
            axesBuilder.add(copy);
        }
        return new CartesianList(axesBuilder.build());
    }

    CartesianList(ImmutableList<List<E>> axes) {
        this.axes = axes;
        int[] axesSizeProduct = new int[(axes.size() + 1)];
        axesSizeProduct[axes.size()] = 1;
        try {
            for (int i = axes.size() - 1; i >= 0; i--) {
                axesSizeProduct[i] = IntMath.checkedMultiply(axesSizeProduct[i + 1], ((List) axes.get(i)).size());
            }
            this.axesSizeProduct = axesSizeProduct;
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
    }

    private int getAxisIndexForProductIndex(int index, int axis) {
        return (index / this.axesSizeProduct[axis + 1]) % ((List) this.axes.get(axis)).size();
    }

    public ImmutableList<E> get(int index) {
        Preconditions.checkElementIndex(index, size());
        return new 1(index);
    }

    public int size() {
        return this.axesSizeProduct[0];
    }

    public boolean contains(@Nullable Object o) {
        if (!(o instanceof List)) {
            return false;
        }
        List<?> list = (List) o;
        if (list.size() != this.axes.size()) {
            return false;
        }
        ListIterator<?> itr = list.listIterator();
        while (itr.hasNext()) {
            if (!((List) this.axes.get(itr.nextIndex())).contains(itr.next())) {
                return false;
            }
        }
        return true;
    }
}
