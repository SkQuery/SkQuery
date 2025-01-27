package com.skquery.skquery.util;

import java.io.Serializable;

public class BiValue<T1, T2> implements Serializable {

    private static final long serialVersionUID = 0L;
    private T1 first;
    private T2 second;

    protected BiValue() {}

    public BiValue(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "" + first + " & " + second + "";
    }

    @SuppressWarnings("rawtypes")
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BiValue biValue = (BiValue) o;

        return !(first != null ? !first.equals(biValue.first) : biValue.first != null) && !(second != null ? !second.equals(biValue.second) : biValue.second != null);

    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
