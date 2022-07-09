package ru.otus.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return em.createQuery("select count(a) = 1 from Author a where a.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }
}
