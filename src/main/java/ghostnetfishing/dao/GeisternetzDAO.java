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
}
