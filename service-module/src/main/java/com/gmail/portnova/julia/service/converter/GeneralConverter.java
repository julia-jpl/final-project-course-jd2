package com.gmail.portnova.julia.service.converter;

public interface GeneralConverter<T, V> {

    V convertObjectToDTO(T object);

    T convertDTOToObject(V object);
}
