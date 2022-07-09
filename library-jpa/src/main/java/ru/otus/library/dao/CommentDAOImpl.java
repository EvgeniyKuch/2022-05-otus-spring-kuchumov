package ru.otus.library.dao;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @PersistenceContext
    private final EntityManager em;

    public CommentDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c " +
                "left join fetch c.book b " +
                "left join fetch b.genre g " +
                "left join fetch b.author a ", Comment.class).getResultList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        return em.createQuery("select c from Comment c " +
                "left join fetch c.book b " +
                "left join fetch b.genre g " +
                "left join fetch b.author a " +
                "where c.book.id = :id", Comment.class)
                .setParameter("id", bookId).getResultList();
    }

    @Override
    public int updateContentById(Long id, String content) {
        return em.createQuery("update Comment c set c.content = :newContent where c.id = :id")
                .setParameter("newContent", content)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(em.find(Comment.class, id)).ifPresent(em::remove);
    }

    @Override
    public boolean existsById(Long id) {
        return em.createQuery("select count(c) = 1 from Comment c where c.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }
}
