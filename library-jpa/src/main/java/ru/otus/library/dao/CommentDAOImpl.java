package ru.otus.library.dao;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
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
