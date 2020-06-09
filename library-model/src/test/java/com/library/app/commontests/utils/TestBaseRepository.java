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
    protected DBCommandTransactionalExecutor dBCommandTransactionalExecutor;


    protected void initializeTest() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();



        dBCommandTransactionalExecutor = new DBCommandTransactionalExecutor(em);
    }


    protected void closeEntityManager() {
        em.close();
        emf.close();
    }

}
