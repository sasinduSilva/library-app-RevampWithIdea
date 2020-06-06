package com.library.app.author.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.library.app.author.model.Author;
import com.library.app.common.model.filter.AuthorFilter.*;
import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.AuthorFilter;

@Stateless
public class AuthorRepository {

    @PersistenceContext
    EntityManager em;

    public Author add(final Author author) {
        em.persist(author);
        return author;
    }

    public Author findById(final Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Author.class, id);
    }

    public void update(final Author author) {
        em.merge(author);
    }

    public boolean existById(final long id) {
        return em.createQuery("Select 1 From Author e where e.id = :id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList().size() > 0;
    }

    @SuppressWarnings("unchecked")
    public PaginatedData<Author> findByFilter(final AuthorFilter filter) {
        final StringBuilder clause = new StringBuilder("WHERE e.id is not null");
        final Map<String, Object> queryParameters = new HashMap<>();
        if (filter.getName() != null) {
            clause.append(" And UPPER(e.name) Like UPPER(:name)");
            queryParameters.put("name", "%" + filter.getName() + "%");
        }

        final StringBuilder clauseSort = new StringBuilder();
        if (filter.hasOrderField()) {
            clauseSort.append("Order by e." + filter.getPaginationData().getOrderFields());
            clauseSort.append(filter.getPaginationData().isAscending() ? " ASC" : " DESC");
        } else {
            clauseSort.append("Order by e.name ASC");
        }

        final Query queryAuthors = em.createQuery("Select e From Author e " + clause.toString() + " "
                + clauseSort.toString());
        applyQueryParametersOnQuery(queryParameters, queryAuthors);
        if (filter.hasPaginationData()) {
            queryAuthors.setFirstResult(filter.getPaginationData().getFirstResult());
            queryAuthors.setMaxResults(filter.getPaginationData().getMaxResults());
        }

        final List<Author> authors = queryAuthors.getResultList();

        final Query queryCount = em.createQuery("Select Count(e) From Author e " + clause.toString());
        applyQueryParametersOnQuery(queryParameters, queryCount);
        final Integer count = ((Long) queryCount.getSingleResult()).intValue();

        return new PaginatedData<Author>(count, authors);
    }

    private void applyQueryParametersOnQuery(final Map<String, Object> queryParameters, final Query query) {
        for (final Entry<String, Object> entryMap : queryParameters.entrySet()) {
            query.setParameter(entryMap.getKey(), entryMap.getValue());
        }
    }

}