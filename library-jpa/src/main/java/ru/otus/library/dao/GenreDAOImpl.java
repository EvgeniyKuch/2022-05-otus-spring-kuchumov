package ru.otus.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @PersistenceContext
    private final EntityManager em;

    public GenreDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return em.createQuery("select count(g) = 1 from Genre g where g.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }
}
