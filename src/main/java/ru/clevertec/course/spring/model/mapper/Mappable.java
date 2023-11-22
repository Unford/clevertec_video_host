package ru.clevertec.course.spring.model.mapper;

import java.util.Collection;

public interface Mappable<E, D> {
    D toDto(E entity);

    Collection<D> toDto(Collection<E> entities);

    E toEntity(D dto);

    Collection<E> toEntity(Collection<D> dtos);
}
