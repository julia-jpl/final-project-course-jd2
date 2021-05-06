package com.gmail.portnova.julia.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public abstract class PageDTO<T> {
    private Long totalPages;
    private List<T> objects = new ArrayList<>();
}
