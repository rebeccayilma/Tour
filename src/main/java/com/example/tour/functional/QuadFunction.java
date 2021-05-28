package com.example.tour.functional;

@FunctionalInterface
public interface QuadFunction<S,T,U,V,R> {
    R apply(S s, T t, U u, V v);
}
