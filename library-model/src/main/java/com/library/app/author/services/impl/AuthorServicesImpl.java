package com.library.app.author.services.impl;

import com.library.app.author.exception.AuthorNotFoundException;
import com.library.app.author.model.Author;
import com.library.app.author.repository.AuthorRepository;
import com.library.app.author.services.AuthorServices;
import com.library.app.category.model.Category;
import com.library.app.common.exception.FieldNotValidException;
import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.AuthorFilter;

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
        validateAuthorFields(author);

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
        return null;
    }

    @Override
    public PaginatedData<Author> findByFilter(AuthorFilter authorFilter) {
        return null;
    }


    private void validateAuthorFields( Author author) {
         Set<ConstraintViolation<Author>> errors = validator.validate(author);
         Iterator<ConstraintViolation<Author>> itErrors = errors.iterator();
        if (itErrors.hasNext()) {
             ConstraintViolation<Author> violation = itErrors.next();
            throw new FieldNotValidException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }
}
