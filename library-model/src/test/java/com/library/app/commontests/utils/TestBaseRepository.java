package com.library.app.commontests.utils;

import com.library.app.category.repository.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Ignore
public class TestBaseRepository {
    private EntityManagerFactory emf;
    protected EntityManager em;
    protected DBCommandTransactionalExecutor dbCommandExecutor;


    protected void initializeTestDB() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();



        dbCommandExecutor = new DBCommandTransactionalExecutor(em);
    }


    protected void closeEntityManager() {
        em.close();
        emf.close();
    }

}
