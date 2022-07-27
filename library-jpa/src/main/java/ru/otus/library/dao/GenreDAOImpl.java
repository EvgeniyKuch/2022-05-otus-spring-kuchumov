package ru.otus.library.dao;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class GenreDAOImpl implements GenreDAO {

    @PersistenceContext
    private final EntityManager em;

    public GenreDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public boolean existsById(Long id) {
        return em.createQuery("select count(g) = 1 from Genre g where g.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }
}
