package com.isa.tasktrackerbackend.mapper;

public interface Mapper<F, T> {
    T mapTo(F f);

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
