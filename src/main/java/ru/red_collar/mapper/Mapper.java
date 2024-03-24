package ru.red_collar.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
