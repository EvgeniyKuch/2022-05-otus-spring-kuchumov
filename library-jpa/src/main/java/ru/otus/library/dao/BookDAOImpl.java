package ru.otus.library.dao;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private final EntityManager em;

    public BookDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteBookById(Long id) {
        Optional.ofNullable(em.find(Book.class, id)).ifPresent(em::remove);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b " +
                "left join fetch b.author " +
                "left join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return em.createQuery("select count(b) = 1 from Book b where b.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }
}
