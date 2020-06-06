package com.library.app.author.repository;

import com.library.app.author.model.Author;
import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.AuthorFilter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AuthorRepository {

    @PersistenceContext
    EntityManager em;

    public Author add(Author author) {
        em.persist(author);
        return author;
    }

    public Author findById(Long id) {
        if(id==null){
            return null;
        }
        return em.find(Author.class, id);
    }

    public void update(Author author) {
        em.merge(author);
    }

    public Object existById(Long id) {
        return em.createQuery("select  1 from Author e where e.id = :id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList().size() > 0;
    }

    @SuppressWarnings("unchecked")
    public PaginatedData<Author> findByFilter(AuthorFilter filter){
        List<Author> authors = em.createQuery("select e FROM Author e order by e.name").getResultList();
        return new PaginatedData<Author>(authors.size(),authors);
    }

}
