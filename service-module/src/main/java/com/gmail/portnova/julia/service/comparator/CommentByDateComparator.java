package com.gmail.portnova.julia.service.comparator;

import com.gmail.portnova.julia.service.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
public class CommentByDateComparator implements Comparator<CommentDTO> {
    @Override
    public int compare(CommentDTO o1, CommentDTO o2) {
        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
    }
}
