package ghostnetfishing.dao;

import ghostnetfishing.model.Geisternetz;
import jakarta.persistence.*;

public class GeisternetzDAO {

	 private static final EntityManagerFactory emf =
	            Persistence.createEntityManagerFactory("ghostNetPersistenceUnit"); // Name der Persistence Unit aus persistence.xml
	
	 public void speichern(Geisternetz geisternetz) { // Methode zum Speichern eines Geisternetzes in der Datenbank
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
}
