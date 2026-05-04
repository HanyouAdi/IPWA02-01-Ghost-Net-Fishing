package ghostnetfishing.model;

//@Entity
public class Person {

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private int telefonnummer;

	public Person() {

	}

	// Konstruktor mit Name / anonyme Person
	public Person(String name) {
		this.name = name;
	}
	// Konstruktor mit Name und Telefonnummer
	public Person(String name, int telefonnummer) {
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
	
	public int getTelefonnummer() {
		return this.telefonnummer;
	}
	
	public void setTelefonnummer(int telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
}
