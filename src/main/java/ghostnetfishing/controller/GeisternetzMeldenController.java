package ghostnetfishing.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.primefaces.event.FlowEvent;

import ghostnetfishing.dao.GeisternetzDAO;
import ghostnetfishing.model.Geisternetz;
import ghostnetfishing.model.Person;
import ghostnetfishing.model.Status;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GeisternetzMeldenController implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Geisternetz geisternetz;
    private Person meldendePerson;

    private boolean anonymMelden;
    private String eingabe;
    
    private GeisternetzDAO geisternetzDAO = new GeisternetzDAO();

    public GeisternetzMeldenController() {
        geisternetz = new Geisternetz();
        meldendePerson = new Person();

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setGemeldetAm(LocalDateTime.now());
    }

    public String onFlowProcess(FlowEvent event) { // Methode, die aufgerufen wird, wenn der Benutzer zum nächsten Schritt im Wizard wechselt
        return event.getNewStep(); 
    }

    public String speichern() {

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setGemeldetAm(LocalDateTime.now());

        if (anonymMelden) {
            geisternetz.setMeldendePerson(null);
        } else {
            geisternetz.setMeldendePerson(meldendePerson);
        }
        
        geisternetzDAO.speichern(geisternetz); // Geisternetz wird in der Datenbank gespeichert

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Geisternetz gemeldet",
                        "Das Geisternetz wurde erfolgreich im System gespeichert."
                )
        );

        geisternetz = new Geisternetz(); // Neues Geisternetz-Objekt wird erstellt, um die Eingabefelder zu leeren
        meldendePerson = new Person(); // Neues Person-Objekt wird erstellt, um die Eingabefelder zu leeren
        anonymMelden = false; // Anonyme Meldung zurücksetzen

        geisternetz.setStatus(Status.GEMELDET); // Status wird auf "GEMELDET" gesetzt
        geisternetz.setGemeldetAm(LocalDateTime.now()); // Meldungszeitpunkt wird auf die aktuelle Zeit gesetzt
        
        return null; // Bleibt auf der aktuellen Seite, damit der Benutzer weitere Geisternetze melden kann
    }

    public Geisternetz getGeisternetz() {
        return geisternetz;
    }

    public void setGeisternetz(Geisternetz geisternetz) {
        this.geisternetz = geisternetz;
    }

    public Person getMeldendePerson() {
        return meldendePerson;
    }

    public void setMeldendePerson(Person meldendePerson) {
        this.meldendePerson = meldendePerson;
    }

    public boolean isAnonymMelden() {
        return anonymMelden;
    }

    public void setAnonymMelden(boolean anonymMelden) {
        this.anonymMelden = anonymMelden;
		if (anonymMelden) { // Name auf "Anonym" setzen,
			this.meldendePerson.setName("Anonym");
			this.meldendePerson.setTelefonnummer(null); // Telefonnummer wird auf null gesetzt, da sie bei anonymen Meldungen nicht benötigt wird
		}
    }
    
    public String getEingabe() {
        return eingabe;
    }

    public void setEingabe(String eingabe) {
        this.eingabe = eingabe;
    }
}
