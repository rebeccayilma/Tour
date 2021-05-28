package com.example.tour.functional;

import java.util.List;

@FunctionalInterface
public interface PentFunction<S,T,U,V,W,R> {
    R apply(S s, T t, U u, V v, W w);
}
