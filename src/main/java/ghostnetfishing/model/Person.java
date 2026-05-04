package ghostnetfishing.model;

import jakarta.persistence.*;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String telefonnummer;

	public Person() {

	}

	// Konstruktor mit Name / anonyme Person
	public Person(String name) {
		this.name = name;
	}
	// Konstruktor mit Name und Telefonnummer
	public Person(String name, String telefonnummer) {
		this.name = name;
		this.telefonnummer = telefonnummer;
	}
	
	public Long getId() {
	    return id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTelefonnummer() {
		return this.telefonnummer;
	}
	
	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
}
