package com.gmail.portnova.julia.service.converter;

import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.model.UserDTO;

public interface GeneralConverter<T, V> {

    V convertObjectToDTO(T object);

    T convertDTOToObject(V object);
}
