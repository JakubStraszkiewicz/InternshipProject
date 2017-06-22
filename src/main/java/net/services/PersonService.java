/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.services;

import java.util.List;
import net.model.Person;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import net.controllers.personController;

/**
 *
 * @author Jakub
 */
@Service
public class PersonService extends EntityService<Person> {
    
    public PersonService(EntityManager em) {
        super(em, Person.class, Person::getId);
    }
      
    public List<Person> findAll() {     
        return em.createNamedQuery(Person.FIND_ALL, Person.class).getResultList();
    }
    
    public int howManyForSortedList(int page,String sortedColumn,Person person) {
        int personOnPage = (int)personController.personOnPage;
        String queryText = "SELECT p FROM Person AS p WHERE LOWER(p.firstName) LIKE :firstName AND "
                + "LOWER(p.lastName) LIKE :lastName AND LOWER(p.email) LIKE :email ORDER BY p." + sortedColumn;
        
        Query query = em.createQuery(queryText,Person.class);       
        query.setParameter("firstName", "%" + person.getFirstName().toLowerCase() + "%");
        query.setParameter("lastName", "%" + person.getLastName().toLowerCase() + "%");
        query.setParameter("email", "%" + person.getEmail().toLowerCase() + "%");
        System.out.print(person.getLastName().toLowerCase());
        return query.getResultList().size();
    }
    
     public List<Person> findSome(int page) {
        int personOnPage = (int)personController.personOnPage;
        
        Query query = em.createNamedQuery(Person.FIND_ALL, Person.class);
        query.setFirstResult(personOnPage*(page-1));
        query.setMaxResults(personOnPage*(page-1)+personOnPage);
        
        return query.getResultList();
    }
     
    public List<Person> getSortedList(int page,String sortedColumn,Person person) {
        int personOnPage = (int)personController.personOnPage;
        String queryText = "SELECT p FROM Person AS p WHERE LOWER(p.firstName) LIKE :firstName AND "
                + "LOWER(p.lastName) LIKE :lastName AND LOWER(p.email) LIKE :email ORDER BY p." + sortedColumn;
        
        Query query = em.createQuery(queryText,Person.class);       
        query.setParameter("firstName", "%" + person.getFirstName().toLowerCase() + "%");
        query.setParameter("lastName", "%" + person.getLastName().toLowerCase() + "%");
        query.setParameter("email", "%" + person.getEmail().toLowerCase() + "%");

        query.setFirstResult(personOnPage*(page-1));
        query.setMaxResults(personOnPage*(page-1)+personOnPage);
        
        return query.getResultList();
    }   
}
