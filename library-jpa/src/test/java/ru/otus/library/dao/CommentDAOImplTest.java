package ru.otus.library.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(CommentDAOImpl.class)
class CommentDAOImplTest {

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    TestEntityManager em;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnAll() {
        assertThat(commentDAO.findAll())
                .usingRecursiveComparison()
                .isEqualTo(getAllComments());
    }

    @Test
    void shouldReturnById() {
        assertThat(commentDAO.findById(1L)).isPresent().get()
                .usingRecursiveComparison().isEqualTo(getComment(1L));
    }

    @Test
    void shouldReturnAllByBookId() {
        assertThat(commentDAO.findAllByBookId(1L)).usingRecursiveComparison()
                .isEqualTo(em.find(Book.class, 1L).getComments());
    }

    @Test
    void updateContentById() {
        commentDAO.updateContentById(1L, "Не понравилось");
        assertThat(getComment(1L).getContent()).isEqualTo("Не понравилось");
    }

    @Test
    void shouldInsertNewCommentAndReturnItWithId() {
        commentDAO.save(newComment());
        assertThat(getComment(3L))
                .usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(newComment());
    }

    @Test
    void shouldUpdateExistedCommentAndReturnIt() {
        Comment comment = getComment(2L).setContent("Захватывает дух");
        commentDAO.save(comment);
        assertThat(getComment(2L)).usingRecursiveComparison()
                .isEqualTo(comment);
    }

    @Test
    void shouldDeleteById() {
        commentDAO.deleteById(1L);
        assertThat(commentDAO.existsById(1L)).isFalse();
    }

    @Test
    void shouldCheckExistsById() {
        assertThat(commentDAO.existsById(2L)).isTrue();
        assertThat(commentDAO.existsById(3L)).isFalse();
    }

    private Comment getComment(Long id) {
        return em.find(Comment.class, id);
    }

    private List<Comment> getAllComments() {
        return em.getEntityManager().createQuery("select c from Comment c", Comment.class).getResultList();
    }

    private Comment newComment() {
        return new Comment()
                .setBook(em.find(Book.class, 1L))
                .setContent("Книга вкусно пахнет");
    }
}
