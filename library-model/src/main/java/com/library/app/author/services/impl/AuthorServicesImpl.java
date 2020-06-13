package com.library.app.author.services.impl;

import com.library.app.author.exception.AuthorNotFoundException;
import com.library.app.author.model.Author;
import com.library.app.author.repository.AuthorRepository;
import com.library.app.author.services.AuthorServices;
import com.library.app.category.model.Category;
import com.library.app.common.exception.FieldNotValidException;
import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.AuthorFilter;
import com.library.app.common.utils.ValidationUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

@Stateless
public class AuthorServicesImpl implements AuthorServices {
    @Inject
    AuthorRepository authorRepository;

    @Inject
    Validator validator;

    @Override
    public Author add(Author author) throws FieldNotValidException {
     ValidationUtils.validateEntityFields(validator,author);

     return authorRepository.add(author);
    }

    @Override
    public void update(Author author) throws FieldNotValidException, AuthorNotFoundException {
        ValidationUtils.validateEntityFields(validator, author);

        if (!authorRepository.existsById(author.getId())) {
            throw new AuthorNotFoundException();
        }

        authorRepository.update(author);
    }

    @Override
    public Author findById(Long id) throws AuthorNotFoundException {
        final Author author = authorRepository.findById(id);
        if (author == null) {
            throw new AuthorNotFoundException();
        }
        return author;
    }

    @Override
    public PaginatedData<Author> findByFilter(AuthorFilter authorFilter) {
        return authorRepository.findByFilter(authorFilter);
    }



}
