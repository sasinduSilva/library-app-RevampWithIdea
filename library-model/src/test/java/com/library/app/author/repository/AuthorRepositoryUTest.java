package com.library.app.author.repository;

import static com.library.app.commontests.author.AuthorForTestRepository.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.AuthorFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.app.author.model.Author;
import com.library.app.commontests.utils.DBCommandTransactionalExecutor;

public class AuthorRepositoryUTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private DBCommandTransactionalExecutor dbCommandExecutor;
	private AuthorRepository authorRepository;

	@Before
	public void initTestCase() {
		emf = Persistence.createEntityManagerFactory("libraryPU");
		em = emf.createEntityManager();

		authorRepository = new AuthorRepository();
		authorRepository.em = em;

		dbCommandExecutor = new DBCommandTransactionalExecutor(em);
	}

	@After
	public void closeEntityManager() {
		em.close();
		emf.close();
	}

	@Test
	public void addAuthorAndFindIt() {
		final Long authorAddedId = dbCommandExecutor.executeCommand(() -> {
			return authorRepository.add(robertMartin()).getId();
		});
		assertThat(authorAddedId, is(notNullValue()));

		final Author author = authorRepository.findById(authorAddedId);
		assertThat(author, is(notNullValue()));
		assertThat(author.getName(), is(equalTo(robertMartin().getName())));
	}

	@Test
	public void findAuthorByIdNotFound() {
		final Author author = authorRepository.findById(999L);
		assertThat(author, is(nullValue()));
	}

	@Test
	public void updateAuthor() {
		final Long authorAddedId = dbCommandExecutor.executeCommand(() -> {
			return authorRepository.add(robertMartin()).getId();
		});
		assertThat(authorAddedId, is(notNullValue()));

		final Author author = authorRepository.findById(authorAddedId);
		assertThat(author.getName(), is(equalTo(robertMartin().getName())));

		author.setName("Uncle Bob");
		dbCommandExecutor.executeCommand(() -> {
			authorRepository.update(author);
			return null;
		});

		final Author authorAfterUpdate = authorRepository.findById(authorAddedId);
		assertThat(authorAfterUpdate.getName(), is(equalTo("Uncle Bob")));
	}

	@Test
	public void existsById() {
		final Long authorAddedId = dbCommandExecutor.executeCommand(() -> {
			return authorRepository.add(robertMartin()).getId();
		});

		assertThat(authorRepository.existById(authorAddedId), is(equalTo(true)));
		assertThat(authorRepository.existById(999l), is(equalTo(false)));
	}
	@Test
	public void findByFilterNoFilter(){
		loadDataForFindbyFilter();

		PaginatedData<Author> result = authorRepository.findByFilter(new AuthorFilter());
		assertThat(result.getNumberOfRows(), is(equalTo(4)));
		assertThat(result.getRows().size(), is(equalTo(4)));
		assertThat(result.getRow(0).getName(), is(equalTo(erichGamma().getName())));
		assertThat(result.getRow(1).getName(), is(equalTo(jamesGosling().getName())));
		assertThat(result.getRow(2).getName(), is(equalTo(martinFowler().getName())));
		assertThat(result.getRow(3).getName(), is(equalTo(robertMartin().getName())));

	}

	private void loadDataForFindbyFilter(){
		dbCommandExecutor.executeCommand(() -> {
			authorRepository.add(robertMartin());
			authorRepository.add(jamesGosling());
			authorRepository.add(martinFowler());
			authorRepository.add(erichGamma());
			return null;
		});
	}


}