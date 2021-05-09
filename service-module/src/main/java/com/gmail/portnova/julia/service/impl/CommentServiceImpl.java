package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.CommentRepository;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.service.CommentService;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.CommentPersistException;
import com.gmail.portnova.julia.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final GeneralConverter<Comment, CommentDTO> commentConverter;

    @Override
    @Transactional
    public List<CommentDTO> findByArticleUuid(String id) {
        UUID articleUuid = UUID.fromString(id);
        List<Comment> comments = commentRepository.findByArticleUuid(articleUuid);
        return comments.stream()
                .map(commentConverter::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO addComment(CommentDTO commentDTO) {
        UUID uuid = UUID.randomUUID();
        commentDTO.setCommentUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date);
        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        if (Objects.nonNull(comment)) {
            commentRepository.persist(comment);
            return commentDTO;
        } else {
            throw new CommentPersistException(String.format("Comment couldn't be saved because article with uuid %s " +
                    "or User with uuid %s doesn't exist", commentDTO.getArticleUuid(), commentDTO.getUserUuid().toString()));
        }
    }
}
