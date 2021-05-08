package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class CommentConverterImpl implements GeneralConverter<Comment, CommentDTO> {
    @Override
    public CommentDTO convertObjectToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUuid(comment.getUuid());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setContent(comment.getContent());
        User user = comment.getUser();
        if (Objects.nonNull(user)) {
            String userLastAndFirstName = String.join(" ", user.getLastName(), user.getFirstName());
            commentDTO.setUserLastAndFirstName(userLastAndFirstName);
        }
        return commentDTO;
    }

    @Override
    public Comment convertDTOToObject(CommentDTO object) {
        throw new UnsupportedOperationException("This method hasn't been implemented");
    }
}
