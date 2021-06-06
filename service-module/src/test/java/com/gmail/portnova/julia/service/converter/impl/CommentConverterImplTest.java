package com.gmail.portnova.julia.service.converter.impl;

import com.gmail.portnova.julia.repository.ArticleRepository;
import com.gmail.portnova.julia.repository.UserRepository;
import com.gmail.portnova.julia.repository.model.Article;
import com.gmail.portnova.julia.repository.model.Comment;
import com.gmail.portnova.julia.repository.model.User;
import com.gmail.portnova.julia.service.model.ArticleDTO;
import com.gmail.portnova.julia.service.model.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.gmail.portnova.julia.service.constant.TimeFormatterConstant.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentConverterImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private CommentConverterImpl commentConverter;

    @Test
    void shouldConvertCommentToCommentDTOAndReturnRightId() {
        Comment comment = new Comment();
        Long id = 1L;
        comment.setId(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertEquals(id, commentDTO.getId());
    }

    @Test
    void shouldConvertCommentToCommentDTOAndReturnRightUuid() {
        Comment comment = new Comment();
        UUID uuid = UUID.randomUUID();
        comment.setUuid(uuid);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertEquals(uuid, commentDTO.getCommentUuid());
    }

    @Test
    void shouldConvertCommentToCommentDTOAndReturnRightCreatedAt() {
        Comment comment = new Comment();
        LocalDateTime date = LocalDateTime.now();
        comment.setCreatedAt(date);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertEquals(date.format(DATE_TIME_FORMATTER), commentDTO.getCreatedAt());
    }

    @Test
    void shouldConvertCommentToCommentDTOAndReturnRightContent() {
        Comment comment = new Comment();
        String content = "content";
        comment.setContent(content);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertEquals(content, commentDTO.getContent());
    }

    @Test
    void shouldConvertCommentToCommentDTOAndReturnRightUserNameWhenUserIsNotNull() {
        Comment comment = new Comment();
        User user = new User();
        String firstName = "first";
        user.setFirstName(firstName);
        String lastName = "last";
        user.setLastName(lastName);
        comment.setUser(user);
        String lastAndFirstName = String.join(" ", lastName, firstName);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertEquals(lastAndFirstName, commentDTO.getUserLastAndFirstName());
    }
    @Test
    void shouldConvertCommentToCommentDTOAndReturnNotNullObject() {
        Comment comment = new Comment();
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);

        CommentDTO commentDTO = commentConverter.convertObjectToDTO(comment);
        assertNotNull(commentDTO);
    }

    @Test
    void shouldConvertCommentDTOToCommentAndReturnRightUuid() {
        CommentDTO commentDTO = new CommentDTO();
        UUID uuid = UUID.randomUUID();
        commentDTO.setCommentUuid(uuid);
        Article article = new Article();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(uuid, comment.getUuid());
    }

    @Test
    void shouldConvertCommentDTOToCommentAndReturnRightCreatedAt() {
        CommentDTO commentDTO = new CommentDTO();
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(date.format(DATE_TIME_FORMATTER), comment.getCreatedAt().format(DATE_TIME_FORMATTER));
    }

    @Test
    void shouldConvertCommentDTOToCommentAndReturnRightContent() {
        CommentDTO commentDTO = new CommentDTO();
        String content = "content";
        commentDTO.setContent(content);
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(content, comment.getContent());
    }

    @Test
    void shouldConvertCommentDTOToCommentAndReturnUserWithRightUuid() {
        CommentDTO commentDTO = new CommentDTO();
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(uuid, comment.getUser().getUuid());
    }
    @Test
    void shouldConvertCommentDTOToCommentAndReturnArticleWithRightUuid() {
        CommentDTO commentDTO = new CommentDTO();
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(uuid, comment.getArticle().getUuid());
    }
    @Test
    void shouldConvertCommentDTOToCommentAndReturnNotNullObject() {
        CommentDTO commentDTO = new CommentDTO();
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertNotNull(comment);
    }
    @Test
    void shouldConvertCommentDTOToCommentAndReturnRightAuthorWhenUserIsNotNull() {
        CommentDTO commentDTO = new CommentDTO();
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        commentDTO.setArticleUuid(uuid.toString());
        User user = new User();
        user.setUuid(uuid);
        commentDTO.setUserUuid(uuid);
        LocalDateTime date = LocalDateTime.now();
        commentDTO.setCreatedAt(date.format(DATE_TIME_FORMATTER));

        String lastname = "last";
        user.setLastName(lastname);
        String firstname = "first";
        user.setFirstName(firstname);

        String author = String.join(" ", lastname, firstname);

        when(articleRepository.findByUuid(uuid)).thenReturn(article);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        Comment comment = commentConverter.convertDTOToObject(commentDTO);
        assertEquals(author, comment.getAuthor());
    }
}