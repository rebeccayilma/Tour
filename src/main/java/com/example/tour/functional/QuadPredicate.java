package com.example.tour.functional;

@FunctionalInterface
public interface QuadPredicate<S,T,U,V> {
    boolean test(S s, T t, U u, V v);
}
