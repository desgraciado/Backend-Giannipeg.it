package it.giannipeg.back.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String givenname;

    @NotNull
    private String surname;
    

    public Person() {
    }
    public Person(@NotNull String givenname, @NotNull String surname) {
        this.givenname = givenname;
        this.surname = surname;
    }

    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
