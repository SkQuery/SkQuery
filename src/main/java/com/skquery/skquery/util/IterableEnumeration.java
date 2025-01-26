package com.skquery.skquery.util;

import ch.njol.util.coll.iterator.EmptyIterator;

import java.util.Enumeration;
import java.util.Iterator;

public class IterableEnumeration<T> implements Iterable<T> {

    final Enumeration<? extends T> e;

    public IterableEnumeration(final Enumeration<? extends T> e) {
        this.e = e;
    }

    @Override
    public Iterator<T> iterator() {
        final Enumeration<? extends T> e = this.e;
        if (e == null)
            return EmptyIterator.get();
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public T next() {
                return e.nextElement();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
