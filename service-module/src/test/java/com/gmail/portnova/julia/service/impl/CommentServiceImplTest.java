package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.repository.CommentRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.service.converter.GeneralConverter;
import com.gmail.portnova.julia.service.exception.ArticleNotFoundException;
import com.gmail.portnova.julia.service.exception.CommentNotFoundException;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private GeneralConverter<Comment, CommentDTO> commentConverter;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void shouldFindCommentByArticleUuidAndReturnEmptyList() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        List<Comment> comments = Collections.emptyList();
        when(commentRepository.findByArticleUuid(uuid)).thenReturn(comments);

        List<CommentDTO> foundCommentsDTO = commentService.findByArticleUuid(id);
        assertEquals(Collections.emptyList(), foundCommentsDTO);
    }
    @Test
    void shouldFindCommentByArticleUuid() {
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Comment comment = new Comment();
        List<Comment> comments = Collections.singletonList(comment);
        when(commentRepository.findByArticleUuid(uuid)).thenReturn(comments);

        CommentDTO commentDTO = new CommentDTO();
        when(commentConverter.convertObjectToDTO(comment)).thenReturn(commentDTO);

        List<CommentDTO> foundCommentsDTO = commentService.findByArticleUuid(id);
        assertEquals(commentDTO, foundCommentsDTO.get(0));
    }
    @Test
    void shouldDeleteCommentByUuid(){
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);
        Comment comment = new Comment();
        comment.setUuid(uuid);
        when(commentRepository.findByUuid(uuid)).thenReturn(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentUuid(uuid);
        when(commentConverter.convertObjectToDTO(comment)).thenReturn(commentDTO);

        CommentDTO result = commentService.deleteCommentByUuid(id);
        assertEquals(uuid, result.getCommentUuid());
    }
    @Test
    void shouldNotDeleteCommentByUuid(){
        String id = "1cc8a402-aaaa-11eb-bcbc-0242ac135502";
        UUID uuid = UUID.fromString(id);

        when(commentRepository.findByUuid(uuid)).thenReturn(null);
        assertThrows(CommentNotFoundException.class, () -> commentService.deleteCommentByUuid(id));
    }
}