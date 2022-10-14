package com.jeppu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "person_tbl")         //Table name
@Entity(name = "people")            //Entity name
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "person_firstname", length = 50)
    private String firstName;

    @Column(name = "person_lastname", length = 50)
    private String lastName;

    @Column(name = "person_email", unique = true, nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "person_birthdate")
    private Date birthDate;

    @Column(name = "person_anniversary_date")
    private LocalDate anniversaryDate;

    @Transient
    private Long longTempVar;

    public Person(String firstName, String lastName, String email, Date birthDate, LocalDate anniversaryDate, Long longTempVar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.anniversaryDate = anniversaryDate;
        this.longTempVar = longTempVar;
    }
}
