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
public class MergeMainApp {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernatedemo");
    static EntityManager manager = null;

    public static void main(String[] args) {
        persist();
        mergeVspersist();
        //mergeNaturalKeyEntity();
        //findAllManagedObjects();
        //merge();
        //mergeTransientObject();
    }

    private static void findAllManagedObjects() {
        System.out.println("\n--- findAllManagedObjects start ---");
        manager = factory.createEntityManager();
        manager.getMetamodel().getEntities().stream()
                .forEach(entityType -> entityType.getName());
        manager.close();
        System.out.println("--- findAllManagedObjects end ---\n");
    }

    private static void merge() {
        System.out.println("\n--- merge() start ---");
        System.out.println("1-------------------");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        System.out.println("2-------------------");
        Person person1 = manager.find(Person.class, 1L);
        System.out.println("3-------------------");
        manager.detach(person1);
        System.out.println("4-------------------");
        Person person2 = manager.find(Person.class, 1L);
        person1.setLastName("Ram");
        System.out.println("5-------------------");
        //manager.merge(person1);
        person1 = manager.merge(person1);               //merge returns the managed object
        System.out.println("6-------------------");
        person1.setLastName("Ram Jeppu");
        manager.getTransaction().commit();
        System.out.println("person1 : " + person1);
        System.out.println("person2 : " + person2);
        System.out.println("7-------------------");
        manager.close();
        System.out.println("8-------------------");
        System.out.println("--- merge() end ---\n");
    }

    private static void mergeVspersist() {
        System.out.println("\n--- mergeVspersist() start ---");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
        System.out.println("1-------------------");
        Book book1 = new Book("AAA-BBB", "Spring Boot");
        manager.persist(book1);
        System.out.println("2-------------------");
        Book book2 = new Book("AAA-BBB", "Hibernate with Spring");
        //manager.persist(book2);                                                 //EXCEPTION : EntityExistsException
        manager.merge(book2);                                                     //will update title in DB
        System.out.println("3-------------------");
        manager.getTransaction().commit();
        System.out.println("4-------------------");
        manager.close();
        System.out.println("5-------------------");
        System.out.println("--- mergeVspersist() end ---\n");
    }

    //For Natural Key, merge will first query the DB to check if there is an Entity with the ID as this is Natural key (provided by user)
    //So, there are 2 queries - select query fired first and then insert
    private static void mergeNaturalKeyEntity() {
        System.out.println("\n--- mergeNaturalKeyEntity() start ---");
        manager = factory.createEntityManager();
        System.out.println("1-------------------");
        Book b1 = new Book("111-222-333", "JPA with Hibernate");
        manager.getTransaction().begin();
        System.out.println("2-------------------");
        b1 = manager.merge(b1);                         //Hibernate: select book0_.isbn as isbn1_0_0_, book0_.title as title2_0_0_ from Book book0_ where book0_.isbn=?
        System.out.println("3-------------------");
        manager.getTransaction().commit();              //Hibernate: insert into Book
        System.out.println("4-------------------");
        manager.close();
        System.out.println("--- mergeNaturalKeyEntity() end ---\n");
    }

    //When merge is invoked for Transient Object, it will not query DB to check if record exists or not as the ID is null.
    //It will directly ingest record in DB
    private static void mergeTransientObject() {
        System.out.println("\n--- mergeTransientObject() start ---");
        manager = factory.createEntityManager();
        System.out.println("1-------------------");
        Person p1 = new Person("Amar", "Alva", "amar.alva@hotmail.com", new Date(), LocalDate.of(2009, 10, 2), 45L);
        System.out.println("2-------------------");
        manager.getTransaction().begin();
        System.out.println("3-------------------");
        p1 = manager.merge(p1);                                  //Hibernate: insert into person_tbl --- if p1=manager.merge() is not assigned, then p1 will still point to transient instance
        System.out.println("4-------------------");
        p1.setLastName("Shetty");
        System.out.println("5-------------------");
        manager.getTransaction().commit();
        System.out.println("6-------------------");
        manager.close();
        System.out.println("7-------------------");
        System.out.println("--- mergeTransientObject() end ---\n");
    }

    private static void persist() {
        System.out.println("\n--- persist() start ---");
        System.out.println("1-------------------");
        manager = factory.createEntityManager();
        System.out.println("2-------------------");
        Person person1 = new Person("Sujay", "Jeppu", "sujay@gmail.com", new Date(), LocalDate.of(2022, 10, 1), 10L);
        System.out.println("3-------------------");
        manager.getTransaction().begin();
        System.out.println("4-------------------");
        manager.persist(person1);
        System.out.println("5-------------------");
        manager.getTransaction().commit();
        System.out.println("6-------------------");
        manager.close();
        System.out.println("7-------------------");
        System.out.println("--- persist() end ---\n");
    }
}
