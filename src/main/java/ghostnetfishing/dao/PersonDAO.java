package ghostnetfishing.dao;

import java.util.List;

import ghostnetfishing.model.Person;
import jakarta.persistence.*;

public class PersonDAO {
	
	 private static final EntityManagerFactory emf =
	            Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");

	    public Person findeNachTelefonnummer(String telefonnummer) {
	        EntityManager em = emf.createEntityManager();
	    
	        try {
	        	String bereinigteTelefonnummer = bereinigeTelefonnummer(telefonnummer); // Bereinigung der Telefonnummer, um sicherzustellen, dass sie im richtigen Format vorliegt
	        	
	            TypedQuery<Person> query = em.createQuery(
	                    "SELECT p FROM Person p WHERE p.telefonnummer = :telefonnummer",
	                    Person.class
	            );

	            query.setParameter("telefonnummer", bereinigteTelefonnummer); // Setzen der bereinigten Telefonnummer als Parameter für die Abfrage
	            query.setMaxResults(1); // Sicherstellen, dass nur eine Person zurückgegeben wird
	            
	            List<Person> personen = query.getResultList();
				if (personen.isEmpty()) {
					return null; // Keine Person gefunden
				} else {
					return personen.get(0); // Erste gefundene Person zurückgeben
				}

	        } finally {
	            em.close();
	        }
	    }
	    
	    public Person speichern(Person person) {
	        EntityManager em = emf.createEntityManager();

	        try {
	            em.getTransaction().begin();

	            person.setTelefonnummer(
	                    bereinigeTelefonnummer(person.getTelefonnummer())
	            );

	            em.persist(person);
	            em.getTransaction().commit();

	            return person;

	        } catch (RuntimeException e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            throw e;
	        } finally {
	            em.close();
	        }
	    }
	    
	    public Person findeOderSpeichere(Person person) {
	        if (person == null || person.getTelefonnummer() == null) {
	            return null;
	        }

	        String bereinigteTelefonnummer = bereinigeTelefonnummer(person.getTelefonnummer());
	        person.setTelefonnummer(bereinigteTelefonnummer);

	        Person vorhandenePerson = findeNachTelefonnummer(bereinigteTelefonnummer);

	        if (vorhandenePerson != null) {
	            return vorhandenePerson;
	        }

	        return speichern(person);
	    }

	    private String bereinigeTelefonnummer(String telefonnummer) {
	        if (telefonnummer == null) {
	            return null;
	        }

	        return telefonnummer.trim().replaceAll("[\\s()-]", "");
	    }
	    
}
