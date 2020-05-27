package com.library.app.commontests.utils;

import com.library.app.category.model.Category;
import org.junit.Ignore;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Ignore
@Stateless
public class TestRepositoryEJB {

    @PersistenceContext
    private EntityManager em;

    private static final List<Class<?>> ENTITIES_TO_REMOVE = Arrays.asList(Category.class);
    
    public void deleteAll(){
        for (Class<?> entityClass:ENTITIES_TO_REMOVE
             ) {
            
        }
    }
    private void deleteAllForEntity(Class<?> entityClass){
        List<Object> rows = em.createQuery("select e from "+ entityClass.getSimpleName() + "e").getResultList();
        for (Object row : rows){
            em.remove(row);
        }
    }
}
