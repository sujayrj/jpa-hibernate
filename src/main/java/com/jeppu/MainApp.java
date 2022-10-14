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

        System.out.println("1------------------------");
        entityManager.getTransaction().begin();
        System.out.println("2------------------------");
        entityManager.persist(person1);
        System.out.println(person1);
        System.out.println("3------------------------");
        person1.setEmail("sujay.jeppu@gmail.com");
        System.out.println("4------------------------");
        entityManager.getTransaction().commit();
        System.out.println("5------------------------");
        entityManager.close();
        System.out.println("6------------------------");
    }
}
