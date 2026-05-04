package ghostnetfishing.controller;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.event.FlowEvent;

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

    public GeisternetzMeldenController() {
        geisternetz = new Geisternetz();
        meldendePerson = new Person();

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setLokalisiertAm(new Date());
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public String speichern() {

        geisternetz.setStatus(Status.GEMELDET);
        geisternetz.setLokalisiertAm(new Date());

        if (anonymMelden) {
            geisternetz.setMeldendePerson(null);
        } else {
            geisternetz.setMeldendePerson(meldendePerson);
        }

        /*
         * Später mit Service/Repository:
         *
         * geisternetzService.meldeGeisternetz(geisternetz);
         *
         * oder:
         *
         * geisternetzService.meldeGeisternetz(
         *     geisternetz,
         *     meldendePerson,
         *     anonymMelden
         * );
         */

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Geisternetz gemeldet",
                        "Das Geisternetz wurde erfolgreich im System erfasst."
                )
        );

        // Für Testphase auf der Seite bleiben:
        return null;

        /*
         * Später alternativ:
         * return "index?faces-redirect=true";
         */
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
		}
    }
    
    public String getEingabe() {
        return eingabe;
    }

    public void setEingabe(String eingabe) {
        this.eingabe = eingabe;
    }
}
