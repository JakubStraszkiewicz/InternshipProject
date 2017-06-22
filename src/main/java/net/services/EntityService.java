/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.services;

import java.util.UUID;
import java.util.function.Function;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jakub
 */
abstract public class EntityService<T> {
    
    final EntityManager em;
    private final Class<T> entityClass;
    private final Function<T, Object> idSupplier;
    
    public EntityService(EntityManager em, Class<T> entityClass, Function<T, Object> idSupplier) {
        this.em = em;
        this.entityClass = entityClass;
        this.idSupplier = idSupplier;
    }
    
    @Transactional
    public void save(T entity) {
        if (em.find(entityClass, idSupplier.apply(entity)) == null) {          
            em.persist(entity);
        } else {         
            em.merge(entity);
        }
    }
    
    public T find(UUID id) {
        return em.find(entityClass, id);
    }
}
