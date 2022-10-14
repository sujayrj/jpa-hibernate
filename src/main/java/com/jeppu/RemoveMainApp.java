package com.jeppu;

import com.jeppu.model.Book;
import com.jeppu.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Date;

/**
 * Hello world!
 */
public class RemoveMainApp {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernatedemo");
    static EntityManager manager = null;

    public static void main(String[] args) {
        //removeTransientNaturalIDObject();
        //removeTransientSurrogateIDObject();
        //removeDetachedObject();
        removeManagedObject();
    }

    private static void removeManagedObject() {
        persist();
        System.out.println("\n --- removeManagedObject() start --- ");
        System.out.println("1----------------------");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        System.out.println("2----------------------");
        //Person person = manager.find(Person.class, 1L);                   //Select query here
        Person person = manager.getReference(Person.class, 1L);          //no select query here
        System.out.println("3----------------------");
        manager.remove(person);
        System.out.println("4----------------------");
        manager.getTransaction().commit();
        manager.close();
        System.out.println("5----------------------");
        System.out.println(" --- removeManagedObject() end --- \n");
    }

    private static void persist() {
        System.out.println("\n --- persist() start --- ");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        Person p1 = new Person("Raj", "Kumar", "raj.kumar@gmail.com", new Date(), LocalDate.of(2023, 10, 19), 44L);
        manager.persist(p1);
        manager.getTransaction().commit();
        System.out.println(" --- persist() end --- \n");
        manager.close();
    }

    //removing detached object will throw error
    private static void removeDetachedObject() {
        System.out.println("\n --- removeDetachedObject() start --- ");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();

        //remove detached surrogate key
        Person p1 = new Person("Raj", "Kumar", "raj.kumar@gmail.com", new Date(), LocalDate.of(2023, 10, 19), 44L);
        System.out.println("1----------------------");
        manager.persist(p1);
        System.out.println("2----------------------");
        manager.detach(p1);
        System.out.println("3----------------------");
        manager.remove(p1);
        System.out.println("4----------------------");
        manager.getTransaction().commit();
        System.out.println("5----------------------");
        manager.close();
        System.out.println("6----------------------");
        System.out.println(" --- removeDetachedObject() end --- \n");
    }

    private static void removeTransientSurrogateIDObject() {
        System.out.println("\n --- removeTransientSurrogateIDObject() start --- ");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        Person p1 = new Person("Raj", "Kumar", "raj.kumar@gmail.com", new Date(), LocalDate.of(2023, 10, 19), 44L);
        System.out.println("1----------------------");
        manager.remove(p1);
        System.out.println("2----------------------");
        manager.getTransaction().commit();
        System.out.println("3----------------------");
        manager.close();
        System.out.println("4----------------------");
        System.out.println(" --- removeTransientSurrogateIDObject() end --- \n");
    }

    //remove transient object
    private static void removeTransientNaturalIDObject() {
        System.out.println("\n --- removeTransientNaturalIDObject() start --- ");
        manager = factory.createEntityManager();
        System.out.println("1----------------------");
        manager.getTransaction().begin();
        System.out.println("2----------------------");
        Book book1 = new Book("ABC-DEF", "Core Java");
        System.out.println("3----------------------");
        manager.remove(book1);
        System.out.println("4----------------------");
        manager.getTransaction().commit();
        System.out.println("5----------------------");
        manager.close();
        System.out.println("6----------------------");
        System.out.println(" --- removeTransientNaturalIDObject() end --- \n");
    }
}
