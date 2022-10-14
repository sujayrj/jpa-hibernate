package com.jeppu;

import com.jeppu.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Date;

/**
 * Hello world!
 */
public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernatedemo");
        EntityManager entityManager = factory.createEntityManager();

        Person person1 = new Person("Sujay", "Jeppu", "sujay@gmail.com", new Date(), LocalDate.of(2022, 10, 1), 10L);

        entityManager.getTransaction().begin();
        entityManager.persist(person1);
        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
