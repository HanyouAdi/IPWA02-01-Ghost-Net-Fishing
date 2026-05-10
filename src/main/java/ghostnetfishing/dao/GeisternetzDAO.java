package ghostnetfishing.dao;

import java.util.List;

import ghostnetfishing.model.Geisternetz;
import ghostnetfishing.model.Status; // Import der Status-Klasse, für User Story 2, abfrage nach Status der Geisternetze
import jakarta.persistence.*;

public class GeisternetzDAO {

	 private static final EntityManagerFactory emf =
	            Persistence.createEntityManagerFactory("ghostNetPersistenceUnit"); // Name der Persistence Unit aus persistence.xml
	
	 public void speichern(Geisternetz geisternetz) { // Methode zum Speichern eines Geisternetzes in der Datenbank ##### User Story 1: Als Nutzer möchte ich ein Geisternetz in der Datenbank speichern können, damit ich es später wieder abrufen kann.
	        EntityManager em = emf.createEntityManager(); // EntityManager wird erstellt, um mit der Datenbank zu kommunizieren

	        try { // Transaktion wird gestartet, um die Datenbankoperationen atomar durchzuführen
	            em.getTransaction().begin(); // Transaktion wird gestartet
	            em.persist(geisternetz); // Geisternetz wird in die Datenbank eingefügt
	            em.getTransaction().commit(); // Transaktion wird abgeschlossen und die Änderungen werden in der Datenbank gespeichert
	        } catch (RuntimeException e) { // Fehlerbehandlung: Wenn ein Fehler
	            if (em.getTransaction().isActive()) { 
	                em.getTransaction().rollback();
	            }
	            throw e;
	        } finally {
	            em.close();
	        }
	    }
	 
	 public List<Geisternetz> findeNachStatus(Status status) {
		    EntityManager em = emf.createEntityManager();

		    try {
		        TypedQuery<Geisternetz> query = em.createQuery(
		                "SELECT g FROM Geisternetz g WHERE g.status = :status",
		                Geisternetz.class
		        );

		        query.setParameter("status", status);

		        return query.getResultList();
		    } finally {
		        em.close();
		    }
		}

		public List<Geisternetz> findeNachStatusListe(List<Status> statusListe) {
		    EntityManager em = emf.createEntityManager();

		    try {
		        TypedQuery<Geisternetz> query = em.createQuery(
		                "SELECT g FROM Geisternetz g WHERE g.status IN :statusListe",
		                Geisternetz.class
		        );

		        query.setParameter("statusListe", statusListe);

		        return query.getResultList();
		    } finally {
		        em.close();
		    }
		}
		
		public List<Geisternetz> findeNachStatusUndBergendePersonTelefonnummer( // Methode zum Finden von Geisternetzen basierend auf ihrem Status und der Telefonnummer der bergenden Person, siehe User Story 3: Als Nutzer möchte ich die Bergung eines Geisternetzes übernehmen können, damit ich mich um die Bergung kümmern kann.
		        Status status,
		        String telefonnummer
		) {
		    EntityManager em = emf.createEntityManager();						//Nur GN werden geladen, die den angegebenen Status "BERGUNG_BEVORSTEHEND" haben und deren bergende Person die angegebene Telefonnummer beistzt.

		    try {
		        TypedQuery<Geisternetz> query = em.createQuery(
		                "SELECT g FROM Geisternetz g " +
		                "WHERE g.status = :status " +
		                "AND g.bergendePerson.telefonnummer = :telefonnummer",
		                Geisternetz.class
		        );

		        query.setParameter("status", status);
		        query.setParameter("telefonnummer", telefonnummer);

		        return query.getResultList();
		    } finally {
		        em.close();
		    }
		}

	    public Geisternetz aktualisieren(Geisternetz geisternetz) {
	        EntityManager em = emf.createEntityManager();

	        try {
	            em.getTransaction().begin();
	            Geisternetz aktualisiertesGeisternetz = em.merge(geisternetz);
	            em.getTransaction().commit();

	            return aktualisiertesGeisternetz;
	        } catch (RuntimeException e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            throw e;
	        } finally {
	            em.close();
	        }
	    }
	    
// Methode zum Finden eines aktiven Geisternetzes basierend auf seinen Koordinaten, siehe User Story 4: 
// Als Nutzer möchte ich ein aktives Geisternetz anhand seiner Koordinaten finden können, damit ich es leichter lokalisieren kann.
	    public Geisternetz findeAktivesGeisternetzNachKoordinaten(Double lat, Double lon) {
	        EntityManager em = emf.createEntityManager();

	        try {
	            TypedQuery<Geisternetz> query = em.createQuery(
	                    "SELECT g FROM Geisternetz g " +
	                    "WHERE g.lat = :lat " +
	                    "AND g.lon = :lon " +
	                    "AND g.status IN :statusListe",
	                    Geisternetz.class
	            );

	            query.setParameter("lat", lat);
	            query.setParameter("lon", lon);
	            query.setParameter(
	                    "statusListe",
	                    List.of(Status.GEMELDET, Status.BERGUNG_BEVORSTEHEND)
	            );

	            query.setMaxResults(1);

	            List<Geisternetz> treffer = query.getResultList();

	            if (treffer.isEmpty()) {
	                return null;
	            }

	            return treffer.get(0);

	        } finally {
	            em.close();
	        }
	    }
}
